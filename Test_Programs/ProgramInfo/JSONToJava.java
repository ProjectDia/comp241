import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  

public class JSONToJava
{
    public static void main(String[] args) throws Exception
    {
		if(args.length != 1)
		{
		    System.err.println("Usage: java JSONToJava <input>");
		    return;
		}
		try
		{
 		   	BufferedReader br = new BufferedReader(new FileReader(args[0]));    //create a new buffered reader
			Gson gson = new Gson();
 		   	String jsonString = br.readLine();
			ShowInfo showinfo = gson.fromJson(jsonString, ShowInfo.class); 
			System.out.println(showinfo.Title + " " + showinfo.Year);
		}
		catch(Exception e)
		{
 	  	}
    }
}  

class ShowInfo 
{ 
    public String Title; 
    public String Year;
    public String Rated; 
    public String Released; 
    public String Runtime; 
    public String Genre; 
    public String Director; 
    public String Writer; 
    public String Actors; 
    public String Plot; 
    public String Language; 
    public String Country; 
    public String Awards;
    public String Poster; 
    public String Source1;
    public String Value1;
    public String Source2;
    public String Value2;
    public String Source3;
    public String Value3;
    public String Metascore;
    public String imdbRating;
    public String imdbVotes;
    public String imdbID;
    public String Type;
    public String DVD;
    public String BoxOffice;
    public String Production;
    public String Website;
    public String Response;
    
    public ShowInfo(){} 
   
    public String getTitle()
    { 
		return Title; 
    } 
    public void setTitle(String Title)
    { 
		this.Title = Title; 
    } 
    public String getYear()
    { 
		return Year; 
    } 
    public void setYear(String Year)
    { 
		this.Year = Year; 
    }
    public String getRated()
    { 
		return Rated; 
    } 
    public void setRated(String Rated)
    { 
		this.Rated = Rated; 
    } 
    public String getReleased()
    { 
		return Released; 
    } 
    public void setReleased(String Released)
    { 
		this.Released = Released; 
    } 
    public String getRuntime()
    { 
		return Runtime; 
    } 
    public void setRuntime(String Runtime)
    { 
		this.Runtime = Runtime; 
    }
    public String getGenre()
    { 
		return Genre; 
    } 
    public void setGenre(String Genre)
    { 
		this.Genre = Genre; 
    } 
    public String getDirector()
    { 
		return Director; 
    } 
    public void setDirector(String Director)
    { 
		this.Director = Director; 
    }  
    public String getWriter()
    { 
		return Writer; 
    } 
    public void setWriter(String Writer)
    { 
		this.Writer = Writer; 
    } 
    public String getActors()
    { 
		return Actors; 
    } 
    public void setActors(String Actors)
    { 
		this.Actors = Actors; 
    }  
    public String getPlot()
    { 
		return Plot; 
    } 
    public void setPlot(String Plot)
    { 
		this.Plot = Plot; 
    } 
    public String getLanguage()
    { 
		return Language; 
    } 
    public void setLanguage(String Language)
    { 
		this.Language = Language; 
    }  
    public String getCountry()
    { 
		return Country; 
    } 
    public void setCountry(String Country)
    { 
		this.Country = Country; 
    } 
    public String getAwards()
    { 
		return Awards; 
    } 
    public void setAwards(String Awards)
    { 
		this.Awards = Awards; 
    }  
    public String getPoster()
    { 
		return Poster; 
    } 
    public void setPoster(String Poster)
    { 
		this.Poster = Poster; 
    } 
    public String getSource1()
    { 
		return Source1; 
    } 
    public void setSource1(String Source1)
    { 
		this.Source1 = Source1; 
    }
    public String getValue1()
    { 
		return Value1; 
    } 
    public void setValue1(String Value1)
    { 
		this.Value1 = Value1; 
    }
    public String getSource2()
    { 
		return Source2; 
    } 
    public void setSource2(String Source2)
    { 
		this.Source2 = Source2; 
    }
    public String getValue2()
    { 
		return Value2; 
    } 
    public void setValue2(String Value2)
    { 
		this.Value2 = Value2; 
    }
    public String getSource3()
    { 
		return Source3; 
    } 
    public void setSource3(String Source3)
    { 
		this.Source3 = Source3; 
    }
    public String getValue3()
    { 
		return Value3; 
    } 
    public void setValue3(String Value3)
    { 
		this.Value3 = Value3; 
    }
    public String getMetascore()
    { 
		return Metascore; 
    } 
    public void setMetascore(String Metascore)
    { 
		this.Metascore = Metascore; 
    }
    public String getimdbRating()
    { 
		return imdbRating; 
    } 
    public void setimdbRating(String imdbRating)
    { 
		this.imdbRating = imdbRating; 
    }
    public String getimdbVotes()
    { 
		return imdbVotes; 
    } 
    public void setimdbVotes(String imdbVotes)
    { 
		this.imdbVotes = imdbVotes; 
    }
    public String getimdbID()
    { 
		return imdbID; 
    } 
    public void setimdbID(String imdbID)
    { 
		this.imdbID = imdbID; 
    }
    public String getType()
    { 
		return Type; 
    } 
    public void setType(String Type)
    { 
		this.Type = Type; 
    }
    public String getDVD()
    { 
		return DVD; 
    } 
    public void setDVD(String DVD)
    { 
		this.DVD = DVD; 
    }
    public String getBoxOffice()
    { 
		return BoxOffice; 
    } 
    public void setBoxOffice(String BoxOffice)
    { 
		this.BoxOffice = BoxOffice; 
    }
    public String getProduction()
    { 
		return Production; 
    } 
    public void setProduction(String Production)
    { 
		this.Production = Production; 
    }
    public String getWebsite()
    { 
		return Website; 
    } 
    public void setWebsite(String Website)
    { 
		this.Website = Website; 
    }
    public String getResponse()
    { 
		return Response; 
    } 
    public void setResponse(String Response)
    { 
		this.Response = Response; 
    }
    public String ToString()
    {
	return Title;
    }
}  