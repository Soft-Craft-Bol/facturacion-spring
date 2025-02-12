
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para mensajeRecepcion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="mensajeRecepcion"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}mensajeServicio"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="advertencia" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="numeroArchivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroDetalle" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mensajeRecepcion", propOrder = {
    "advertencia",
    "numeroArchivo",
    "numeroDetalle"
})
public class MensajeRecepcion
    extends MensajeServicio
{

    protected Boolean advertencia;
    protected Integer numeroArchivo;
    protected Integer numeroDetalle;

    /**
     * Obtiene el valor de la propiedad advertencia.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAdvertencia() {
        return advertencia;
    }

    /**
     * Define el valor de la propiedad advertencia.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAdvertencia(Boolean value) {
        this.advertencia = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroArchivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroArchivo() {
        return numeroArchivo;
    }

    /**
     * Define el valor de la propiedad numeroArchivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroArchivo(Integer value) {
        this.numeroArchivo = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroDetalle.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroDetalle() {
        return numeroDetalle;
    }

    /**
     * Define el valor de la propiedad numeroDetalle.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroDetalle(Integer value) {
        this.numeroDetalle = value;
    }

}
