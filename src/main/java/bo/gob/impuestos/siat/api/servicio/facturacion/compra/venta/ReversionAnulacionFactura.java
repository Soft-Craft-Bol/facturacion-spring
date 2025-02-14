
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para reversionAnulacionFactura complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="reversionAnulacionFactura"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioReversionAnulacionFactura" type="{https://siat.impuestos.gob.bo/}solicitudReversionAnulacion"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reversionAnulacionFactura", propOrder = {
    "solicitudServicioReversionAnulacionFactura"
})
public class ReversionAnulacionFactura {

    @XmlElement(name = "SolicitudServicioReversionAnulacionFactura", required = true)
    protected SolicitudReversionAnulacion solicitudServicioReversionAnulacionFactura;

    /**
     * Obtiene el valor de la propiedad solicitudServicioReversionAnulacionFactura.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudReversionAnulacion }
     *     
     */
    public SolicitudReversionAnulacion getSolicitudServicioReversionAnulacionFactura() {
        return solicitudServicioReversionAnulacionFactura;
    }

    /**
     * Define el valor de la propiedad solicitudServicioReversionAnulacionFactura.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudReversionAnulacion }
     *     
     */
    public void setSolicitudServicioReversionAnulacionFactura(SolicitudReversionAnulacion value) {
        this.solicitudServicioReversionAnulacionFactura = value;
    }

}
