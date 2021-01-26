/**
 * PulsarInterfaceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.comviva.asa.orchestrator2pulsar.ws.schema;

public interface PulsarInterfaceService extends javax.xml.rpc.Service {
    public java.lang.String getPulsarInterfacePortAddress();

    public com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterface getPulsarInterfacePort() throws javax.xml.rpc.ServiceException;

    public com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterface getPulsarInterfacePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
