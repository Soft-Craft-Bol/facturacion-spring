
package bo.gob.impuestos.siat.api.facturacion.operaciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para modelDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="modelDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://siat.impuestos.gob.bo/}model"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modelDto")
@XmlSeeAlso({
    RespuestaComunicacion.class,
    MensajeServicio.class,
    RespuestaRegistroPuntoVenta.class,
    RespuestaPuntoVentaComisionista.class,
    RespuestaCierreSistemas.class,
    RespuestaListaEventos.class,
    RespuestaConsultaPuntoVenta.class,
    RespuestaCierrePuntoVenta.class
})
public abstract class ModelDto
    extends Model
{


}
