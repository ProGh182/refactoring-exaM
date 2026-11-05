package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateursLoyerTest {

    private static final double CENTIME = 0.001;
    private static final Loyer CENT_EUROS = () -> 100.0;

    private final AssembleurLoyer assembleur = new AssembleurLoyer();

    @Test
    void loyer_de_base_vient_du_tarif_du_logement() {
        Logement t1 = FabriqueLogements.creer(TypeLogement.T1, "B105", 30, 1, false, 0);
        assertEquals(440.0, new LoyerDeBase(t1).montant(), CENTIME);
    }

    @Test
    void majoration_meuble_de_15_pourcent() {
        assertEquals(115.0, new MajorationMeuble(CENT_EUROS).montant(), CENTIME);
    }

    @Test
    void minoration_rez_de_chaussee_de_5_pourcent() {
        assertEquals(95.0, new MinorationRezDeChaussee(CENT_EUROS).montant(), CENTIME);
    }

    @Test
    void option_menage_applique_le_taux_recu() {
        assertEquals(108.0, new OptionMenage(CENT_EUROS, 1.08).montant(), CENTIME);
        assertEquals(104.0, new OptionMenage(CENT_EUROS, 1.04).montant(), CENTIME);
    }

    @Test
    void option_parking_ajoute_45_euros() {
        assertEquals(145.0, new OptionParking(CENT_EUROS).montant(), CENTIME);
    }

    @Test
    void option_laverie_ajoute_12_euros() {
        assertEquals(112.0, new OptionLaverie(CENT_EUROS).montant(), CENTIME);
    }

    @Test
    void option_fibre_ajoute_le_prix_recu() {
        assertEquals(120.0, new OptionFibre(CENT_EUROS, 20).montant(), CENTIME);
        assertEquals(100.0, new OptionFibre(CENT_EUROS, 0).montant(), CENTIME);
    }

    @Test
    void option_assurance_ajoute_9_euros() {
        assertEquals(109.0, new OptionAssurance(CENT_EUROS).montant(), CENTIME);
    }

    @Test
    void l_ordre_des_decorateurs_change_le_resultat() {
        assertEquals(153.0, new OptionParking(new OptionMenage(CENT_EUROS, 1.08)).montant(), CENTIME);
        assertEquals(156.6, new OptionMenage(new OptionParking(CENT_EUROS), 1.08).montant(), CENTIME);
    }

    @Test
    void l_assembleur_applique_le_menage_avant_les_prix_fixes() {
        Logement t1 = FabriqueLogements.creer(TypeLogement.T1, "B105", 30, 1, false, 0);
        Set<Option> options = EnumSet.of(Option.PARKING, Option.MENAGE);
        assertEquals(520.2, assembleur.assembler(t1, options).montant(), CENTIME);
    }

    @Test
    void l_assembleur_utilise_les_prix_de_la_colocation() {
        Logement colocation = FabriqueLogements.creer(TypeLogement.COLOCATION, "B301", 80, 3, true, 4);
        Set<Option> options = EnumSet.of(Option.MENAGE, Option.FIBRE);
        assertEquals(263.12, assembleur.assembler(colocation, options).montant(), CENTIME);
    }

    @Test
    void l_assembleur_applique_meuble_puis_rez_de_chaussee() {
        Logement studio = FabriqueLogements.creer(TypeLogement.STUDIO, "A001", 26, 0, true, 0);
        assertEquals(397.67, assembleur.assembler(studio, EnumSet.noneOf(Option.class)).montant(), CENTIME);
    }
}
