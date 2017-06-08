import java.io.*;
import java.net.*;
//NICK'S API:
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.util.regex.*;
import java.text.*;
//BRAD'S API:
import java.net.*;
import java.io.*;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  

class ServerApp{
	public static void main(String[] args) throws IOException{
		int portNumber = 1234;
		ServerSocket serverSock = null;	//creates server socket
		try {
		    serverSock = new ServerSocket(portNumber);
		    System.out.println("opened Server on port: " + portNumber);
		} catch (IOException e){
			System.err.println("could not open on port: " + portNumber);
			System.exit(0);
		}
		System.out.println("Server started");
		while(true){ //will continue until interupt exception
			Socket clientSock = null; 	//creates a socket for a client
			try {
		    	clientSock = serverSock.accept();
			} catch (IOException e){
				System.err.println("Cannot accept this sock");
				System.exit(0);
			}

			PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

			//The input from the user should be a single string with format "flag data"
			/*flag inputs: 
			0: search for channel data for data: channel number
			1: recommended algorithm for data: genre 	note: for the recommended tasks, there will be three indeces in the array, 'flag' 'name' 'type'
			2: recommended algorithm for data: actor

			*/
			String fromClient;
			while((fromClient = in.readLine()) == null){} //continue until the server receives first line of input 

			String[] userInArr = fromClient.split("\\|");

			switch(userInArr[0]){
				case "0":
					Date date = new Date();
					System.out.println(date + " A client asked for data about channel: " + userInArr[1]);
					String showName = getTVShow(userInArr[1]);
					String showInfo = getInfo(showName.split(" "));
					out.println(showName);
					out.println(showInfo);
					break;
				case "1": 
					String[][] filmInfo = genreSearch(userInArr[1], userInArr[2]);
					for(int i = 0; i < 10; i++){
						out.println(filmInfo[i][0]);
						out.println(filmInfo[i][1]);
					}
					break;
				case "2":
				out.println(userInArr[1]);
					String[][] movieInfo = actorSearch(userInArr[1]);
					for(int i = 0; i < 4; i++){
						System.out.println(movieInfo[i][0]);
						System.out.println(movieInfo[i][1]);
					}
					break;
			}

			out.close();      
    		in.close();                   // disconnect the client
    		clientSock.close();
		}
	}
	//NICK'S API:
	public static String getTVShow(String channelIn){
	try {		
		String[] channels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "15", "16", "17", "20", "25", "26", "27", "28", "29", "30", "31", "32", "33", "35", "36", "39", "41", "48", "50", "51", "70", "71"};
		Integer[] ids = {9, 10, 11, 12, 13, 38, 14, 27, 30, 28, 51, 37, 55, 20, 46, 58, 45, 7, 39, 56, 21, 36, 59, 18, 62, 8, 35, 49, 41, 60, 43, 23, 24, 25, 26};
		int i;
		for(i = 0; !(channelIn.equals(channels[i])); i++){}
		Integer channel = ids[i];

		String stringOut;// = new String[2];
		String url = "http://www.freeviewnz.tv/tvguide/whats-on/?channelId=" + (channel);
		Document doc = Jsoup.connect(url).userAgent("Mobile").get();
		
		//try to get the show name
		try{
			String showName = doc.select("div > h3").first().toString().replace("<h3>", "").replace("</h3>", "");
			stringOut = showName;
		}catch(Exception ex){
			stringOut = "Unknown broadcast; possibly infomercials";
		}
		
		return stringOut;
	}catch (Exception ex){ 
		ex.printStackTrace(); 
		String tmp;// = new String[2];
		tmp = "error receiving channel data"; //tmp[1] = "error"; 
		return tmp;
		}
	}
//BRAD'S API
	public static String getInfo(String[] show){
		try{
			if(show.length < 1){
	    		System.err.println("Usage: java GetJSON <input>");
	    		return "problem finding info";
			}
			//Setting up the URL
    		String imdbSearch = "http://www.omdbapi.com/?t=";
			imdbSearch += show[0];
			for(int i = 1; i < show.length; i++){
			    imdbSearch += ("+" + show[i]);
			}
			imdbSearch += "&apikey=975f28dc";
			//Searching the URL
			URL searchURL = new URL(imdbSearch);
		   	BufferedReader input = new BufferedReader(new InputStreamReader(searchURL.openStream()));	

		    String programInfo = input.readLine();
	    	input.close();
	
			String JSON = removeArray(programInfo);
			return JSON;
		}catch(Exception e){
			return "error finding info";
		}
    }

    public static String removeArray(String output){
		try{
		    String remove = "\"Ratings\":";

		    output = output.replaceAll(remove, "").replace("[", "").replace("]", "").replace("{", "").replace("}", "");    
		    output = "{" + output + "}";
		}
		catch(Exception e){
			output = "Trouble finding show data";
		}

		return output;
    }
    public static String[][] genreSearch(String genre, String type)
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
			}
			return filmInfo;
		}
		catch (Exception e)
		{
			return null;
		}	
	}
	public static String[][] actorSearch(String name)
	{
		try
		{
			String nameSearch = "http://www.imdb.com/search/name?name=";
			
			String[] nameArray = name.split(" ");
			
			nameSearch += nameArray[0];
			
			for(int i = 1; i < nameArray.length; i++)
			{
				nameSearch += ("%20" + nameArray[i]);
			}
			System.out.println(name);
			Document doc = Jsoup.connect(nameSearch).get();
			
			String actorURLEnd = doc.select(".name > a").first().toString();
			actorURLEnd = actorURLEnd.substring(9, actorURLEnd.lastIndexOf("/"));
			actorURLEnd = actorURLEnd.substring(0, actorURLEnd.lastIndexOf("/"));
			
			String actorURL = "http://www.imdb.com" + actorURLEnd;
			
			Document actorPage = Jsoup.connect(actorURL).get();
			
			String actorImage = actorPage.select(".image > a").toString();
			actorImage = actorImage.substring(actorImage.indexOf("src=") + 5);
			actorImage = actorImage.substring(0, actorImage.indexOf("\""));
			System.out.println(actorImage);
			
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
			}
			return movieInfo;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
