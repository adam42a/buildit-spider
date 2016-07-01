package buildit.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Retrieves the HTML at a given URL into a String.
 */
class Reader {

    /**
     * This allows us to mock the website entirely.
     * 
     * We can pass in a URI and from that get a URL and then
     * {@link URL#openConnection()} can return a mock {@link HttpURLConnection}
     * and then {@link HttpURLConnection#getInputStream()} can return the
     * {@link InputStream} from our chosen HTML test file.
     * 
     * @param urlString
     *            already checked for MalformedURL
     * @return HTML string
     * @throws MalformedURLException
     *             must do validation for this before calling
     * @throws ProtocolException
     *             validate prior to calling
     * @throws IOException
     *             don't worry at this point
     * @throws URISyntaxException
     *             if someone asks for rubbish
     */
    public String read(String urlString) throws MalformedURLException,
            ProtocolException, IOException, URISyntaxException {
        URLConnection conn = new URL(urlString).openConnection();
        conn.setRequestProperty("Accept", "text/html");
        StringBuilder html = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            String out = "";
            while ((out = br.readLine()) != null) {
                html.append(out + System.lineSeparator());
            }
        }
        return html.toString();
    }

}
