
package bo.gob.impuestos.siat.api.facturacion.codigos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para solicitudListaCufdDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="solicitudListaCufdDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigoPuntoVenta" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codigoSucursal" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="cuis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudListaCufdDto", propOrder = {
    "codigoPuntoVenta",
    "codigoSucursal",
    "cuis"
})
public class SolicitudListaCufdDto {

    protected Integer codigoPuntoVenta;
    protected int codigoSucursal;
    @XmlElement(required = true)
    protected String cuis;

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

}
