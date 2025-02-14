
package bo.gob.impuestos.siat.api.servicio.recepcion.compras;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para solicitudAnulacionCompra complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="solicitudAnulacionCompra"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}solicitudCompras"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codAutorizacion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nitProveedor" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="nroDuiDim" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nroFactura" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudAnulacionCompra", propOrder = {
    "codAutorizacion",
    "nitProveedor",
    "nroDuiDim",
    "nroFactura"
})
public class SolicitudAnulacionCompra
    extends SolicitudCompras
{

    @XmlElement(required = true)
    protected String codAutorizacion;
    protected long nitProveedor;
    @XmlElement(required = true)
    protected String nroDuiDim;
    protected long nroFactura;

    /**
     * Obtiene el valor de la propiedad codAutorizacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    /**
     * Define el valor de la propiedad codAutorizacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAutorizacion(String value) {
        this.codAutorizacion = value;
    }

    /**
     * Obtiene el valor de la propiedad nitProveedor.
     * 
     */
    public long getNitProveedor() {
        return nitProveedor;
    }

    /**
     * Define el valor de la propiedad nitProveedor.
     * 
     */
    public void setNitProveedor(long value) {
        this.nitProveedor = value;
    }

    /**
     * Obtiene el valor de la propiedad nroDuiDim.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroDuiDim() {
        return nroDuiDim;
    }

    /**
     * Define el valor de la propiedad nroDuiDim.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroDuiDim(String value) {
        this.nroDuiDim = value;
    }

    /**
     * Obtiene el valor de la propiedad nroFactura.
     * 
     */
    public long getNroFactura() {
        return nroFactura;
    }

    /**
     * Define el valor de la propiedad nroFactura.
     * 
     */
    public void setNroFactura(long value) {
        this.nroFactura = value;
    }

}
