package fr.efrei.residence;

/**
 * Attribue les identifiants des étudiants et des baux.
 */
public class GenerateurIdentifiants {

    private int etudiants = 0;
    private int baux = 0;

    public String prochainEtudiant() {
        etudiants++;
        return "ETU-" + etudiants;
    }

    public String prochainBail() {
        baux++;
        return "BAIL-" + String.format("%03d", baux);
    }
}
