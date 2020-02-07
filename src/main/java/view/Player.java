package view;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import model.PlayerModel;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 29.01.2020
 */

@Tag("player-lol")
@NpmPackage(value = "@polymer/paper-tooltip", version = "3.0.1")
@JsModule("./src/player.js")
public class Player extends PolymerTemplate<PlayerModel> {

    public Player(String src, String playerName) {
        setId("template");
        getModel().setSrc(src);
        getModel().setPlayerName(playerName);
    }
}
