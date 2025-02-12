
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para verificacionEstadoFactura complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="verificacionEstadoFactura"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioVerificacionEstadoFactura" type="{https://siat.impuestos.gob.bo/}solicitudVerificacionEstado"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificacionEstadoFactura", propOrder = {
    "solicitudServicioVerificacionEstadoFactura"
})
public class VerificacionEstadoFactura {

    @XmlElement(name = "SolicitudServicioVerificacionEstadoFactura", required = true)
    protected SolicitudVerificacionEstado solicitudServicioVerificacionEstadoFactura;

    /**
     * Obtiene el valor de la propiedad solicitudServicioVerificacionEstadoFactura.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudVerificacionEstado }
     *     
     */
    public SolicitudVerificacionEstado getSolicitudServicioVerificacionEstadoFactura() {
        return solicitudServicioVerificacionEstadoFactura;
    }

    /**
     * Define el valor de la propiedad solicitudServicioVerificacionEstadoFactura.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudVerificacionEstado }
     *     
     */
    public void setSolicitudServicioVerificacionEstadoFactura(SolicitudVerificacionEstado value) {
        this.solicitudServicioVerificacionEstadoFactura = value;
    }

}
