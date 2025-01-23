/**
 * RespuestaListaActividades.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class RespuestaListaActividades  extends bo.gob.impuestos.siat.RespuestaConfiguracion  implements java.io.Serializable {
    private bo.gob.impuestos.siat.ActividadesDto[] listaActividades;

    public RespuestaListaActividades() {
    }

    public RespuestaListaActividades(
           bo.gob.impuestos.siat.MensajeServicio[] mensajesList,
           java.lang.Boolean transaccion,
           bo.gob.impuestos.siat.ActividadesDto[] listaActividades) {
        super(
            mensajesList,
            transaccion);
        this.listaActividades = listaActividades;
    }


    /**
     * Gets the listaActividades value for this RespuestaListaActividades.
     * 
     * @return listaActividades
     */
    public bo.gob.impuestos.siat.ActividadesDto[] getListaActividades() {
        return listaActividades;
    }


    /**
     * Sets the listaActividades value for this RespuestaListaActividades.
     * 
     * @param listaActividades
     */
    public void setListaActividades(bo.gob.impuestos.siat.ActividadesDto[] listaActividades) {
        this.listaActividades = listaActividades;
    }

    public bo.gob.impuestos.siat.ActividadesDto getListaActividades(int i) {
        return this.listaActividades[i];
    }

    public void setListaActividades(int i, bo.gob.impuestos.siat.ActividadesDto _value) {
        this.listaActividades[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaListaActividades)) return false;
        RespuestaListaActividades other = (RespuestaListaActividades) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.listaActividades==null && other.getListaActividades()==null) || 
             (this.listaActividades!=null &&
              java.util.Arrays.equals(this.listaActividades, other.getListaActividades())));
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
        if (getListaActividades() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaActividades());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaActividades(), i);
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
        new org.apache.axis.description.TypeDesc(RespuestaListaActividades.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaActividades"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaActividades");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaActividades"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "actividadesDto"));
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
