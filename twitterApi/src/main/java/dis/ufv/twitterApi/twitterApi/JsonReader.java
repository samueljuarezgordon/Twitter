package dis.ufv.twitterApi.twitterApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonReader {

    String fileName = "tweets.json";
    public ArrayList<Tweet> readJson() throws FileNotFoundException {
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        ArrayList<Tweet> tweets = gson.fromJson(new FileReader(fileName), new TypeToken<ArrayList<Tweet>>(){}.getType());
        return tweets;
    }

    public void writeJson(ArrayList<Tweet> tweets) throws IOException {
        //System.out.println(tweets.get(0).getTweet());
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        FileWriter writer = new FileWriter(fileName);
        JsonWriter jsonWriter = new JsonWriter(writer);
        jsonWriter.setIndent("  ");
        JsonArray jsonArray = gson.toJsonTree(tweets).getAsJsonArray();
        System.out.println(jsonArray.toString());
        gson.toJson(jsonArray, jsonWriter);
        jsonWriter.close();
    }
}
