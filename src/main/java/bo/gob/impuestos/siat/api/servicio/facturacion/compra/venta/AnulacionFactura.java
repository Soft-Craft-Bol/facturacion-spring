
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anulacionFactura complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="anulacionFactura"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioAnulacionFactura" type="{https://siat.impuestos.gob.bo/}solicitudAnulacion"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anulacionFactura", propOrder = {
    "solicitudServicioAnulacionFactura"
})
public class AnulacionFactura {

    @XmlElement(name = "SolicitudServicioAnulacionFactura", required = true)
    protected SolicitudAnulacion solicitudServicioAnulacionFactura;

    /**
     * Obtiene el valor de la propiedad solicitudServicioAnulacionFactura.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudAnulacion }
     *     
     */
    public SolicitudAnulacion getSolicitudServicioAnulacionFactura() {
        return solicitudServicioAnulacionFactura;
    }

    /**
     * Define el valor de la propiedad solicitudServicioAnulacionFactura.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudAnulacion }
     *     
     */
    public void setSolicitudServicioAnulacionFactura(SolicitudAnulacion value) {
        this.solicitudServicioAnulacionFactura = value;
    }

}
