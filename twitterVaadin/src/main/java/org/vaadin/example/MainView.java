package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    private final VerticalLayout content;
    private final Tab TimeLine;
    private final Tab NewTweet;

    VerticalLayout tweetsLayout;
    VerticalLayout newTweetLayout;
    public MainView(@Autowired TweetService service) {

        VerticalLayout layout = new VerticalLayout();
        tweetsLayout = new VerticalLayout();
        newTweetLayout = new VerticalLayout();
        HorizontalLayout inputsTipo = new HorizontalLayout();
        HorizontalLayout inputsNombre = new HorizontalLayout();
        content = new VerticalLayout();

        H1 titulo = new H1("Twitter");
        titulo.getStyle().set("align-self", "center").set("margin", "10px");

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();


        try{
            tweets = service.getTweets();

        } catch (Exception ex) {
            Notification.show("Error al leer los Tweets");
        }


        for (Tweet tweet : tweets) {

            H4 usuario = new H4(tweet.getUsuario());
            Span tweetText = new Span(tweet.getTweet());
            Span fecha = new Span(tweet.getFecha().toString());
            Span empty = new Span(" ");

            Button borrarTweet = new Button("Borrar", e -> {
                int id = tweet.getId();
                try{
                    service.deleteTweet(id);
                    UI.getCurrent().getPage().reload();
                } catch (Exception ex) {
                    Notification.show("Error al leer el Pokémon");
                }
            });
            borrarTweet.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            borrarTweet.addClickShortcut(Key.ENTER);

            tweetsLayout.add(usuario, tweetText, fecha, empty, borrarTweet);

        }

        H2 headline = new H2("Nuevo Tweet");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        //Campos del formulario
        TextField usuario = new TextField("Usuario");
        usuario.setPlaceholder("Usuario");
        usuario.setRequired(true);

        TextField tweet = new TextField("Tweet");
        tweet.setPlaceholder("Tweet");
        tweet.setRequired(true);

        // Se añaden los campos al layout.
        FormLayout fieldLayout = new FormLayout(usuario, tweet);

        ArrayList<Tweet> finalTweets = tweets;
        Button tweetear = new Button("Tweetear", e -> {
            int id = finalTweets.size()+1;
            Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            Tweet nuevo = new Tweet(id ,usuario.getValue(), tweet.getValue(), date);

            try {
                service.addTweet(nuevo);
                Notification.show("Tweet publicado correctamente");
                UI.getCurrent().getPage().reload();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });
        tweetear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        newTweetLayout.add(headline, fieldLayout, tweetear);

        TimeLine = new Tab("Timeline");
        NewTweet = new Tab("Nuevo Tweet");
        Tabs tabSheet = new Tabs(TimeLine, NewTweet);
        TimeLine.getStyle().set("width", "70%");
        NewTweet.getStyle().set("width", "30%");
        tabSheet.getStyle().set("width", "100%");
        tabSheet.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );
        setContent(tabSheet.getSelectedTab());

        layout.add(titulo, tabSheet, content);
        add(layout);
    }

    private void setContent(Tab tab) {
        content.removeAll();

        if (tab.equals(TimeLine)) {
            content.add(tweetsLayout);
        } else if (tab.equals(NewTweet)) {
            content.add(newTweetLayout);
        } else {
            content.add(new Paragraph("Seleccione una Pestaña."));
        }

    }

}
