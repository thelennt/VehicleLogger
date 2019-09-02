package com.eb.ethereum.ethereumandroid;


import java.io.File;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class EthWallet {

    static public Credentials getEthWallet(File file, String password) throws Exception
    {
        return WalletUtils.loadCredentials(password, file.getAbsoluteFile());
    }
    
    static public Credentials createEthWallet(String password, String destination) throws Exception
    {
         String dest = WalletUtils.generateLightNewWalletFile(password,
            new File(destination));
        return WalletUtils.loadCredentials(password, new File(dest));
    }
}
