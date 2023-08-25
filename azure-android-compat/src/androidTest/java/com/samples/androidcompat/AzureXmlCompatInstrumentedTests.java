package com.samples.androidcompat;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.azure.xml.XmlReader;
import com.azure.xml.XmlWriter;
import com.azure.xml.implementation.DefaultXmlReader;
import com.azure.xml.implementation.DefaultXmlWriter;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;

/**
 * Instrumented tests, which will execute on an Android device.
 * These cases test when the Android user is attempting to use Azure XML methods, namely
 * DefaultXMLReader, DefaultXMLWriter, XmlReader, which were flagged as incompatible by
 * the Animal Sniffer plugin with an Android API level 26 signature.
 *
 */
@RunWith(AndroidJUnit4.class)
public class AzureXmlCompatInstrumentedTests {
    /* When the current DefaultXMLReader and DefaultXMLWriter are used, the following error is
    thrown on build:

    error: cannot access XMLStreamException
            XmlReader reader = DefaultXmlReader.fromString("");
                                                          ^
    class file for javax.xml.stream.XMLStreamException not found
    1 error

    @Test
    public void defaultXmlReaderTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertThrows(NoClassDefFoundError.class,() -> {
            XmlReader reader = DefaultXmlReader.fromString("");
            assertNotNull(reader);
        });
    }
    @Test
    public void defaultXmlWriterTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertThrows(NoClassDefFoundError.class,() -> {
            XmlWriter writer = DefaultXmlWriter.toStream(new ByteArrayOutputStream());
            assertNotNull(writer);
        });
    }
    */
}