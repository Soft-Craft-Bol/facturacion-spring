
package bo.gob.impuestos.siat.api.facturacion.sincronizacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para sincronizarActividadesResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="sincronizarActividadesResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaListaActividades" type="{https://siat.impuestos.gob.bo/}respuestaListaActividades" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sincronizarActividadesResponse", propOrder = {
    "respuestaListaActividades"
})
public class SincronizarActividadesResponse {

    @XmlElement(name = "RespuestaListaActividades")
    protected RespuestaListaActividades respuestaListaActividades;

    /**
     * Obtiene el valor de la propiedad respuestaListaActividades.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaListaActividades }
     *     
     */
    public RespuestaListaActividades getRespuestaListaActividades() {
        return respuestaListaActividades;
    }

    /**
     * Define el valor de la propiedad respuestaListaActividades.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaListaActividades }
     *     
     */
    public void setRespuestaListaActividades(RespuestaListaActividades value) {
        this.respuestaListaActividades = value;
    }

}
