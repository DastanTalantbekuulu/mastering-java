package org.jqurantree.tanzil;

import java.io.InputStream;

import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;
import org.jqurantree.core.resource.ResourceUtil;
import org.jqurantree.orthography.Sura;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class TanzilReader {

    public static final String TANZIL_RESOURCE_PATH = "/tanzil/quran-uthmani.xml";

    public Sura[] readXml() {
        return readXml(TANZIL_RESOURCE_PATH);
    }

    public Sura[] readXml(String resourcePath) {

        try {
            TanzilHandler handler = new TanzilHandler();

            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);

            InputStream stream = ResourceUtil.open(resourcePath);
            InputSource source = new InputSource(stream);

            reader.parse(source);
            stream.close();

            return handler.getChapters();

        } catch (Exception exception) {
            throw new QuranException(Errors.INVALID_TANZIL_XML, exception);
        }
    }
}
