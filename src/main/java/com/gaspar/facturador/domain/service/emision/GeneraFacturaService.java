package com.gaspar.facturador.domain.service.emision;

import com.gaspar.facturador.application.request.VentaDetalleRequest;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.commons.CodigoModalidadEmisionEnum;
import com.gaspar.facturador.commons.CodigoTipoDocumentoFiscalEnum;
import com.gaspar.facturador.commons.CodigoTipoEmisionEnum;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.*;
import com.gaspar.facturador.domain.helpers.GZIPHelper;
import com.gaspar.facturador.domain.helpers.Utils;
import com.gaspar.facturador.domain.helpers.XmlHelper;
import com.gaspar.facturador.domain.repository.*;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.persistence.view.LeyendaView;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.apache.xmlbeans.XmlObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.GZIPOutputStream;


@Service
public class GeneraFacturaService {

    private final AppConfig appConfig;
    private final PaqueteFacturas paqueteFacturas;
    public final GZIPHelper gzipHelper;

    private final IProductoServicioRepository productoServicioRepository;
    private final IFacturaRepository facturaCabeceraRepository;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ILeyendaRepository leyendaRepository;
    private final IClienteRepository clienteRepository;
    private final IItemRepository itemRepository;

    private final List<byte[]> facturasTemporales = new CopyOnWriteArrayList<>();

    public GeneraFacturaService(
            AppConfig appConfig, PaqueteFacturas paqueteFacturas,
            GZIPHelper gzipHelper,
            IProductoServicioRepository productoServicioRepository,
            IFacturaRepository facturaCabeceraRepository,
            IPuntoVentaRepository puntoVentaRepository,
            IClienteRepository clienteRepository,
            ILeyendaRepository leyendaRepository,
            IItemRepository itemRepository
    ) {
        this.appConfig = appConfig;
        this.paqueteFacturas = paqueteFacturas;
        this.gzipHelper = gzipHelper;
        this.productoServicioRepository = productoServicioRepository;
        this.facturaCabeceraRepository = facturaCabeceraRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.clienteRepository = clienteRepository;
        this.leyendaRepository = leyendaRepository;
        this.itemRepository = itemRepository;
    }

    public FacturaElectronicaCompraVenta llenarDatos(VentaRequest ventaRequest, CufdEntity cufd) throws DatatypeConfigurationException {

        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(ventaRequest.getIdPuntoVenta());
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto de venta no encontrado");

        Optional<ClienteEntity> cliente = clienteRepository.findById(ventaRequest.getIdCliente());
        if (cliente.isEmpty()) throw new ProcessException("Cliente no encontrado");

        Optional<ItemEntity> item = null;
        Optional<ProductoServicioEntity> productoServicio = null;

        DetalleCompraVenta detalleCompraVenta = null;
        List<DetalleCompraVenta> detalleCompraVentaList = new LinkedList<>();

        BigDecimal total = BigDecimal.ZERO;

        for (VentaDetalleRequest ventaDetalleRequest : ventaRequest.getDetalle()) {

            item = this.itemRepository.findById(ventaDetalleRequest.getIdProducto());
            if (item.isEmpty()) throw new ProcessException("Item no encontrado");

            productoServicio = this.productoServicioRepository.findByCodigoProducto(item.get().getCodigoProductoSin().longValue());
            if (productoServicio.isEmpty()) throw new ProcessException("Homologación de item no encontrado");

            detalleCompraVenta = new DetalleCompraVenta.Builder().buildItem(
                item.get(),
                productoServicio.get().getCodigoActividad(),
                ventaDetalleRequest.getCantidad(),
                ventaDetalleRequest.getMontoDescuento()
            ).build();

            detalleCompraVentaList.add(detalleCompraVenta);
            total = total.add(detalleCompraVenta.getSubTotal());
        }

        if (detalleCompraVentaList.isEmpty()) throw new ProcessException("Detalle no puede ser vacío");

        long numeroFactura = this.facturaCabeceraRepository.obtenerNumeroFactura();

        Optional<LeyendaView> leyenda = this.leyendaRepository.obtenerLeyenda(detalleCompraVentaList.get(0).getActividadEconomica());

        CabeceraCompraVenta cabeceraCompraVenta = new CabeceraCompraVenta.Builder()
                .buildEmpresa(puntoVenta.get())
                .buildCliente(cliente.get())
                .buildPago(total)
                .setUsuario(ventaRequest.getUsuario())
                .setNumeroFactura(numeroFactura)
                .setFechaEmision(Utils.localDateTimeToXMLGregorianCalendar(LocalDateTime.now()))
                .setLeyenda(leyenda.get().getLeyenda())
                .setNitClienteExcepcion(ventaRequest.getNitInvalido())
                .setCafc(ventaRequest.getCafc())
                .build();

        FacturaElectronicaCompraVenta facturaElectronicaCompraVenta = new FacturaElectronicaCompraVenta();
        facturaElectronicaCompraVenta.setCabecera(cabeceraCompraVenta);
        facturaElectronicaCompraVenta.setDetalle(detalleCompraVentaList);


        String cuf = this.obtenerCUF(facturaElectronicaCompraVenta, cufd);

        facturaElectronicaCompraVenta.getCabecera().setCufd(cufd.getCodigo());
        facturaElectronicaCompraVenta.getCabecera().setCuf(cuf);

        return facturaElectronicaCompraVenta;
    }

