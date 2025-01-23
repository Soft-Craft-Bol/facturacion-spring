/**
 * ServicioFacturacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public interface ServicioFacturacion extends java.rmi.Remote {
    public bo.gob.impuestos.siat.RespuestaRecepcion recepcionPaqueteFactura(bo.gob.impuestos.siat.SolicitudRecepcionPaquete solicitudServicioRecepcionPaquete) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaComunicacion verificarComunicacion() throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaRecepcion recepcionFactura(bo.gob.impuestos.siat.SolicitudRecepcionFactura solicitudServicioRecepcionFactura) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaRecepcion validacionRecepcionMasivaFactura(bo.gob.impuestos.siat.SolicitudValidacionRecepcion solicitudServicioValidacionRecepcionMasiva) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaRecepcion recepcionAnexos(bo.gob.impuestos.siat.SolicitudRecepcionAnexos solicitudRecepcionAnexos) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaRecepcion recepcionMasivaFactura(bo.gob.impuestos.siat.SolicitudRecepcionMasiva solicitudServicioRecepcionMasiva) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaRecepcion verificacionEstadoFactura(bo.gob.impuestos.siat.SolicitudVerificacionEstado solicitudServicioVerificacionEstadoFactura) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaRecepcion validacionRecepcionPaqueteFactura(bo.gob.impuestos.siat.SolicitudValidacionRecepcion solicitudServicioValidacionRecepcionPaquete) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaRecepcion anulacionFactura(bo.gob.impuestos.siat.SolicitudAnulacion solicitudServicioAnulacionFactura) throws java.rmi.RemoteException;
}
