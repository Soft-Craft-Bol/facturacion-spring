/**
 * SolicitudRecepcionFactura.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class SolicitudRecepcionFactura  extends bo.gob.impuestos.siat.SolicitudRecepcion  implements java.io.Serializable {
    private byte[] archivo;

    private java.lang.String fechaEnvio;

    private java.lang.String hashArchivo;

    public SolicitudRecepcionFactura() {
    }

    public SolicitudRecepcionFactura(
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
           byte[] archivo,
           java.lang.String fechaEnvio,
           java.lang.String hashArchivo) {
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
        this.archivo = archivo;
        this.fechaEnvio = fechaEnvio;
        this.hashArchivo = hashArchivo;
    }


    /**
     * Gets the archivo value for this SolicitudRecepcionFactura.
     * 
     * @return archivo
     */
    public byte[] getArchivo() {
        return archivo;
    }


    /**
     * Sets the archivo value for this SolicitudRecepcionFactura.
     * 
     * @param archivo
     */
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }


    /**
     * Gets the fechaEnvio value for this SolicitudRecepcionFactura.
     * 
     * @return fechaEnvio
     */
    public java.lang.String getFechaEnvio() {
        return fechaEnvio;
    }


    /**
     * Sets the fechaEnvio value for this SolicitudRecepcionFactura.
     * 
     * @param fechaEnvio
     */
    public void setFechaEnvio(java.lang.String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }


    /**
     * Gets the hashArchivo value for this SolicitudRecepcionFactura.
     * 
     * @return hashArchivo
     */
    public java.lang.String getHashArchivo() {
        return hashArchivo;
    }


    /**
     * Sets the hashArchivo value for this SolicitudRecepcionFactura.
     * 
     * @param hashArchivo
     */
    public void setHashArchivo(java.lang.String hashArchivo) {
        this.hashArchivo = hashArchivo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudRecepcionFactura)) return false;
        SolicitudRecepcionFactura other = (SolicitudRecepcionFactura) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.archivo==null && other.getArchivo()==null) || 
             (this.archivo!=null &&
              java.util.Arrays.equals(this.archivo, other.getArchivo()))) &&
            ((this.fechaEnvio==null && other.getFechaEnvio()==null) || 
             (this.fechaEnvio!=null &&
              this.fechaEnvio.equals(other.getFechaEnvio()))) &&
            ((this.hashArchivo==null && other.getHashArchivo()==null) || 
             (this.hashArchivo!=null &&
              this.hashArchivo.equals(other.getHashArchivo())));
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
        if (getArchivo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArchivo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArchivo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFechaEnvio() != null) {
            _hashCode += getFechaEnvio().hashCode();
        }
        if (getHashArchivo() != null) {
            _hashCode += getHashArchivo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudRecepcionFactura.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionFactura"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("archivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "archivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaEnvio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaEnvio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hashArchivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hashArchivo"));
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
