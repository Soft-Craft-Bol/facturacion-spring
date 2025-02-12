
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registroPuntoVentaComisionistaResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registroPuntoVentaComisionistaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaPuntoVentaComisionista" type="{https://siat.impuestos.gob.bo/}respuestaPuntoVentaComisionista" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registroPuntoVentaComisionistaResponse", propOrder = {
    "respuestaPuntoVentaComisionista"
})
public class RegistroPuntoVentaComisionistaResponse {

    @XmlElement(name = "RespuestaPuntoVentaComisionista")
    protected RespuestaPuntoVentaComisionista respuestaPuntoVentaComisionista;

    /**
     * Obtiene el valor de la propiedad respuestaPuntoVentaComisionista.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaPuntoVentaComisionista }
     *     
     */
    public RespuestaPuntoVentaComisionista getRespuestaPuntoVentaComisionista() {
        return respuestaPuntoVentaComisionista;
    }

    /**
     * Define el valor de la propiedad respuestaPuntoVentaComisionista.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaPuntoVentaComisionista }
     *     
     */
    public void setRespuestaPuntoVentaComisionista(RespuestaPuntoVentaComisionista value) {
        this.respuestaPuntoVentaComisionista = value;
    }

}
