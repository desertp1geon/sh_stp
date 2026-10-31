
package com.example.sh_stp.basic.koin;

import android.content.Context;

public final class JavaKoin {

    private JavaKoin() {
    }

    public static void startKoinApplication(Context context) {
        KoinInjectionKt.startKoinApplication(context);
    }
}
