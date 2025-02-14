
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cierrePuntoVentaResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cierrePuntoVentaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaCierrePuntoVenta" type="{https://siat.impuestos.gob.bo/}respuestaCierrePuntoVenta" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cierrePuntoVentaResponse", propOrder = {
    "respuestaCierrePuntoVenta"
})
public class CierrePuntoVentaResponse {

    @XmlElement(name = "RespuestaCierrePuntoVenta")
    protected RespuestaCierrePuntoVenta respuestaCierrePuntoVenta;

    /**
     * Obtiene el valor de la propiedad respuestaCierrePuntoVenta.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaCierrePuntoVenta }
     *     
     */
    public RespuestaCierrePuntoVenta getRespuestaCierrePuntoVenta() {
        return respuestaCierrePuntoVenta;
    }

    /**
     * Define el valor de la propiedad respuestaCierrePuntoVenta.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaCierrePuntoVenta }
     *     
     */
    public void setRespuestaCierrePuntoVenta(RespuestaCierrePuntoVenta value) {
        this.respuestaCierrePuntoVenta = value;
    }

}
