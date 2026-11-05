package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptionLocalVeloTest {

    private static final double CENTIME = 0.001;

    private final AssembleurLoyer assembleur = new AssembleurLoyer();
    private final Logement t1 = FabriqueLogements.creer(TypeLogement.T1, "B105", 30, 1, false, 0, 0);

    @Test
    void ajoute_6_euros() {
        assertEquals(106.0, new OptionLocalVelo(() -> 100.0).montant(), CENTIME);
    }

    @Test
    void l_assembleur_l_applique_apres_l_assurance() {
        assertEquals(455.0, assembleur.assembler(t1, EnumSet.of(Option.ASSURANCE, Option.VELO)).montant(), CENTIME);
    }

    @Test
    void le_menage_reste_applique_avant() {
        assertEquals(481.2, assembleur.assembler(t1, EnumSet.of(Option.VELO, Option.MENAGE)).montant(), CENTIME); // 440 × 1,08 + 6
    }

    @Test
    void la_remise_porte_aussi_sur_le_local_velo() {
        CalculateurLoyer calculateur = new CalculateurLoyer(assembleur);
        assertEquals(401.4, calculateur.calculerLoyer(t1, new ProfilAlternant(), EnumSet.of(Option.VELO)), CENTIME); // 446 × 0,9
    }

    @Test
    void la_quittance_le_liste_en_dernier() {
        assertEquals(Option.VELO, Option.values()[Option.values().length - 1]);
        assertEquals("local vélo", Option.VELO.getLibelle());
    }
}
