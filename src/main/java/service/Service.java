package service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 22.01.2020
 */


@ApplicationScoped
public class Service {

    private Client client;
    private WebTarget target;

    @PostConstruct
    protected void init() {
        client = ClientBuilder.newClient();
        target = client.target("https://apiv2.apifootball.com")
                .queryParam("APIkey", "bce43b70570b19a4802654371068fbe72bc125514efc346e28feef43fc6d5add")
                .queryParam("action", "get_events");
    }


    public Response getMatches( Integer teamId,
                                           Integer leagueId,
                                           String from,
                                           String to) {
        return target.queryParam("team_id", teamId)
                     .queryParam("league_id", leagueId)
                     .queryParam("from", from)
                     .queryParam("to", to)
                     .request(MediaType.APPLICATION_JSON).get();
    }
}
