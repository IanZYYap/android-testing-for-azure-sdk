package com.samples.azureandroidvalidation;

import static com.azure.core.implementation.ReflectionUtils.getLookupToUse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented tests, which will execute on an Android device.
 * These test cases are for cases where the Android user has already installed the necessary
 * dependencies to run the Azure SDK, so it validates that the dependencies contain all of the
 * necessary libraries and methods.
 */
@RunWith(AndroidJUnit4.class)
public class AzureCoreValidationInstrumentedTests {
    /**
     *
     */
    //Test ReflectionUtils method from azure-core that may throw an ExceptionInInitializerError
    @Test
    public void reflectionUtilsTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertThrows(NullPointerException.class, () -> {
            getLookupToUse(null);
        });
    }
}