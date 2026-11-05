package fr.efrei.residence;

/**
 * Occupation d'un logement par un ou plusieurs étudiants.
 */
public interface Hebergement {

    int getNombrePlaces();

    int nombreOccupants();

    boolean estLibre();

    /** Précondition : {@link #estLibre()} est vrai. */
    void accueillir(Etudiant e);

    void liberer(Etudiant e);

    /** Appelée quand un occupant quitte définitivement le logement (sortie, pas annulation). */
    void apresDepart();
}
