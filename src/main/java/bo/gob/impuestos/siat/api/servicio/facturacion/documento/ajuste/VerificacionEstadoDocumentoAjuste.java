
package bo.gob.impuestos.siat.api.servicio.facturacion.documento.ajuste;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para verificacionEstadoDocumentoAjuste complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="verificacionEstadoDocumentoAjuste"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioVerificacionEstadoDocumentoAjuste" type="{https://siat.impuestos.gob.bo/}solicitudVerificacionEstado"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificacionEstadoDocumentoAjuste", propOrder = {
    "solicitudServicioVerificacionEstadoDocumentoAjuste"
})
public class VerificacionEstadoDocumentoAjuste {

    @XmlElement(name = "SolicitudServicioVerificacionEstadoDocumentoAjuste", required = true)
    protected SolicitudVerificacionEstado solicitudServicioVerificacionEstadoDocumentoAjuste;

    /**
     * Obtiene el valor de la propiedad solicitudServicioVerificacionEstadoDocumentoAjuste.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudVerificacionEstado }
     *     
     */
    public SolicitudVerificacionEstado getSolicitudServicioVerificacionEstadoDocumentoAjuste() {
        return solicitudServicioVerificacionEstadoDocumentoAjuste;
    }

    /**
     * Define el valor de la propiedad solicitudServicioVerificacionEstadoDocumentoAjuste.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudVerificacionEstado }
     *     
     */
    public void setSolicitudServicioVerificacionEstadoDocumentoAjuste(SolicitudVerificacionEstado value) {
        this.solicitudServicioVerificacionEstadoDocumentoAjuste = value;
    }

}
