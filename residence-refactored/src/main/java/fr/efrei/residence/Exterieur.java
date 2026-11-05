package fr.efrei.residence;

/**
 * Espaces extérieurs d'un logement.
 */
public interface Exterieur {

    /** @return la surface du balcon en m², 0 si le logement n'en a pas */
    double getSurfaceBalcon();
}
