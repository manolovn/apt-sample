package com.manolovn.android.apt_sample;

import android.app.Activity;
import android.os.Bundle;
import com.manolovn.android.apt_sample.annotations.Debug;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            doNothing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Debug
    private void doNothing() throws InterruptedException {
        Thread.sleep(3000);
    }

}
