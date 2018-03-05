/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.cine.city.noumea.sdk;

import com.gargoylesoftware.htmlunit.WebClient;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class FilmDetails {

    final static Logger logger = LoggerFactory.getLogger(FilmDetails.class);

    public FilmDetails(){
        
    }
    
        
   /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the afficheURL
     */
    public URL getAfficheURL() {
        return afficheURL;
    }

    /**
     * @param afficheURL the afficheURL to set
     */
    public void setAfficheURL(URL afficheURL) {
        this.afficheURL = afficheURL;
    }

    /**
     * @return the filmId
     */
    public int getFilmId() {
        return filmId;
    }

    /**
     * @param filmId the filmId to set
     */
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    /**
     * @return the filmURL
     */
    public URL getFilmURL() {
        return filmURL;
    }

    /**
     * @param filmURL the filmURL to set
     */
    public void setFilmURL(URL filmURL) {
        this.filmURL = filmURL;
    }

    /**
     * @return the realisateur
     */
    public String getRealisateur() {
        return realisateur;
    }

    /**
     * @param realisateur the realisateur to set
     */
    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    /**
     * @return the acteurs
     */
    public String getActeurs() {
        return acteurs;
    }

    /**
     * @param acteurs the acteurs to set
     */
    public void setActeurs(String acteurs) {
        this.acteurs = acteurs;
    }

    /**
     * @return the note
     */
    public int getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(int note) {
        this.note = note;
    }

    /**
     * @return the synopsys
     */
    public String getSynopsys() {
        return synopsys;
    }

    /**
     * @param synopsys the synopsys to set
     */
    public void setSynopsys(String synopsys) {
        this.synopsys = synopsys;
    }

    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return the pays
     */
    public String getPays() {
        return pays;
    }

    /**
     * @param pays the pays to set
     */
    public void setPays(String pays) {
        this.pays = pays;
    }

    /**
     * @return the annee
     */
    public int getAnnee() {
        return annee;
    }

    /**
     * @param annee the annee to set
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    /**
     * @return the dureeMinutes
     */
    public int getDureeMinutes() {
        return dureeMinutes;
    }

    /**
     * @param dureeMinutes the dureeMinutes to set
     */
    public void setDureeMinutes(int dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }

    /**
     * @return the publicCible
     */
    public String getPublicCible() {
        return publicCible;
    }

    /**
     * @param publicCible the publicCible to set
     */
    public void setPublicCible(String publicCible) {
        this.publicCible = publicCible;
    }
    private String titre;
    private URL afficheURL;
    private int filmId;
    private URL filmURL;
    private String realisateur;
    private String acteurs;
    private int note;
    private String synopsys;
    private String genre;
    private String pays;
    private int annee;
    private int dureeMinutes;
    private String publicCible;
            
    
    
}
