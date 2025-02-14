
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registroEventoSignificativo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registroEventoSignificativo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudEventoSignificativo" type="{https://siat.impuestos.gob.bo/}solicitudEventoSignificativo"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registroEventoSignificativo", propOrder = {
    "solicitudEventoSignificativo"
})
public class RegistroEventoSignificativo {

    @XmlElement(name = "SolicitudEventoSignificativo", required = true)
    protected SolicitudEventoSignificativo solicitudEventoSignificativo;

    /**
     * Obtiene el valor de la propiedad solicitudEventoSignificativo.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudEventoSignificativo }
     *     
     */
    public SolicitudEventoSignificativo getSolicitudEventoSignificativo() {
        return solicitudEventoSignificativo;
    }

    /**
     * Define el valor de la propiedad solicitudEventoSignificativo.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudEventoSignificativo }
     *     
     */
    public void setSolicitudEventoSignificativo(SolicitudEventoSignificativo value) {
        this.solicitudEventoSignificativo = value;
    }

}
