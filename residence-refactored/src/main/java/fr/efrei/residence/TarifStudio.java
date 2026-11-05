package fr.efrei.residence;

public class TarifStudio extends TarifDeBase {

    private static final double PRIX_M2 = 14;
    private static final double LOYER_MINIMUM = 350;

    @Override
    public double loyerBase(double surface, int places) {
        double loyer = surface * PRIX_M2;
        if (loyer < LOYER_MINIMUM) loyer = LOYER_MINIMUM;
        return loyer;
    }
}
