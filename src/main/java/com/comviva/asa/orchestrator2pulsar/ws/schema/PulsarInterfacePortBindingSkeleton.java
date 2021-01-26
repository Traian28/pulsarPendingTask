/**
 * PulsarInterfacePortBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.comviva.asa.orchestrator2pulsar.ws.schema;

public class PulsarInterfacePortBindingSkeleton implements com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterface, org.apache.axis.wsdl.Skeleton {
    private com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterface impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.orchestrator2pulsar.asa.comviva.com/", "messageData"), com.comviva.asa.orchestrator2pulsar.ws.schema.MessageData.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("sendMessage", _params, new javax.xml.namespace.QName("", "return"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://ws.orchestrator2pulsar.asa.comviva.com/", "requestResponse"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.orchestrator2pulsar.asa.comviva.com/", "sendMessage"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("sendMessage") == null) {
            _myOperations.put("sendMessage", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("sendMessage")).add(_oper);
    }

    public PulsarInterfacePortBindingSkeleton() {
        this.impl = new com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterfacePortBindingImpl();
    }

    public PulsarInterfacePortBindingSkeleton(com.comviva.asa.orchestrator2pulsar.ws.schema.PulsarInterface impl) {
        this.impl = impl;
    }
    public com.comviva.asa.orchestrator2pulsar.ws.schema.RequestResponse sendMessage(com.comviva.asa.orchestrator2pulsar.ws.schema.MessageData arg0, java.lang.String arg1) throws java.rmi.RemoteException
    {
        com.comviva.asa.orchestrator2pulsar.ws.schema.RequestResponse ret = impl.sendMessage(arg0, arg1);
        return ret;
    }

}
