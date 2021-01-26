/**
 * PulsarInterfaceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.comviva.asa.orchestrator2pulsar.ws.schema;

public class PulsarInterfaceServiceLocator extends org.apache.axis.client.Service implements com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterfaceService {

    public PulsarInterfaceServiceLocator() {
    }


    public PulsarInterfaceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PulsarInterfaceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PulsarInterfacePort
    private java.lang.String PulsarInterfacePort_address = "http://10.46.157.202:8880/wsPulsar";

    public java.lang.String getPulsarInterfacePortAddress() {
        return PulsarInterfacePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PulsarInterfacePortWSDDServiceName = "PulsarInterfacePort";

    public java.lang.String getPulsarInterfacePortWSDDServiceName() {
        return PulsarInterfacePortWSDDServiceName;
    }

    public void setPulsarInterfacePortWSDDServiceName(java.lang.String name) {
        PulsarInterfacePortWSDDServiceName = name;
    }

    public com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterface getPulsarInterfacePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PulsarInterfacePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPulsarInterfacePort(endpoint);
    }

    public com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterface getPulsarInterfacePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterfacePortBindingStub _stub = new com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterfacePortBindingStub(portAddress, this);
            _stub.setPortName(getPulsarInterfacePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPulsarInterfacePortEndpointAddress(java.lang.String address) {
        PulsarInterfacePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterfacePortBindingStub _stub = new com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterfacePortBindingStub(new java.net.URL(PulsarInterfacePort_address), this);
                _stub.setPortName(getPulsarInterfacePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("PulsarInterfacePort".equals(inputPortName)) {
            return getPulsarInterfacePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.orchestrator2pulsar.asa.comviva.com/", "PulsarInterfaceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.orchestrator2pulsar.asa.comviva.com/", "PulsarInterfacePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PulsarInterfacePort".equals(portName)) {
            setPulsarInterfacePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
