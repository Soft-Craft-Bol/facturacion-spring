
package bo.gob.impuestos.siat.api.servicio.facturacion.documento.ajuste;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para solicitudReversionAnulacion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="solicitudReversionAnulacion"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}solicitudRecepcion"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cuf" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudReversionAnulacion", propOrder = {
    "cuf"
})
public class SolicitudReversionAnulacion
    extends SolicitudRecepcion
{

    @XmlElement(required = true)
    protected String cuf;

    /**
     * Obtiene el valor de la propiedad cuf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuf() {
        return cuf;
    }

    /**
     * Define el valor de la propiedad cuf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuf(String value) {
        this.cuf = value;
    }

}
