
package bo.gob.impuestos.siat.api.servicio.recepcion.compras;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consultaCompras complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consultaCompras"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudConsultaCompras" type="{https://siat.impuestos.gob.bo/}solicitudConsultaCompras"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaCompras", propOrder = {
    "solicitudConsultaCompras"
})
public class ConsultaCompras {

    @XmlElement(name = "SolicitudConsultaCompras", required = true)
    protected SolicitudConsultaCompras solicitudConsultaCompras;

    /**
     * Obtiene el valor de la propiedad solicitudConsultaCompras.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudConsultaCompras }
     *     
     */
    public SolicitudConsultaCompras getSolicitudConsultaCompras() {
        return solicitudConsultaCompras;
    }

    /**
     * Define el valor de la propiedad solicitudConsultaCompras.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudConsultaCompras }
     *     
     */
    public void setSolicitudConsultaCompras(SolicitudConsultaCompras value) {
        this.solicitudConsultaCompras = value;
    }

}
