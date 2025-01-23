/**
 * RespuestaListaParametricasLeyendas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class RespuestaListaParametricasLeyendas  extends bo.gob.impuestos.siat.RespuestaConfiguracion  implements java.io.Serializable {
    private bo.gob.impuestos.siat.ParametricaLeyendasDto[] listaLeyendas;

    public RespuestaListaParametricasLeyendas() {
    }

    public RespuestaListaParametricasLeyendas(
           bo.gob.impuestos.siat.MensajeServicio[] mensajesList,
           java.lang.Boolean transaccion,
           bo.gob.impuestos.siat.ParametricaLeyendasDto[] listaLeyendas) {
        super(
            mensajesList,
            transaccion);
        this.listaLeyendas = listaLeyendas;
    }


    /**
     * Gets the listaLeyendas value for this RespuestaListaParametricasLeyendas.
     * 
     * @return listaLeyendas
     */
    public bo.gob.impuestos.siat.ParametricaLeyendasDto[] getListaLeyendas() {
        return listaLeyendas;
    }


    /**
     * Sets the listaLeyendas value for this RespuestaListaParametricasLeyendas.
     * 
     * @param listaLeyendas
     */
    public void setListaLeyendas(bo.gob.impuestos.siat.ParametricaLeyendasDto[] listaLeyendas) {
        this.listaLeyendas = listaLeyendas;
    }

    public bo.gob.impuestos.siat.ParametricaLeyendasDto getListaLeyendas(int i) {
        return this.listaLeyendas[i];
    }

    public void setListaLeyendas(int i, bo.gob.impuestos.siat.ParametricaLeyendasDto _value) {
        this.listaLeyendas[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaListaParametricasLeyendas)) return false;
        RespuestaListaParametricasLeyendas other = (RespuestaListaParametricasLeyendas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.listaLeyendas==null && other.getListaLeyendas()==null) || 
             (this.listaLeyendas!=null &&
              java.util.Arrays.equals(this.listaLeyendas, other.getListaLeyendas())));
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
        if (getListaLeyendas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaLeyendas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaLeyendas(), i);
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
        new org.apache.axis.description.TypeDesc(RespuestaListaParametricasLeyendas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaParametricasLeyendas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaLeyendas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaLeyendas"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "parametricaLeyendasDto"));
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
