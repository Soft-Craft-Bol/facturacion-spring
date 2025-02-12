
package bo.gob.impuestos.siat.api.facturacion.sincronizacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para sincronizarParametricaTipoPuntoVentaResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="sincronizarParametricaTipoPuntoVentaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaListaParametricas" type="{https://siat.impuestos.gob.bo/}respuestaListaParametricas" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sincronizarParametricaTipoPuntoVentaResponse", propOrder = {
    "respuestaListaParametricas"
})
public class SincronizarParametricaTipoPuntoVentaResponse {

    @XmlElement(name = "RespuestaListaParametricas")
    protected RespuestaListaParametricas respuestaListaParametricas;

    /**
     * Obtiene el valor de la propiedad respuestaListaParametricas.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaListaParametricas }
     *     
     */
    public RespuestaListaParametricas getRespuestaListaParametricas() {
        return respuestaListaParametricas;
    }

    /**
     * Define el valor de la propiedad respuestaListaParametricas.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaListaParametricas }
     *     
     */
    public void setRespuestaListaParametricas(RespuestaListaParametricas value) {
        this.respuestaListaParametricas = value;
    }

}
