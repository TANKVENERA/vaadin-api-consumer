package model;

import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 29.01.2020
 */

public interface PlayerModel extends TemplateModel {

    void setSrc(String src);
    void setPlayerName(String playerName);
}
