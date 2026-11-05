package fr.efrei.residence;

import java.util.Set;

/**
 * Calcule le loyer hors charges et les charges d'un logement.
 */
public class CalculateurLoyer {

    private final AssembleurLoyer assembleur;

    public CalculateurLoyer(AssembleurLoyer assembleur) {
        this.assembleur = assembleur;
    }

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
