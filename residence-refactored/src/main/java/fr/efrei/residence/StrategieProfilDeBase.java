package fr.efrei.residence;

import java.util.Optional;

/**
 * Règles communes : chaque profil ne redéfinit que ce qui le distingue.
 */
public abstract class StrategieProfilDeBase implements StrategieProfil {

    private static final int MULTIPLE_SOLVABILITE = 3;
    private static final double FRAIS_DOSSIER = 100;
    private static final int PREAVIS = 30;
    private static final double TAUX_PENALITE = 0.05;
    private static final double PENALITE_MINIMUM = 10;

    @Override
    public Optional<String> motifRefus(String telephone) {
        return Optional.empty();
    }

    @Override
    public double appliquerRemise(double loyer) {
        return loyer;
    }

    @Override
    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return ressources >= loyerChargesComprises * MULTIPLE_SOLVABILITE;
    }

    @Override
    public double depotGarantie(double loyerHorsCharges) {
        return loyerHorsCharges;
    }

    @Override
    public double fraisDossier() {
        return FRAIS_DOSSIER;
    }

    @Override
    public int joursPreavis() {
        return PREAVIS;
    }

    @Override
    public double penaliteRetard(double loyerChargesComprises) {
        double penalite = loyerChargesComprises * TAUX_PENALITE;
        if (penalite < PENALITE_MINIMUM) penalite = PENALITE_MINIMUM;
        return Utils.arrondir(penalite);
    }

    @Override
    public boolean recoitCopieEmail() {
        return false;
    }
}
