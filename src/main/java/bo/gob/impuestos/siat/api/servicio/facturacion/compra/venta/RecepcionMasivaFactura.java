
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para recepcionMasivaFactura complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="recepcionMasivaFactura"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioRecepcionMasiva" type="{https://siat.impuestos.gob.bo/}solicitudRecepcionMasiva"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recepcionMasivaFactura", propOrder = {
    "solicitudServicioRecepcionMasiva"
})
public class RecepcionMasivaFactura {

    @XmlElement(name = "SolicitudServicioRecepcionMasiva", required = true)
    protected SolicitudRecepcionMasiva solicitudServicioRecepcionMasiva;

    /**
     * Obtiene el valor de la propiedad solicitudServicioRecepcionMasiva.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudRecepcionMasiva }
     *     
     */
    public SolicitudRecepcionMasiva getSolicitudServicioRecepcionMasiva() {
        return solicitudServicioRecepcionMasiva;
    }

    /**
     * Define el valor de la propiedad solicitudServicioRecepcionMasiva.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudRecepcionMasiva }
     *     
     */
    public void setSolicitudServicioRecepcionMasiva(SolicitudRecepcionMasiva value) {
        this.solicitudServicioRecepcionMasiva = value;
    }

}
