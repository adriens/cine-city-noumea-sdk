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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.StringTokenizer;

/**
 *
 * @author salad74
 */
public class FilmsWrapper {

    public static final String URL_ROOT = "http://www.cinecity.nc";
    public static final String URL_ACCUEIL = "http://www.cinecity.nc/Home/Accueil";
    public static final String URL_CLASSEMENTS = "http://www.cinecity.nc/Cinecity/Classements";
    public static final String URL_BASE_FILM8 = "http://www.cinecity.nc/Cinecity/Film";
    public static final String INFO_ADRESSE_PHYSIQUE = "18 Rue de la Somme, Nouméa, New Caledonia";
    public static final String INFO_URL_GOOGLE_MAPS = "https://goo.gl/maps/UBuJxg13raT2";
    public static final String INFO_TELEPHONE = "+687 29.20.20";
    public static final String INFO_URL_WEBSITE = "http://www.cinecity.nc";
    public static final String INFO_URL_FACEBOOK = "https://www.facebook.com/pages/Cin%C3%A9-City-NC/1955636754710969";

    final static Logger logger = LoggerFactory.getLogger(FilmsWrapper.class);

    public enum FilmRankingCategory {
        BEST,
        WORST;
    }

    public FilmsWrapper() {
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

    }

    public static final int extractFilmIdFromFilmURL(String filmURL) {
        int out;
        String tmp = filmURL;

        // http://www.cinecity.nc/Cinecity/Film/39428
        logger.debug("Input url to extract : <" + filmURL + ">");
        //tmp = tmp.replace(FilmsWrapper.URL_BASE_FILM8 + "/", "");
        logger.debug("Intermediar transform : <" + tmp + ">");
        return Integer.parseInt(tmp.replace(FilmsWrapper.URL_BASE_FILM8 + "/", ""));
    }

    private static WebClient buildWebClient() {
        // Disable verbose logs
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setDownloadImages(false);
        return webClient;
    }

