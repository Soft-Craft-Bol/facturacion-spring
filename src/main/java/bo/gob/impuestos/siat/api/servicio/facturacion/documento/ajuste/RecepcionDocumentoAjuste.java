
package bo.gob.impuestos.siat.api.servicio.facturacion.documento.ajuste;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para recepcionDocumentoAjuste complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="recepcionDocumentoAjuste"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudServicioRecepcionDocumentoAjuste" type="{https://siat.impuestos.gob.bo/}solicitudRecepcionFactura"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recepcionDocumentoAjuste", propOrder = {
    "solicitudServicioRecepcionDocumentoAjuste"
})
public class RecepcionDocumentoAjuste {

    @XmlElement(name = "SolicitudServicioRecepcionDocumentoAjuste", required = true)
    protected SolicitudRecepcionFactura solicitudServicioRecepcionDocumentoAjuste;

    /**
     * Obtiene el valor de la propiedad solicitudServicioRecepcionDocumentoAjuste.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudRecepcionFactura }
     *     
     */
    public SolicitudRecepcionFactura getSolicitudServicioRecepcionDocumentoAjuste() {
        return solicitudServicioRecepcionDocumentoAjuste;
    }

    /**
     * Define el valor de la propiedad solicitudServicioRecepcionDocumentoAjuste.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudRecepcionFactura }
     *     
     */
    public void setSolicitudServicioRecepcionDocumentoAjuste(SolicitudRecepcionFactura value) {
        this.solicitudServicioRecepcionDocumentoAjuste = value;
    }

}
