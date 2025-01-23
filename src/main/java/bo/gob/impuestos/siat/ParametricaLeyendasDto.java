/**
 * ParametricaLeyendasDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class ParametricaLeyendasDto  implements java.io.Serializable {
    private java.lang.String codigoActividad;

    private java.lang.String descripcionLeyenda;

    public ParametricaLeyendasDto() {
    }

    public ParametricaLeyendasDto(
           java.lang.String codigoActividad,
           java.lang.String descripcionLeyenda) {
           this.codigoActividad = codigoActividad;
           this.descripcionLeyenda = descripcionLeyenda;
    }


    /**
     * Gets the codigoActividad value for this ParametricaLeyendasDto.
     * 
     * @return codigoActividad
     */
    public java.lang.String getCodigoActividad() {
        return codigoActividad;
    }


    /**
     * Sets the codigoActividad value for this ParametricaLeyendasDto.
     * 
     * @param codigoActividad
     */
    public void setCodigoActividad(java.lang.String codigoActividad) {
        this.codigoActividad = codigoActividad;
    }


    /**
     * Gets the descripcionLeyenda value for this ParametricaLeyendasDto.
     * 
     * @return descripcionLeyenda
     */
    public java.lang.String getDescripcionLeyenda() {
        return descripcionLeyenda;
    }


    /**
     * Sets the descripcionLeyenda value for this ParametricaLeyendasDto.
     * 
     * @param descripcionLeyenda
     */
    public void setDescripcionLeyenda(java.lang.String descripcionLeyenda) {
        this.descripcionLeyenda = descripcionLeyenda;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ParametricaLeyendasDto)) return false;
        ParametricaLeyendasDto other = (ParametricaLeyendasDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoActividad==null && other.getCodigoActividad()==null) || 
             (this.codigoActividad!=null &&
              this.codigoActividad.equals(other.getCodigoActividad()))) &&
            ((this.descripcionLeyenda==null && other.getDescripcionLeyenda()==null) || 
             (this.descripcionLeyenda!=null &&
              this.descripcionLeyenda.equals(other.getDescripcionLeyenda())));
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
        if (getCodigoActividad() != null) {
            _hashCode += getCodigoActividad().hashCode();
        }
        if (getDescripcionLeyenda() != null) {
            _hashCode += getDescripcionLeyenda().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ParametricaLeyendasDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "parametricaLeyendasDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoActividad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoActividad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionLeyenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionLeyenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
