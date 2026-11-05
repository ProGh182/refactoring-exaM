package fr.efrei.residence;

public class TarifColocation extends TarifDeBase {

    private static final double PRIX_M2 = 11;
    private static final double TAUX_MENAGE = 1.04; // parties communes déjà nettoyées
    private static final double PRIX_FIBRE = 0; // fibre incluse
    private static final double CHARGES = 25;

    @Override
    public double loyerBase(double surface, int places) {
        return surface * PRIX_M2 / places;
    }

    @Override
    public double tauxMenage() {
        return TAUX_MENAGE;
    }

    @Override
    public double prixFibre() {
        return PRIX_FIBRE;
    }

    @Override
    public double charges() {
        return CHARGES;
    }
}
