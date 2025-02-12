package bo.gob.impuestos.siat.api.facturacion.sincronizacion;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para respuestaConfiguracion complex type.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaConfiguracion", propOrder = {
        "mensajesList",
        "transaccion"
})
@XmlSeeAlso({
        RespuestaListaParametricas.class,
        RespuestaListaActividades.class,
        RespuestaFechaHora.class,
        RespuestaListaParametricasLeyendas.class,
        RespuestaListaActividadesDocumentoSector.class,
        RespuestaListaProductos.class
})
public class RespuestaConfiguracion extends ModelDto {

    @XmlElement(nillable = true)
    protected List<MensajeServicio> mensajesList;
    protected Boolean transaccion;

    /**
     * Método que devuelve la lista de mensajes en lugar de un array.
     */
    public List<MensajeServicio> getMensajesList() {
        if (mensajesList == null) {
            mensajesList = new ArrayList<>();
        }
        return mensajesList;
    }

    /**
     * Método para establecer la lista de mensajes.
     */
    public void setMensajesList(List<MensajeServicio> mensajesList) {
        this.mensajesList = mensajesList;
    }

    /**
     * Obtiene el valor de la propiedad transaccion.
     */
    public Boolean isTransaccion() {
        return transaccion;
    }

    /**
     * Define el valor de la propiedad transaccion.
     */
    public void setTransaccion(Boolean value) {
        this.transaccion = value;
    }
}
