package org.vaadin.example;

import java.util.Date;

public class Tweet {
    public int id;
    public String usuario;
    public String tweet;
    public Date fecha;

    public Tweet(int id, String usuario, String tweet, Date fecha) {
        this.id = id;
        this.usuario = usuario;
        this.tweet = tweet;
        this.fecha = fecha;
    }

    public Tweet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
