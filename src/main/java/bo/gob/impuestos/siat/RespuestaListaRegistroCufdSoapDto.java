/**
 * RespuestaListaRegistroCufdSoapDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class RespuestaListaRegistroCufdSoapDto  implements java.io.Serializable {
    private java.lang.String codigo;

    private java.lang.String codigoControl;

    private java.lang.Integer codigoPuntoVenta;

    private java.lang.Integer codigoSucursal;

    private java.lang.String cuis;

    private java.lang.String direccion;

    private java.util.Calendar fechaVigencia;

    private bo.gob.impuestos.siat.MensajeServicio[] mensajeServicioList;

    private boolean transaccion;

    public RespuestaListaRegistroCufdSoapDto() {
    }

    public RespuestaListaRegistroCufdSoapDto(
           java.lang.String codigo,
           java.lang.String codigoControl,
           java.lang.Integer codigoPuntoVenta,
           java.lang.Integer codigoSucursal,
           java.lang.String cuis,
           java.lang.String direccion,
           java.util.Calendar fechaVigencia,
           bo.gob.impuestos.siat.MensajeServicio[] mensajeServicioList,
           boolean transaccion) {
           this.codigo = codigo;
           this.codigoControl = codigoControl;
           this.codigoPuntoVenta = codigoPuntoVenta;
           this.codigoSucursal = codigoSucursal;
           this.cuis = cuis;
           this.direccion = direccion;
           this.fechaVigencia = fechaVigencia;
           this.mensajeServicioList = mensajeServicioList;
           this.transaccion = transaccion;
    }


    /**
     * Gets the codigo value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return codigo
     */
    public java.lang.String getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the codigoControl value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return codigoControl
     */
    public java.lang.String getCodigoControl() {
        return codigoControl;
    }


    /**
     * Sets the codigoControl value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param codigoControl
     */
    public void setCodigoControl(java.lang.String codigoControl) {
        this.codigoControl = codigoControl;
    }


    /**
     * Gets the codigoPuntoVenta value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return codigoPuntoVenta
     */
    public java.lang.Integer getCodigoPuntoVenta() {
        return codigoPuntoVenta;
    }


    /**
     * Sets the codigoPuntoVenta value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param codigoPuntoVenta
     */
    public void setCodigoPuntoVenta(java.lang.Integer codigoPuntoVenta) {
        this.codigoPuntoVenta = codigoPuntoVenta;
    }


    /**
     * Gets the codigoSucursal value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return codigoSucursal
     */
    public java.lang.Integer getCodigoSucursal() {
        return codigoSucursal;
    }


    /**
     * Sets the codigoSucursal value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param codigoSucursal
     */
    public void setCodigoSucursal(java.lang.Integer codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }


    /**
     * Gets the cuis value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return cuis
     */
    public java.lang.String getCuis() {
        return cuis;
    }


    /**
     * Sets the cuis value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param cuis
     */
    public void setCuis(java.lang.String cuis) {
        this.cuis = cuis;
    }


    /**
     * Gets the direccion value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return direccion
     */
    public java.lang.String getDireccion() {
        return direccion;
    }


    /**
     * Sets the direccion value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param direccion
     */
    public void setDireccion(java.lang.String direccion) {
        this.direccion = direccion;
    }


    /**
     * Gets the fechaVigencia value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return fechaVigencia
     */
    public java.util.Calendar getFechaVigencia() {
        return fechaVigencia;
    }


    /**
     * Sets the fechaVigencia value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param fechaVigencia
     */
    public void setFechaVigencia(java.util.Calendar fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }


    /**
     * Gets the mensajeServicioList value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return mensajeServicioList
     */
    public bo.gob.impuestos.siat.MensajeServicio[] getMensajeServicioList() {
        return mensajeServicioList;
    }


    /**
     * Sets the mensajeServicioList value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param mensajeServicioList
     */
    public void setMensajeServicioList(bo.gob.impuestos.siat.MensajeServicio[] mensajeServicioList) {
        this.mensajeServicioList = mensajeServicioList;
    }

    public bo.gob.impuestos.siat.MensajeServicio getMensajeServicioList(int i) {
        return this.mensajeServicioList[i];
    }

    public void setMensajeServicioList(int i, bo.gob.impuestos.siat.MensajeServicio _value) {
        this.mensajeServicioList[i] = _value;
    }


    /**
     * Gets the transaccion value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @return transaccion
     */
    public boolean isTransaccion() {
        return transaccion;
    }


    /**
     * Sets the transaccion value for this RespuestaListaRegistroCufdSoapDto.
     * 
     * @param transaccion
     */
    public void setTransaccion(boolean transaccion) {
        this.transaccion = transaccion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaListaRegistroCufdSoapDto)) return false;
        RespuestaListaRegistroCufdSoapDto other = (RespuestaListaRegistroCufdSoapDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigo==null && other.getCodigo()==null) || 
             (this.codigo!=null &&
              this.codigo.equals(other.getCodigo()))) &&
            ((this.codigoControl==null && other.getCodigoControl()==null) || 
             (this.codigoControl!=null &&
              this.codigoControl.equals(other.getCodigoControl()))) &&
            ((this.codigoPuntoVenta==null && other.getCodigoPuntoVenta()==null) || 
             (this.codigoPuntoVenta!=null &&
              this.codigoPuntoVenta.equals(other.getCodigoPuntoVenta()))) &&
            ((this.codigoSucursal==null && other.getCodigoSucursal()==null) || 
             (this.codigoSucursal!=null &&
              this.codigoSucursal.equals(other.getCodigoSucursal()))) &&
            ((this.cuis==null && other.getCuis()==null) || 
             (this.cuis!=null &&
              this.cuis.equals(other.getCuis()))) &&
            ((this.direccion==null && other.getDireccion()==null) || 
             (this.direccion!=null &&
              this.direccion.equals(other.getDireccion()))) &&
            ((this.fechaVigencia==null && other.getFechaVigencia()==null) || 
             (this.fechaVigencia!=null &&
              this.fechaVigencia.equals(other.getFechaVigencia()))) &&
            ((this.mensajeServicioList==null && other.getMensajeServicioList()==null) || 
             (this.mensajeServicioList!=null &&
              java.util.Arrays.equals(this.mensajeServicioList, other.getMensajeServicioList()))) &&
            this.transaccion == other.isTransaccion();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        if (getCodigoControl() != null) {
            _hashCode += getCodigoControl().hashCode();
        }
        if (getCodigoPuntoVenta() != null) {
            _hashCode += getCodigoPuntoVenta().hashCode();
        }
        if (getCodigoSucursal() != null) {
            _hashCode += getCodigoSucursal().hashCode();
        }
        if (getCuis() != null) {
            _hashCode += getCuis().hashCode();
        }
        if (getDireccion() != null) {
            _hashCode += getDireccion().hashCode();
        }
        if (getFechaVigencia() != null) {
            _hashCode += getFechaVigencia().hashCode();
        }
        if (getMensajeServicioList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMensajeServicioList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMensajeServicioList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isTransaccion() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaListaRegistroCufdSoapDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaRegistroCufdSoapDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoControl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoControl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPuntoVenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoPuntoVenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSucursal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSucursal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVigencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVigencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeServicioList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensajeServicioList"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "mensajeServicio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
