package fr.efrei.residence;

public class TarifT2 extends TarifDeBase {

    private static final double PRIX_M2 = 12;
    private static final double FORFAIT = 100;

    @Override
    public double loyerBase(double surface, int places) {
        return surface * PRIX_M2 + FORFAIT;
    }
}
