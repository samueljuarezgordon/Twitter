package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class API {
    private static final String urlPrefix = "http://localhost:8081/%s/%s";

    public String getTweets() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl = String.format(urlPrefix, "tweets", "");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(fullUrl))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();
    }

    public String postTweet(Tweet tweet) throws Exception{
        // creamos la url de la api
        String fullUrl = String.format(urlPrefix, "tweet", "");

        // creamos el constructor de gson con eñ formato de fecha que necesirta la api
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        // convertimos el objeto zona a json
        JsonObject jsonObject = gson.toJsonTree(tweet).getAsJsonObject();
        // creamos un objeto HttpResquest con la url y el json
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(fullUrl)).POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).header("Content-Type", "application/json").build();
        // obternemos la respuesta de la api y la convertimos a String
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        // devolvemos la respuesta
        return response.body();
    }

    public String deleteTweet(int id) throws Exception{
        // creamos la url de la api
        String fullUrl = String.format(urlPrefix, "tweet", id);

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(fullUrl)).DELETE().build();
        // obternemos la respuesta de la api y la convertimos a String
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        // devolvemos la respuesta
        return response.body();
    }
}
