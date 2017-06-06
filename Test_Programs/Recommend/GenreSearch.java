import java.io.*;
import java.util.*;
import java.text.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GenreSearch
{
	public static void main(String[] args)
	{
		try
		{
			String genreSearch = "http://www.imdb.com/search/title?count=10&genres=";
			
			genreSearch += args[0];
			
			for(int i = 1; i <= args.length; i++)
			{
				if(args[i].equals("feature"))
				{
					break;
				}
				else if(args[i].equals("tv_series"))
				{
					break;
				}
				
				genreSearch += "," + args[i];
			}
			genreSearch += "&title_type=" + args[args.length - 1];
			
			Document doc = Jsoup.connect(genreSearch).get();
			
			String filmNames = doc.select(".lister-item-header > a").toString();
			
			String[] filmArray = filmNames.split("[\r\n]+");
			String[][] filmInfo = new String[10][2];
			
			for(int i = 0; i < 10; i++)
			{
				filmArray[i] = filmArray[i].replace("</a>", "");
				filmInfo[i][0] = filmArray[i].substring(filmArray[i].indexOf("\"") + 1);
				filmInfo[i][0] = filmInfo[i][0].substring(0, filmInfo[i][0].lastIndexOf("/"));
				filmInfo[i][0] = "http://www.imdb.com" + filmInfo[i][0];
				filmInfo[i][1] = filmArray[i].substring(filmArray[i].lastIndexOf(">") + 1);
				System.out.println(filmInfo[i][0]);
				System.out.println(filmInfo[i][1]);
			}
		}
		catch (Exception e)
		{
		}
	}
}