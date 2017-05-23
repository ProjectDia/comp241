import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.util.regex.*;
public class JsoupTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
		LineList list = new LineList();
        String url = "http://www.freeviewnz.tv/tvguide/whats-on/?channelId=9";

        Document doc = Jsoup.connect(url).get();

        String result = doc.text();

        System.out.println(result);
        
        
        Pattern pattern = Pattern.compile("^([a-zA-Z]+)([0-9]+)(.*)");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()){
        	list.add(matcher.group());
        	System.out.println(matcher.group());
        	
        }
       
    }

}
