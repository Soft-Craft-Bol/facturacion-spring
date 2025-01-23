/**
 * RespuestaListaActividadesDocumentoSector.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class RespuestaListaActividadesDocumentoSector  extends bo.gob.impuestos.siat.RespuestaConfiguracion  implements java.io.Serializable {
    private bo.gob.impuestos.siat.ActividadesDocumentoSectorDto[] listaActividadesDocumentoSector;

    public RespuestaListaActividadesDocumentoSector() {
    }

    public RespuestaListaActividadesDocumentoSector(
           bo.gob.impuestos.siat.MensajeServicio[] mensajesList,
           java.lang.Boolean transaccion,
           bo.gob.impuestos.siat.ActividadesDocumentoSectorDto[] listaActividadesDocumentoSector) {
        super(
            mensajesList,
            transaccion);
        this.listaActividadesDocumentoSector = listaActividadesDocumentoSector;
    }


    /**
     * Gets the listaActividadesDocumentoSector value for this RespuestaListaActividadesDocumentoSector.
     * 
     * @return listaActividadesDocumentoSector
     */
    public bo.gob.impuestos.siat.ActividadesDocumentoSectorDto[] getListaActividadesDocumentoSector() {
        return listaActividadesDocumentoSector;
    }


    /**
     * Sets the listaActividadesDocumentoSector value for this RespuestaListaActividadesDocumentoSector.
     * 
     * @param listaActividadesDocumentoSector
     */
    public void setListaActividadesDocumentoSector(bo.gob.impuestos.siat.ActividadesDocumentoSectorDto[] listaActividadesDocumentoSector) {
        this.listaActividadesDocumentoSector = listaActividadesDocumentoSector;
    }

    public bo.gob.impuestos.siat.ActividadesDocumentoSectorDto getListaActividadesDocumentoSector(int i) {
        return this.listaActividadesDocumentoSector[i];
    }

    public void setListaActividadesDocumentoSector(int i, bo.gob.impuestos.siat.ActividadesDocumentoSectorDto _value) {
        this.listaActividadesDocumentoSector[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaListaActividadesDocumentoSector)) return false;
        RespuestaListaActividadesDocumentoSector other = (RespuestaListaActividadesDocumentoSector) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.listaActividadesDocumentoSector==null && other.getListaActividadesDocumentoSector()==null) || 
             (this.listaActividadesDocumentoSector!=null &&
              java.util.Arrays.equals(this.listaActividadesDocumentoSector, other.getListaActividadesDocumentoSector())));
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
        if (getListaActividadesDocumentoSector() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaActividadesDocumentoSector());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaActividadesDocumentoSector(), i);
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
        new org.apache.axis.description.TypeDesc(RespuestaListaActividadesDocumentoSector.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "respuestaListaActividadesDocumentoSector"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaActividadesDocumentoSector");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaActividadesDocumentoSector"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "actividadesDocumentoSectorDto"));
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
