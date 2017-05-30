import java.net.*;
import java.io.*;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  

public class GetJSON
{
    public static void main(String[] args) throws Exception
    {
		if(args.length < 1)
		{
	    	System.err.println("Usage: java GetJSON <input>");
	    	return;
		}

    	String imdbSearch = "http://www.omdbapi.com/?t=";

		imdbSearch += args[0];

		for(int i = 1; i < args.length; i++)
		{
		    imdbSearch += ("+" + args[i]);
		}
		
		imdbSearch += "&apikey=975f28dc";

		URL searchURL = new URL(imdbSearch);

	    BufferedReader input = new BufferedReader(new InputStreamReader(searchURL.openStream()));

	    String programInfo = input.readLine();
	    input.close();
	
		String JSON = removeArray(programInfo);
		System.out.println(JSON);
    }

    public static String removeArray(String output)
    {
		try
       	{
		    String remove = "\"Ratings\":";

		    output = output.replaceAll(remove, "").replace("[", "").replace("]", "").replace("{", "").replace("}", "");    
		    output = "{" + output + "}";
		}
		catch(Exception e)
		{	
		}

		return output;
    }
}