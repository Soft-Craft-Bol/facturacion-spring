/**
 * MensajeRecepcion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class MensajeRecepcion  extends bo.gob.impuestos.siat.MensajeServicio  implements java.io.Serializable {
    private java.lang.Boolean advertencia;

    private java.lang.Integer numeroArchivo;

    private java.lang.Integer numeroDetalle;

    public MensajeRecepcion() {
    }

    public MensajeRecepcion(
           java.lang.Integer codigo,
           java.lang.String descripcion,
           java.lang.Boolean advertencia,
           java.lang.Integer numeroArchivo,
           java.lang.Integer numeroDetalle) {
        super(
            codigo,
            descripcion);
        this.advertencia = advertencia;
        this.numeroArchivo = numeroArchivo;
        this.numeroDetalle = numeroDetalle;
    }


    /**
     * Gets the advertencia value for this MensajeRecepcion.
     * 
     * @return advertencia
     */
    public java.lang.Boolean getAdvertencia() {
        return advertencia;
    }


    /**
     * Sets the advertencia value for this MensajeRecepcion.
     * 
     * @param advertencia
     */
    public void setAdvertencia(java.lang.Boolean advertencia) {
        this.advertencia = advertencia;
    }


    /**
     * Gets the numeroArchivo value for this MensajeRecepcion.
     * 
     * @return numeroArchivo
     */
    public java.lang.Integer getNumeroArchivo() {
        return numeroArchivo;
    }


    /**
     * Sets the numeroArchivo value for this MensajeRecepcion.
     * 
     * @param numeroArchivo
     */
    public void setNumeroArchivo(java.lang.Integer numeroArchivo) {
        this.numeroArchivo = numeroArchivo;
    }


    /**
     * Gets the numeroDetalle value for this MensajeRecepcion.
     * 
     * @return numeroDetalle
     */
    public java.lang.Integer getNumeroDetalle() {
        return numeroDetalle;
    }


    /**
     * Sets the numeroDetalle value for this MensajeRecepcion.
     * 
     * @param numeroDetalle
     */
    public void setNumeroDetalle(java.lang.Integer numeroDetalle) {
        this.numeroDetalle = numeroDetalle;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MensajeRecepcion)) return false;
        MensajeRecepcion other = (MensajeRecepcion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.advertencia==null && other.getAdvertencia()==null) || 
             (this.advertencia!=null &&
              this.advertencia.equals(other.getAdvertencia()))) &&
            ((this.numeroArchivo==null && other.getNumeroArchivo()==null) || 
             (this.numeroArchivo!=null &&
              this.numeroArchivo.equals(other.getNumeroArchivo()))) &&
            ((this.numeroDetalle==null && other.getNumeroDetalle()==null) || 
             (this.numeroDetalle!=null &&
              this.numeroDetalle.equals(other.getNumeroDetalle())));
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
        if (getAdvertencia() != null) {
            _hashCode += getAdvertencia().hashCode();
        }
        if (getNumeroArchivo() != null) {
            _hashCode += getNumeroArchivo().hashCode();
        }
        if (getNumeroDetalle() != null) {
            _hashCode += getNumeroDetalle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MensajeRecepcion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "mensajeRecepcion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("advertencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "advertencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroArchivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroArchivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDetalle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDetalle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
