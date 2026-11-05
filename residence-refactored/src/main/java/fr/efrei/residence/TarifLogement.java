package fr.efrei.residence;

/**
 * Règles de prix qui dépendent du type de logement.
 */
public interface TarifLogement {

    double loyerBase(double surface, int places);

    double tauxMenage();

    double prixFibre();

    double charges();
}
