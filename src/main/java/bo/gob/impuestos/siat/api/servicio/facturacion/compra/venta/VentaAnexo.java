
package bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ventaAnexo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ventaAnexo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}modelDto"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codigoProducto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codigoProductoSin" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="tipoCodigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ventaAnexo", propOrder = {
    "codigo",
    "codigoProducto",
    "codigoProductoSin",
    "tipoCodigo"
})
public class VentaAnexo
    extends ModelDto
{

    @XmlElement(required = true)
    protected String codigo;
    @XmlElement(required = true)
    protected String codigoProducto;
    protected long codigoProductoSin;
    @XmlElement(required = true)
    protected String tipoCodigo;

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoProducto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * Define el valor de la propiedad codigoProducto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoProducto(String value) {
        this.codigoProducto = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoProductoSin.
     * 
     */
    public long getCodigoProductoSin() {
        return codigoProductoSin;
    }

    /**
     * Define el valor de la propiedad codigoProductoSin.
     * 
     */
    public void setCodigoProductoSin(long value) {
        this.codigoProductoSin = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoCodigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCodigo() {
        return tipoCodigo;
    }

    /**
     * Define el valor de la propiedad tipoCodigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCodigo(String value) {
        this.tipoCodigo = value;
    }

}
