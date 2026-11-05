package fr.efrei.residence;

public class ProfilAlternant extends StrategieProfilDeBase {

    private static final double COEFFICIENT_REMISE = 0.90;
    private static final int MULTIPLE_SOLVABILITE = 2;
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
    public int joursPreavis() {
        return PREAVIS;
    }
}
