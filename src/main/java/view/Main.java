package view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import model.FootballMatch;
import model.StartLineUp;
import service.Service;
import util.StaticDataLoader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 22.01.2020
 */

@Route
@Dependent
@StyleSheet("styles/style.css")
@Theme(value = Material.class, variant = Material.DARK)
public class Main extends VerticalLayout implements RouterLayout {

   private Response  matches;

    @Inject
    public Main(Service service) {

        Button filter = new Button("Fetch results");
        Grid<FootballMatch> items = new Grid<>(FootballMatch.class);
        items.setPageSize(50);
        items.removeAllColumns();
        items.setHeightByRows(true);
        items.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        items.addComponentColumn(match -> new Label(match.getLeagueName())).setHeader("League").setWidth("50px");
        items.addComponentColumn(match -> new Label(match.getHomeTeamName() + "  " + match.getHometeamScore()
                                                         + " - " + match.getAwayteamScore() + "  " + match.getAwayteamName())).setHeader("Match");
        items.addComponentColumn(match -> new Label(match.getDate() + "T" + match.getTime())).setHeader("Time");
        items.addComponentColumn(match -> new Label(match.getStatus().equals("Finished") ? "FT" : "LIVE")).setHeader("Status").setWidth("50px");
        items.addComponentColumn(match -> new Label(match.getMatchRound().substring(5))).setHeader("Round");

        items.setItemDetailsRenderer(new ComponentRenderer<>(item -> {
            HorizontalLayout gameUnit = new HorizontalLayout();
            HorizontalLayout field = new HorizontalLayout();
            field.addClassName("aaa");
            try {
                field.add(printLineUp(item.getLineup().getHome().getLineUp(), item.getHometeamSystem(), false),
                          printLineUp(item.getLineup().getAway().getLineUp(), item.getAwayteamSystem(), true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            HorizontalLayout homeLabel = new HorizontalLayout();
            homeLabel.getStyle().set("margin", "auto");
            HorizontalLayout awayLabel = new HorizontalLayout();
            awayLabel.getStyle().set("margin", "auto");
            Image homeBadge = new Image(item.getHomeBadge(), "");
            homeLabel.add(homeBadge, new Label(item.getHometeamScore()));
            Image awayBadge = new Image(item.getAwayBadge(), "");
            awayLabel.add(awayBadge, new Label(item.getAwayteamScore()));
            gameUnit.add(homeLabel, field, awayLabel);
            return gameUnit;
        }));


        ObjectMapper objectMapper = new ObjectMapper();

        filter.addClickListener((e) -> {
             matches = service.getMatches(null, 149, "2020-01-15", "2020-01-20");
            String json = matches.readEntity(String.class);

            try {
                List<FootballMatch> matches1 = objectMapper.readValue(json, new TypeReference<List<FootballMatch>>() {});
                items.setItems(matches1);
                items.getDataProvider().refreshAll();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(filter, items);

    }

    private static Div getBlock (StartLineUp ...player) {
        Div div = new Div();
        div.getStyle().set("width", "35px").set("height", "35px");
        if (player.length == 1) {
            div.add(new Player("image?name=" + player[0].getNumber(), player[0].getPlayer()));
        }
        return  div;
    }

    private static VerticalLayout getLine () {
        VerticalLayout v = new VerticalLayout();
        v.getStyle().set("width", "35px").set("margin-left", "0.9em").set("margin-top", "0.3em");
       return v;
    }

    private static HorizontalLayout printLineUp(List<StartLineUp> startLineUpList, String scheme, boolean isAwayTeam) throws IOException {
        HorizontalLayout lineUp = new HorizontalLayout();
        lineUp.getStyle().set("margin-right", "15px");
        List<Component> hors = new ArrayList<>();
        String matrix = StaticDataLoader.getSchemas().get(scheme) == null ? StaticDataLoader.getSchemas().get("4 - 4 - 2") : StaticDataLoader.getSchemas().get(scheme);
        String[] lines = matrix.split(" ");
        int position = 0;
        for (int i = 0; i < lines.length; i++) {
            VerticalLayout v = getLine();
            List<Component> list = new ArrayList<>();
            char[] nmb = lines[i].toCharArray();
            for (char number : nmb) {
                if (number == '1') {
                    StartLineUp player = getPlayer(++position, startLineUpList);
                    list.add(getBlock(player));
                }
                else list.add(getBlock());
            }
            reverse(v, !isAwayTeam, list);
            hors.add(v);
        }
        reverse(lineUp, isAwayTeam, hors);
        return lineUp;
    }

    private static StartLineUp getPlayer(int pos, List<StartLineUp> startLineUpList) {
        StartLineUp player = new StartLineUp();
        for (StartLineUp p : startLineUpList) {
            if (p.getPosition() == pos) player = p ;
        }
        return player;
    }

    private static <T extends HasComponents> void  reverse (T t, boolean flag, List<Component> l) {
        Component [] c = new Component[l.size()];
        if (flag) {
            Collections.reverse(l);
            t.add(l.toArray(c));
        }
        else t.add(l.toArray(c));
    }

}