    public static final String getFilmURL(int filmId) {
        //http://www.cinecity.nc/Cinecity/Film/50225
        return URL_ROOT + "/Cinecity/Film/" + filmId;
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
            logger.debug("Cinecity local URL : " + URL_ROOT + localUri.getAttribute("href"));
            aFilm.setCinecityFilmURL(new URL(URL_ROOT + localUri.getAttribute("href")));

            //filmName
            HtmlElement filmName = (HtmlElement) urls.get(1);
            aFilm.setName(filmName.getTextContent());
            logger.debug("Film name : " + aFilm.getName());

            // affiche
            HtmlElement affiche = film.getElementsByTagName("img").get(0);
            logger.debug("img : " + URL_ROOT + affiche.getAttribute("src"));
            aFilm.setCinecityAfficheURL(new URL(URL_ROOT + affiche.getAttribute("src")));
            out.add(aFilm);
        }
        return out;
    }

    public ArrayList<Film> getTop20() throws IOException {
        return getTop(FilmRankingCategory.BEST, 20);
    }

    public ArrayList<Film> getTop(FilmRankingCategory rankingCategoty, int nbFilms) throws IOException {
        int lNbLocalFilm = 20;

        if (nbFilms > 20) {
            lNbLocalFilm = 20;
        } else if (nbFilms < 1) {
            lNbLocalFilm = 1;
        } else {
            lNbLocalFilm = 20;
        }

        ArrayList<Film> out = new ArrayList<>();

        WebClient webClient = buildWebClient();
        HtmlPage htmlPage = webClient.getPage(URL_CLASSEMENTS);

        logger.info("Top <" + nbFilms + "> des <" + rankingCategoty + ">extrait du top 20 des films de ce genre sur Cinecity :");

        //DomElement domFilms;
        DomElement domFilms;
        if ((rankingCategoty == FilmsWrapper.FilmRankingCategory.BEST) || rankingCategoty == null) {
            // it no rank style provided, five the top ones by default
            domFilms = htmlPage.getElementById("col_gauche_coul");
        } else {
            domFilms = htmlPage.getElementById("col_droite_coul");
        }

        DomNodeList<HtmlElement> filmsList = domFilms.getElementsByTagName("a");
        //logger.info("Nb top 20 films : <" + filmsList.size() + ">");
        String filmURL;
        String filmTitle;
        int filmLoopId = 0;
        for (HtmlElement film : filmsList) {
            filmLoopId++;
            Film aFilm = new Film();

            // fiilm url
            filmURL = URL_ROOT + film.getAttribute("href");

            aFilm.setCinecityFilmURL(new URL(filmURL));
            logger.debug("Film URL : <" + aFilm.getCinecityFilmURL() + ">");

            //film name
            filmTitle = film.getAttribute("title");
            logger.debug("Film title : <" + filmTitle + ">");
            aFilm.setName(filmTitle);
            // pas d'affiche sur cette page
            // on va la récuéprer à partir de son URL cinecity
            int lFilmId = FilmsWrapper.extractFilmIdFromFilmURL(aFilm.getCinecityFilmURL().toString());
            logger.debug("Film id : <" + lFilmId + ">");
            //logger.info("filmId : <");
            //FilmsWrapper lWrapper = new FilmsWrapper();
            
            // STop getting details it takes to much time at this point on Heroku whic has
            //very short timeout
            /*URL lAfficheURL = this.getDetailsOfFilm(lFilmId).getAfficheURL();
            logger.debug("Affiche URL : <" + lAfficheURL + ">");
            aFilm.setCinecityAfficheURL(lAfficheURL);
            */
            out.add(aFilm);

            if (filmLoopId == nbFilms) {
                return out;
            }
        }
        return out;
    }

    public ArrayList<Film> getWorsts20() throws IOException {
        return this.getTop(FilmRankingCategory.WORST, 20);
    }

    public static final String cleanRawDetails(String rawDetails) {
        String out;
        out = rawDetails;
        logger.debug("rawdetails : <" + out + ">");
        out = out.replace("Voir la bande-annonce", "").trim();
        out = out.substring(0, out.length() - 1);
        out = out.trim();
        logger.debug("cleaned rawdetails : <" + out + ">");
        // Guerre - USA - 2018 - 131 mn - Tous publics
        return out;
    }

    public final static String extractGenreFromRawDetails(String inRawDetails) {
        String out = FilmsWrapper.cleanRawDetails(inRawDetails);
        StringTokenizer st = new StringTokenizer(out, "-");
        out = st.nextToken().trim();
        return out;
    }

    public FilmDetails getDetailsOfFilm(int filmId) throws IOException {
        WebClient webClient = buildWebClient();
        HtmlPage htmlPage = webClient.getPage(FilmsWrapper.getFilmURL(filmId));
        FilmDetails out = new FilmDetails();

        out.setFilmId(filmId);
        //http://www.cinecity.nc/Cinecity/Film/39371
        out.setFilmURL(new URL(FilmsWrapper.getFilmURL(filmId)));
        logger.info("Récuperation des details du film <" + filmId + ">");
        logger.debug("Film url : <" + out.getFilmURL() + ">");
        // le titre
        String titre = ((HtmlElement) htmlPage.getElementById("fiche_titre")).getTextContent().trim();
        logger.debug("Film details pour <" + filmId + ">");
        logger.debug("Nom du film : <" + titre + ">");
        out.setTitre(titre);

        // url de l'affiche
        String afficheURL = ((HtmlElement) htmlPage.getElementById("fiche_affiche")).getElementsByTagName("img").get(0).getAttribute("src");
        logger.debug("detected url : <" + afficheURL + ">");
        afficheURL = URL_ROOT + afficheURL;
        logger.debug("url complète : <" + afficheURL + ">");
        out.setAfficheURL(new URL(afficheURL));
        // realisateur
        String realisateur = ((HtmlElement) htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(0).getTextContent();
        logger.debug("realisateur : <" + realisateur + ">");
        out.setRealisateur(realisateur);

        //acteurs
        String acteurs = ((HtmlElement) htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(1).getTextContent();
        logger.debug("acteurs : <" + acteurs + ">");
        out.setActeurs(acteurs);

        // note
        String note = ((HtmlElement) htmlPage.getElementById("fiche_vote")).getElementsByTagName("span").get(0).getElementsByTagName("b").get(0).getTextContent();
        logger.debug("note : <" + note + ">");
        out.setNote(Integer.parseInt(note));

        // synopsys
        String synopsys;
        DomNodeList<HtmlElement> htmlNodes = htmlPage.getElementById("fiche_texte").getElementsByTagName("p");
        // le synopsys est toujours le dernier p de la liste
        //synopsys = ((HtmlElement) htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(2).getTextContent();
        synopsys = htmlNodes.get(htmlNodes.size() - 1).getTextContent();
        logger.debug("Synopsys : <" + synopsys + ">");
        out.setSynopsys(synopsys);

        // details (Genre - Pays - Année - durée en minutes - Type de public -)
        // exemple : Guerre - USA - 2018 - 131 mn - Tous publics
        // rawDetails
        String rawDetails = ((HtmlElement) htmlPage.getElementById("fiche_texte")).getElementsByTagName("h1").get(0).getTextContent().trim();
        logger.debug("rawDetails : <" + rawDetails + ">");
        out.setRawDetails(FilmsWrapper.cleanRawDetails(rawDetails));
        //String test = FilmsWrapper.cleanRawDetails(rawDetails);
        //FilmsWrapper.getRawDetails(rawDetails);
        //ArrayList<String> detailsList = FilmsWrapper.getRawDetailsAsList(rawDetails);

        // le genre est TOUJOURS en position 0
        out.setGenre(FilmsWrapper.extractGenreFromRawDetails(rawDetails));
        logger.debug("Set genre to <" + out.getGenre() + ">");
        // Parfois il y a plusieurs pays... ;-(
        //out.setPays(detailsList.get(1));

        //out.setAnnee(Integer.parseInt(detailsList.get(detailsList.size() - 3)));
        // durée en avant dernière position
        //String duree = detailsList.get(detailsList.size() - 2);
        //logger.info("Duree extraite : <" + duree + ">");
        //out.setDureeMinutes(FilmsWrapper.parseDuree(duree));
        //String publicCible = detailsList.get(detailsList.size() - 1);
        //out.setPublicCible(publicCible);
        return out;
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
            //wrapper.getDetailsOfFilm(50225);
            //wrapper.getTop(FilmRankingCategory.BEST, 3);
            wrapper.getWorsts20();
            //wrapper.getDetailsOfFilm(39476);
            //wrapper.getDetailsOfFilm(39373);
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
