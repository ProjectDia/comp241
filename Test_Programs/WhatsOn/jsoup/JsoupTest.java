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
		Integer[] channels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 20, 25, 26, 27, 28, 29, 30, 31, 32, 33, 35, 36, 39, 41, 48, 50, 51, 70, 71};
		Integer[] ids = {9, 10, 11, 12, 13, 38, 14, 27, 30, 28, 51, 37, 55, 20, 46, 58, 45, 7, 39, 56, 21, 36, 59, 18, 62, 8, 35, 49, 41, 60, 43, 23, 24, 25, 26};
		Integer channel = ids[Arrays.asList(channels).indexOf(args[0])];
		try {		
   			String url = "http://www.freeviewnz.tv/tvguide/whats-on/?channelId=" + channel;
			Document doc = Jsoup.connect(url).userAgent("Mobile").get();
			
			String showName = doc.select("div > h3").first().toString().replace("<h3>", "").replace("</h3>", "");
     	  		System.out.println(showName);
     	  	
     	  		String description = doc.select("div.synopsis").first().toString().replace("<div class=\"synopsis\">", "").replace("</div>", "");
     	  		description = description.substring(3, description.lastIndexOf('.') + 1);
     	  		System.out.println(description);
		}
		catch (Exception ex){ 
			ex.printStackTrace(); 
			return; 
		}
	}
}
