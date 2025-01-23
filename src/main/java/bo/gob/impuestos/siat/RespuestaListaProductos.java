/**
 * RespuestaListaProductos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class RespuestaListaProductos  extends bo.gob.impuestos.siat.RespuestaConfiguracion  implements java.io.Serializable {
    private bo.gob.impuestos.siat.ProductosDto[] listaCodigos;

    public RespuestaListaProductos() {
    }

    public RespuestaListaProductos(
           bo.gob.impuestos.siat.MensajeServicio[] mensajesList,
           java.lang.Boolean transaccion,
           bo.gob.impuestos.siat.ProductosDto[] listaCodigos) {
        super(
            mensajesList,
            transaccion);
        this.listaCodigos = listaCodigos;
    }


    /**
     * Gets the listaCodigos value for this RespuestaListaProductos.
     * 
     * @return listaCodigos
     */
    public bo.gob.impuestos.siat.ProductosDto[] getListaCodigos() {
        return listaCodigos;
    }


    /**
     * Sets the listaCodigos value for this RespuestaListaProductos.
     * 
     * @param listaCodigos
     */
    public void setListaCodigos(bo.gob.impuestos.siat.ProductosDto[] listaCodigos) {
        this.listaCodigos = listaCodigos;
    }

    public bo.gob.impuestos.siat.ProductosDto getListaCodigos(int i) {
        return this.listaCodigos[i];
    }

    public void setListaCodigos(int i, bo.gob.impuestos.siat.ProductosDto _value) {
        this.listaCodigos[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaListaProductos)) return false;
        RespuestaListaProductos other = (RespuestaListaProductos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.listaCodigos==null && other.getListaCodigos()==null) || 
             (this.listaCodigos!=null &&
              java.util.Arrays.equals(this.listaCodigos, other.getListaCodigos())));
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
        if (getListaCodigos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaCodigos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaCodigos(), i);
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
        new org.apache.axis.description.TypeDesc(RespuestaListaProductos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaProductos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaCodigos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaCodigos"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "productosDto"));
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
