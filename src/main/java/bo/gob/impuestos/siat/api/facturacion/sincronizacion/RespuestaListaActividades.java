
package bo.gob.impuestos.siat.api.facturacion.sincronizacion;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para respuestaListaActividades complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="respuestaListaActividades"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}respuestaConfiguracion"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="listaActividades" type="{https://siat.impuestos.gob.bo/}actividadesDto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaListaActividades", propOrder = {
    "listaActividades"
})
public class RespuestaListaActividades
    extends RespuestaConfiguracion
{

    @XmlElement(nillable = true)
    protected List<ActividadesDto> listaActividades;

    /**
     * Gets the value of the listaActividades property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the listaActividades property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaActividades().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActividadesDto }
     * 
     * 
     */
    public List<ActividadesDto> getListaActividades() {
        if (listaActividades == null) {
            listaActividades = new ArrayList<ActividadesDto>();
        }
        return this.listaActividades;
    }

}
