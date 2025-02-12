
package bo.gob.impuestos.siat.api.servicio.recepcion.compras;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para confirmacionCompras complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="confirmacionCompras"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudConfirmacionCompras" type="{https://siat.impuestos.gob.bo/}solicitudRecepcionCompras"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "confirmacionCompras", propOrder = {
    "solicitudConfirmacionCompras"
})
public class ConfirmacionCompras {

    @XmlElement(name = "SolicitudConfirmacionCompras", required = true)
    protected SolicitudRecepcionCompras solicitudConfirmacionCompras;

    /**
     * Obtiene el valor de la propiedad solicitudConfirmacionCompras.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudRecepcionCompras }
     *     
     */
    public SolicitudRecepcionCompras getSolicitudConfirmacionCompras() {
        return solicitudConfirmacionCompras;
    }

    /**
     * Define el valor de la propiedad solicitudConfirmacionCompras.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudRecepcionCompras }
     *     
     */
    public void setSolicitudConfirmacionCompras(SolicitudRecepcionCompras value) {
        this.solicitudConfirmacionCompras = value;
    }

}
