package fr.efrei.residence;

public class TarifDuplex extends TarifDeBase {

    private static final double PRIX_M2 = 12.5;
    private static final double FORFAIT = 80;

    @Override
    public double loyerBase(double surface, int places) {
        return surface * PRIX_M2 + FORFAIT;
    }
}
