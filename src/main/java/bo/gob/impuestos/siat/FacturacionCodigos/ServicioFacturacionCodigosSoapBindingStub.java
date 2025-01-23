/**
 * ServicioFacturacionCodigosSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat.FacturacionCodigos;

public class ServicioFacturacionCodigosSoapBindingStub extends org.apache.axis.client.Stub implements bo.gob.impuestos.siat.ServicioFacturacionCodigos {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[7];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verificarNit");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudVerificarNit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudVerificarNit"), bo.gob.impuestos.siat.SolicitudVerificarNit.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaVerificarNit"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaVerificarNit.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaVerificarNit"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verificarComunicacion");
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaComunicacion"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaComunicacion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaComunicacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cuisMasivo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudCuisMasivoSistemas"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCuisMasivoSistemas"), bo.gob.impuestos.siat.SolicitudCuisMasivoSistemas.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaCuisMasivo"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaCuisMasivo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaCuisMasivo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cufd");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudCufd"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCufd"), bo.gob.impuestos.siat.SolicitudCufd.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaCufd"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaCufd.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaCufd"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("notificaCertificadoRevocado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudNotificaRevocado"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudNotifcaRevocado"), bo.gob.impuestos.siat.SolicitudNotifcaRevocado.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaNotificaRevocado"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaNotificaRevocado.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaNotificaRevocado"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cuis");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudCuis"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCuis"), bo.gob.impuestos.siat.SolicitudCuis.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaCuis"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaCuis.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaCuis"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cufdMasivo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SolicitudCufdMasivo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCufdMasivo"), bo.gob.impuestos.siat.SolicitudCufdMasivo.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaCufdMasivo"));
        oper.setReturnClass(bo.gob.impuestos.siat.RespuestaCufdMasivo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "RespuestaCufdMasivo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

    }

    public ServicioFacturacionCodigosSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ServicioFacturacionCodigosSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ServicioFacturacionCodigosSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaConfiguracion");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaConfiguracion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaCufd");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaCufd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaCufdMasivo");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaCufdMasivo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaCuis");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaCuis.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaCuisMasivo");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaCuisMasivo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaRegistroCufdSoapDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaListaRegistroCufdSoapDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaRegistroCuisSoapDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaListaRegistroCuisSoapDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaNotificaRevocado");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaNotificaRevocado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaVerificarNit");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.RespuestaVerificarNit.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCufd");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudCufd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCufdMasivo");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudCufdMasivo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCuis");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudCuis.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCuisMasivoSistemas");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudCuisMasivoSistemas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudListaCufdDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudListaCufdDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudListaCuisDto");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudListaCuisDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudNotifcaRevocado");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudNotifcaRevocado.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudVerificarNit");
            cachedSerQNames.add(qName);
            cls = bo.gob.impuestos.siat.SolicitudVerificarNit.class;
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

    public bo.gob.impuestos.siat.RespuestaVerificarNit verificarNit(bo.gob.impuestos.siat.SolicitudVerificarNit solicitudVerificarNit) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "verificarNit"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudVerificarNit});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaVerificarNit) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaVerificarNit) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaVerificarNit.class);
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

    public bo.gob.impuestos.siat.RespuestaCuisMasivo cuisMasivo(bo.gob.impuestos.siat.SolicitudCuisMasivoSistemas solicitudCuisMasivoSistemas) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "cuisMasivo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudCuisMasivoSistemas});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaCuisMasivo) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaCuisMasivo) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaCuisMasivo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaCufd cufd(bo.gob.impuestos.siat.SolicitudCufd solicitudCufd) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "cufd"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudCufd});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaCufd) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaCufd) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaCufd.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaNotificaRevocado notificaCertificadoRevocado(bo.gob.impuestos.siat.SolicitudNotifcaRevocado solicitudNotificaRevocado) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "notificaCertificadoRevocado"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudNotificaRevocado});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaNotificaRevocado) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaNotificaRevocado) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaNotificaRevocado.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaCuis cuis(bo.gob.impuestos.siat.SolicitudCuis solicitudCuis) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "cuis"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudCuis});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaCuis) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaCuis) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaCuis.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public bo.gob.impuestos.siat.RespuestaCufdMasivo cufdMasivo(bo.gob.impuestos.siat.SolicitudCufdMasivo solicitudCufdMasivo) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "cufdMasivo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitudCufdMasivo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (bo.gob.impuestos.siat.RespuestaCufdMasivo) _resp;
            } catch (java.lang.Exception _exception) {
                return (bo.gob.impuestos.siat.RespuestaCufdMasivo) org.apache.axis.utils.JavaUtils.convert(_resp, bo.gob.impuestos.siat.RespuestaCufdMasivo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
