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
//END NICK'S API

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
			//**
			String showName = getTVShow(fromClient);
			out.println(showName);
			//out.println(showName[1]);

			out.close();      
    		in.close();                   // disconnect the client
    		clientSock.close();
		}
	}
	//NICK'S API:
	public static String getTVShow(String channel)  {
	try {		
		String stringOut;// = new String[2];
		String url = "http://www.freeviewnz.tv/tvguide/whats-on/?channelId=" + (Integer.parseInt(channel) + 8);
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
		tmp = "error"; //tmp[1] = "error"; 
		return tmp;
		}
	}
}
