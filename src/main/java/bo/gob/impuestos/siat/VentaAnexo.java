/**
 * VentaAnexo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class VentaAnexo  extends bo.gob.impuestos.siat.ModelDto  implements java.io.Serializable {
    private java.lang.String codigo;

    private java.lang.String codigoProducto;

    private long codigoProductoSin;

    private java.lang.String tipoCodigo;

    public VentaAnexo() {
    }

    public VentaAnexo(
           java.lang.String codigo,
           java.lang.String codigoProducto,
           long codigoProductoSin,
           java.lang.String tipoCodigo) {
        this.codigo = codigo;
        this.codigoProducto = codigoProducto;
        this.codigoProductoSin = codigoProductoSin;
        this.tipoCodigo = tipoCodigo;
    }


    /**
     * Gets the codigo value for this VentaAnexo.
     * 
     * @return codigo
     */
    public java.lang.String getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this VentaAnexo.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the codigoProducto value for this VentaAnexo.
     * 
     * @return codigoProducto
     */
    public java.lang.String getCodigoProducto() {
        return codigoProducto;
    }


    /**
     * Sets the codigoProducto value for this VentaAnexo.
     * 
     * @param codigoProducto
     */
    public void setCodigoProducto(java.lang.String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }


    /**
     * Gets the codigoProductoSin value for this VentaAnexo.
     * 
     * @return codigoProductoSin
     */
    public long getCodigoProductoSin() {
        return codigoProductoSin;
    }


    /**
     * Sets the codigoProductoSin value for this VentaAnexo.
     * 
     * @param codigoProductoSin
     */
    public void setCodigoProductoSin(long codigoProductoSin) {
        this.codigoProductoSin = codigoProductoSin;
    }


    /**
     * Gets the tipoCodigo value for this VentaAnexo.
     * 
     * @return tipoCodigo
     */
    public java.lang.String getTipoCodigo() {
        return tipoCodigo;
    }


    /**
     * Sets the tipoCodigo value for this VentaAnexo.
     * 
     * @param tipoCodigo
     */
    public void setTipoCodigo(java.lang.String tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VentaAnexo)) return false;
        VentaAnexo other = (VentaAnexo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigo==null && other.getCodigo()==null) || 
             (this.codigo!=null &&
              this.codigo.equals(other.getCodigo()))) &&
            ((this.codigoProducto==null && other.getCodigoProducto()==null) || 
             (this.codigoProducto!=null &&
              this.codigoProducto.equals(other.getCodigoProducto()))) &&
            this.codigoProductoSin == other.getCodigoProductoSin() &&
            ((this.tipoCodigo==null && other.getTipoCodigo()==null) || 
             (this.tipoCodigo!=null &&
              this.tipoCodigo.equals(other.getTipoCodigo())));
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
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        if (getCodigoProducto() != null) {
            _hashCode += getCodigoProducto().hashCode();
        }
        _hashCode += new Long(getCodigoProductoSin()).hashCode();
        if (getTipoCodigo() != null) {
            _hashCode += getTipoCodigo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VentaAnexo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "ventaAnexo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoProducto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoProducto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoProductoSin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoProductoSin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCodigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCodigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
