[![Build Status](https://travis-ci.org/adriens/cine-city-noumea-sdk.svg?branch=master)](https://travis-ci.org/adriens/cine-city-noumea-sdk) [![](https://jitpack.io/v/adriens/cine-city-noumea-sdk.svg)](https://jitpack.io/#adriens/cine-city-noumea-sdk) [![Dependency Status](https://beta.gemnasium.com/badges/github.com/adriens/cine-city-noumea-sdk.svg)](https://beta.gemnasium.com/projects/github.com/adriens/cine-city-noumea-sdk)

# cine-city-noumea-sdk
SDK java pour interagir avec le site (http://www.cinecity.nc) du CineCity (Nouméa, NC)

# Usage

Pour compiler et lancer la classe main de demo  (joué à la fin du build Travis) :

```
mvn exec:java
```

Exemple de code (joué à la fin du build Travis également) :

```java
public static void main(String[] args) {
        try {
            FilmsWrapper wrapper = new FilmsWrapper();
            ArrayList<Film> listeFilmsDuJour = wrapper.getFilmsDuJour();
            int filmIndex = 0;
            for (Film aFilm : listeFilmsDuJour) {
                filmIndex++;
                System.out.println("Film <" + filmIndex + "> trouvé : <" + aFilm + ">");
            }
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
 ```
