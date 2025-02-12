
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registroPuntoVentaComisionista complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registroPuntoVentaComisionista"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SolicitudPuntoVentaComisionista" type="{https://siat.impuestos.gob.bo/}solicitudPuntoVentaComisionista"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registroPuntoVentaComisionista", propOrder = {
    "solicitudPuntoVentaComisionista"
})
public class RegistroPuntoVentaComisionista {

    @XmlElement(name = "SolicitudPuntoVentaComisionista", required = true)
    protected SolicitudPuntoVentaComisionista solicitudPuntoVentaComisionista;

    /**
     * Obtiene el valor de la propiedad solicitudPuntoVentaComisionista.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudPuntoVentaComisionista }
     *     
     */
    public SolicitudPuntoVentaComisionista getSolicitudPuntoVentaComisionista() {
        return solicitudPuntoVentaComisionista;
    }

    /**
     * Define el valor de la propiedad solicitudPuntoVentaComisionista.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudPuntoVentaComisionista }
     *     
     */
    public void setSolicitudPuntoVentaComisionista(SolicitudPuntoVentaComisionista value) {
        this.solicitudPuntoVentaComisionista = value;
    }

}
