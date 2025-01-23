/**
 * ServicioFacturacionSincronizacionLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat.FacturacionSincronizacion;

public class ServicioFacturacionSincronizacionLocator extends org.apache.axis.client.Service implements bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacion {

    public ServicioFacturacionSincronizacionLocator() {
    }


    public ServicioFacturacionSincronizacionLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicioFacturacionSincronizacionLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicioFacturacionSincronizacionPort
    private java.lang.String ServicioFacturacionSincronizacionPort_address = "https://pilotosiatservicios.impuestos.gob.bo/v2/FacturacionSincronizacion";

    public java.lang.String getServicioFacturacionSincronizacionPortAddress() {
        return ServicioFacturacionSincronizacionPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicioFacturacionSincronizacionPortWSDDServiceName = "ServicioFacturacionSincronizacionPort";

    public java.lang.String getServicioFacturacionSincronizacionPortWSDDServiceName() {
        return ServicioFacturacionSincronizacionPortWSDDServiceName;
    }

    public void setServicioFacturacionSincronizacionPortWSDDServiceName(java.lang.String name) {
        ServicioFacturacionSincronizacionPortWSDDServiceName = name;
    }

    public bo.gob.impuestos.siat.ServicioFacturacionSincronizacion getServicioFacturacionSincronizacionPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServicioFacturacionSincronizacionPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicioFacturacionSincronizacionPort(endpoint);
    }

    public bo.gob.impuestos.siat.ServicioFacturacionSincronizacion getServicioFacturacionSincronizacionPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacionSoapBindingStub _stub = new bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacionSoapBindingStub(portAddress, this);
            _stub.setPortName(getServicioFacturacionSincronizacionPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServicioFacturacionSincronizacionPortEndpointAddress(java.lang.String address) {
        ServicioFacturacionSincronizacionPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (bo.gob.impuestos.siat.ServicioFacturacionSincronizacion.class.isAssignableFrom(serviceEndpointInterface)) {
                bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacionSoapBindingStub _stub = new bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacionSoapBindingStub(new java.net.URL(ServicioFacturacionSincronizacionPort_address), this);
                _stub.setPortName(getServicioFacturacionSincronizacionPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ServicioFacturacionSincronizacionPort".equals(inputPortName)) {
            return getServicioFacturacionSincronizacionPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/FacturacionSincronizacion", "ServicioFacturacionSincronizacion");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/FacturacionSincronizacion", "ServicioFacturacionSincronizacionPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicioFacturacionSincronizacionPort".equals(portName)) {
            setServicioFacturacionSincronizacionPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
