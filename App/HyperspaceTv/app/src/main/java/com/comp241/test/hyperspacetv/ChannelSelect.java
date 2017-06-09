package com.comp241.test.hyperspacetv;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.*;
import java.io.*;

public class ChannelSelect extends AppCompatActivity {

    int channelNumber;
    EditText txtChannel;
    String channelTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void onConfirmClick(View v) {
        txtChannel = (EditText) findViewById(R.id.txtChannel);
        channelTxt = txtChannel.getText().toString();
        if (channelTxt.length() == 0) {
            toastMessage("Enter Channel Number");
            return;
        } else {
            try {
                channelNumber = Integer.parseInt(channelTxt);
                new Client().execute("" + channelNumber);
            } catch (Exception e) {
            }
        }
    }

    public void onChannelSelectClick(View v) {
        setContentView(R.layout.activity_channel_select);
    }

    public void toastMessage(String message){
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.makeText(ChannelSelect.this, message, toast.LENGTH_SHORT).show();
    }

    public void displayInfo(String info) {
        int index;
        String showDataText = "";
        String[] array = info.split("\\|");
        setContentView(R.layout.activity_channel_data);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setTextSize(32);
        TextView txtShowData = (TextView) findViewById(R.id.txtShowData);
        txtShowData.setTextSize(20);
        TextView txtActorLinks = (TextView) findViewById(R.id.txtActorLinks);
        ImageView imgPoster = (ImageView)findViewById(R.id.imgPoster);
        Button[] actorButtons = {(Button) findViewById(R.id.btnActorOne),
                (Button) findViewById(R.id.btnActorTwo),
                (Button) findViewById(R.id.btnActorThree),
                (Button) findViewById(R.id.btnActorFour)};
        for (index = 0; index < 4; index++) actorButtons[index].setVisibility(View.INVISIBLE);
        try {
            Gson gson = new Gson();
            ShowInfo showinfo = gson.fromJson(array[1], ShowInfo.class);
            if (showinfo.Response.equals("True")) {
                txtTitle.setText(showinfo.Title + " (" + showinfo.Year + ")");
                showDataText += (!"N/A".equals(showinfo.Rated))?"Rated: " + showinfo.Rated + "\n":"";
                showDataText += (!"N/A".equals(showinfo.Released))?"Released: " + showinfo.Released + "\n":"";
                showDataText += (!"N/A".equals(showinfo.Runtime))?"Runtime: " + showinfo.Runtime + "\n":"";
                showDataText += (!"N/A".equals(showinfo.Genre))?"Genre: " + showinfo.Genre + "\n":"";
                showDataText += (!"N/A".equals(showinfo.Director))?"Director: " + showinfo.Director + "\n":"";
                showDataText += (!"N/A".equals(showinfo.Writer))?"Writer: " + showinfo.Writer + "\n":"";
                showDataText += (!"N/A".equals(showinfo.Plot))?"\nPlot: " + showinfo.Plot + "\n":"";
                showDataText += (!"N/A".equals(showinfo.Awards))?"\nAwards: " + showinfo.Awards + "\n":"";
                showDataText += (!"N/A".equals(showinfo.imdbRating))?"Rating: " + showinfo.imdbRating + "/10\n":"";
                txtShowData.setText(showDataText);
                new SetImageTask(imgPoster).execute(showinfo.Poster);
                String[] actors = showinfo.Actors.split("\\,");
                for (index = 0; index < actors.length; index++){
                    actorButtons[index].setText(actors[index]);
                    actorButtons[index].setVisibility(View.VISIBLE);
                }
            } else {
                txtTitle.setText(array[0]);
                txtShowData.setText("...No additional info...");
                imgPoster.setVisibility(View.INVISIBLE);
                txtActorLinks.setVisibility(View.INVISIBLE);
            }
        }
        catch(Exception e)
        {
            txtTitle.setText(array[0]);
            txtShowData.setText("...Exception " + e.getMessage() + "...");
        }
    }

    private class SetImageTask extends AsyncTask<String, String, Bitmap> {
        ImageView imgView;

