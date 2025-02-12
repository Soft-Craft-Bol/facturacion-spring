
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consultaEventoSignificativoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consultaEventoSignificativoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaListaEventos" type="{https://siat.impuestos.gob.bo/}respuestaListaEventos" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaEventoSignificativoResponse", propOrder = {
    "respuestaListaEventos"
})
public class ConsultaEventoSignificativoResponse {

    @XmlElement(name = "RespuestaListaEventos")
    protected RespuestaListaEventos respuestaListaEventos;

    /**
     * Obtiene el valor de la propiedad respuestaListaEventos.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaListaEventos }
     *     
     */
    public RespuestaListaEventos getRespuestaListaEventos() {
        return respuestaListaEventos;
    }

    /**
     * Define el valor de la propiedad respuestaListaEventos.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaListaEventos }
     *     
     */
    public void setRespuestaListaEventos(RespuestaListaEventos value) {
        this.respuestaListaEventos = value;
    }

}
