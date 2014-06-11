package com.manolovn.android.apt_sample;

import com.manolovn.android.apt_sample.annotations.Debug;
import org.junit.Test;

/**
 * Main test
 *
 * @author manolovn
 */
public class MainTest {

    @Test
    public void annotationTest() {
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
