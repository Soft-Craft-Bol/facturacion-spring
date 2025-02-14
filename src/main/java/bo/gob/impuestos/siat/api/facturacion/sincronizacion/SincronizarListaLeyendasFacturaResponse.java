
package bo.gob.impuestos.siat.api.facturacion.sincronizacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para sincronizarListaLeyendasFacturaResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="sincronizarListaLeyendasFacturaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaListaParametricasLeyendas" type="{https://siat.impuestos.gob.bo/}respuestaListaParametricasLeyendas" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sincronizarListaLeyendasFacturaResponse", propOrder = {
    "respuestaListaParametricasLeyendas"
})
public class SincronizarListaLeyendasFacturaResponse {

    @XmlElement(name = "RespuestaListaParametricasLeyendas")
    protected RespuestaListaParametricasLeyendas respuestaListaParametricasLeyendas;

    /**
     * Obtiene el valor de la propiedad respuestaListaParametricasLeyendas.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaListaParametricasLeyendas }
     *     
     */
    public RespuestaListaParametricasLeyendas getRespuestaListaParametricasLeyendas() {
        return respuestaListaParametricasLeyendas;
    }

    /**
     * Define el valor de la propiedad respuestaListaParametricasLeyendas.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaListaParametricasLeyendas }
     *     
     */
    public void setRespuestaListaParametricasLeyendas(RespuestaListaParametricasLeyendas value) {
        this.respuestaListaParametricasLeyendas = value;
    }

}
