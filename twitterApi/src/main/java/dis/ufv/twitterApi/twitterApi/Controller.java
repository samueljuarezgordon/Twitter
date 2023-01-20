package dis.ufv.twitterApi.twitterApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class Controller {

    ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    JsonReader jsonReader = new JsonReader();

    @GetMapping("/tweets")
    public ArrayList<Tweet> getTweets() throws FileNotFoundException {
        tweets = jsonReader.readJson();
        return tweets;
    }

    @PostMapping("/tweet")
    public void addTweet(@RequestBody String tweetString) throws IOException {
        Tweet tweet = new GsonBuilder().setDateFormat("dd-MM-yyyy").create().fromJson(tweetString, Tweet.class);
        tweet.setId(tweets.size());
        tweets.add(tweet);
        //System.out.println(tweet.getTweet());
        jsonReader.writeJson(tweets);

    }

    @DeleteMapping("/tweet/{id}")
    public void deleteTweet(@PathVariable int id) throws IOException {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                tweets.remove(tweet);
                break;
            }
        }
        jsonReader.writeJson(tweets);
    }

}
