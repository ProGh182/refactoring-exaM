package fr.efrei.residence;

/**
 * Caractéristiques descriptives d'un logement.
 */
public interface FicheLogement {

    String getNumero();

    TypeLogement getType();

    double getSurface();

    int getEtage();

    boolean isMeuble();

    boolean estAccessiblePMR();
}
