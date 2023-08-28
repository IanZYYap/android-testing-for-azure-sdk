package com.samples.androidcompat;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.azure.core.management.implementation.polling.PollOperation;
import com.azure.core.management.implementation.serializer.AzureJacksonAdapter;
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerAdapter;
import com.azure.xml.XmlReader;
import com.azure.xml.implementation.DefaultXmlReader;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Instrumented tests, which will execute on an Android device.
 *
 */
@RunWith(AndroidJUnit4.class)
public class AzureCoreMgmtCompatInstrumentedTests {
    @Test
    public void defaultCoreMgmtPollOperationTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        SerializerAdapter adapter = AzureJacksonAdapter.createDefaultSerializerAdapter();
        Type pollResultType = (GenericArrayType) () -> null;
        assertNull(PollOperation.deserialize(adapter, null, pollResultType));
    }
}