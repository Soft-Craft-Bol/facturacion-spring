
package bo.gob.impuestos.siat.api.facturacion.codigos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para notificaCertificadoRevocadoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="notificaCertificadoRevocadoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaNotificaRevocado" type="{https://siat.impuestos.gob.bo/}respuestaNotificaRevocado" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificaCertificadoRevocadoResponse", propOrder = {
    "respuestaNotificaRevocado"
})
public class NotificaCertificadoRevocadoResponse {

    @XmlElement(name = "RespuestaNotificaRevocado")
    protected RespuestaNotificaRevocado respuestaNotificaRevocado;

    /**
     * Obtiene el valor de la propiedad respuestaNotificaRevocado.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotificaRevocado }
     *     
     */
    public RespuestaNotificaRevocado getRespuestaNotificaRevocado() {
        return respuestaNotificaRevocado;
    }

    /**
     * Define el valor de la propiedad respuestaNotificaRevocado.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotificaRevocado }
     *     
     */
    public void setRespuestaNotificaRevocado(RespuestaNotificaRevocado value) {
        this.respuestaNotificaRevocado = value;
    }

}
