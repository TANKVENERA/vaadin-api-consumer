package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 22.01.2020
 */


@Getter
@Setter
public class FootballMatch {

    @JsonProperty("match_id")
    private Integer id;
    @JsonProperty("country_id")
    private String ctrId;
    @JsonProperty("country_name")
    private String ctrName;
    @JsonProperty("league_id")
    private Integer leagueId;
    @JsonProperty("league_name")
    private String leagueName;
    @JsonProperty("match_date")
    private String date;
    @JsonProperty("match_status")
    private String status;
    @JsonProperty("match_time")
    private String time;
    @JsonProperty("match_hometeam_id")
    private Integer hometeamId;
    @JsonProperty("match_hometeam_name")
    private String homeTeamName;
    @JsonProperty("match_hometeam_score")
    private Integer hometeamScore;
    @JsonProperty("match_awayteam_name")
    private String awayteamName;
    @JsonProperty("match_awayteam_id")
    private Integer awayteamId;
    @JsonProperty("match_awayteam_score")
    private Integer awayteamScore;
    @JsonProperty("match_hometeam_halftime_score")
    private Integer hometeamHalftimeScore;
    @JsonProperty("match_awayteam_halftime_score")
    private Integer awayteamHalftimeScore;
    @JsonProperty("match_hometeam_extra_score")
    private Integer matchHometeamExtraScore;
    @JsonProperty("match_awayteam_extra_score")
    private Integer awayteamExtraScore;
    @JsonProperty("match_hometeam_penalty_score")
    private Integer hometeamPenaltyScore;
    @JsonProperty("match_awayteam_penalty_score")
    private Integer awayteamPenaltyScore;
    @JsonProperty("match_hometeam_ft_score")
    private Integer hometeamFtScore;
    @JsonProperty("match_awayteam_ft_score")
    private Integer awayteamFtScore;
    @JsonProperty("match_hometeam_system")
    private String hometeamSystem;
    @JsonProperty("match_awayteam_system")
    private String awayteamSystem;
    @JsonProperty("match_live")
    private Integer matchLive;
    @JsonProperty("match_round")
    private String matchRound;
    @JsonProperty("match_stadium")
    private String stadium;
    @JsonProperty("match_referee")
    private String referee;
    @JsonProperty("team_home_badge")
    private String homeBadge;
    @JsonProperty("team_away_badge")
    private String awayBadge;
    @JsonIgnore
    List<Object> goalscorer;
    @JsonIgnore
    List<Object> cards;
    @JsonIgnore
    Object substitutions;
    @JsonIgnore
    Object lineup;
    @JsonIgnore
    List<Object> statistics;

    @Override
    public String toString() {
        return "FootballMatch{" +
                "id='" + id + '\'' +
                ", match_hometeam_name='" + homeTeamName + '\'' +
                '}';
    }
}
