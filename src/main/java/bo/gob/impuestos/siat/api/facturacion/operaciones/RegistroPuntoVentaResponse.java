
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registroPuntoVentaResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registroPuntoVentaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaRegistroPuntoVenta" type="{https://siat.impuestos.gob.bo/}respuestaRegistroPuntoVenta" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registroPuntoVentaResponse", propOrder = {
    "respuestaRegistroPuntoVenta"
})
public class RegistroPuntoVentaResponse {

    @XmlElement(name = "RespuestaRegistroPuntoVenta")
    protected RespuestaRegistroPuntoVenta respuestaRegistroPuntoVenta;

    /**
     * Obtiene el valor de la propiedad respuestaRegistroPuntoVenta.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaRegistroPuntoVenta }
     *     
     */
    public RespuestaRegistroPuntoVenta getRespuestaRegistroPuntoVenta() {
        return respuestaRegistroPuntoVenta;
    }

    /**
     * Define el valor de la propiedad respuestaRegistroPuntoVenta.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaRegistroPuntoVenta }
     *     
     */
    public void setRespuestaRegistroPuntoVenta(RespuestaRegistroPuntoVenta value) {
        this.respuestaRegistroPuntoVenta = value;
    }

}
