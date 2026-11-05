package fr.efrei.residence;

public class MinorationRezDeChaussee extends DecorateurLoyer {

    private static final double COEFFICIENT = 0.95;

    public MinorationRezDeChaussee(Loyer interieur) {
        super(interieur);
    }

    @Override
    public double montant() {
        return interieur.montant() * COEFFICIENT;
    }
}
