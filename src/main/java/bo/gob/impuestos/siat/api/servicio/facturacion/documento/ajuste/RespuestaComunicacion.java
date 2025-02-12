
package bo.gob.impuestos.siat.api.servicio.facturacion.documento.ajuste;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para respuestaComunicacion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="respuestaComunicacion"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}modelDto"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mensajesList" type="{https://siat.impuestos.gob.bo/}mensajeServicio" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="transaccion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaComunicacion", propOrder = {
    "mensajesList",
    "transaccion"
})
public class RespuestaComunicacion
    extends ModelDto
{

    @XmlElement(nillable = true)
    protected List<MensajeServicio> mensajesList;
    protected Boolean transaccion;

    /**
     * Gets the value of the mensajesList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the mensajesList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMensajesList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MensajeServicio }
     * 
     * 
     */
    public List<MensajeServicio> getMensajesList() {
        if (mensajesList == null) {
            mensajesList = new ArrayList<MensajeServicio>();
        }
        return this.mensajesList;
    }

    /**
     * Obtiene el valor de la propiedad transaccion.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTransaccion() {
        return transaccion;
    }

    /**
     * Define el valor de la propiedad transaccion.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTransaccion(Boolean value) {
        this.transaccion = value;
    }

}
