package buildit.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class TestTools {

    /**
     * @param fileName a file name of a file we can find on the classpath
     * @return the contents as a string
     * @throws IOException if things go wrong
     */
    public static String getHtmlTestString(String fileName) throws IOException {
        URL fileUrl = TestTools.class.getResource(fileName);
        StringBuilder output = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(fileUrl.openStream()))) {
            String out = "";
            while ((out = br.readLine()) != null) {
                output.append(out + System.lineSeparator());
            }
        }
        return output.toString();
    }

    /**
     * @param fileName
     *            a simple file name of a file on the classpath e.g. src/test/resources
     * @return protocol + filename, e.g. file:///C:/Stuff/test.txt
     */
    public static String getFileUrl(String fileName) {
        URL fileUrl = TestTools.class.getResource(fileName);
        return fileUrl.toString();
    }

}
