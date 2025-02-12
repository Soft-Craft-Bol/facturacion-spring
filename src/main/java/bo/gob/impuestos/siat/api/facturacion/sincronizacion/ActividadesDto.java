
package bo.gob.impuestos.siat.api.facturacion.sincronizacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para actividadesDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="actividadesDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigoCaeb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipoActividad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actividadesDto", propOrder = {
    "codigoCaeb",
    "descripcion",
    "tipoActividad"
})
public class ActividadesDto {

    protected String codigoCaeb;
    protected String descripcion;
    protected String tipoActividad;

    /**
     * Obtiene el valor de la propiedad codigoCaeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoCaeb() {
        return codigoCaeb;
    }

    /**
     * Define el valor de la propiedad codigoCaeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoCaeb(String value) {
        this.codigoCaeb = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoActividad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoActividad() {
        return tipoActividad;
    }

    /**
     * Define el valor de la propiedad tipoActividad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoActividad(String value) {
        this.tipoActividad = value;
    }

}
