public class GetRecommend
{
	public static void main()
	{
		//Received from JSONToJava function
		String genre;
		String type;
		//if wanting recommendation based on genre
		//genre must be in format a,b,c
		//type must be converted to either feature (for a movie) or tv_series
		genreSearch(genre, type);
		
		//if wanting recommendation based on actor
		//must be name seperated by spaces
		actorSearch(name);
	}
	
	public void genreSearch(String genre, String type)
	{
		try
		{
			String genreSearch = "http://www.imdb.com/search/title?count=10&genres=";
			
			genreSearch += genre + "&title_type=" + type;
			
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
	
	public void actorSearch(String name)
	{
		try
		{
			String nameSearch = "http://www.imdb.com/search/name?name=";
			
			String[] nameArray = name.split(" ");
			
			nameSearch += nameArray[0]
			
			for(int i = 1; i < nameArray.length - 1; i++)
			{
				nameSearch += ("%20" + nameArray[i]);
			}
			
			Document doc = Jsoup.connect(nameSearch).get();
			
			String actorURLEnd = doc.select(".name > a").first().toString();
			actorURLEnd = actorURLEnd.substring(9, actorURLEnd.lastIndexOf("/"));
			actorURLEnd = actorURLEnd.substring(0, actorURLEnd.lastIndexOf("/"));
			
			String actorURL = "http://www.imdb.com" + actorURLEnd;
			
			Document actorPage = Jsoup.connect(actorURL).get();
			
			String knownFor = actorPage.select(".knownfor-title-role > a").toString();
			String[] movieNames = knownFor.split("[\r\n]+");
			String[][] movieInfo = new String[4][2];
			
			for(int i = 0; i < 4; i++)
			{
				movieNames[i] = movieNames[i].replace("</a>", "");
				movieInfo[i][0] = movieNames[i].substring(movieNames[i].indexOf("\"") + 1);
				movieInfo[i][0] = movieInfo[i][0].substring(0, movieInfo[i][0].lastIndexOf("/"));
				movieInfo[i][0] = "http://www.imdb.com" + movieInfo[i][0];
				movieInfo[i][1] = movieNames[i].substring(movieNames[i].lastIndexOf(">") + 1);
				System.out.println(movieInfo[i][0]);
				System.out.println(movieInfo[i][1]);
			}
		}
		catch (Exception e)
		{
		}
	}
}