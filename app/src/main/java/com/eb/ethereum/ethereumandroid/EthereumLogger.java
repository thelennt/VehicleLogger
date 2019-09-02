package com.eb.ethereum.ethereumandroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.math.BigInteger;

import org.web3j.abi.TypeReference;
import org.web3j.crypto.Credentials;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.protocol.Web3j;

public class EthereumLogger {
    private EthereumLogger(){};
    
    private static String getFunction = "getLog";
    private static String addFunction = "setLog";
    private static String setVINFunction = "setVIN";
    private static String getVINFunction = "getVIN";
    private static String contract = "0x6C7f324fD6EBF02cE2C4F2932aFaA2Da234B6d20";
    
    static public String addLog(Web3j web3j, Credentials credentials, Log log) throws Exception
    {
        return EthService.callFunction(web3j, contract, addFunction, Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(log.log),new org.web3j.abi.datatypes.Utf8String(log.date.toString())),credentials);
    }

    static public String setVIN(Web3j web3j, Credentials credentials, String vin) throws Exception
    {
        return EthService.callFunction(web3j, contract, setVINFunction, Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(vin)),credentials);
    }
    static public List<String> getLog(Web3j web3j, Credentials credentials, int count) throws Exception
    {
        ArrayList<Type> list = new ArrayList<Type>();
        List params = Arrays.asList(new org.web3j.abi.datatypes.Uint(java.math.BigInteger.valueOf(count)));

        List<TypeReference<?>> returnTypes = Arrays.<TypeReference<?>>asList(new TypeReference<org.web3j.abi.datatypes.Utf8String>(){},new TypeReference<org.web3j.abi.datatypes.Utf8String>(){});
        List<org.web3j.abi.datatypes.Type> value = EthService.getState( web3j, contract, getFunction,
            credentials,params,returnTypes);
        List<String> myList = new ArrayList();
        
        for(int i=0;i<value.size();i++)
        {
            myList.add(value.get(i).toString());
        }
        return myList;
    }

    static public String getVIN(Web3j web3j, Credentials credentials) throws Exception
    {
        ArrayList<Type> list = new ArrayList<Type>();
        List params = Arrays.asList();

        List<TypeReference<?>> returnTypes = Arrays.<TypeReference<?>>asList(new TypeReference<org.web3j.abi.datatypes.Utf8String>() {
        });

        List<Type> t = Collections.emptyList();
        List<org.web3j.abi.datatypes.Type> value = EthService.getState( web3j, contract, getVINFunction,
                credentials,params,returnTypes);
        List<String> myList = new ArrayList();


        for(int i=0;i<value.size();i++)
        {
            return value.get(i).toString();
        }
        return "";
    }
}
