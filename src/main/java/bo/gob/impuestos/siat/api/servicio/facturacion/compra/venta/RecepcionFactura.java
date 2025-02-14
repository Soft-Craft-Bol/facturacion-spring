
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para recepcionFactura complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="recepcionFactura"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioRecepcionFactura" type="{https://siat.impuestos.gob.bo/}solicitudRecepcionFactura"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recepcionFactura", propOrder = {
    "solicitudServicioRecepcionFactura"
})
public class RecepcionFactura {

    @XmlElement(name = "SolicitudServicioRecepcionFactura", required = true)
    protected SolicitudRecepcionFactura solicitudServicioRecepcionFactura;

    /**
     * Obtiene el valor de la propiedad solicitudServicioRecepcionFactura.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudRecepcionFactura }
     *     
     */
    public SolicitudRecepcionFactura getSolicitudServicioRecepcionFactura() {
        return solicitudServicioRecepcionFactura;
    }

    /**
     * Define el valor de la propiedad solicitudServicioRecepcionFactura.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudRecepcionFactura }
     *     
     */
    public void setSolicitudServicioRecepcionFactura(SolicitudRecepcionFactura value) {
        this.solicitudServicioRecepcionFactura = value;
    }

}
