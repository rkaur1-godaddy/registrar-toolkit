package neustar.registry.jtoolkit2.se.eps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import neustar.registry.jtoolkit2.Timer;
import neustar.registry.jtoolkit2.se.CLTRID;

public class EpsExemptCommandTest {
    private EpsExemptCommand cmd1;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
        cmd1 = new EpsExemptCommand("test");
    }

    @Test
    public void shouldAddLabelToCommand() {
        try {
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command><check><exempt xmlns=\"http://ns.uniregistry.net/eps-1.0\""
                    + " xsi:schemaLocation=\"http://ns.uniregistry.net/eps-1.0 eps-1.0.xsd\">"
                    + "<label>test</label></exempt></check>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd1.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldAddMultipleLabelsToCommand() {
        try {
            cmd1 = new EpsExemptCommand(new String[] {"test", "test2"});
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command><check><exempt xmlns=\"http://ns.uniregistry.net/eps-1.0\""
                    + " xsi:schemaLocation=\"http://ns.uniregistry.net/eps-1.0 eps-1.0.xsd\">"
                    + "<label>test</label><label>test2</label>"
                    + "</exempt></check><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd1.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testGetCommandType() {
        assertEquals("check", cmd1.getCommandType().getCommandName());
    }

    @Test
    public void testGetObjectType() {
        assertEquals("eps", cmd1.getObjectType().getName());
    }

    @Test
    public void testToXML() {
        try {
            assertNotNull(cmd1.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

