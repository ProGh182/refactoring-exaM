package fr.efrei.residence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PushServiceTest {

    private final JournalNotifications journal = new JournalNotifications();
    private final ByteArrayOutputStream console = new ByteArrayOutputStream();
    private PrintStream sortieOrigine;

    @BeforeEach
    void capturerLaConsole() {
        sortieOrigine = System.out;
        System.setOut(new PrintStream(console, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restaurerLaConsole() {
        System.setOut(sortieOrigine);
    }

    @Test
    void la_fabrique_fournit_le_canal_push() {
        assertInstanceOf(PushService.class, new FabriqueCanaux(journal).pour(Canal.PUSH));
    }

    @Test
    void format_push_vers_l_email_de_l_etudiant() {
        new PushService(journal).envoyer(etudiant(Profil.STANDARD), "Votre bail BAIL-001", "Bienvenue !");
        assertEquals("[PUSH] -> lea.push@mail.fr : Votre bail BAIL-001", console.toString(StandardCharsets.UTF_8).trim());
        assertEquals(List.of(new Envoi(Canal.PUSH, "lea.push@mail.fr", "Votre bail BAIL-001", 0)), journal.parCanal());
    }

    @Test
    void une_relance_passe_aussi_par_le_push() {
        new PushService(journal).relancer(etudiant(Profil.STANDARD), "BAIL-002", 40);
        assertEquals("[PUSH] -> lea.push@mail.fr : RELANCE - 40,00 € impayés sur BAIL-002",
                console.toString(StandardCharsets.UTF_8).trim());
    }

    @Test
    void un_international_en_push_recoit_aussi_l_email_en_anglais() {
        new ServiceNotification(new FabriqueCanaux(journal)).notifier(etudiant(Profil.INTERNATIONAL), "Préavis", "Texte");
        assertEquals(List.of(
                new Envoi(Canal.EMAIL, "lea.push@mail.fr", "[EN] Préavis", 0),
                new Envoi(Canal.PUSH, "lea.push@mail.fr", "Préavis", 0)
        ), journal.parCanal());
    }

    @Test
    void un_push_ne_compte_ni_comme_sms_ni_comme_timbre() {
        new PushService(journal).envoyer(etudiant(Profil.STANDARD), "Sujet", "Texte");
        assertEquals(0, journal.nombre(Canal.SMS));
        assertEquals(0, journal.coutTotal(), 0.001);
    }

    private static Etudiant etudiant(Profil profil) {
        return new Etudiant("ETU-1", "Push", "Léa", "lea.push@mail.fr", "+33600000000", null, profil,
                FabriqueProfils.pour(profil, 0), 3000, false, Canal.PUSH);
    }
}
