package com.example.aptsample;

import android.app.Activity;
import android.os.Bundle;
import com.manolovn.android.apt_sample.MethodDebug;
import com.manolovn.android.apt_sample.annotations.Debug;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MethodDebug.scan(this);

        try {
            doNothing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Debug
    public void doNothing() throws InterruptedException {
        Thread.sleep(3000);
    }

}
