/**
 * ServicioFacturacionCodigos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public interface ServicioFacturacionCodigos extends java.rmi.Remote {
    public bo.gob.impuestos.siat.RespuestaComunicacion verificarComunicacion() throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaVerificarNit verificarNit(bo.gob.impuestos.siat.SolicitudVerificarNit solicitudVerificarNit) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaCuisMasivo cuisMasivo(bo.gob.impuestos.siat.SolicitudCuisMasivoSistemas solicitudCuisMasivoSistemas) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaCufd cufd(bo.gob.impuestos.siat.SolicitudCufd solicitudCufd) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaNotificaRevocado notificaCertificadoRevocado(bo.gob.impuestos.siat.SolicitudNotifcaRevocado solicitudNotificaRevocado) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaCuis cuis(bo.gob.impuestos.siat.SolicitudCuis solicitudCuis) throws java.rmi.RemoteException;
    public bo.gob.impuestos.siat.RespuestaCufdMasivo cufdMasivo(bo.gob.impuestos.siat.SolicitudCufdMasivo solicitudCufdMasivo) throws java.rmi.RemoteException;
}
