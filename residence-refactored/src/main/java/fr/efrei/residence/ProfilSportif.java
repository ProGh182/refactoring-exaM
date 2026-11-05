package fr.efrei.residence;

/**
 * Sportif de haut niveau. Dépôt d'un mois et pénalités normales : règles communes.
 */
public class ProfilSportif extends StrategieProfilDeBase {

    private static final double COEFFICIENT_REMISE = 0.85;
    private static final int MULTIPLE_SOLVABILITE = 2;
    private static final double FRAIS_DOSSIER = 50;
    private static final int PREAVIS = 15;

    @Override
    public double appliquerRemise(double loyer) {
        return loyer * COEFFICIENT_REMISE;
    }

    @Override
    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return ressources >= loyerChargesComprises * MULTIPLE_SOLVABILITE;
    }

    @Override
    public double fraisDossier() {
        return FRAIS_DOSSIER;
    }

    @Override
    public int joursPreavis() {
        return PREAVIS;
    }
}
