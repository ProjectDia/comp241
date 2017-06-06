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
			InetAddress i = clientSock.getInetAddress(); // get address of socket

			PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

			String fromClient;
			while((fromClient = in.readLine()) == null){} //continue until the server receives input 
			System.out.println("A client asked for data about channel: " + fromClient);
			//out.println("You have asked for channel data for channel: " + fromClient); 

			//MAKE SURE CLIENT IN IS INTEGER
			String showName = getTVShow(fromClient);
			String showInfo = getInfo(showName.split(" "));
			out.println(showName);
			out.println(showInfo);
			//out.println(showName[1]);

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
		//try to get the synopsis
		/*
		try{
			String description = doc.select("div.synopsis").first().toString().replace("<div class=\"synopsis\">", "").replace("</div>", "");
			description = description.substring(3, description.lastIndexOf('.') + 1);
			stringOut[1] = description;
		}catch(Exception ex){
			stringOut[0] = "No info available";
		}
		*/
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
}
