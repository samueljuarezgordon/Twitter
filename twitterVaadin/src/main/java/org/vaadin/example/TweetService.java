package org.vaadin.example;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

@Service
public class TweetService implements Serializable {

    public ArrayList<Tweet> getTweets() throws URISyntaxException, IOException, InterruptedException {
        API api = new API();
        String resultsAPI = api.getTweets();
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        ArrayList<Tweet> tweets = gson.fromJson(resultsAPI, new TypeToken<ArrayList<Tweet>>(){}.getType());
        System.out.println(tweets.get(0).getTweet());
        return tweets;
    }


    public static String addTweet(Tweet tweet) throws Exception{
        API api = new API();
        // recibimos los datos de la zona y los enviamos a la api
        String respuesta = api.postTweet(tweet);
        return respuesta;
    }

    public static String deleteTweet(int id) throws Exception{
        API api = new API();
        // recibimos los datos de la zona y los enviamos a la api
        String respuesta = api.deleteTweet(id);
        return respuesta;
    }


}
