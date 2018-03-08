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
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;
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

    public enum FilmRankingCategory {
    BEST,
    WORTS;
    }
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
    
    public ArrayList<Film> getTop(FilmRankingCategory rankingCategoty, int nbFilms) throws IOException {
        int lNbLocalFilm = 20;
        
        if(nbFilms > 20){
            lNbLocalFilm = 20;
        }
        else if(nbFilms < 1){
            lNbLocalFilm = 1;
        }
        else {
            lNbLocalFilm = 20;
        }
        
        ArrayList<Film> out = new ArrayList<>();

        WebClient webClient = buildWebClient();
        HtmlPage htmlPage = webClient.getPage(URL_CLASSEMENTS);

        logger.info("Top <" + nbFilms+ "> des <"+ rankingCategoty + ">extrait du top 20 des films de ce genre sur Cinecity :");

        //DomElement domFilms;
        DomElement domFilms;
        if((rankingCategoty == FilmsWrapper.FilmRankingCategory.BEST) || rankingCategoty == null){
            // it no rank style provided, five the top ones by default
            domFilms = htmlPage.getElementById("col_gauche_coul");
        }
        else{
             domFilms = htmlPage.getElementById("col_droite_coul");
        }
        
        DomNodeList<HtmlElement> filmsList = domFilms.getElementsByTagName("a");
        //logger.info("Nb top 20 films : <" + filmsList.size() + ">");
        String filmURL;
        String filmTitle;
        int filmLoopId=0;
        for (HtmlElement film : filmsList) {
            filmLoopId++;
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
            if(filmLoopId == nbFilms){
                return out;
            }
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
    
    public static final String parseRawDetails(String rawDetails){
    String out;
    out = rawDetails;
    logger.info("rawdetails : <" + out + ">");
    out = out.replace("Voir la bande-annonce", "").trim();
    out = out.substring(0, out.length()-1);
    out = out.trim();
    
    logger.info("rawdetails : <" + out + ">");
    
    // Guerre - USA - 2018 - 131 mn - Tous publics
    return out;
}
    
    public static final int parseDuree(String dureeText){
        int out;
        String tmp = dureeText;
        tmp = tmp.replace("mn", "");
        tmp = StringUtils.strip(tmp);
        out = Integer.parseInt(tmp);
        return out;
    }
    
    /**
     * Position :
     * 0 : Genre
     * 1 : Pays
     * 2 : Année
     * 3 : durée au format "x mn"
     * 4 : public
     */
    public static final ArrayList<String> getRawDetails(String rawDetails){
        ArrayList<String> out = new ArrayList<>();
        String cleanRaw = FilmsWrapper.parseRawDetails(rawDetails);
        // tolenize
        StringTokenizer st = new StringTokenizer(cleanRaw, "-");
        String item;
        while (st.hasMoreElements()) {
		item = StringUtils.strip(st.nextElement().toString());
                logger.info("Detecte : <" + item + ">");
                out.add(item);
	}
        return out;
    }
    
    
    
    public FilmDetails getDetailsOfFilm(int filmId) throws IOException {
        WebClient webClient = buildWebClient();
        HtmlPage htmlPage = webClient.getPage(FilmsWrapper.getFilmURL(filmId));
        FilmDetails out = new FilmDetails();
        
        out.setFilmId(filmId);
        
        logger.info("Récuperation des details du film <" + filmId + ">");
        // le titre
        String titre = ((HtmlElement)htmlPage.getElementById("fiche_titre")).getTextContent().trim();
        logger.info("Film details pour <" + filmId + ">");
        logger.info("Nom du film : <" + titre + ">");
        out.setTitre(titre);
        
        // url de l'affiche
        String afficheURL = ((HtmlElement)htmlPage.getElementById("fiche_affiche")).getElementsByTagName("img").get(0).getAttribute("src");
        logger.info("detected url : <" + afficheURL + ">");
        afficheURL = URL_ROOT + afficheURL;
        logger.info("url complète : <" + afficheURL + ">");
        out.setAfficheURL(new URL(afficheURL));
        // realisateur
        String realisateur = ((HtmlElement)htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(0).getTextContent();
        logger.info("realisateur : <" + realisateur + ">");
        out.setRealisateur(realisateur);
        
        //acteurs
        String acteurs = ((HtmlElement)htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(1).getTextContent();
        logger.info("acteurs : <" + acteurs + ">");
        out.setActeurs(acteurs);
           
        // note
        String note = ((HtmlElement)htmlPage.getElementById("fiche_vote")).getElementsByTagName("span").get(0).getElementsByTagName("b").get(0).getTextContent();
        logger.info("note : <" + note + ">");
        out.setNote(Integer.parseInt(note));
        
        // synopsys
        String synopsys;
        synopsys = ((HtmlElement)htmlPage.getElementById("fiche_texte")).getElementsByTagName("p").get(2).getTextContent();
        logger.info("Synopsys : <" + synopsys + ">");
        out.setSynopsys(synopsys);
        
        // details (Genre - Pays - Année - durée en minutes - Type de public -)
        // exemple : Guerre - USA - 2018 - 131 mn - Tous publics
        // rawDetails
        String rawDetails = ((HtmlElement)htmlPage.getElementById("fiche_texte")).getElementsByTagName("h1").get(0).getTextContent().trim();
        logger.info("rawDetails : <" + rawDetails + ">");
        String test = FilmsWrapper.parseRawDetails(rawDetails);
        //FilmsWrapper.getRawDetails(rawDetails);
        ArrayList<String> detailsList = FilmsWrapper.getRawDetails(rawDetails);
        out.setGenre(detailsList.get(0));
        out.setPays(detailsList.get(1));
        out.setAnnee(Integer.parseInt(detailsList.get(2)));
        String duree = detailsList.get(3);
        out.setDureeMinutes(FilmsWrapper.parseDuree(duree));
        
        String publicCible = detailsList.get(4);
        out.setPublicCible(publicCible);
        
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
            wrapper.getTop(FilmRankingCategory.WORTS, 3);
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
