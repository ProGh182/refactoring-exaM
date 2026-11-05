package fr.efrei.residence;

import java.util.Optional;

/**
 * Règles qui dépendent du profil de l'étudiant.
 */
public interface StrategieProfil {

    /** @return le motif de refus de l'inscription, ou vide si le profil est valide */
    Optional<String> motifRefus(String telephone);

    double appliquerRemise(double loyer);

    boolean estSolvable(double ressources, double loyerChargesComprises);

    double depotGarantie(double loyerHorsCharges);

    double fraisDossier();

    int joursPreavis();

    double penaliteRetard(double loyerChargesComprises);

    /** @return {@code true} si l'étudiant reçoit une copie par email quand son canal n'est pas l'email */
    boolean recoitCopieEmail();
}
