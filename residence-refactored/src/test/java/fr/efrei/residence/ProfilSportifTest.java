package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfilSportifTest {

    private static final double CENTIME = 0.001;

    private final StrategieProfil sportif = FabriqueProfils.pour(Profil.SPORTIF, 0);

    @Test
    void la_fabrique_choisit_la_strategie_sportif() {
        assertInstanceOf(ProfilSportif.class, sportif);
    }

    @Test
    void remise_de_15_pourcent() {
        assertEquals(85.0, sportif.appliquerRemise(100), CENTIME);
    }

    @Test
    void solvabilite_2_fois_le_loyer_charges_comprises() {
        assertTrue(sportif.estSolvable(1000, 500));
        assertFalse(sportif.estSolvable(999, 500));
    }

    @Test
    void depot_d_un_mois_et_50_euros_de_frais() {
        assertEquals(400.0, sportif.depotGarantie(400), CENTIME);
        assertEquals(50.0, sportif.fraisDossier(), CENTIME);
    }

    @Test
    void preavis_de_15_jours() {
        assertEquals(15, sportif.joursPreavis());
    }

    @Test
    void penalites_de_retard_normales() {
        assertEquals(20.0, sportif.penaliteRetard(400), CENTIME);
        assertEquals(10.0, sportif.penaliteRetard(100), CENTIME);
    }

    @Test
    void aucune_condition_d_inscription_particuliere() {
        assertTrue(sportif.motifRefus(null).isEmpty());
        assertFalse(sportif.recoitCopieEmail());
    }

    @Test
    void loyer_d_un_t1_pour_un_sportif() {
        Logement t1 = FabriqueLogements.creer(TypeLogement.T1, "B105", 30, 1, false, 0, 0);
        CalculateurLoyer calculateur = new CalculateurLoyer(new AssembleurLoyer());
        assertEquals(374.0, calculateur.calculerLoyer(t1, sportif, java.util.EnumSet.noneOf(Option.class)), CENTIME);
    }
}
