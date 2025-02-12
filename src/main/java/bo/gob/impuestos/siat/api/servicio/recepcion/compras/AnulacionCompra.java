
package bo.gob.impuestos.siat.api.servicio.recepcion.compras;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anulacionCompra complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="anulacionCompra"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudAnulacionCompra" type="{https://siat.impuestos.gob.bo/}solicitudAnulacionCompra"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anulacionCompra", propOrder = {
    "solicitudAnulacionCompra"
})
public class AnulacionCompra {

    @XmlElement(name = "SolicitudAnulacionCompra", required = true)
    protected SolicitudAnulacionCompra solicitudAnulacionCompra;

    /**
     * Obtiene el valor de la propiedad solicitudAnulacionCompra.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudAnulacionCompra }
     *     
     */
    public SolicitudAnulacionCompra getSolicitudAnulacionCompra() {
        return solicitudAnulacionCompra;
    }

    /**
     * Define el valor de la propiedad solicitudAnulacionCompra.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudAnulacionCompra }
     *     
     */
    public void setSolicitudAnulacionCompra(SolicitudAnulacionCompra value) {
        this.solicitudAnulacionCompra = value;
    }

}
