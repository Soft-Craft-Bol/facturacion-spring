/**
 * SolicitudValidacionRecepcion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class SolicitudValidacionRecepcion  extends bo.gob.impuestos.siat.SolicitudRecepcion  implements java.io.Serializable {
    private java.lang.String codigoRecepcion;

    public SolicitudValidacionRecepcion() {
    }

    public SolicitudValidacionRecepcion(
           int codigoAmbiente,
           int codigoDocumentoSector,
           int codigoEmision,
           int codigoModalidad,
           java.lang.Integer codigoPuntoVenta,
           java.lang.String codigoSistema,
           int codigoSucursal,
           java.lang.String cufd,
           java.lang.String cuis,
           long nit,
           int tipoFacturaDocumento,
           java.lang.String codigoRecepcion) {
        super(
            codigoAmbiente,
            codigoDocumentoSector,
            codigoEmision,
            codigoModalidad,
            codigoPuntoVenta,
            codigoSistema,
            codigoSucursal,
            cufd,
            cuis,
            nit,
            tipoFacturaDocumento);
        this.codigoRecepcion = codigoRecepcion;
    }


    /**
     * Gets the codigoRecepcion value for this SolicitudValidacionRecepcion.
     * 
     * @return codigoRecepcion
     */
    public java.lang.String getCodigoRecepcion() {
        return codigoRecepcion;
    }


    /**
     * Sets the codigoRecepcion value for this SolicitudValidacionRecepcion.
     * 
     * @param codigoRecepcion
     */
    public void setCodigoRecepcion(java.lang.String codigoRecepcion) {
        this.codigoRecepcion = codigoRecepcion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudValidacionRecepcion)) return false;
        SolicitudValidacionRecepcion other = (SolicitudValidacionRecepcion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigoRecepcion==null && other.getCodigoRecepcion()==null) || 
             (this.codigoRecepcion!=null &&
              this.codigoRecepcion.equals(other.getCodigoRecepcion())));
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
        if (getCodigoRecepcion() != null) {
            _hashCode += getCodigoRecepcion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudValidacionRecepcion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudValidacionRecepcion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoRecepcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoRecepcion"));
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
