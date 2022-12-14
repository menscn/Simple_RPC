package com.mensc.rpc.client;

import com.msc.rpc.client.call.AsyncCallService;
import com.msc.rpc.client.call.SyncCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mSc
 * @version 1.0
 * @Package com.mensc.rpc.client
 * @Description:
 */
@SpringBootApplication
public class RPCClientApplication implements CommandLineRunner {

    @Autowired
    private SyncCallService syncCallService;

    @Autowired
    private AsyncCallService asyncCallService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RPCClientApplication.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        asyncCallService.asyncCall();
        //syncCallService.syncCallTest();
    }
}