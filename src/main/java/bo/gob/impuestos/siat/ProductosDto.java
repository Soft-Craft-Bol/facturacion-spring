/**
 * ProductosDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class ProductosDto  implements java.io.Serializable {
    private java.lang.String codigoActividad;

    private java.lang.Long codigoProducto;

    private java.lang.String descripcionProducto;

    private java.lang.String[] nandina;

    public ProductosDto() {
    }

    public ProductosDto(
           java.lang.String codigoActividad,
           java.lang.Long codigoProducto,
           java.lang.String descripcionProducto,
           java.lang.String[] nandina) {
           this.codigoActividad = codigoActividad;
           this.codigoProducto = codigoProducto;
           this.descripcionProducto = descripcionProducto;
           this.nandina = nandina;
    }


    /**
     * Gets the codigoActividad value for this ProductosDto.
     * 
     * @return codigoActividad
     */
    public java.lang.String getCodigoActividad() {
        return codigoActividad;
    }


    /**
     * Sets the codigoActividad value for this ProductosDto.
     * 
     * @param codigoActividad
     */
    public void setCodigoActividad(java.lang.String codigoActividad) {
        this.codigoActividad = codigoActividad;
    }


    /**
     * Gets the codigoProducto value for this ProductosDto.
     * 
     * @return codigoProducto
     */
    public java.lang.Long getCodigoProducto() {
        return codigoProducto;
    }


    /**
     * Sets the codigoProducto value for this ProductosDto.
     * 
     * @param codigoProducto
     */
    public void setCodigoProducto(java.lang.Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }


    /**
     * Gets the descripcionProducto value for this ProductosDto.
     * 
     * @return descripcionProducto
     */
    public java.lang.String getDescripcionProducto() {
        return descripcionProducto;
    }


    /**
     * Sets the descripcionProducto value for this ProductosDto.
     * 
     * @param descripcionProducto
     */
    public void setDescripcionProducto(java.lang.String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }


    /**
     * Gets the nandina value for this ProductosDto.
     * 
     * @return nandina
     */
    public java.lang.String[] getNandina() {
        return nandina;
    }


    /**
     * Sets the nandina value for this ProductosDto.
     * 
     * @param nandina
     */
    public void setNandina(java.lang.String[] nandina) {
        this.nandina = nandina;
    }

    public java.lang.String getNandina(int i) {
        return this.nandina[i];
    }

    public void setNandina(int i, java.lang.String _value) {
        this.nandina[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProductosDto)) return false;
        ProductosDto other = (ProductosDto) obj;
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
            ((this.codigoProducto==null && other.getCodigoProducto()==null) || 
             (this.codigoProducto!=null &&
              this.codigoProducto.equals(other.getCodigoProducto()))) &&
            ((this.descripcionProducto==null && other.getDescripcionProducto()==null) || 
             (this.descripcionProducto!=null &&
              this.descripcionProducto.equals(other.getDescripcionProducto()))) &&
            ((this.nandina==null && other.getNandina()==null) || 
             (this.nandina!=null &&
              java.util.Arrays.equals(this.nandina, other.getNandina())));
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
        if (getCodigoProducto() != null) {
            _hashCode += getCodigoProducto().hashCode();
        }
        if (getDescripcionProducto() != null) {
            _hashCode += getDescripcionProducto().hashCode();
        }
        if (getNandina() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNandina());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNandina(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProductosDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "productosDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoActividad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoActividad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoProducto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoProducto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionProducto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionProducto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nandina");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nandina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
