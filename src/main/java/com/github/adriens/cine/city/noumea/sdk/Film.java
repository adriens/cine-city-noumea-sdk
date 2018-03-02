/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.cine.city.noumea.sdk;

import java.net.URL;

/**
 *
 * @author salad74
 */
public class Film {

    /**
     * @return the cinecityFilmURL
     */
    public Film(){
        
    }
    public Film(URL cinecityFilmURL, String filmName, URL cinecityAfficheURL){
        super();
        setCinecityFilmURL(cinecityFilmURL);
        setName(filmName);
        setCinecityAfficheURL(cinecityAfficheURL);
        
    }
    
    public String toString(){
        if(getName() == null){
            return "";
        }
        else{
            return getName();
        }
    }
    public URL getCinecityFilmURL() {
        return cinecityFilmURL;
    }

    /**
     * @param cinecityFilmURL the cinecityFilmURL to set
     */
    public void setCinecityFilmURL(URL cinecityFilmURL) {
        this.cinecityFilmURL = cinecityFilmURL;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cinecityAfficheURL
     */
    public URL getCinecityAfficheURL() {
        return cinecityAfficheURL;
    }

    /**
     * @param cinecityAfficheURL the cinecityAfficheURL to set
     */
    public void setCinecityAfficheURL(URL cinecityAfficheURL) {
        this.cinecityAfficheURL = cinecityAfficheURL;
    }
    private URL cinecityFilmURL;
    private String name;
    private URL cinecityAfficheURL;
}
