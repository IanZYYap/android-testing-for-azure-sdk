package com.samples.androidcompat;

import static com.azure.core.implementation.ReflectionUtils.getConstructorInvoker;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.lang.invoke.MethodHandles;

/**
 * Instrumented test cases for ReflectionUtils.
 * For reasons not yet certain, an API level 26 emulator seems to function correctly while
 * greater levels such as 30 and 34 throw NoSuchMethodExceptions caused by:
 * java.lang.invoke.MethodHandles$Lookup.<init>
 * Might be something to do with the Gradle build file, as the AzureAndroidValidation library does
 * not exhibit this unusual behaviour at all, its tests run as expected for all emulators
 */
@RunWith(AndroidJUnit4.class)
public class AzureCoreReflectionUtilsCompatInstrumentedTest {
    // Test a method from Azure Core Utils that is throwing an exception for Android in some cases
    /*@Test
    public void compatReflectionUtilsTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        getLookupToUse(null);
    }*/
    @Test
    // Test Alan's reworked ReflectionUtils that should be more compatible with Android
    public void coreReflectionUtilsTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //Throws NullPointerException in API level 26, returns Invoker with higher API levels
        assertThrows(NullPointerException.class, () -> {
            getConstructorInvoker(null, null);
        });
    }
    @Test
    public void coreReflectionUtilsWithClassTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //Throws IllegalArgumentException in API level 26, returns Invoker at higher API levels
        assertThrows(IllegalArgumentException.class, () -> {
            getConstructorInvoker(Class.class, null);
        });
    }

    @Test
    public void coreReflectionUtilsWithClassConstructor() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Returns NoSuchMethodException for all emulators
        assertThrows(NoSuchMethodException.class, () -> {
            getConstructorInvoker(Class.class,
                    Class.class.getDeclaredConstructor(Class.class));
        });
    }
    @Test
    public void coreReflectionUtilsWithMethodHandleConstructor() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Returns IllegalArgumentException for 26, and NoSuchMethodException for higher levels
        assertThrows(IllegalArgumentException.class, () -> {
            getConstructorInvoker(MethodHandles.Lookup.class,
                    MethodHandles.Lookup.class.getDeclaredConstructor(Class.class));
        });
    }
}
