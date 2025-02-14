
package bo.gob.impuestos.siat.api.servicio.recepcion.compras;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para recepcionPaqueteCompras complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="recepcionPaqueteCompras"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudRecepcionCompras" type="{https://siat.impuestos.gob.bo/}solicitudRecepcionCompras"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recepcionPaqueteCompras", propOrder = {
    "solicitudRecepcionCompras"
})
public class RecepcionPaqueteCompras {

    @XmlElement(name = "SolicitudRecepcionCompras", required = true)
    protected SolicitudRecepcionCompras solicitudRecepcionCompras;

    /**
     * Obtiene el valor de la propiedad solicitudRecepcionCompras.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudRecepcionCompras }
     *     
     */
    public SolicitudRecepcionCompras getSolicitudRecepcionCompras() {
        return solicitudRecepcionCompras;
    }

    /**
     * Define el valor de la propiedad solicitudRecepcionCompras.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudRecepcionCompras }
     *     
     */
    public void setSolicitudRecepcionCompras(SolicitudRecepcionCompras value) {
        this.solicitudRecepcionCompras = value;
    }

}
