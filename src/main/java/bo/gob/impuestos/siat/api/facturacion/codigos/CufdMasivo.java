
package bo.gob.impuestos.siat.api.facturacion.codigos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cufdMasivo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cufdMasivo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudCufdMasivo" type="{https://siat.impuestos.gob.bo/}solicitudCufdMasivo"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cufdMasivo", propOrder = {
    "solicitudCufdMasivo"
})
public class CufdMasivo {

    @XmlElement(name = "SolicitudCufdMasivo", required = true)
    protected SolicitudCufdMasivo solicitudCufdMasivo;

    /**
     * Obtiene el valor de la propiedad solicitudCufdMasivo.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudCufdMasivo }
     *     
     */
    public SolicitudCufdMasivo getSolicitudCufdMasivo() {
        return solicitudCufdMasivo;
    }

    /**
     * Define el valor de la propiedad solicitudCufdMasivo.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudCufdMasivo }
     *     
     */
    public void setSolicitudCufdMasivo(SolicitudCufdMasivo value) {
        this.solicitudCufdMasivo = value;
    }

}
