package fr.efrei.residence;

public class TarifT1 extends TarifDeBase {

    private static final double PRIX_M2 = 13;
    private static final double FORFAIT = 50;

    @Override
    public double loyerBase(double surface, int places) {
        return surface * PRIX_M2 + FORFAIT;
    }
}
