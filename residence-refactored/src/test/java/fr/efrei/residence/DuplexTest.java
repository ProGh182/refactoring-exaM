package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DuplexTest {

    private static final double CENTIME = 0.001;

    private final Logement duplex = FabriqueLogements.creer(TypeLogement.DUPLEX, "C003", 40, 1, false, 0, 0);

    @Test
    void la_fabrique_cree_un_duplex() {
        assertInstanceOf(Duplex.class, duplex);
        assertEquals(TypeLogement.DUPLEX, duplex.getType());
    }

    @Test
    void loyer_de_base_12_euros_50_le_m2_plus_80_euros() {
        assertEquals(580.0, new LoyerDeBase(duplex).montant(), CENTIME);
    }

    @Test
    void un_seul_occupant_et_pas_de_balcon() {
        assertEquals(1, duplex.getNombrePlaces());
        assertEquals(0, duplex.getSurfaceBalcon(), CENTIME);
        duplex.accueillir(etudiant());
        assertEquals(false, duplex.estLibre());
    }

    @Test
    void charges_et_options_standard() {
        CalculateurLoyer calculateur = new CalculateurLoyer(new AssembleurLoyer());
        assertEquals(35.0, calculateur.calculerCharges(duplex), CENTIME);
        assertEquals(646.4, calculateur.calculerLoyer(duplex, new ProfilStandard(),
                EnumSet.of(Option.MENAGE, Option.FIBRE)), CENTIME); // 580 × 1,08 + 20
    }

    @Test
    void on_peut_ajouter_un_duplex_au_parc_et_il_apparait_au_rapport() {
        Residence residence = new Residence();
        assertNotNull(new ServiceParc(residence).ajouterLogement("DUPLEX", "C003", 60, 1, true, 0, 0));
        String rapport = new GenerateurRapport(residence, new Database(), new JournalNotifications()).generer();
        assertTrue(rapport.contains("Duplex       : 0/1\n"));
    }

    private static Etudiant etudiant() {
        return new Etudiant("ETU-1", "Test", "Duplex", "duplex@mail.fr", null, null, Profil.STANDARD,
                new ProfilStandard(), 3000, false, Canal.EMAIL);
    }
}
