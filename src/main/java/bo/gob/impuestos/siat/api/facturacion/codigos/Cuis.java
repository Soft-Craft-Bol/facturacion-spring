
package bo.gob.impuestos.siat.api.facturacion.codigos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cuis complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cuis"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudCuis" type="{https://siat.impuestos.gob.bo/}solicitudCuis"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cuis", propOrder = {
    "solicitudCuis"
})
public class Cuis {

    @XmlElement(name = "SolicitudCuis", required = true)
    protected SolicitudCuis solicitudCuis;

    /**
     * Obtiene el valor de la propiedad solicitudCuis.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudCuis }
     *     
     */
    public SolicitudCuis getSolicitudCuis() {
        return solicitudCuis;
    }

    /**
     * Define el valor de la propiedad solicitudCuis.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudCuis }
     *     
     */
    public void setSolicitudCuis(SolicitudCuis value) {
        this.solicitudCuis = value;
    }

}
