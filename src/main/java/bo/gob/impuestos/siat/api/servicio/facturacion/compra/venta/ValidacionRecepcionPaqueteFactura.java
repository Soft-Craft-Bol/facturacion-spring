
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para validacionRecepcionPaqueteFactura complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="validacionRecepcionPaqueteFactura"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioValidacionRecepcionPaquete" type="{https://siat.impuestos.gob.bo/}solicitudValidacionRecepcion"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validacionRecepcionPaqueteFactura", propOrder = {
    "solicitudServicioValidacionRecepcionPaquete"
})
public class ValidacionRecepcionPaqueteFactura {

    @XmlElement(name = "SolicitudServicioValidacionRecepcionPaquete", required = true)
    protected SolicitudValidacionRecepcion solicitudServicioValidacionRecepcionPaquete;

    /**
     * Obtiene el valor de la propiedad solicitudServicioValidacionRecepcionPaquete.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudValidacionRecepcion }
     *     
     */
    public SolicitudValidacionRecepcion getSolicitudServicioValidacionRecepcionPaquete() {
        return solicitudServicioValidacionRecepcionPaquete;
    }

    /**
     * Define el valor de la propiedad solicitudServicioValidacionRecepcionPaquete.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudValidacionRecepcion }
     *     
     */
    public void setSolicitudServicioValidacionRecepcionPaquete(SolicitudValidacionRecepcion value) {
        this.solicitudServicioValidacionRecepcionPaquete = value;
    }

}
