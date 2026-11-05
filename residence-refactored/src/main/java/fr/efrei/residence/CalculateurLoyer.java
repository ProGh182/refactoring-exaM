package fr.efrei.residence;

import java.util.Set;

public class CalculateurLoyer {

    private final AssembleurLoyer assembleur = new AssembleurLoyer();

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, StrategieProfil profil, Set<Option> options) {
        double loyer = assembleur.assembler(l, options).montant();
        return Utils.arrondir(profil.appliquerRemise(loyer));
    }

    public double calculerCharges(Logement l) {
        return l.getTarif().charges();
    }
}
