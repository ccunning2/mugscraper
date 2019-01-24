import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;

import java.io.File;
import java.util.List;

import static java.lang.Thread.sleep;

public class MugScraper {


    public static void main(String[] args) {
        try (final WebClient client = new WebClient()) {


            //client.getOptions().setCssEnabled(false);
            //client.getOptions().setJavaScriptEnabled(false);

            final HtmlPage page = client.getPage("https://journalstar.com/mugshots/");
            sleep(15000);
            System.out.println(page.getBody().asText());
           // page.save(new File("/Users/cameroncunning/Java/test"));
            final DomNodeList<DomNode> divs = page.querySelectorAll("div.mugshots__inmates");
//            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//div[@class='mugshots__inmate']");
            for (DomNode item : divs) {
                System.out.println(item.asText());
            }

//            final HtmlDivision div = page.getHtmlElementById("some_div_id");
//            final HtmlAnchor anchor = page.getAnchorByName("anchor_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
