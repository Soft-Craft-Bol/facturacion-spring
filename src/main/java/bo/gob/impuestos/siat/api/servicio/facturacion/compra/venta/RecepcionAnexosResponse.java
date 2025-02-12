
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para recepcionAnexosResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="recepcionAnexosResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaRecepcionAnexos" type="{https://siat.impuestos.gob.bo/}respuestaRecepcion" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recepcionAnexosResponse", propOrder = {
    "respuestaRecepcionAnexos"
})
public class RecepcionAnexosResponse {

    @XmlElement(name = "RespuestaRecepcionAnexos")
    protected RespuestaRecepcion respuestaRecepcionAnexos;

    /**
     * Obtiene el valor de la propiedad respuestaRecepcionAnexos.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaRecepcion }
     *     
     */
    public RespuestaRecepcion getRespuestaRecepcionAnexos() {
        return respuestaRecepcionAnexos;
    }

    /**
     * Define el valor de la propiedad respuestaRecepcionAnexos.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaRecepcion }
     *     
     */
    public void setRespuestaRecepcionAnexos(RespuestaRecepcion value) {
        this.respuestaRecepcionAnexos = value;
    }

}
