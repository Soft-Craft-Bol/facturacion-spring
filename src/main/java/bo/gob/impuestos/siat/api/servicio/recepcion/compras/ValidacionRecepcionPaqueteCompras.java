
package bo.gob.impuestos.siat.api.servicio.recepcion.compras;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para validacionRecepcionPaqueteCompras complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="validacionRecepcionPaqueteCompras"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudValidacionRecepcionCompras" type="{https://siat.impuestos.gob.bo/}solicitudValidacionRecepcionCompras"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validacionRecepcionPaqueteCompras", propOrder = {
    "solicitudValidacionRecepcionCompras"
})
public class ValidacionRecepcionPaqueteCompras {

    @XmlElement(name = "SolicitudValidacionRecepcionCompras", required = true)
    protected SolicitudValidacionRecepcionCompras solicitudValidacionRecepcionCompras;

    /**
     * Obtiene el valor de la propiedad solicitudValidacionRecepcionCompras.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudValidacionRecepcionCompras }
     *     
     */
    public SolicitudValidacionRecepcionCompras getSolicitudValidacionRecepcionCompras() {
        return solicitudValidacionRecepcionCompras;
    }

    /**
     * Define el valor de la propiedad solicitudValidacionRecepcionCompras.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudValidacionRecepcionCompras }
     *     
     */
    public void setSolicitudValidacionRecepcionCompras(SolicitudValidacionRecepcionCompras value) {
        this.solicitudValidacionRecepcionCompras = value;
    }

}
