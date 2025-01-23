/**
 * RespuestaRecepcion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class RespuestaRecepcion  extends bo.gob.impuestos.siat.ModelDto  implements java.io.Serializable {
    private java.lang.String codigoDescripcion;

    private java.lang.Integer codigoEstado;

    private java.lang.String codigoRecepcion;

    private bo.gob.impuestos.siat.MensajeRecepcion[] mensajesList;

    private java.lang.Boolean transaccion;

    public RespuestaRecepcion() {
    }

    public RespuestaRecepcion(
           java.lang.String codigoDescripcion,
           java.lang.Integer codigoEstado,
           java.lang.String codigoRecepcion,
           bo.gob.impuestos.siat.MensajeRecepcion[] mensajesList,
           java.lang.Boolean transaccion) {
        this.codigoDescripcion = codigoDescripcion;
        this.codigoEstado = codigoEstado;
        this.codigoRecepcion = codigoRecepcion;
        this.mensajesList = mensajesList;
        this.transaccion = transaccion;
    }


    /**
     * Gets the codigoDescripcion value for this RespuestaRecepcion.
     * 
     * @return codigoDescripcion
     */
    public java.lang.String getCodigoDescripcion() {
        return codigoDescripcion;
    }


    /**
     * Sets the codigoDescripcion value for this RespuestaRecepcion.
     * 
     * @param codigoDescripcion
     */
    public void setCodigoDescripcion(java.lang.String codigoDescripcion) {
        this.codigoDescripcion = codigoDescripcion;
    }


    /**
     * Gets the codigoEstado value for this RespuestaRecepcion.
     * 
     * @return codigoEstado
     */
    public java.lang.Integer getCodigoEstado() {
        return codigoEstado;
    }


    /**
     * Sets the codigoEstado value for this RespuestaRecepcion.
     * 
     * @param codigoEstado
     */
    public void setCodigoEstado(java.lang.Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }


    /**
     * Gets the codigoRecepcion value for this RespuestaRecepcion.
     * 
     * @return codigoRecepcion
     */
    public java.lang.String getCodigoRecepcion() {
        return codigoRecepcion;
    }


    /**
     * Sets the codigoRecepcion value for this RespuestaRecepcion.
     * 
     * @param codigoRecepcion
     */
    public void setCodigoRecepcion(java.lang.String codigoRecepcion) {
        this.codigoRecepcion = codigoRecepcion;
    }


    /**
     * Gets the mensajesList value for this RespuestaRecepcion.
     * 
     * @return mensajesList
     */
    public bo.gob.impuestos.siat.MensajeRecepcion[] getMensajesList() {
        return mensajesList;
    }


    /**
     * Sets the mensajesList value for this RespuestaRecepcion.
     * 
     * @param mensajesList
     */
    public void setMensajesList(bo.gob.impuestos.siat.MensajeRecepcion[] mensajesList) {
        this.mensajesList = mensajesList;
    }

    public bo.gob.impuestos.siat.MensajeRecepcion getMensajesList(int i) {
        return this.mensajesList[i];
    }

    public void setMensajesList(int i, bo.gob.impuestos.siat.MensajeRecepcion _value) {
        this.mensajesList[i] = _value;
    }


    /**
     * Gets the transaccion value for this RespuestaRecepcion.
     * 
     * @return transaccion
     */
    public java.lang.Boolean getTransaccion() {
        return transaccion;
    }


    /**
     * Sets the transaccion value for this RespuestaRecepcion.
     * 
     * @param transaccion
     */
    public void setTransaccion(java.lang.Boolean transaccion) {
        this.transaccion = transaccion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaRecepcion)) return false;
        RespuestaRecepcion other = (RespuestaRecepcion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigoDescripcion==null && other.getCodigoDescripcion()==null) || 
             (this.codigoDescripcion!=null &&
              this.codigoDescripcion.equals(other.getCodigoDescripcion()))) &&
            ((this.codigoEstado==null && other.getCodigoEstado()==null) || 
             (this.codigoEstado!=null &&
              this.codigoEstado.equals(other.getCodigoEstado()))) &&
            ((this.codigoRecepcion==null && other.getCodigoRecepcion()==null) || 
             (this.codigoRecepcion!=null &&
              this.codigoRecepcion.equals(other.getCodigoRecepcion()))) &&
            ((this.mensajesList==null && other.getMensajesList()==null) || 
             (this.mensajesList!=null &&
              java.util.Arrays.equals(this.mensajesList, other.getMensajesList()))) &&
            ((this.transaccion==null && other.getTransaccion()==null) || 
             (this.transaccion!=null &&
              this.transaccion.equals(other.getTransaccion())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCodigoDescripcion() != null) {
            _hashCode += getCodigoDescripcion().hashCode();
        }
        if (getCodigoEstado() != null) {
            _hashCode += getCodigoEstado().hashCode();
        }
        if (getCodigoRecepcion() != null) {
            _hashCode += getCodigoRecepcion().hashCode();
        }
        if (getMensajesList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMensajesList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMensajesList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTransaccion() != null) {
            _hashCode += getTransaccion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaRecepcion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaRecepcion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoDescripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoDescripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoRecepcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoRecepcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajesList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensajesList"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "mensajeRecepcion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
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
