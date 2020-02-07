package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 28.01.2020
 */

@Getter
@Setter
public class HomeLineUp {

    @JsonProperty("starting_lineups")
    private List<StartLineUp> lineUp;

    @JsonIgnore
    private List<Object> substitutes;

    @JsonIgnore
    private List<Object> coach;

    @JsonIgnore
    private List<Object> missing_players;
}
