package fr.efrei.residence;

/**
 * Prix communs : chaque type fournit son loyer de base et redéfinit ce qui le distingue.
 */
public abstract class TarifDeBase implements TarifLogement {

    private static final double TAUX_MENAGE = 1.08;
    private static final double PRIX_FIBRE = 20;
    private static final double CHARGES = 35;

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
