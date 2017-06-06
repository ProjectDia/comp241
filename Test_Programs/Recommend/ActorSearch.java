import java.io.*;
import java.util.*;
import java.text.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ActorSearch
{
	public static void main(String[] args)
	{
		try
		{
			String nameSearch = "http://www.imdb.com/search/name?name=";
			
			nameSearch += args[0];
			
			for(int i = 1; i <args.length; i++)
			{
				nameSearch += ("%20" + args[i]);
			}
			
			Document doc = Jsoup.connect(nameSearch).get();
			
			String actorURLEnd = doc.select(".name > a").first().toString();
			actorURLEnd = actorURLEnd.substring(9, actorURLEnd.lastIndexOf("/"));
			actorURLEnd = actorURLEnd.substring(0, actorURLEnd.lastIndexOf("/"));
			
			String actorURL = "http://www.imdb.com" + actorURLEnd;
			
			Document actorPage = Jsoup.connect(actorURL).get();
			
			String knownFor = actorPage.select(".knownfor-title-role > a").toString();
			String moviePosterHTML = actorPage.select(".knownfor-title > a").toString();
			String[] moviePosterLink = moviePosterHTML.split("[\r\n]+");
			String[] movieNames = knownFor.split("[\r\n]+");
			String[][] movieInfo = new String[4][3];
			
			for(int i = 0; i < 4; i++)
			{
				movieNames[i] = movieNames[i].replace("</a>", "");
				movieInfo[i][0] = movieNames[i].substring(movieNames[i].indexOf("\"") + 1);
				movieInfo[i][0] = movieInfo[i][0].substring(0, movieInfo[i][0].lastIndexOf("/"));
				movieInfo[i][0] = "http://www.imdb.com" + movieInfo[i][0];
				movieInfo[i][1] = movieNames[i].substring(movieNames[i].lastIndexOf(">") + 1);
				movieInfo[i][2] = moviePosterLink[i].substring(moviePosterLink[i].indexOf("src=") + 5);
				movieInfo[i][2] = movieInfo[i][2].substring(0, movieInfo[i][2].indexOf("\""));
				System.out.println(movieInfo[i][2]);
				System.out.println(movieInfo[i][0]);
				System.out.println(movieInfo[i][1]);
			}
		}
		catch (Exception e)
		{
		}
	}
}