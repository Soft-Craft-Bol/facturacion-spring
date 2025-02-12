
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para solicitudValidacionRecepcion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="solicitudValidacionRecepcion"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}solicitudRecepcion"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigoRecepcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudValidacionRecepcion", propOrder = {
    "codigoRecepcion"
})
public class SolicitudValidacionRecepcion
    extends SolicitudRecepcion
{

    @XmlElement(required = true)
    protected String codigoRecepcion;

    /**
     * Obtiene el valor de la propiedad codigoRecepcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoRecepcion() {
        return codigoRecepcion;
    }

    /**
     * Define el valor de la propiedad codigoRecepcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoRecepcion(String value) {
        this.codigoRecepcion = value;
    }

}
