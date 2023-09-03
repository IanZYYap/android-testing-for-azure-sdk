package com.samples.androidcompat;

//import static com.azure.core.implementation.ReflectionUtils.getConstructorInvoker;
import static com.samples.androidcompat.ReflectionUtils.getLookupToUse;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AzureCoreInstrumentedTest {
    // Test a method from Azure Core Utils that is throwing an exception for Android in some cases
    @Test
    public void reflectionUtilsTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        getLookupToUse(null);
    }
/*    @Test
    // Test Alan's reworked ReflectionUtils that should be more compatible with Android
    public void reflectionUtilsTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        getConstructorInvoker(Class.class, null);
    }*/
}
