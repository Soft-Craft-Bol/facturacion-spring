/**
 * ServicioFacturacionLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat.ServicioFacturacionCompraVenta;

public class ServicioFacturacionLocator extends org.apache.axis.client.Service implements bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacion {

    public ServicioFacturacionLocator() {
    }


    public ServicioFacturacionLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicioFacturacionLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicioFacturacionPort
    private java.lang.String ServicioFacturacionPort_address = "https://pilotosiatservicios.impuestos.gob.bo/v2/ServicioFacturacionCompraVenta";

    public java.lang.String getServicioFacturacionPortAddress() {
        return ServicioFacturacionPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicioFacturacionPortWSDDServiceName = "ServicioFacturacionPort";

    public java.lang.String getServicioFacturacionPortWSDDServiceName() {
        return ServicioFacturacionPortWSDDServiceName;
    }

    public void setServicioFacturacionPortWSDDServiceName(java.lang.String name) {
        ServicioFacturacionPortWSDDServiceName = name;
    }

    public bo.gob.impuestos.siat.ServicioFacturacion getServicioFacturacionPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServicioFacturacionPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicioFacturacionPort(endpoint);
    }

    public bo.gob.impuestos.siat.ServicioFacturacion getServicioFacturacionPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacionSoapBindingStub _stub = new bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacionSoapBindingStub(portAddress, this);
            _stub.setPortName(getServicioFacturacionPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServicioFacturacionPortEndpointAddress(java.lang.String address) {
        ServicioFacturacionPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (bo.gob.impuestos.siat.ServicioFacturacion.class.isAssignableFrom(serviceEndpointInterface)) {
                bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacionSoapBindingStub _stub = new bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacionSoapBindingStub(new java.net.URL(ServicioFacturacionPort_address), this);
                _stub.setPortName(getServicioFacturacionPortWSDDServiceName());
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
        if ("ServicioFacturacionPort".equals(inputPortName)) {
            return getServicioFacturacionPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/ServicioFacturacionCompraVenta", "ServicioFacturacion");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/ServicioFacturacionCompraVenta", "ServicioFacturacionPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicioFacturacionPort".equals(portName)) {
            setServicioFacturacionPortEndpointAddress(address);
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
