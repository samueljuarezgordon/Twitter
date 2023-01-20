package dis.ufv.twitterApi.twitterApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Controller {

    ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    @GetMapping("/tweets")
    public ArrayList<Tweet> getTweets(){
        return tweets;
    }

    @PostMapping("/tweet")
    public void addTweet(@RequestBody Tweet tweet){
        tweet.setId(tweets.size()+1);
        tweets.add(tweet);
    }



}
