package com.eb.ethereum.ethereumandroid;

import java.util.Date;

public class Log {

    String log;
    Date date;
    private Log(){};
    
    public Log(String l)
    {
        log = l;
        date = new Date();
    }
}
