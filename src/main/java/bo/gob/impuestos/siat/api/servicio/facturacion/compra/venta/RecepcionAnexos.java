
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para recepcionAnexos complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="recepcionAnexos"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudRecepcionAnexos" type="{https://siat.impuestos.gob.bo/}solicitudRecepcionAnexos"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recepcionAnexos", propOrder = {
    "solicitudRecepcionAnexos"
})
public class RecepcionAnexos {

    @XmlElement(name = "SolicitudRecepcionAnexos", required = true)
    protected SolicitudRecepcionAnexos solicitudRecepcionAnexos;

    /**
     * Obtiene el valor de la propiedad solicitudRecepcionAnexos.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudRecepcionAnexos }
     *     
     */
    public SolicitudRecepcionAnexos getSolicitudRecepcionAnexos() {
        return solicitudRecepcionAnexos;
    }

    /**
     * Define el valor de la propiedad solicitudRecepcionAnexos.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudRecepcionAnexos }
     *     
     */
    public void setSolicitudRecepcionAnexos(SolicitudRecepcionAnexos value) {
        this.solicitudRecepcionAnexos = value;
    }

}
