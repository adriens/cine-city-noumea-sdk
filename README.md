[![Build Status](https://travis-ci.org/adriens/cine-city-noumea-sdk.svg?branch=master)](https://travis-ci.org/adriens/cine-city-noumea-sdk) [![](https://jitpack.io/v/adriens/cine-city-noumea-sdk.svg)](https://jitpack.io/#adriens/cine-city-noumea-sdk)

# cine-city-noumea-sdk
SDK java pour interagir avec le site du CineCity (Nouméa, NC)

# Usage

Pour compiler et lancer la classe main de demo :

```
mvn exec:java
```

Exemple de code :

```java
public static void main(String[] args) {
        try {
            ArrayList<Film> listeFilmsDuJour = FilmsWrapper.getFilmsDuJour();
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
