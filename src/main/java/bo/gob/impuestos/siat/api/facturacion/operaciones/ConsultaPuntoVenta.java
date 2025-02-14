
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consultaPuntoVenta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consultaPuntoVenta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudConsultaPuntoVenta" type="{https://siat.impuestos.gob.bo/}solicitudConsultaPuntoVenta"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaPuntoVenta", propOrder = {
    "solicitudConsultaPuntoVenta"
})
public class ConsultaPuntoVenta {

    @XmlElement(name = "SolicitudConsultaPuntoVenta", required = true)
    protected SolicitudConsultaPuntoVenta solicitudConsultaPuntoVenta;

    /**
     * Obtiene el valor de la propiedad solicitudConsultaPuntoVenta.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudConsultaPuntoVenta }
     *     
     */
    public SolicitudConsultaPuntoVenta getSolicitudConsultaPuntoVenta() {
        return solicitudConsultaPuntoVenta;
    }

    /**
     * Define el valor de la propiedad solicitudConsultaPuntoVenta.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudConsultaPuntoVenta }
     *     
     */
    public void setSolicitudConsultaPuntoVenta(SolicitudConsultaPuntoVenta value) {
        this.solicitudConsultaPuntoVenta = value;
    }

}
