package com.samples.androidcompat;

import android.content.Context;
import com.azure.xml.*;
import com.azure.xml.implementation.DefaultXmlReader;
import com.azure.xml.implementation.DefaultXmlWriter;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import javax.xml.stream.XMLStreamException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AzureXmlInstrumentedTests {
    /**
     * Tests whether DefaultXMLReader can make an XmlReader. Will throw NoSuchMethodError because
     * the StAX library is missing XmlInputFactory.newFactory() which is in the JDK, it only has
     * the XmlInputFactory.newInstance() method which should function identically to newFactory()
     */
    @Test
    public void defaultXmlReaderTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertThrows(NoSuchMethodError.class,() -> {
            try {
                XmlReader reader = DefaultXmlReader.fromString("");
                assertNotNull(reader);
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Tests whether DefaultXMLWriter can make an XmlWriter. Will throw NoSuchMethodError because
     * the StAX library is missing XmlOutputFactory.newFactory() which is in the JDK, it only has
     * the XmlOutputFactory.newInstance() method which should function identically to newFactory()
     */
    @Test
    public void defaultXmlWriterTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertThrows(NoSuchMethodError.class,() -> {
            try {
                XmlWriter writer = DefaultXmlWriter.toStream(new ByteArrayOutputStream());
                assertNotNull(writer);
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Tests whether the Replacement DefaultXMLReader can make an XmlReader.
     */
    @Test
    public void defaultXmlReaderReplacementTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        try {
            XmlReader reader = DefaultXmlReader_Android_Replace.fromString("");
            assertNotNull(reader);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests whether the Replacement DefaultXMLWriter can make an XmlWriter.
     */
    @Test
    public void defaultXmlWriterReplacementTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        try {
            XmlWriter writer = DefaultXmlWriter_Android_Replace.toStream(new ByteArrayOutputStream());
            assertNotNull(writer);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests whether the Reflection DefaultXMLWriter can make an XmlWriter.
     */
    @Test
    public void defaultXmlReaderReflectionTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        try {
            XmlReader reader = DefaultXmlReader_Android_Reflection.fromString("");
            assertNotNull(reader);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests whether the Reflection DefaultXMLWriter can make an XmlWriter.
     */
    @Test
    public void defaultXmlWriterReflectionTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        try {
            XmlWriter writer = DefaultXmlWriter_Android_Reflection.toStream(new ByteArrayOutputStream());
            assertNotNull(writer);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}