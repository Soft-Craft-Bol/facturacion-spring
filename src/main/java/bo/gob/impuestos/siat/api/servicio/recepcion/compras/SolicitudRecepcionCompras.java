
package bo.gob.impuestos.siat.api.servicio.recepcion.compras;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para solicitudRecepcionCompras complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="solicitudRecepcionCompras"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}solicitudCompras"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="archivo" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="cantidadFacturas" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="fechaEnvio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="gestion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="hashArchivo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="periodo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudRecepcionCompras", propOrder = {
    "archivo",
    "cantidadFacturas",
    "fechaEnvio",
    "gestion",
    "hashArchivo",
    "periodo"
})
public class SolicitudRecepcionCompras
    extends SolicitudCompras
{

    @XmlElement(required = true)
    protected byte[] archivo;
    protected int cantidadFacturas;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaEnvio;
    protected int gestion;
    @XmlElement(required = true)
    protected String hashArchivo;
    protected int periodo;

    /**
     * Obtiene el valor de la propiedad archivo.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getArchivo() {
        return archivo;
    }

    /**
     * Define el valor de la propiedad archivo.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setArchivo(byte[] value) {
        this.archivo = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidadFacturas.
     * 
     */
    public int getCantidadFacturas() {
        return cantidadFacturas;
    }

    /**
     * Define el valor de la propiedad cantidadFacturas.
     * 
     */
    public void setCantidadFacturas(int value) {
        this.cantidadFacturas = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaEnvio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaEnvio() {
        return fechaEnvio;
    }

    /**
     * Define el valor de la propiedad fechaEnvio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaEnvio(XMLGregorianCalendar value) {
        this.fechaEnvio = value;
    }

    /**
     * Obtiene el valor de la propiedad gestion.
     * 
     */
    public int getGestion() {
        return gestion;
    }

    /**
     * Define el valor de la propiedad gestion.
     * 
     */
    public void setGestion(int value) {
        this.gestion = value;
    }

    /**
     * Obtiene el valor de la propiedad hashArchivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashArchivo() {
        return hashArchivo;
    }

    /**
     * Define el valor de la propiedad hashArchivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashArchivo(String value) {
        this.hashArchivo = value;
    }

    /**
     * Obtiene el valor de la propiedad periodo.
     * 
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * Define el valor de la propiedad periodo.
     * 
     */
    public void setPeriodo(int value) {
        this.periodo = value;
    }

}
