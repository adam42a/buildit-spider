package buildit.spider;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Instantiate a {@link SiteNode} with its {@link URL} and then pass it to 
 * {@link Reader} to get its HTML and then to {@link Scanner} to extract
 * its links and content.
 */
public class SiteNode {

    /**
     * Using {@link ArrayList} rather than {@link LinkedList} because only 
     * adding at end and iterating. 
     */
    private final List<String> externalUrls = new ArrayList(); 
    private final List<String> staticContent = new ArrayList();
    private final List<SiteNode> childNodes = new ArrayList();
    private final String url;
    
    public SiteNode(String newHtml) {
        url = newHtml;
    }

    public List<SiteNode> getChildNodes() {
        return childNodes;
    }

    public List<String> getStaticContent() {
        return staticContent;
    }

    public List<String> getExternalUrls() {
        return externalUrls;
    }

    public String getUrl() {
        return url;
    }

}
