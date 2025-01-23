/**
 * SolicitudRecepcionPaquete.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class SolicitudRecepcionPaquete  extends bo.gob.impuestos.siat.SolicitudRecepcionFactura  implements java.io.Serializable {
    private java.lang.String cafc;

    private int cantidadFacturas;

    private long codigoEvento;

    public SolicitudRecepcionPaquete() {
    }

    public SolicitudRecepcionPaquete(
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
           java.lang.String hashArchivo,
           java.lang.String cafc,
           int cantidadFacturas,
           long codigoEvento) {
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
            tipoFacturaDocumento,
            archivo,
            fechaEnvio,
            hashArchivo);
        this.cafc = cafc;
        this.cantidadFacturas = cantidadFacturas;
        this.codigoEvento = codigoEvento;
    }


    /**
     * Gets the cafc value for this SolicitudRecepcionPaquete.
     * 
     * @return cafc
     */
    public java.lang.String getCafc() {
        return cafc;
    }


    /**
     * Sets the cafc value for this SolicitudRecepcionPaquete.
     * 
     * @param cafc
     */
    public void setCafc(java.lang.String cafc) {
        this.cafc = cafc;
    }


    /**
     * Gets the cantidadFacturas value for this SolicitudRecepcionPaquete.
     * 
     * @return cantidadFacturas
     */
    public int getCantidadFacturas() {
        return cantidadFacturas;
    }


    /**
     * Sets the cantidadFacturas value for this SolicitudRecepcionPaquete.
     * 
     * @param cantidadFacturas
     */
    public void setCantidadFacturas(int cantidadFacturas) {
        this.cantidadFacturas = cantidadFacturas;
    }


    /**
     * Gets the codigoEvento value for this SolicitudRecepcionPaquete.
     * 
     * @return codigoEvento
     */
    public long getCodigoEvento() {
        return codigoEvento;
    }


    /**
     * Sets the codigoEvento value for this SolicitudRecepcionPaquete.
     * 
     * @param codigoEvento
     */
    public void setCodigoEvento(long codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudRecepcionPaquete)) return false;
        SolicitudRecepcionPaquete other = (SolicitudRecepcionPaquete) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cafc==null && other.getCafc()==null) || 
             (this.cafc!=null &&
              this.cafc.equals(other.getCafc()))) &&
            this.cantidadFacturas == other.getCantidadFacturas() &&
            this.codigoEvento == other.getCodigoEvento();
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
        if (getCafc() != null) {
            _hashCode += getCafc().hashCode();
        }
        _hashCode += getCantidadFacturas();
        _hashCode += new Long(getCodigoEvento()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudRecepcionPaquete.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudRecepcionPaquete"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cafc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cafc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantidadFacturas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cantidadFacturas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
