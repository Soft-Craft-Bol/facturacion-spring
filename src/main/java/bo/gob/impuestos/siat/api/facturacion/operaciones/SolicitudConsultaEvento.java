
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para solicitudConsultaEvento complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="solicitudConsultaEvento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigoAmbiente" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="codigoPuntoVenta" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codigoSistema" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codigoSucursal" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="cufd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cuis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fechaEvento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="nit" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudConsultaEvento", propOrder = {
    "codigoAmbiente",
    "codigoPuntoVenta",
    "codigoSistema",
    "codigoSucursal",
    "cufd",
    "cuis",
    "fechaEvento",
    "nit"
})
public class SolicitudConsultaEvento {

    protected int codigoAmbiente;
    @XmlElementRef(name = "codigoPuntoVenta", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> codigoPuntoVenta;
    @XmlElement(required = true)
    protected String codigoSistema;
    protected int codigoSucursal;
    @XmlElement(required = true)
    protected String cufd;
    @XmlElement(required = true)
    protected String cuis;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaEvento;
    protected long nit;

    /**
     * Obtiene el valor de la propiedad codigoAmbiente.
     * 
     */
    public int getCodigoAmbiente() {
        return codigoAmbiente;
    }

    /**
     * Define el valor de la propiedad codigoAmbiente.
     * 
     */
    public void setCodigoAmbiente(int value) {
        this.codigoAmbiente = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoPuntoVenta.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCodigoPuntoVenta() {
        return codigoPuntoVenta;
    }

    /**
     * Define el valor de la propiedad codigoPuntoVenta.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCodigoPuntoVenta(JAXBElement<Integer> value) {
        this.codigoPuntoVenta = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoSistema.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoSistema() {
        return codigoSistema;
    }

    /**
     * Define el valor de la propiedad codigoSistema.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoSistema(String value) {
        this.codigoSistema = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoSucursal.
     * 
     */
    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * Define el valor de la propiedad codigoSucursal.
     * 
     */
    public void setCodigoSucursal(int value) {
        this.codigoSucursal = value;
    }

    /**
     * Obtiene el valor de la propiedad cufd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCufd() {
        return cufd;
    }

    /**
     * Define el valor de la propiedad cufd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCufd(String value) {
        this.cufd = value;
    }

    /**
     * Obtiene el valor de la propiedad cuis.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuis() {
        return cuis;
    }

    /**
     * Define el valor de la propiedad cuis.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuis(String value) {
        this.cuis = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaEvento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaEvento() {
        return fechaEvento;
    }

    /**
     * Define el valor de la propiedad fechaEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaEvento(XMLGregorianCalendar value) {
        this.fechaEvento = value;
    }

    /**
     * Obtiene el valor de la propiedad nit.
     * 
     */
    public long getNit() {
        return nit;
    }

    /**
     * Define el valor de la propiedad nit.
     * 
     */
    public void setNit(long value) {
        this.nit = value;
    }

}
