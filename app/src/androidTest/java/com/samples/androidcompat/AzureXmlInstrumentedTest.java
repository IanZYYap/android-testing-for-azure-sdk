package com.samples.androidcompat;

import android.content.Context;
import com.azure.xml.*;
import com.azure.xml.implementation.DefaultXmlReader;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import javax.xml.stream.XMLStreamException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AzureXmlInstrumentedTest {
    @Test
    public void xmlSerializableTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // TODO: Resolve XMLOutputFactory.newFactory() in the main repo
        try {
            XmlReader reader = DefaultXmlReader.fromString("");
            assertNotNull(reader);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        fail();
    }
}