package fr.efrei.residence;

/**
 * Moyen de contacter un étudiant.
 */
public interface CanalNotification {

    void envoyer(Etudiant etudiant, String sujet, String message);

    /** Les relances d'impayés ont leurs propres textes sur chaque canal. */
    void relancer(Etudiant etudiant, String idBail, double solde);
}
