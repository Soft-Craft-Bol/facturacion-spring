/**
 * ServicioFacturacionSincronizacionSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat.FacturacionSincronizacion;

public class ServicioFacturacionSincronizacionSoapBindingStub extends org.apache.axis.client.Stub implements bo.gob.impuestos.siat.ServicioFacturacionSincronizacion {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[19];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaMotivoAnulacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarActividades");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaActividades"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaActividades.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaActividades"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarFechaHora");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaFechaHora"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaFechaHora.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaFechaHora"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarListaLeyendasFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricasLeyendas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricasLeyendas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricasLeyendas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaTipoHabitacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarListaActividadesDocumentoSector");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaActividadesDocumentoSector"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaActividadesDocumentoSector.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaActividadesDocumentoSector"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaTipoDocumentoIdentidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaUnidadMedida");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaTipoDocumentoSector");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaTiposFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verificarComunicacion");
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaComunicacion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaComunicacion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarListaMensajesServicios");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaTipoMetodoPago");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaEventosSignificativos");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaTipoPuntoVenta");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarListaProductosServicios");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaProductos"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaProductos.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaProductos"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaTipoEmision");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaPaisOrigen");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sincronizarParametricaTipoMoneda");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudSincronizacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion"), bo.gob.impuestos.siat.SolicitudSincronizacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaListaParametricas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaListaParametricas"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

    }

    public ServicioFacturacionSincronizacionSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ServicioFacturacionSincronizacionSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ServicioFacturacionSincronizacionSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "actividadesDocumentoSectorDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.ActividadesDocumentoSectorDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "actividadesDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.ActividadesDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "mensajeServicio");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.MensajeServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "model");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.Model.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "modelDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.ModelDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "parametricaLeyendasDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.ParametricaLeyendasDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "parametricasDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.ParametricasDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "productosDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.ProductosDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaComunicacion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaComunicacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaConfiguracion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaConfiguracion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaFechaHora");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaFechaHora.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaActividades");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaListaActividades.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaActividadesDocumentoSector");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaListaActividadesDocumentoSector.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricas");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaListaParametricas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricasLeyendas");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaListaParametricasLeyendas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaProductos");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaListaProductos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudSincronizacion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudSincronizacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaMotivoAnulacion(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaMotivoAnulacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaActividades sincronizarActividades(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarActividades"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaActividades) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaActividades) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaActividades.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaFechaHora sincronizarFechaHora(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarFechaHora"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaFechaHora) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaFechaHora) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaFechaHora.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricasLeyendas sincronizarListaLeyendasFactura(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarListaLeyendasFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricasLeyendas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricasLeyendas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricasLeyendas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaTipoHabitacion(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaTipoHabitacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaActividadesDocumentoSector sincronizarListaActividadesDocumentoSector(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarListaActividadesDocumentoSector"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaActividadesDocumentoSector) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaActividadesDocumentoSector) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaActividadesDocumentoSector.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaTipoDocumentoIdentidad(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaTipoDocumentoIdentidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaUnidadMedida(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaUnidadMedida"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaTipoDocumentoSector(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaTipoDocumentoSector"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaTiposFactura(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaTiposFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaComunicacion verificarComunicacion() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "verificarComunicacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaComunicacion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaComunicacion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaComunicacion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarListaMensajesServicios(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarListaMensajesServicios"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaTipoMetodoPago(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaTipoMetodoPago"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaEventosSignificativos(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaEventosSignificativos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaTipoPuntoVenta(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaTipoPuntoVenta"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaProductos sincronizarListaProductosServicios(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarListaProductosServicios"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaProductos) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaProductos) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaProductos.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaTipoEmision(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaTipoEmision"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaPaisOrigen(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaPaisOrigen"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaListaParametricas sincronizarParametricaTipoMoneda(bo.gob.impuestos.siat.SolicitudSincronizacion solicitudSincronizacion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "sincronizarParametricaTipoMoneda"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudSincronizacion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaListaParametricas) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaListaParametricas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
