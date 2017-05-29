import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
//import java.util.Date;
import java.util.regex.*;
import java.text.*;
public class JsoupTest{
	public static void main(String[] args)  {
		try {		
   			String url = "http://www.freeviewnz.tv/tvguide/whats-on/?channelId=" + (Integer.parseInt(args[0]) + 8);
			Document doc = Jsoup.connect(url).userAgent("Mobile").get();
			Elements things = doc.select("div > h3");
     	  	System.out.println(things.first().toString().replace("<h3>", "").replace("</h3>", ""));
     	  	Elements thingsxd = doc.select("div.synopsis");
     	  	System.out.println(thingsxd.first().toString().replace("<div class=\"synopsis\">", "").replace("</div>", "").substring(3));
		}
		catch (Exception ex){ 
			ex.printStackTrace(); 
			return; 
		}
	}
}
