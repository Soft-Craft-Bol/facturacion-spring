
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cierreOperacionesSistemaResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cierreOperacionesSistemaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaCierreSistemas" type="{https://siat.impuestos.gob.bo/}respuestaCierreSistemas" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cierreOperacionesSistemaResponse", propOrder = {
    "respuestaCierreSistemas"
})
public class CierreOperacionesSistemaResponse {

    @XmlElement(name = "RespuestaCierreSistemas")
    protected RespuestaCierreSistemas respuestaCierreSistemas;

    /**
     * Obtiene el valor de la propiedad respuestaCierreSistemas.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaCierreSistemas }
     *     
     */
    public RespuestaCierreSistemas getRespuestaCierreSistemas() {
        return respuestaCierreSistemas;
    }

    /**
     * Define el valor de la propiedad respuestaCierreSistemas.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaCierreSistemas }
     *     
     */
    public void setRespuestaCierreSistemas(RespuestaCierreSistemas value) {
        this.respuestaCierreSistemas = value;
    }

}
