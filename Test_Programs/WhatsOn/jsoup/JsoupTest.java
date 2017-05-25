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

        Document doc = Jsoup.connect(url)
        .userAgent("Mobile")
        
        .get();

        String result = doc.text();

     //  System.out.println(result);
      
		  
       
        String[] tokens  = result.split("\\s '");
        for (String s:tokens){
        	System.out.println(s);
        	list.add(s);
        }
        
       String whatsOn =  list.whatsOn();
       
    }

}
