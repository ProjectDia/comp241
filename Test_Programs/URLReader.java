import java.net.*;
import java.io.*;

public class URLReader
{
    public static void main(String[] args) throws Exception
    {
	if(args.length < 0)
	{
	    System.err.println("Usage: URLReader <input>");
	    return;
	}

        String imdbSearch = "http://www.omdbapi.com/?t=";

	imdbSearch += args[0];

	for(int i = 1; i < args.length; i++)
	{
	    imdbSearch += ("+" + args[i]);
	}

	URL searchURL = new URL(imdbSearch);

        BufferedReader input = new BufferedReader(new InputStreamReader(searchURL.openStream()));

        String inputLine;
        while ((inputLine = input.readLine()) != null)
            System.out.println(inputLine);
        input.close();
    }
}