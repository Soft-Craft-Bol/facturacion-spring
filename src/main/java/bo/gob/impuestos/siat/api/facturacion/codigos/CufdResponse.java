
package bo.gob.impuestos.siat.api.facturacion.codigos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cufdResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cufdResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaCufd" type="{https://siat.impuestos.gob.bo/}respuestaCufd" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cufdResponse", propOrder = {
    "respuestaCufd"
})
public class CufdResponse {

    @XmlElement(name = "RespuestaCufd")
    protected RespuestaCufd respuestaCufd;

    /**
     * Obtiene el valor de la propiedad respuestaCufd.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaCufd }
     *     
     */
    public RespuestaCufd getRespuestaCufd() {
        return respuestaCufd;
    }

    /**
     * Define el valor de la propiedad respuestaCufd.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaCufd }
     *     
     */
    public void setRespuestaCufd(RespuestaCufd value) {
        this.respuestaCufd = value;
    }

}
