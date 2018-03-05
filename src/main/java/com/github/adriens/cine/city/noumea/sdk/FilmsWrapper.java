/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.cine.city.noumea.sdk;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class FilmsWrapper {

    public static final String URL_ROOT = "http://www.cinecity.nc/";
    public static final String URL_ACCUEIL = "http://www.cinecity.nc/Home/Accueil/";
    public static final String URL_CLASSEMENTS = "http://www.cinecity.nc/Cinecity/Classements/";

    public static final String INFO_ADRESSE_PHYSIQUE = "18 Rue de la Somme, Nouméa, New Caledonia";
    public static final String INFO_URL_GOOGLE_MAPS = "https://goo.gl/maps/UBuJxg13raT2";
    public static final String INFO_TELEPHONE = "+687 29.20.20";
    public static final String INFO_URL_WEBSITE = "http://www.cinecity.nc";
    public static final String INFO_URL_FACEBOOK = "https://www.facebook.com/pages/Cin%C3%A9-City-NC/1955636754710969";
    
    final static Logger logger = LoggerFactory.getLogger(FilmsWrapper.class);

    private static WebClient buildWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setDownloadImages(false);
        return webClient;
    }

    public static final String getFilmURL(int filmId){
        //http://www.cinecity.nc/Cinecity/Film/50225
        return URL_ROOT + "Cinecity/Film/" + filmId;
    }
    public ArrayList<Film> getFilmsDuJour() throws IOException {
        ArrayList<Film> out = new ArrayList<>();

        WebClient webClient = buildWebClient();
        HtmlPage htmlPage = webClient.getPage(FilmsWrapper.URL_ACCUEIL);
        DomElement domFilms = htmlPage.getElementById("tableFilms");
        logger.debug(domFilms.toString());
        DomNodeList<HtmlElement> filmsList = domFilms.getElementsByTagName("li");
        logger.info("Liste des films actuellement au Cinecity :");
        logger.info("Nb films : <" + filmsList.size() + ">");
        int nbFilms = 0;

        for (HtmlElement film : filmsList) {
            nbFilms++;
            Film aFilm = new Film();
            logger.info("#####################################");
            logger.info("Film <" + nbFilms + ">");
            logger.debug(film.asXml());

            // cineCity URL
            DomNodeList urls = film.getElementsByTagName("a");
            HtmlElement localUri = (HtmlElement) urls.get(0);
            logger.info("Cinecity local URL : " + URL_ROOT + localUri.getAttribute("href"));
            aFilm.setCinecityFilmURL(new URL(URL_ROOT + localUri.getAttribute("href")));

            //filmName
            HtmlElement filmName = (HtmlElement) urls.get(1);
            aFilm.setName(filmName.getTextContent());
            logger.info("Film name : " + aFilm.getName());

            // affiche
            HtmlElement affiche = film.getElementsByTagName("img").get(0);
            logger.debug("img : " + URL_ROOT + affiche.getAttribute("src"));
            aFilm.setCinecityAfficheURL(new URL(URL_ROOT + affiche.getAttribute("src")));
            out.add(aFilm);
        }
        return out;
    }

    public ArrayList<Film> getTop20() throws IOException {
        ArrayList<Film> out = new ArrayList<>();

        WebClient webClient = buildWebClient();
        HtmlPage htmlPage = webClient.getPage(URL_CLASSEMENTS);

        logger.info("Liste des 20 meilleurs films sur Cinecity :");

        DomElement domFilms = htmlPage.getElementById("col_gauche_coul");
        DomNodeList<HtmlElement> filmsList = domFilms.getElementsByTagName("a");
        logger.info("Nb top 20 films : <" + filmsList.size() + ">");
        String filmURL;
        String filmTitle;

        for (HtmlElement film : filmsList) {
            Film aFilm = new Film();

            // fiilm url
            filmURL = URL_ROOT + film.getAttribute("href");
            System.out.println(filmURL);
            aFilm.setCinecityFilmURL(new URL(filmURL));

            //film name
            filmTitle = film.getAttribute("title");
            System.out.println(filmTitle);
            aFilm.setName(filmTitle);
            out.add(aFilm);
        }
        return out;
    }

    public ArrayList<Film> getWorst20() throws IOException {
        ArrayList<Film> out = new ArrayList<>();

        WebClient webClient = buildWebClient();
        HtmlPage htmlPage = webClient.getPage(URL_CLASSEMENTS);

        DomElement domFilms = htmlPage.getElementById("col_droite_coul");
        DomNodeList<HtmlElement> filmsList = domFilms.getElementsByTagName("a");
        //logger.info("Film name : " + aFilm.getName());
        logger.info("Nb top 20 films : <" + filmsList.size() + ">");
        String filmURL;
        String filmTitle;
        logger.info("Liste des pires 20 films sur Cinecity :");

        for (HtmlElement film : filmsList) {
            Film aFilm = new Film();

            // fiilm url
            filmURL = URL_ROOT + film.getAttribute("href");

            aFilm.setCinecityFilmURL(new URL(filmURL));
            logger.info(aFilm.getCinecityFilmURL().toString());
            //film name
            filmTitle = film.getAttribute("title");
            //System.out.println(filmTitle);
            aFilm.setName(filmTitle);
            logger.info("Film : <" + aFilm.getName() + ">");
            out.add(aFilm);
        }
        return out;
    }

    public void setFilmDetails(int filmId) throws IOException {
        WebClient webClient = buildWebClient();
        HtmlPage htmlPage = webClient.getPage(FilmsWrapper.getFilmURL(filmId));
        
        // le titre
        String titre = ((HtmlElement)htmlPage.getElementById("fiche_titre")).getTextContent().trim();
        logger.info("Film details pour <" + filmId + ">");
        logger.info("Nom du film : <" + titre + ">");
        
        // url de l'affiche
        String afficheURL = ((HtmlElement)htmlPage.getElementById("fiche_affiche")).getElementsByTagName("img").get(0).getAttribute("src");
        logger.info("detected url : <" + afficheURL + ">");
        afficheURL = URL_ROOT + afficheURL;
        logger.info("url co;complète : <" + afficheURL + ">");
        
        // realisateur
        String realisateur = ((HtmlElement)htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(0).getTextContent();
        logger.info("realisateur : <" + realisateur + ">");
        
        //acteurs
        String acteurs = ((HtmlElement)htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(1).getTextContent();
        logger.info("acteurs : <" + acteurs + ">");
        
        // details (Genre - Pays - Année - durée en minutes - Type de public -)
        // exemple : Guerre - USA - 2018 - 131 mn - Tous publics
        // rawDetails
        String rawDetails = ((HtmlElement)htmlPage.getElementById("fiche_texte")).getElementsByTagName("h1").get(0).getTextContent();
        logger.info("rawDetails : <" + rawDetails + ">");
                
        // note
        String note = ((HtmlElement)htmlPage.getElementById("fiche_vote")).getElementsByTagName("span").get(0).getElementsByTagName("b").get(0).getTextContent();
        logger.info("note : <" + note + ">");
        
        // synopsus
        String synopsys;
        synopsys = ((HtmlElement)htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(2).getTextContent();
        logger.info("Synopsys : <" + synopsys + ">");
        
    }
    public static void main(String[] args) {
        try {
            FilmsWrapper wrapper = new FilmsWrapper();
            /*ArrayList<Film> listeFilmsDuJour = wrapper.getFilmsDuJour();
            int filmIndex = 0;
            for (Film aFilm : listeFilmsDuJour) {
                filmIndex++;
                System.out.println("Film <" + filmIndex + "> trouvé : <" + aFilm + ">");
            }
            */
            wrapper.setFilmDetails(50225);
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
