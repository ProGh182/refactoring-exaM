package fr.efrei.residence;

import java.util.Optional;

public class ProfilInternational extends StrategieProfilDeBase {

    private static final int MOIS_DE_DEPOT = 2;
    private static final double FRAIS_DOSSIER = 150;

    @Override
    public Optional<String> motifRefus(String telephone) {
        if (telephone == null || !telephone.startsWith("+")) {
            return Optional.of("numéro international (+XX) obligatoire");
        }
        return Optional.empty();
    }

    @Override
    public double depotGarantie(double loyerHorsCharges) {
        return loyerHorsCharges * MOIS_DE_DEPOT;
    }

    @Override
    public double fraisDossier() {
        return FRAIS_DOSSIER;
    }

    @Override
    public boolean recoitCopieEmail() {
        return true; // demande de l'administration
    }
}
