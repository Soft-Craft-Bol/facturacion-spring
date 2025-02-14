
package bo.gob.impuestos.siat.api.facturacion.codigos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cuisResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cuisResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaCuis" type="{https://siat.impuestos.gob.bo/}respuestaCuis" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cuisResponse", propOrder = {
    "respuestaCuis"
})
public class CuisResponse {

    @XmlElement(name = "RespuestaCuis")
    protected RespuestaCuis respuestaCuis;

    /**
     * Obtiene el valor de la propiedad respuestaCuis.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaCuis }
     *     
     */
    public RespuestaCuis getRespuestaCuis() {
        return respuestaCuis;
    }

    /**
     * Define el valor de la propiedad respuestaCuis.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaCuis }
     *     
     */
    public void setRespuestaCuis(RespuestaCuis value) {
        this.respuestaCuis = value;
    }

}