    private String obtenerCUF(FacturaElectronicaCompraVenta facturaElectronicaCompraVenta, CufdEntity cufd) {

        DatosCUF datosFacturaCUF = new DatosCUF();
        datosFacturaCUF.setNitEmisor(facturaElectronicaCompraVenta.getCabecera().getNitEmisor());
        datosFacturaCUF.setFechaHora(Utils.xMLGregorianCalendarToLocalDateTime(facturaElectronicaCompraVenta.getCabecera().getFechaEmision()));
        datosFacturaCUF.setSucursal(facturaElectronicaCompraVenta.getCabecera().getCodigoSucursal());
        datosFacturaCUF.setModalidad(CodigoModalidadEmisionEnum.ELECTRONICA_EN_LINEA.getValue());
        datosFacturaCUF.setTipoEmision(CodigoTipoEmisionEnum.ONLINE.getValue());
        datosFacturaCUF.setTipoFacturaDocumentoAjuste(CodigoTipoDocumentoFiscalEnum.FACTURA_CON_DERECHO_CREDITO_FISCAL.getValue());
        datosFacturaCUF.setTipoDocumentoSector(facturaElectronicaCompraVenta.getCabecera().getCodigoDocumentoSector());
        datosFacturaCUF.setNumeroFactura(facturaElectronicaCompraVenta.getCabecera().getNumeroFactura());
        datosFacturaCUF.setPuntoVenta(facturaElectronicaCompraVenta.getCabecera().getCodigoPuntoVenta());

        return Utils.obtenerCUF(datosFacturaCUF, cufd.getCodigoControl());
    }

    public byte[] obtenerArchivo(FacturaElectronicaCompraVenta factura, boolean guardarArchivos) throws Exception {
        // 1. Generar XML
        byte[] xml = this.getXmlBytes(factura);
        // 2. Firmar XML
        byte[] xmlFirmado = this.firmarArchivo(xml);
        // 3. Validar XML
        XmlObject xmlFacturaObj = XmlObject.Factory.parse(new String(xmlFirmado));
        this.validarContraXSD(xmlFacturaObj);
        // 4. Comprimir XML
        byte[] xmlComprimidoZip = this.comprimirXMLFactura(xmlFirmado, factura.getCabecera().getCuf(), guardarArchivos);

        return xmlComprimidoZip;
    }


    public void imprimirXmlSinFirmar(FacturaElectronicaCompraVenta factura) throws JAXBException {
        byte[] xmlBytes = this.getXmlBytes(factura);
        String xmlString = new String(xmlBytes);
    }

    public void imprimirXmlFirmado(FacturaElectronicaCompraVenta factura) throws Exception {
        byte[] xmlFirmado = this.firmarArchivo(this.getXmlBytes(factura));
        String xmlStringFirmado = new String(xmlFirmado);
    }


    public byte[] getXmlBytes(FacturaElectronicaCompraVenta facturaObject) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(FacturaElectronicaCompraVenta.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(facturaObject, writer);
        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] firmarArchivo(byte[] xmlBytes) throws Exception {
        PrivateKey privateKey = XmlHelper.getPrivateKey(appConfig.getPathFiles() + "/resources/cert/clav.pem"); //privateKey.pem
        X509Certificate cert =  XmlHelper.getX509Certificate(appConfig.getPathFiles() + "/resources/cert/cer.pem"); //ende.crt

        return XmlHelper.firmarDsig(xmlBytes, privateKey, cert);
    }

    private void validarContraXSD(XmlObject xmlFacturaObj) {
        String schemaFile = appConfig.getPathFiles() + "/resources/xsd/facturaElectronicaCompraVenta.xsd";
        if(!XmlHelper.validate(schemaFile, xmlFacturaObj)) {
            throw new ProcessException("El XML no cumple las especificaciones de impuestos.");
        }
    }

    private byte[] comprimirXMLFactura(byte[] xmlFacturaFirmada, String cuf, boolean guardarArchivos) throws Exception {
        if (guardarArchivos) {
            File tempFacturaXml = this.escribirArchivo(xmlFacturaFirmada, cuf);
            File archivoComprimido = this.obtenerArchivoComprimido(tempFacturaXml);
            byte[] comprimidoByte = this.compresionArchivo(archivoComprimido);
            return comprimidoByte;
        } else {
            // Comprimir en memoria sin guardar archivos
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                gzipOutputStream.write(xmlFacturaFirmada);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    public void limpiarFacturasTemporales() {
        this.facturasTemporales.clear();
    }

    private File escribirArchivo(byte[] xmlFacturaFirmada, String cuf) throws Exception {
        String contextPath = appConfig.getPathFiles();
        String fullPath = contextPath + "/facturas/paquetes/" + cuf + ".xml";

        File tempFacturaXml = new File(fullPath);

        Document sourceDoc = XmlHelper.leerXML(xmlFacturaFirmada);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(tempFacturaXml.getPath()));
        Source input = new DOMSource(sourceDoc);
        transformer.transform(input, output);

        return tempFacturaXml;
    }

    private File obtenerArchivoComprimido(File tempFacturaXml) {
        if (!gzipHelper.compress(tempFacturaXml)) {
            throw new ProcessException("No se pudo comprimir la factura");
        }

        File archivoComprimido = new File(tempFacturaXml.getPath() + ".zip");
        return archivoComprimido;
    }

    private byte[] compresionArchivo(File archivoComprimido) throws Exception {
        byte[] comprimidoByte = Files.readAllBytes(archivoComprimido.toPath());
        return comprimidoByte;
    }

}
