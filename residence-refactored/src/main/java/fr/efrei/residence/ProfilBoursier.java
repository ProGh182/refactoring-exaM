package fr.efrei.residence;

import java.util.Optional;

public class ProfilBoursier extends StrategieProfilDeBase {

    private static final int ECHELON_MIN = 0;
    private static final int ECHELON_MAX = 7;
    private static final double REMISE_PAR_ECHELON = 0.05;
    private static final double REMISE_MAX = 0.30;
    private static final int PREAVIS = 15;

    private final int echelon;

    public ProfilBoursier(int echelon) {
        this.echelon = echelon;
    }

    @Override
    public Optional<String> motifRefus(String telephone) {
        if (echelon < ECHELON_MIN || echelon > ECHELON_MAX) {
            return Optional.of("échelon de bourse invalide");
        }
        return Optional.empty();
    }

    @Override
    public double appliquerRemise(double loyer) {
        double taux = echelon * REMISE_PAR_ECHELON;
        if (taux > REMISE_MAX) taux = REMISE_MAX;
        return loyer * (1 - taux);
    }

    @Override
    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return true; // garantie Visale
    }

    @Override
    public double depotGarantie(double loyerHorsCharges) {
        return 0;
    }

    @Override
    public double fraisDossier() {
        return 0;
    }

    @Override
    public int joursPreavis() {
        return PREAVIS;
    }

    @Override
    public double penaliteRetard(double loyerChargesComprises) {
        return 0;
    }
}
