package com.eb.ethereum.ethereumandroid;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

public class EthService {

    public static String getBalance(Web3j web3j) throws Exception
    {
        EthGetBalance ethGetBalance = web3j
                .ethGetBalance("0xbD6Ae16B2E6F4217B328452b0B7EbE02f8574B14", DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get();

        double balance = (double)ethGetBalance.getBalance().divide(BigInteger.valueOf(10000L)).
                divide(BigInteger.valueOf(100000000000L)).longValue()/ 1000.0;

        return Double.toString(balance);
    }

    public static String callFunction(Web3j web3j, String contractAddress, String functionName, List<org.web3j.abi.datatypes.Type> values, Credentials credentials) throws Exception
    {

        Function function = new Function(
             functionName,  // function we're calling
                values, new ArrayList()
             );

         EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        String encodedFunction = FunctionEncoder.encode(function);

        BigInteger gasPrice = BigInteger.valueOf(1000000000L);
        BigInteger gasLimit = BigInteger.valueOf(229224L);
        Transaction transaction = Transaction.createFunctionCallTransaction(
                credentials.getAddress(), nonce, gasPrice, gasLimit, contractAddress,
                encodedFunction);

        EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();

        String transactionHash = transactionResponse.getTransactionHash();

        return transactionHash;
    }
    
    public static List<Type> getState(Web3j web3j, String contractName, String functionName, Credentials credentials,
        List<Type> params, List<TypeReference<?>> returnTypes) throws Exception
    {
        Function function = new Function(
             functionName,  // function we're calling
             params, returnTypes
             );

        String encodedFunction = FunctionEncoder.encode(function);

        EthCall response =
        web3j.ethCall(
                Transaction.createEthCallTransaction(credentials.getAddress(), contractName, encodedFunction),
             DefaultBlockParameterName.LATEST)
             .sendAsync().get();

        List<org.web3j.abi.datatypes.Type> value =  FunctionReturnDecoder.decode(
             response.getValue(), function.getOutputParameters());

        return value;
    }
    
    public static Web3j getWeb3j(String ip) throws Exception
    {
        return Web3jFactory.build(new HttpService(ip));
    }
    
}
