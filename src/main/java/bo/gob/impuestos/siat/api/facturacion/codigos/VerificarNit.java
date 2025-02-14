
package bo.gob.impuestos.siat.api.facturacion.codigos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para verificarNit complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="verificarNit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudVerificarNit" type="{https://siat.impuestos.gob.bo/}solicitudVerificarNit"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificarNit", propOrder = {
    "solicitudVerificarNit"
})
public class VerificarNit {

    @XmlElement(name = "SolicitudVerificarNit", required = true)
    protected SolicitudVerificarNit solicitudVerificarNit;

    /**
     * Obtiene el valor de la propiedad solicitudVerificarNit.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudVerificarNit }
     *     
     */
    public SolicitudVerificarNit getSolicitudVerificarNit() {
        return solicitudVerificarNit;
    }

    /**
     * Define el valor de la propiedad solicitudVerificarNit.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudVerificarNit }
     *     
     */
    public void setSolicitudVerificarNit(SolicitudVerificarNit value) {
        this.solicitudVerificarNit = value;
    }

}
