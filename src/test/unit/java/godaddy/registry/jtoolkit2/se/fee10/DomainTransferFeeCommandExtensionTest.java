package godaddy.registry.jtoolkit2.se.fee10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import godaddy.registry.jtoolkit2.Timer;
import godaddy.registry.jtoolkit2.se.CLTRID;
import godaddy.registry.jtoolkit2.se.Command;
import godaddy.registry.jtoolkit2.se.DomainTransferRequestCommand;
import godaddy.registry.jtoolkit2.se.Period;


public class DomainTransferFeeCommandExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtension() throws SAXException {

        Command cmd = new DomainTransferRequestCommand("jtkutest.com.au", new Period(3), "jtkUT3st");

        final DomainTransferFeeCommandExtension ext =
                new DomainTransferFeeCommandExtension(BigDecimal.valueOf(10.00), "USD");

        try {
            cmd.appendExtension(ext);

            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command>"
                    + "<transfer op=\"request\">"
                    + "<transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name>"
                    + "<period unit=\"y\">3</period>"
                    + "<authInfo>"
                    + "<pw>jtkUT3st</pw>"
                    + "</authInfo>"
                    + "</transfer>"
                    + "</transfer>"
                    + "<extension>"
                    + "<transfer xmlns=\"urn:ietf:params:xml:ns:epp:fee-1.0\">"
                    +  "<currency>USD</currency>"
                    + "<fee>10.00</fee>"
                    + "</transfer>"
                    + "</extension>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                    + "</command>"
                    + "</epp>";

            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }

    }
}

