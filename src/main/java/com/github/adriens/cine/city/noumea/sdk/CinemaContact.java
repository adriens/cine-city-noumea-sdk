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

    public static final String INFO_URL_GOOGLE_MAPS = "https://goo.gl/maps/UBuJxg13raT2";
    public static final String INFO_TELEPHONE = "+687 29.20.20";
    public static final String INFO_URL_WEBSITE = "http://www.cinecity.nc";
    public static final String INFO_URL_FACEBOOK = "https://www.facebook.com/pages/Cin%C3%A9-City-NC/1955636754710969";


    private final String adressePhysique = FilmsWrapper.INFO_ADRESSE_PHYSIQUE;
    private final URL urlGoogleMaps;
    private final String telephone = FilmsWrapper.INFO_TELEPHONE;
    private final URL urlWebsite;
    private final URL urlFacebook;
    
    public CinemaContact() throws MalformedURLException {
        this.urlGoogleMaps = new URL(FilmsWrapper.INFO_URL_GOOGLE_MAPS);
        this.urlWebsite = new URL(FilmsWrapper.INFO_URL_WEBSITE);
        this.urlFacebook = new URL(FilmsWrapper.INFO_URL_FACEBOOK);
        
    } 
            
            
}
