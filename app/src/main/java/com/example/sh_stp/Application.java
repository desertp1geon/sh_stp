package com.example.sh_stp;

import com.example.sh_stp.basic.koin.JavaKoin;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JavaKoin.startKoinApplication(this);
    }
}