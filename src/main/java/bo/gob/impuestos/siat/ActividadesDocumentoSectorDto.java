/**
 * ActividadesDocumentoSectorDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class ActividadesDocumentoSectorDto  extends bo.gob.impuestos.siat.ModelDto  implements java.io.Serializable {
    private java.lang.String codigoActividad;

    private java.lang.Integer codigoDocumentoSector;

    private java.lang.String tipoDocumentoSector;

    public ActividadesDocumentoSectorDto() {
    }

    public ActividadesDocumentoSectorDto(
           java.lang.String codigoActividad,
           java.lang.Integer codigoDocumentoSector,
           java.lang.String tipoDocumentoSector) {
        this.codigoActividad = codigoActividad;
        this.codigoDocumentoSector = codigoDocumentoSector;
        this.tipoDocumentoSector = tipoDocumentoSector;
    }


    /**
     * Gets the codigoActividad value for this ActividadesDocumentoSectorDto.
     * 
     * @return codigoActividad
     */
    public java.lang.String getCodigoActividad() {
        return codigoActividad;
    }


    /**
     * Sets the codigoActividad value for this ActividadesDocumentoSectorDto.
     * 
     * @param codigoActividad
     */
    public void setCodigoActividad(java.lang.String codigoActividad) {
        this.codigoActividad = codigoActividad;
    }


    /**
     * Gets the codigoDocumentoSector value for this ActividadesDocumentoSectorDto.
     * 
     * @return codigoDocumentoSector
     */
    public java.lang.Integer getCodigoDocumentoSector() {
        return codigoDocumentoSector;
    }


    /**
     * Sets the codigoDocumentoSector value for this ActividadesDocumentoSectorDto.
     * 
     * @param codigoDocumentoSector
     */
    public void setCodigoDocumentoSector(java.lang.Integer codigoDocumentoSector) {
        this.codigoDocumentoSector = codigoDocumentoSector;
    }


    /**
     * Gets the tipoDocumentoSector value for this ActividadesDocumentoSectorDto.
     * 
     * @return tipoDocumentoSector
     */
    public java.lang.String getTipoDocumentoSector() {
        return tipoDocumentoSector;
    }


    /**
     * Sets the tipoDocumentoSector value for this ActividadesDocumentoSectorDto.
     * 
     * @param tipoDocumentoSector
     */
    public void setTipoDocumentoSector(java.lang.String tipoDocumentoSector) {
        this.tipoDocumentoSector = tipoDocumentoSector;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ActividadesDocumentoSectorDto)) return false;
        ActividadesDocumentoSectorDto other = (ActividadesDocumentoSectorDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigoActividad==null && other.getCodigoActividad()==null) || 
             (this.codigoActividad!=null &&
              this.codigoActividad.equals(other.getCodigoActividad()))) &&
            ((this.codigoDocumentoSector==null && other.getCodigoDocumentoSector()==null) || 
             (this.codigoDocumentoSector!=null &&
              this.codigoDocumentoSector.equals(other.getCodigoDocumentoSector()))) &&
            ((this.tipoDocumentoSector==null && other.getTipoDocumentoSector()==null) || 
             (this.tipoDocumentoSector!=null &&
              this.tipoDocumentoSector.equals(other.getTipoDocumentoSector())));
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
        if (getCodigoActividad() != null) {
            _hashCode += getCodigoActividad().hashCode();
        }
        if (getCodigoDocumentoSector() != null) {
            _hashCode += getCodigoDocumentoSector().hashCode();
        }
        if (getTipoDocumentoSector() != null) {
            _hashCode += getTipoDocumentoSector().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActividadesDocumentoSectorDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "actividadesDocumentoSectorDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoActividad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoActividad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoDocumentoSector");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoDocumentoSector"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumentoSector");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumentoSector"));
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
