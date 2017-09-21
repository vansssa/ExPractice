package com.utapass.expense;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    @Category({Critical.class,FAST.class})
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.utapass.expense", appContext.getPackageName());
    }


    public interface IntegrationTests {}
    public interface PerformanceTests {}
    public interface Priority {}
    public interface Medium extends Priority {}
    public interface Low extends Priority {}
    public interface High extends Priority {}
    public interface Critical extends Priority {}
    public interface RAT extends IntegrationTests {}
    public interface FAST extends IntegrationTests {}
    public interface TOFT extends IntegrationTests {}
}
