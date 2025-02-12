
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consultaEventoSignificativo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consultaEventoSignificativo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudConsultaEvento" type="{https://siat.impuestos.gob.bo/}solicitudConsultaEvento"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaEventoSignificativo", propOrder = {
    "solicitudConsultaEvento"
})
public class ConsultaEventoSignificativo {

    @XmlElement(name = "SolicitudConsultaEvento", required = true)
    protected SolicitudConsultaEvento solicitudConsultaEvento;

    /**
     * Obtiene el valor de la propiedad solicitudConsultaEvento.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudConsultaEvento }
     *     
     */
    public SolicitudConsultaEvento getSolicitudConsultaEvento() {
        return solicitudConsultaEvento;
    }

    /**
     * Define el valor de la propiedad solicitudConsultaEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudConsultaEvento }
     *     
     */
    public void setSolicitudConsultaEvento(SolicitudConsultaEvento value) {
        this.solicitudConsultaEvento = value;
    }

}
