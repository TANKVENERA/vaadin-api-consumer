package view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinService;
import model.FootballMatch;
import service.Service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 22.01.2020
 */

@Route
@Dependent
@StyleSheet("styles/style.css")
public class Main extends VerticalLayout implements RouterLayout {

   private Response  matches;

   @PostConstruct
   public void init () {

   }

    @Inject
    public Main(Service service) {
        Button filter = new Button("Fetch results");
        Grid<FootballMatch> items = new Grid<>(FootballMatch.class);
        items.setPageSize(50);
        items.removeAllColumns();
        items.setHeightByRows(true);
        items.addComponentColumn(match -> new Label(match.getCtrName())).setHeader("Country");
        items.addComponentColumn(match -> new Label(match.getLeagueName())).setHeader("League").setWidth("50px");
        items.addComponentColumn(match -> new Label(match.getHomeTeamName() + "  " + match.getHometeamScore()
                                                         + " - " + match.getAwayteamScore() + "  " + match.getAwayteamName())).setHeader("Match");
        items.addComponentColumn(match -> new Label(match.getDate() + "T" + match.getTime())).setHeader("Time");
        items.addComponentColumn(match -> new Label(match.getStatus().equals("Finished") ? "FT" : "LIVE")).setHeader("Status").setWidth("50px");
        items.addComponentColumn(match -> new Label(match.getHometeamSystem() + " - " + match.getAwayteamSystem())).setHeader("Scheme").setWidth("50px");
        items.addComponentColumn(match -> {
            Label label = new Label(match.getMatchRound().substring(5));
            label.setWidth("50px");
            label.getStyle().set("margin", "unset !important");
            return label;
        }).setHeader("Round");

        items.setItemDetailsRenderer(new ComponentRenderer<>(item -> {
            HorizontalLayout field = new HorizontalLayout();
            field.addClassName("aaa");
            field.add(printLineUp(item.getHometeamSystem(), false), printLineUp(reverseLineUp(item.getAwayteamSystem()), true));
            return field;
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

    private static Div getDiv () {
        Div div = new Div();
        div.setWidth("35px");
        div.setHeight("35px");
        return div;
    }

    private static Image getNmb () {
        return  new Image("images/1.svg", "");
    }

    private static VerticalLayout getLine () {
        VerticalLayout v = new VerticalLayout();
        v.getStyle().set("width", "35px").set("margin-left", "0.9em").set("margin-top", "0.3em");
       return v;
    }

    private static HorizontalLayout printLineUp (String scheme, boolean isAwayTeam) {
        HorizontalLayout lineUp = new HorizontalLayout();
            switch (scheme) {
                case ("4 - 3 - 3") : lineUp.add(standing(1), standing(4), standing(1), standing(2), standing(21), standing(1));
                    break;
                case ("3 - 3 - 4") : lineUp.add(standing(1), standing(21), standing(2), standing(1), standing(4), standing(1));
                    break;
                case ("4 - 2 - 3 - 1") : lineUp.add(standing(1), standing(4), standing(2), standing(0), standing(31), standing(1));
                    break;
                case ("1 - 3 - 2 - 4") : lineUp.add(standing(1), standing(31), standing(0), standing(2), standing(4), standing(1));
                    break;
                case ("3 - 5 - 2") : lineUp.add(standing(1), standing(3), standing(21), standing(22), standing(1), standing(2));
                    break;
                case ("2 - 5 - 3") : lineUp.add(standing(2), standing(1), standing(22), standing(21), standing(3), standing(1));
                break;
                default: if (isAwayTeam) lineUp.add(standing(2), standing(0), standing(22), standing(2), standing(4), standing(1));
                            else lineUp.add(standing(1), standing(4), standing(2), standing(22), standing(0), standing(2));
            }
        return lineUp;
    }

    private static VerticalLayout standing (int type) {
        VerticalLayout v = getLine();

        switch (type) {
            case (0):
                v.add(getDiv(), getDiv(), getDiv(), getDiv(), getDiv(), getDiv(), getDiv());
                break;
            case (1):
                v.add(getDiv(), getDiv(), getDiv(), getNmb(), getDiv(), getDiv(), getDiv());
                break;
            case (2):
                v.add(getDiv(), getDiv(), getNmb(), getDiv(), getNmb(), getDiv(), getDiv());
                break;
            case (21):
                v.add(getDiv(), getNmb(), getDiv(), getDiv(), getDiv(), getNmb(), getDiv());
                break;
            case (22):
                v.add(getNmb(), getDiv(), getDiv(), getDiv(), getDiv(), getDiv(), getNmb());
                break;
            case (3):
                v.add(getDiv(), getNmb(), getDiv(), getNmb(), getDiv(), getNmb(), getDiv());
                break;
            case (31):
                v.add(getNmb(), getDiv(), getDiv(), getNmb(), getDiv(), getDiv(), getNmb());
                break;
            case (4):
                v.add(getNmb(), getDiv(), getNmb(), getDiv(), getNmb(), getDiv(), getNmb());
                break;
        }
        return v;
    }

    private static String reverseLineUp(String scheme) {
       String reverse = "";
        for(int i = scheme.length() - 1; i >= 0; i--)
        {
            reverse = reverse + scheme.charAt(i);
        }
       return reverse;
    }


}
