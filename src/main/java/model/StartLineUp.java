package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 28.01.2020
 */

@Getter
@Setter
public class StartLineUp {

    @JsonProperty("lineup_player")
    private String player;
    @JsonProperty("lineup_number")
    private Integer number;
    @JsonProperty("lineup_position")
    private Integer position;

    @Override
    public String toString() {
        return "StartLineUp{" +
                "player='" + player + '\'' +
                ", number=" + number +
                ", position=" + position +
                '}';
    }
}
