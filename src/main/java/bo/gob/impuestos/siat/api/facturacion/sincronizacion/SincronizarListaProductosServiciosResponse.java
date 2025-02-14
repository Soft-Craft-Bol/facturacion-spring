
package bo.gob.impuestos.siat.api.facturacion.sincronizacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para sincronizarListaProductosServiciosResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="sincronizarListaProductosServiciosResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RespuestaListaProductos" type="{https://siat.impuestos.gob.bo/}respuestaListaProductos" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sincronizarListaProductosServiciosResponse", propOrder = {
    "respuestaListaProductos"
})
public class SincronizarListaProductosServiciosResponse {

    @XmlElement(name = "RespuestaListaProductos")
    protected RespuestaListaProductos respuestaListaProductos;

    /**
     * Obtiene el valor de la propiedad respuestaListaProductos.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaListaProductos }
     *     
     */
    public RespuestaListaProductos getRespuestaListaProductos() {
        return respuestaListaProductos;
    }

    /**
     * Define el valor de la propiedad respuestaListaProductos.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaListaProductos }
     *     
     */
    public void setRespuestaListaProductos(RespuestaListaProductos value) {
        this.respuestaListaProductos = value;
    }

}
