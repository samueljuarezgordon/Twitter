package dis.ufv.twitterApi.twitterApi;

import java.util.Date;

public class Tweet {
    private int Id;
    private String Usuario;
    private String Tweet;
    private Date Fecha;

    public Tweet(int id, String usuario, String tweet, Date fecha) {
        Id = id;
        Usuario = usuario;
        Tweet = tweet;
        Fecha = fecha;
    }

    public Tweet() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getTweet() {
        return Tweet;
    }

    public void setTweet(String tweet) {
        Tweet = tweet;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }
}
