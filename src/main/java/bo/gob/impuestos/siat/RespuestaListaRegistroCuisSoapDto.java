/**
 * RespuestaListaRegistroCuisSoapDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class RespuestaListaRegistroCuisSoapDto  implements java.io.Serializable {
    private java.lang.String codigo;

    private java.lang.Integer codigoPuntoVenta;

    private java.lang.Integer codigoSucursal;

    private java.util.Calendar fechaVigencia;

    private bo.gob.impuestos.siat.MensajeServicio[] mensajeServicioList;

    private boolean transaccion;

    public RespuestaListaRegistroCuisSoapDto() {
    }

    public RespuestaListaRegistroCuisSoapDto(
           java.lang.String codigo,
           java.lang.Integer codigoPuntoVenta,
           java.lang.Integer codigoSucursal,
           java.util.Calendar fechaVigencia,
           bo.gob.impuestos.siat.MensajeServicio[] mensajeServicioList,
           boolean transaccion) {
           this.codigo = codigo;
           this.codigoPuntoVenta = codigoPuntoVenta;
           this.codigoSucursal = codigoSucursal;
           this.fechaVigencia = fechaVigencia;
           this.mensajeServicioList = mensajeServicioList;
           this.transaccion = transaccion;
    }


    /**
     * Gets the codigo value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @return codigo
     */
    public java.lang.String getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the codigoPuntoVenta value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @return codigoPuntoVenta
     */
    public java.lang.Integer getCodigoPuntoVenta() {
        return codigoPuntoVenta;
    }


    /**
     * Sets the codigoPuntoVenta value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @param codigoPuntoVenta
     */
    public void setCodigoPuntoVenta(java.lang.Integer codigoPuntoVenta) {
        this.codigoPuntoVenta = codigoPuntoVenta;
    }


    /**
     * Gets the codigoSucursal value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @return codigoSucursal
     */
    public java.lang.Integer getCodigoSucursal() {
        return codigoSucursal;
    }


    /**
     * Sets the codigoSucursal value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @param codigoSucursal
     */
    public void setCodigoSucursal(java.lang.Integer codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }


    /**
     * Gets the fechaVigencia value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @return fechaVigencia
     */
    public java.util.Calendar getFechaVigencia() {
        return fechaVigencia;
    }


    /**
     * Sets the fechaVigencia value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @param fechaVigencia
     */
    public void setFechaVigencia(java.util.Calendar fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }


    /**
     * Gets the mensajeServicioList value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @return mensajeServicioList
     */
    public bo.gob.impuestos.siat.MensajeServicio[] getMensajeServicioList() {
        return mensajeServicioList;
    }


    /**
     * Sets the mensajeServicioList value for this RespuestaListaRegistroCuisSoapDto.
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
     * Gets the transaccion value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @return transaccion
     */
    public boolean isTransaccion() {
        return transaccion;
    }


    /**
     * Sets the transaccion value for this RespuestaListaRegistroCuisSoapDto.
     * 
     * @param transaccion
     */
    public void setTransaccion(boolean transaccion) {
        this.transaccion = transaccion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaListaRegistroCuisSoapDto)) return false;
        RespuestaListaRegistroCuisSoapDto other = (RespuestaListaRegistroCuisSoapDto) obj;
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
            ((this.codigoPuntoVenta==null && other.getCodigoPuntoVenta()==null) || 
             (this.codigoPuntoVenta!=null &&
              this.codigoPuntoVenta.equals(other.getCodigoPuntoVenta()))) &&
            ((this.codigoSucursal==null && other.getCodigoSucursal()==null) || 
             (this.codigoSucursal!=null &&
              this.codigoSucursal.equals(other.getCodigoSucursal()))) &&
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
        if (getCodigoPuntoVenta() != null) {
            _hashCode += getCodigoPuntoVenta().hashCode();
        }
        if (getCodigoSucursal() != null) {
            _hashCode += getCodigoSucursal().hashCode();
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
        new org.apache.axis.description.TypeDesc(RespuestaListaRegistroCuisSoapDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaRegistroCuisSoapDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
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
