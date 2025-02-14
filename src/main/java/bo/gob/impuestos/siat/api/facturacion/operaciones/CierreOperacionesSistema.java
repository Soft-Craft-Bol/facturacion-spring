
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cierreOperacionesSistema complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cierreOperacionesSistema"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudOperaciones" type="{https://siat.impuestos.gob.bo/}solicitudOperaciones"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cierreOperacionesSistema", propOrder = {
    "solicitudOperaciones"
})
public class CierreOperacionesSistema {

    @XmlElement(name = "SolicitudOperaciones", required = true)
    protected SolicitudOperaciones solicitudOperaciones;

    /**
     * Obtiene el valor de la propiedad solicitudOperaciones.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudOperaciones }
     *     
     */
    public SolicitudOperaciones getSolicitudOperaciones() {
        return solicitudOperaciones;
    }

    /**
     * Define el valor de la propiedad solicitudOperaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudOperaciones }
     *     
     */
    public void setSolicitudOperaciones(SolicitudOperaciones value) {
        this.solicitudOperaciones = value;
    }

}
