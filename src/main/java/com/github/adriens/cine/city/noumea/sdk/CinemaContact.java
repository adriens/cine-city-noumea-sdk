/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.cine.city.noumea.sdk;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author salad74
 */
public class CinemaContact {

    /**
     * @return the adressePhysique
     */
    public String getAdressePhysique() {
        return adressePhysique;
    }

    /**
     * @param adressePhysique the adressePhysique to set
     */
    public void setAdressePhysique(String adressePhysique) {
        this.adressePhysique = adressePhysique;
    }

    /**
     * @return the urlGoogleMaps
     */
    public URL getUrlGoogleMaps() {
        return urlGoogleMaps;
    }

    /**
     * @param urlGoogleMaps the urlGoogleMaps to set
     */
    public void setUrlGoogleMaps(URL urlGoogleMaps) {
        this.urlGoogleMaps = urlGoogleMaps;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the urlWebsite
     */
    public URL getUrlWebsite() {
        return urlWebsite;
    }

    /**
     * @param urlWebsite the urlWebsite to set
     */
    public void setUrlWebsite(URL urlWebsite) {
        this.urlWebsite = urlWebsite;
    }

    /**
     * @return the urlFacebook
     */
    public URL getUrlFacebook() {
        return urlFacebook;
    }

    /**
     * @param urlFacebook the urlFacebook to set
     */
    public void setUrlFacebook(URL urlFacebook) {
        this.urlFacebook = urlFacebook;
    }

    public static final String INFO_URL_GOOGLE_MAPS = "https://goo.gl/maps/UBuJxg13raT2";
    public static final String INFO_TELEPHONE = "+687 29.20.20";
    public static final String INFO_URL_WEBSITE = "http://www.cinecity.nc";
    public static final String INFO_URL_FACEBOOK = "https://www.facebook.com/pages/Cin%C3%A9-City-NC/1955636754710969";


    private String adressePhysique = FilmsWrapper.INFO_ADRESSE_PHYSIQUE;
    private URL urlGoogleMaps;
    private String telephone = FilmsWrapper.INFO_TELEPHONE;
    private URL urlWebsite;
    private URL urlFacebook;
    
    public CinemaContact() throws MalformedURLException {
        this.urlGoogleMaps = new URL(FilmsWrapper.INFO_URL_GOOGLE_MAPS);
        this.urlWebsite = new URL(FilmsWrapper.INFO_URL_WEBSITE);
        this.urlFacebook = new URL(FilmsWrapper.INFO_URL_FACEBOOK);
        
    } 
            
            
}
