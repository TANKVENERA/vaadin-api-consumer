package model;

import lombok.Getter;
import lombok.Setter;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 22.01.2020
 */
@Getter
@Setter
public class Country {


    private String country_name;
    private String country_id;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    @Override
    public String toString() {
        return "{ name: " + country_name + ", id: " + country_id + " }";
    }
}