        public SetImageTask(ImageView imageView){
            imgView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap image = null;
            String imageUrl = params[0];
            try {
                InputStream imageStream = new URL(imageUrl).openStream();
                image = BitmapFactory.decodeStream(imageStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            imgView.setImageBitmap(image);
        }
    }

    private class Client extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String received = "";
            try {
                String fromServer;
                Socket socket = new Socket("f.l0.nz", 1234);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("0|" + params[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while ((fromServer = in.readLine()) != null) received += fromServer + "|";
                in.close();
            } catch (UnknownHostException e) {
                Log.w("error", "Unknown host");
            } catch (IOException e) {
                Log.w("error", "IO Exception for host");
            } catch (Exception e) {
                Log.w("error", "Other");
            }
            return received;
        }

        @Override
        protected void onPostExecute(String info) {
            if (!info.split("\\|")[0].equals("error receiving channel data")) displayInfo(info);
            else toastMessage("Enter Valid Freeview Channel Number");
        }
    }

    class ShowInfo {
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

        public ShowInfo() {
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String Year) {
            this.Year = Year;
        }

        public String getRated() {
            return Rated;
        }

        public void setRated(String Rated) {
            this.Rated = Rated;
        }

        public String getReleased() {
            return Released;
        }

        public void setReleased(String Released) {
            this.Released = Released;
        }

        public String getRuntime() {
            return Runtime;
        }

        public void setRuntime(String Runtime) {
            this.Runtime = Runtime;
        }

        public String getGenre() {
            return Genre;
        }

        public void setGenre(String Genre) {
            this.Genre = Genre;
        }

        public String getDirector() {
            return Director;
        }

        public void setDirector(String Director) {
            this.Director = Director;
        }

        public String getWriter() {
            return Writer;
        }

        public void setWriter(String Writer) {
            this.Writer = Writer;
        }

        public String getActors() {
            return Actors;
        }

        public void setActors(String Actors) {
            this.Actors = Actors;
        }

        public String getPlot() {
            return Plot;
        }

        public void setPlot(String Plot) {
            this.Plot = Plot;
        }

        public String getLanguage() {
            return Language;
        }

        public void setLanguage(String Language) {
            this.Language = Language;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String Country) {
            this.Country = Country;
        }

        public String getAwards() {
            return Awards;
        }

        public void setAwards(String Awards) {
            this.Awards = Awards;
        }

        public String getPoster() {
            return Poster;
        }

        public void setPoster(String Poster) {
            this.Poster = Poster;
        }

        public String getSource1() {
            return Source1;
        }

        public void setSource1(String Source1) {
            this.Source1 = Source1;
        }

        public String getValue1() {
            return Value1;
        }

        public void setValue1(String Value1) {
            this.Value1 = Value1;
        }

        public String getSource2() {
            return Source2;
        }

        public void setSource2(String Source2) {
            this.Source2 = Source2;
        }

        public String getValue2() {
            return Value2;
        }

        public void setValue2(String Value2) {
            this.Value2 = Value2;
        }

        public String getSource3() {
            return Source3;
        }

        public void setSource3(String Source3) {
            this.Source3 = Source3;
        }

        public String getValue3() {
            return Value3;
        }

        public void setValue3(String Value3) {
            this.Value3 = Value3;
        }

        public String getMetascore() {
            return Metascore;
        }

        public void setMetascore(String Metascore) {
            this.Metascore = Metascore;
        }

        public String getimdbRating() {
            return imdbRating;
        }

        public void setimdbRating(String imdbRating) {
            this.imdbRating = imdbRating;
        }

        public String getimdbVotes() {
            return imdbVotes;
        }

        public void setimdbVotes(String imdbVotes) {
            this.imdbVotes = imdbVotes;
        }

        public String getimdbID() {
            return imdbID;
        }

        public void setimdbID(String imdbID) {
            this.imdbID = imdbID;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getDVD() {
            return DVD;
        }

        public void setDVD(String DVD) {
            this.DVD = DVD;
        }

        public String getBoxOffice() {
            return BoxOffice;
        }

        public void setBoxOffice(String BoxOffice) {
            this.BoxOffice = BoxOffice;
        }

        public String getProduction() {
            return Production;
        }

        public void setProduction(String Production) {
            this.Production = Production;
        }

        public String getWebsite() {
            return Website;
        }

        public void setWebsite(String Website) {
            this.Website = Website;
        }

        public String getResponse() {
            return Response;
        }

        public void setResponse(String Response) {
            this.Response = Response;
        }

        public String ToString() {
            return Title;
        }
    }
}
