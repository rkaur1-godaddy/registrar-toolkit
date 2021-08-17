package godaddy.registry.jtoolkit2.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import godaddy.registry.jtoolkit2.ErrorPkg;

/**
 * An XMLParser parses an XML document in String form and generates a DOM
 * representation of the XML document.  This implementation is not thread safe;
 * a single instance may not be used simultaneously in separate threads.  An
 * attempt is made to load schemas from the location specified in the
 * SessionManagerProperties specified.  Documents are validated against any
 * such loaded schemas unless validation is disabled.
 *
 * Uses the user level logger.
 */
public class XMLParser {
    public static final String USER_LOGGER = ".user";
    private static DocumentBuilderFactory builderFactory;
    private static String pname;
    private static boolean isInitialised = false;
    private DocumentBuilder builder;

    static {
        pname = XMLParser.class.getPackage().getName();

        builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        init();
    }

    /**
     * Create an XMLParser as configured from <a
     * href="#init(SessionManagerProperties)">init</a>.
     */
    public XMLParser() {
        if (builderFactory == null) {
            throw new Error("XMLParser was not initialised prior to use.");
        }

        try {
            builder = builderFactory.newDocumentBuilder();
            builder.setErrorHandler(HandlerFactory.newInstance());
        } catch (ParserConfigurationException pce) {
            Logger.getLogger(pname + USER_LOGGER).log(Level.SEVERE, "XML parser configuration:", pce);
        }
    }

    /**
     * Initialise the DocumentBuilderFactory which provides XML document
     * builders to the toolkit, and optionally the Schema instances to be used
     * for validating commands generated by the toolkit. To be called only once
     * at application startup.
     * @see EPPSchemaProvider
     */
    public static synchronized void init() {
        if (!isInitialised) {
            try {
                builderFactory.setFeature(
                        javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING,
                        true);
                builderFactory.setSchema(EPPSchemaProvider.getSchema());
            } catch (UnsupportedOperationException uoe) {
                Logger.getLogger(pname + USER_LOGGER).warning(uoe.getMessage());
                Logger.getLogger(pname + USER_LOGGER).warning(
                        ErrorPkg.getMessage(
                            "xml.parser.operation.unsupported"));
            } catch (ParserConfigurationException pce) {
                Logger.getLogger(pname + USER_LOGGER).warning(pce.getMessage());
                Logger.getLogger(pname + USER_LOGGER).warning(
                        ErrorPkg.getMessage(
                            "xml.parser.feature.unsupported"));
            }

            isInitialised = true;
        }
    }

    /**
     * Generate a DOM representation of the given XML source document.
     * Parsing of the input string is delegated to a DocumentBuilder
     * implementation which is obtained from the DocumentBuilderFactory
     * configured during <a href="#init(SessionManagerProperties)">init</a>.
     */
    public XMLDocument parse(String xml) throws ParsingException {
        ByteArrayInputStream xmlInputStream
            = new ByteArrayInputStream(xml.getBytes());

        try {
            Document doc = builder.parse(xmlInputStream);
            return new XMLDocument(doc.getDocumentElement(), xml);
        } catch (SAXException saxe) {
            throw new ParsingException(saxe);
        } catch (IOException ioe) {
            throw new ParsingException(ioe);
        }
    }

    DocumentBuilder getDocumentBuilder() {
        return builder;
    }
}

