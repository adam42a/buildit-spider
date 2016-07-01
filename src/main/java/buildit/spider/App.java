package buildit.spider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This is the entry point for the Spider app.
 * 
 * To return something testable, we will collate all the links and images as
 * properties into nodes which can be nested like the tree of a website's site
 * map.
 * 
 * The website's landing page is represented by the top {@link SiteNode} which
 * contains a list of child nodes and grandchild nodes and so forth in a tree.
 * 
 *
 */
public class App {

    private final Scanner scanner;
    private final Reader reader;

    public App(Reader newReader, Scanner newScanner) {
        scanner = newScanner;
        reader = newReader;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    /**
     * The plan with this one is to use a stack to hold all the pages that need
     * scanning, adding to it as we go along whenever we find new children,
     * rather than using a recursive algorithm.
     * 
     * Because whenever we find a new page, we are deep within the scanning, we
     * just create a new {@link SiteNode} by instantiating it with the URL and
     * that's it at that point.
     * 
     * Once the scan of a page is finished, we process any children that were
     * found.
     * 
     * @param url
     *            string with full protocol e.g. https:// or file://
     * @return the {@link SiteNode} representing this landing page
     * @throws URISyntaxException
     *             if the parameter is gobbledy-gook
     * @throws IOException
     *             if something bad goes wrong
     * @throws ProtocolException
     *             if parameter has no protocol prefix
     * @throws MalformedURLException
     *             see {@link URISyntaxException} above
     */
    public SiteNode crawl(String url) throws MalformedURLException,
            ProtocolException, IOException, URISyntaxException {
        // need something like this to check we only scan pages once
        Map<String, SiteNode> map = new HashMap();
        // read the link
        SiteNode landingPage = new SiteNode(url);
        String htmlSource = reader.read(url);
        scanner.scan(landingPage, htmlSource);
        Stack<SiteNode> stack = new Stack();
        stack.addAll(landingPage.getChildNodes());
        while (!stack.isEmpty()) {
            // scan all children in stack
            SiteNode child = stack.pop();
            htmlSource = reader.read(child.getUrl());
            scanner.scan(child, htmlSource);
            stack.addAll(child.getChildNodes());
        }
        return landingPage;
    }
}
