package com.samples.azureandroidvalidation;

import static com.azure.core.implementation.ReflectionUtils.getConstructorInvoker;
//import static com.azure.core.implementation.ReflectionUtils.getLookupToUse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.invoke.MethodHandles;

/**
 * Instrumented test cases for ReflectionUtils.
 * Should exhibit expected behaviours for Android API level 26 and above.
 */
@RunWith(AndroidJUnit4.class)
public class AzureCoreReflectionUtilsValidationInstrumentedTests {
    //Test ReflectionUtils method from azure-core that may throw an ExceptionInInitializerError
    /*@Test
    public void validationReflectionUtilsTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertThrows(NullPointerException.class, () -> {
            getLookupToUse(null);
        });
    }*/
    @Test
    // Test Alan's reworked ReflectionUtils that should be more compatible with Android
    public void coreReflectionUtilsTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //Throws NullPointerException for all emulators
        assertThrows(NullPointerException.class, () -> {
            getConstructorInvoker(null, null);
        });
    }
    @Test
    public void coreReflectionUtilsWithClassTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //Throws IllegalArgumentException for all emulators
        assertThrows(IllegalArgumentException.class, () -> {
            getConstructorInvoker(Class.class, null);
        });
    }

    @Test
    public void coreReflectionUtilsWithClassConstructor() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Throws NoSuchMethodException for all emulators
        assertThrows(NoSuchMethodException.class, () -> {
            getConstructorInvoker(Class.class,
                    Class.class.getDeclaredConstructor(Class.class));
        });
    }
    @Test
    public void coreReflectionUtilsWithMethodHandleConstructor() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Throws IllegalArgumentException for all
        assertThrows(IllegalArgumentException.class, () -> {
            getConstructorInvoker(MethodHandles.Lookup.class,
                    MethodHandles.Lookup.class.getDeclaredConstructor(Class.class));
        });
    }
}