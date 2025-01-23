/**
 * ServicioFacturacionSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat.ServicioFacturacionCompraVenta;

public class ServicioFacturacionSoapBindingStub extends org.apache.axis.client.Stub implements bo.gob.impuestos.siat.ServicioFacturacion {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("recepcionPaqueteFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudServicioRecepcionPaquete"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionPaquete"), bo.gob.impuestos.siat.SolicitudRecepcionPaquete.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaRecepcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaServicioFacturacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verificarComunicacion");
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaComunicacion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaComunicacion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("recepcionFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudServicioRecepcionFactura"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionFactura"), bo.gob.impuestos.siat.SolicitudRecepcionFactura.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaRecepcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaServicioFacturacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validacionRecepcionMasivaFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudServicioValidacionRecepcionMasiva"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudValidacionRecepcion"), bo.gob.impuestos.siat.SolicitudValidacionRecepcion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaRecepcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaServicioFacturacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("recepcionAnexos");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudRecepcionAnexos"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionAnexos"), bo.gob.impuestos.siat.SolicitudRecepcionAnexos.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaRecepcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaRecepcionAnexos"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("recepcionMasivaFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudServicioRecepcionMasiva"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionMasiva"), bo.gob.impuestos.siat.SolicitudRecepcionMasiva.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaRecepcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaServicioFacturacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verificacionEstadoFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudServicioVerificacionEstadoFactura"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudVerificacionEstado"), bo.gob.impuestos.siat.SolicitudVerificacionEstado.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaRecepcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaServicioFacturacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validacionRecepcionPaqueteFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudServicioValidacionRecepcionPaquete"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudValidacionRecepcion"), bo.gob.impuestos.siat.SolicitudValidacionRecepcion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaRecepcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaServicioFacturacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("anulacionFactura");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudServicioAnulacionFactura"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudAnulacion"), bo.gob.impuestos.siat.SolicitudAnulacion.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaRecepcion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaServicioFacturacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

    }

    public ServicioFacturacionSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ServicioFacturacionSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ServicioFacturacionSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "mensajeRecepcion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.MensajeRecepcion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "mensajeServicio");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.MensajeServicio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "model");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.Model.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "modelDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.ModelDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaComunicacion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaComunicacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaRecepcion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudAnulacion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudAnulacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudRecepcion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionAnexos");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudRecepcionAnexos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionFactura");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudRecepcionFactura.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionMasiva");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudRecepcionMasiva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionPaquete");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudRecepcionPaquete.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudValidacionRecepcion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudValidacionRecepcion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudVerificacionEstado");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudVerificacionEstado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "ventaAnexo");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.VentaAnexo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public bo.gob.impuestos.siat.RespuestaRecepcion recepcionPaqueteFactura(bo.gob.impuestos.siat.SolicitudRecepcionPaquete solicitudServicioRecepcionPaquete) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "recepcionPaqueteFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudServicioRecepcionPaquete});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaRecepcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaComunicacion verificarComunicacion() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "verificarComunicacion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaComunicacion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaComunicacion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaComunicacion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaRecepcion recepcionFactura(bo.gob.impuestos.siat.SolicitudRecepcionFactura solicitudServicioRecepcionFactura) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "recepcionFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudServicioRecepcionFactura});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaRecepcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaRecepcion validacionRecepcionMasivaFactura(bo.gob.impuestos.siat.SolicitudValidacionRecepcion solicitudServicioValidacionRecepcionMasiva) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "validacionRecepcionMasivaFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudServicioValidacionRecepcionMasiva});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaRecepcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaRecepcion recepcionAnexos(bo.gob.impuestos.siat.SolicitudRecepcionAnexos solicitudRecepcionAnexos) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "recepcionAnexos"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudRecepcionAnexos});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaRecepcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaRecepcion recepcionMasivaFactura(bo.gob.impuestos.siat.SolicitudRecepcionMasiva solicitudServicioRecepcionMasiva) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "recepcionMasivaFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudServicioRecepcionMasiva});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaRecepcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaRecepcion verificacionEstadoFactura(bo.gob.impuestos.siat.SolicitudVerificacionEstado solicitudServicioVerificacionEstadoFactura) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "verificacionEstadoFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudServicioVerificacionEstadoFactura});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaRecepcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaRecepcion validacionRecepcionPaqueteFactura(bo.gob.impuestos.siat.SolicitudValidacionRecepcion solicitudServicioValidacionRecepcionPaquete) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "validacionRecepcionPaqueteFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudServicioValidacionRecepcionPaquete});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaRecepcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaRecepcion anulacionFactura(bo.gob.impuestos.siat.SolicitudAnulacion solicitudServicioAnulacionFactura) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "anulacionFactura"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudServicioAnulacionFactura});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaRecepcion) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaRecepcion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
