/**
 * SolicitudCufdMasivo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bo.gob.impuestos.siat;

public class SolicitudCufdMasivo  implements java.io.Serializable {
    private int codigoAmbiente;

    private int codigoModalidad;

    private java.lang.String codigoSistema;

    private bo.gob.impuestos.siat.SolicitudListaCufdDto[] datosSolicitud;

    private long nit;

    public SolicitudCufdMasivo() {
    }

    public SolicitudCufdMasivo(
           int codigoAmbiente,
           int codigoModalidad,
           java.lang.String codigoSistema,
           bo.gob.impuestos.siat.SolicitudListaCufdDto[] datosSolicitud,
           long nit) {
           this.codigoAmbiente = codigoAmbiente;
           this.codigoModalidad = codigoModalidad;
           this.codigoSistema = codigoSistema;
           this.datosSolicitud = datosSolicitud;
           this.nit = nit;
    }


    /**
     * Gets the codigoAmbiente value for this SolicitudCufdMasivo.
     * 
     * @return codigoAmbiente
     */
    public int getCodigoAmbiente() {
        return codigoAmbiente;
    }


    /**
     * Sets the codigoAmbiente value for this SolicitudCufdMasivo.
     * 
     * @param codigoAmbiente
     */
    public void setCodigoAmbiente(int codigoAmbiente) {
        this.codigoAmbiente = codigoAmbiente;
    }


    /**
     * Gets the codigoModalidad value for this SolicitudCufdMasivo.
     * 
     * @return codigoModalidad
     */
    public int getCodigoModalidad() {
        return codigoModalidad;
    }


    /**
     * Sets the codigoModalidad value for this SolicitudCufdMasivo.
     * 
     * @param codigoModalidad
     */
    public void setCodigoModalidad(int codigoModalidad) {
        this.codigoModalidad = codigoModalidad;
    }


    /**
     * Gets the codigoSistema value for this SolicitudCufdMasivo.
     * 
     * @return codigoSistema
     */
    public java.lang.String getCodigoSistema() {
        return codigoSistema;
    }


    /**
     * Sets the codigoSistema value for this SolicitudCufdMasivo.
     * 
     * @param codigoSistema
     */
    public void setCodigoSistema(java.lang.String codigoSistema) {
        this.codigoSistema = codigoSistema;
    }


    /**
     * Gets the datosSolicitud value for this SolicitudCufdMasivo.
     * 
     * @return datosSolicitud
     */
    public bo.gob.impuestos.siat.SolicitudListaCufdDto[] getDatosSolicitud() {
        return datosSolicitud;
    }


    /**
     * Sets the datosSolicitud value for this SolicitudCufdMasivo.
     * 
     * @param datosSolicitud
     */
    public void setDatosSolicitud(bo.gob.impuestos.siat.SolicitudListaCufdDto[] datosSolicitud) {
        this.datosSolicitud = datosSolicitud;
    }

    public bo.gob.impuestos.siat.SolicitudListaCufdDto getDatosSolicitud(int i) {
        return this.datosSolicitud[i];
    }

    public void setDatosSolicitud(int i, bo.gob.impuestos.siat.SolicitudListaCufdDto _value) {
        this.datosSolicitud[i] = _value;
    }


    /**
     * Gets the nit value for this SolicitudCufdMasivo.
     * 
     * @return nit
     */
    public long getNit() {
        return nit;
    }


    /**
     * Sets the nit value for this SolicitudCufdMasivo.
     * 
     * @param nit
     */
    public void setNit(long nit) {
        this.nit = nit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudCufdMasivo)) return false;
        SolicitudCufdMasivo other = (SolicitudCufdMasivo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigoAmbiente == other.getCodigoAmbiente() &&
            this.codigoModalidad == other.getCodigoModalidad() &&
            ((this.codigoSistema==null && other.getCodigoSistema()==null) || 
             (this.codigoSistema!=null &&
              this.codigoSistema.equals(other.getCodigoSistema()))) &&
            ((this.datosSolicitud==null && other.getDatosSolicitud()==null) || 
             (this.datosSolicitud!=null &&
              java.util.Arrays.equals(this.datosSolicitud, other.getDatosSolicitud()))) &&
            this.nit == other.getNit();
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
        _hashCode += getCodigoAmbiente();
        _hashCode += getCodigoModalidad();
        if (getCodigoSistema() != null) {
            _hashCode += getCodigoSistema().hashCode();
        }
        if (getDatosSolicitud() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDatosSolicitud());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDatosSolicitud(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getNit()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudCufdMasivo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudCufdMasivo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoAmbiente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoAmbiente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoModalidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoModalidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSistema");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSistema"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosSolicitud");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datosSolicitud"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://siat.impuestos.gob.bo/", "solicitudListaCufdDto"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nit"));
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
