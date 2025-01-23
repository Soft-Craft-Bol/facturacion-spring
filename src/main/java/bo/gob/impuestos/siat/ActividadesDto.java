/**
 * ActividadesDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class ActividadesDto  implements java.io.Serializable {
    private java.lang.String codigoCaeb;

    private java.lang.String descripcion;

    private java.lang.String tipoActividad;

    public ActividadesDto() {
    }

    public ActividadesDto(
           java.lang.String codigoCaeb,
           java.lang.String descripcion,
           java.lang.String tipoActividad) {
           this.codigoCaeb = codigoCaeb;
           this.descripcion = descripcion;
           this.tipoActividad = tipoActividad;
    }


    /**
     * Gets the codigoCaeb value for this ActividadesDto.
     * 
     * @return codigoCaeb
     */
    public java.lang.String getCodigoCaeb() {
        return codigoCaeb;
    }


    /**
     * Sets the codigoCaeb value for this ActividadesDto.
     * 
     * @param codigoCaeb
     */
    public void setCodigoCaeb(java.lang.String codigoCaeb) {
        this.codigoCaeb = codigoCaeb;
    }


    /**
     * Gets the descripcion value for this ActividadesDto.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this ActividadesDto.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the tipoActividad value for this ActividadesDto.
     * 
     * @return tipoActividad
     */
    public java.lang.String getTipoActividad() {
        return tipoActividad;
    }


    /**
     * Sets the tipoActividad value for this ActividadesDto.
     * 
     * @param tipoActividad
     */
    public void setTipoActividad(java.lang.String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ActividadesDto)) return false;
        ActividadesDto other = (ActividadesDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoCaeb==null && other.getCodigoCaeb()==null) || 
             (this.codigoCaeb!=null &&
              this.codigoCaeb.equals(other.getCodigoCaeb()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.tipoActividad==null && other.getTipoActividad()==null) || 
             (this.tipoActividad!=null &&
              this.tipoActividad.equals(other.getTipoActividad())));
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
        if (getCodigoCaeb() != null) {
            _hashCode += getCodigoCaeb().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getTipoActividad() != null) {
            _hashCode += getTipoActividad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActividadesDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "actividadesDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCaeb");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoCaeb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoActividad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoActividad"));
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
