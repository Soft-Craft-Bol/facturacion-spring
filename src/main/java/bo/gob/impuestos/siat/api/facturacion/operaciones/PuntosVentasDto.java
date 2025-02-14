
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para puntosVentasDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="puntosVentasDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigoPuntoVenta" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="nombrePuntoVenta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipoPuntoVenta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "puntosVentasDto", propOrder = {
    "codigoPuntoVenta",
    "nombrePuntoVenta",
    "tipoPuntoVenta"
})
public class PuntosVentasDto {

    protected Integer codigoPuntoVenta;
    protected String nombrePuntoVenta;
    protected String tipoPuntoVenta;

    /**
     * Obtiene el valor de la propiedad codigoPuntoVenta.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodigoPuntoVenta() {
        return codigoPuntoVenta;
    }

    /**
     * Define el valor de la propiedad codigoPuntoVenta.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodigoPuntoVenta(Integer value) {
        this.codigoPuntoVenta = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrePuntoVenta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombrePuntoVenta() {
        return nombrePuntoVenta;
    }

    /**
     * Define el valor de la propiedad nombrePuntoVenta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombrePuntoVenta(String value) {
        this.nombrePuntoVenta = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoPuntoVenta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPuntoVenta() {
        return tipoPuntoVenta;
    }

    /**
     * Define el valor de la propiedad tipoPuntoVenta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPuntoVenta(String value) {
        this.tipoPuntoVenta = value;
    }

}
