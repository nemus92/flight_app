package com.myflight.app;

import com.myflight.app.service.GateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    // TODO runner for executing service call on app startup, in this example adding gates and flights

    @Autowired
    GateService gateService;

    @Override
    public void run(String... args) throws Exception {
        gateService.insertGatesToDB();
    }
}
