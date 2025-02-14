
package bo.gob.impuestos.siat.api.servicio.facturacion.documento.ajuste;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anulacionDocumentoAjuste complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="anulacionDocumentoAjuste"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioAnulacionDocumentoAjuste" type="{https://siat.impuestos.gob.bo/}solicitudAnulacion"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anulacionDocumentoAjuste", propOrder = {
    "solicitudServicioAnulacionDocumentoAjuste"
})
public class AnulacionDocumentoAjuste {

    @XmlElement(name = "SolicitudServicioAnulacionDocumentoAjuste", required = true)
    protected SolicitudAnulacion solicitudServicioAnulacionDocumentoAjuste;

    /**
     * Obtiene el valor de la propiedad solicitudServicioAnulacionDocumentoAjuste.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudAnulacion }
     *     
     */
    public SolicitudAnulacion getSolicitudServicioAnulacionDocumentoAjuste() {
        return solicitudServicioAnulacionDocumentoAjuste;
    }

    /**
     * Define el valor de la propiedad solicitudServicioAnulacionDocumentoAjuste.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudAnulacion }
     *     
     */
    public void setSolicitudServicioAnulacionDocumentoAjuste(SolicitudAnulacion value) {
        this.solicitudServicioAnulacionDocumentoAjuste = value;
    }

}
