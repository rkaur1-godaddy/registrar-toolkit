package godaddy.registry.jtoolkit2.se.app;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import godaddy.registry.jtoolkit2.se.DomainCreateResponse;
import godaddy.registry.jtoolkit2.xml.XMLDocument;
import godaddy.registry.jtoolkit2.xml.XMLParser;

public class DomainCreateApplicationResponseExtensionTest {

    private static final String XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">" +
                    "  <response>" +
                    "    <result code=\"1000\">" +
                    "      <msg lang=\"en\">Command completed successfully</msg>" +
                    "    </result>" +
                    "    <resData>" +
                    "      <domain:creData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\">" +
                    "        <domain:name>domdomdom-one4.apple</domain:name>" +
                    "        <domain:crDate>2013-04-01T21:51:27Z</domain:crDate>" +
                    "      </domain:creData>" +
                    "    </resData>" +
                    "    <extension>" +
                    "<app:creData xmlns:app=\"urn:ar:params:xml:ns:application-1.0\">" +
                    "        <app:id>ACF7B10EBC3284E34B4A7C350DA27EA7B-ARI</app:id>" +
                    "                <app:phase>sunrise</app:phase>" +
                    "      </app:creData>" +
                    "    </extension>" +
                    "    <trID>" +
                    "      <clTRID>ABC-12345</clTRID>" +
                    "      <svTRID>1a1f7fa4-c898-4ad0-8f9b-2c9d40f0d96a</svTRID>" +
                    "    </trID>" +
                    "  </response>" +
                    "</epp>";

    private XMLParser xmlParser;
    private DomainCreateApplicationResponseExtension responseExtension;
    private DomainCreateResponse domainResponse;



    @Before
    public void setUp() throws Exception {
        xmlParser = new XMLParser();
        responseExtension = new DomainCreateApplicationResponseExtension();
        domainResponse = new DomainCreateResponse();
        domainResponse.registerExtension(responseExtension);
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        domainResponse.fromXML(doc);
    }

    @Test
    public void shouldGetId() {
        assertTrue(responseExtension.isInitialised());
        assertEquals(responseExtension.getId(), "ACF7B10EBC3284E34B4A7C350DA27EA7B-ARI");
    }

    @Test
    public void shouldGetPhase() {
        assertTrue(responseExtension.isInitialised());
        assertEquals(responseExtension.getPhase(), "sunrise");

    }

}
