package buildit.spider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * The testing framework took up a considerable amount of coding time, probably
 * more than the stakeholders anticipated.
 * 
 * Started out initially creating a spider bot {@link Reader} with the plan to
 * mock it to return the desired test HTML from files dependent on URL, but
 * considered that too restrictive as a requirement when considering designs for
 * the core algorithm.
 * 
 * It actually threatened to swallow up all the time available, so moved across
 * to {@link Reader} design which allows us to feed in {@link URL}s which
 * doesn't distinguish between files in the project file system or remote HTML
 * pages.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // can remove this later when done
public class AppTest {

    private static final String LANDING_PAGE_HTML = "/landing-page.html";

    /**
     * First test: instantiates the app and calls the primary function with the
     * URL to crawl.
     * 
     * Testable artifact: {@link SiteNode} which holds images, external links
     * and child pages.
     */
    @Test
    public void testApp() throws Exception {
        Scanner mockScanner = new Scanner();
        Reader reader = new Reader();
        App testApp = new App(reader, mockScanner);
        SiteNode output = testApp
                .crawl(TestTools.getFileUrl(LANDING_PAGE_HTML));
        assertNotNull("we should get the top site node", output);
        assertFalse("we should see child pages",
                output.getChildNodes().isEmpty());
    }

    /**
     * 
     * Test that a parent page isn't scanned again if it's child page links back
     * to it.
     */
    @Test
    public void testEachPageScannedOnlyOnce() {
        fail("TBD");
    }

    /**
     * Make sure our app can fetch the HTML and turn it into a scannable string
     * in a way we can mock easily with HTML test files.
     * 
     * @throws IOException
     *             something badly wrong
     * @throws URISyntaxException
     *             we are being stupid
     */
    @Test
    public void testGetHtml() throws IOException, URISyntaxException {
        String expected = TestTools.getHtmlTestString(LANDING_PAGE_HTML);
        Reader reader = new Reader();
        String html = reader.read(TestTools.getFileUrl(LANDING_PAGE_HTML));
        assertEquals("HTML returned should equal mocked", expected, html);
    }

    @Test
    public void testScan() throws IOException {
        Scanner scanner = new Scanner();
        SiteNode output = new SiteNode(LANDING_PAGE_HTML);
        scanner.scan(output, TestTools.getHtmlTestString(LANDING_PAGE_HTML));
        assertEquals("mock scanner will find sub-pages", 4,
                output.getChildNodes().size());
        assertEquals("mock scanner will find static content items", 1,
                output.getStaticContent().size());
        assertEquals("mock scanner will find external URLs", 2,
                output.getExternalUrls().size());

    }
}
