package com.eb.ethereum.ethereumandroid;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import 	android.widget.Button;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String ip = "http://35.226.29.195:3389";
    private int port = 0;
    private String wallet = "/sdcard/wallet.json";
    private Credentials credentials;
    private Web3j web3j;
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     *         If the activity is being re-initialized after
     *         previously being shut down then this Bundle contains the data it most
     *         recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
            }

            PackageManager m = getPackageManager();
            String s = getPackageName();

            setContentView(R.layout.activity_main);
            
            Button addButton = (Button)findViewById(R.id.AddLog); 
            Button getButton = (Button)findViewById(R.id.GetLog); 
            web3j = EthService.getWeb3j(ip);
            credentials = EthWallet.getEthWallet(new File(wallet), "wallet");
            BigInteger c = credentials.getEcKeyPair().getPrivateKey();
            addButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) 
                {
                    EditText text = (EditText)findViewById(R.id.LogInput); 
                    String value = text.getText().toString(); 
                    
                    Log newlog = new Log(value);
                    try
                    {
                        String myString = EthereumLogger.addLog(web3j, credentials, newlog);
                        Toast.makeText(MainActivity.this, "Hash Value is: " + myString, Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                } 
            }); 

            getButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) 
                { 
                    try
                    {
                        TextView text = (TextView)findViewById(R.id.LogOutput);
                        text.setText("");
                        String log = "";
                        for(int i=0;i<5;i++) {
                            List<String> list = EthereumLogger.getLog(web3j, credentials, i+1);
                            for (String string : list) {
                                log = log + " " + string;
                            }
                            text.append(log + "\n");
                            log = "";
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                } 
            });
            TextView VIN = (TextView)findViewById(R.id.VIN);
            TextView Balance = (TextView)findViewById(R.id.Balance);

            Balance.setText("Balance is " + EthService.getBalance(web3j));
            VIN.setText("Car VIN is: " + EthereumLogger.getVIN(web3j,credentials));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(com.eb.ethereum.ethereumandroid.R.menu.main, menu);
        return true;
    }
}

