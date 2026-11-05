package fr.efrei.residence;

import java.time.LocalDate;
import java.util.EnumSet;

public class Main {

    public static void main(String[] args) {
        // construction du graphe d'objets : seul Main (et l'adaptateur de test) le fait
        Database db = new Database();
        Residence residence = new Residence();
        JournalNotifications journal = new JournalNotifications();
        GenerateurIdentifiants identifiants = new GenerateurIdentifiants();
        ServiceNotification notification = new ServiceNotification(new FabriqueCanaux(journal));
        CalculateurLoyer calculateur = new CalculateurLoyer(new AssembleurLoyer());

        ServiceParc parc = new ServiceParc(residence);
        ServiceInscription inscription = new ServiceInscription(new ValidateurInscription(db), db, identifiants);
        ServiceAttribution attribution = new ServiceAttribution(residence, calculateur, db, identifiants, notification);
        ServiceBail baux = new ServiceBail(db, notification);
        ServiceRelance relance = new ServiceRelance(db, notification);
        GenerateurQuittance quittances = new GenerateurQuittance();
        GenerateurRapport rapport = new GenerateurRapport(residence, db, journal);

        parc.ajouterLogement("STUDIO", "C002", 16, 0, false, 0);
        parc.ajouterLogement("DUPLEX", "C003", 60, 1, true, 0);

        System.out.println();
        Etudiant alice = inscription.inscrire("Martin", "Alice", "alice.martin@mail.fr", "0601020304", null,
                Profil.STANDARD, 0, 1500, false, Canal.EMAIL);
        Etudiant bilal = inscription.inscrire("Haddad", "Bilal", "bilal.haddad@mail.fr", "0611223344", null,
                Profil.BOURSIER, 5, 450, false, Canal.SMS);
        Etudiant chloe = inscription.inscrire("Durand", "Chloé", "chloe.durand@mail.fr", "0622334455", null,
                Profil.ALTERNANT, 0, 1100, true, Canal.EMAIL);
        Etudiant diego = inscription.inscrire("Alvarez", "Diego", "diego.alvarez@mail.es", "+34612345678", null,
                Profil.INTERNATIONAL, 0, 3000, false, Canal.SMS);
        Etudiant emma = inscription.inscrire("Petit", "Emma", "emma.petit@mail.fr", null,
                "12 rue des Lilas, 35000 Rennes", Profil.STANDARD, 0, 1300, false, Canal.COURRIER);
        Etudiant gaelle = inscription.inscrire("Morel", "Gaëlle", "gaelle.morel@mail.fr", "0633445566", null,
                Profil.STANDARD, 0, 1000, false, Canal.EMAIL);
        Etudiant hugo = inscription.inscrire("Leroy", "Hugo", "hugo.leroy@mail.fr", "0644556677", null,
                Profil.BOURSIER, 7, 300, true, Canal.EMAIL);
        inscription.inscrire("Faure", "Farid", "farid.faure-at-mail.fr", "0655667788", null,
                Profil.STANDARD, 0, 1200, false, Canal.EMAIL);
        inscription.inscrire("Wang", "Li", "li.wang@mail.cn", "0612121212", null,
                Profil.INTERNATIONAL, 0, 2000, false, Canal.EMAIL);
        inscription.inscrire("Doublon", "Alice", "alice.martin@mail.fr", "0601020304", null,
                Profil.STANDARD, 0, 1500, false, Canal.EMAIL);

        System.out.println();
        LocalDate signature = LocalDate.of(2026, 8, 25);
        Bail bA = attribution.attribuerLogement(alice, TypeLogement.STUDIO, EnumSet.of(Option.PARKING, Option.ASSURANCE), signature);
        Bail bB = attribution.attribuerLogement(bilal, TypeLogement.COLOCATION, EnumSet.of(Option.MENAGE, Option.LAVERIE, Option.FIBRE), signature);
        Bail bC = attribution.attribuerLogement(chloe, TypeLogement.T1, EnumSet.of(Option.LAVERIE, Option.FIBRE, Option.ASSURANCE), signature);
        Bail bD = attribution.attribuerLogement(diego, TypeLogement.T2, EnumSet.of(Option.MENAGE, Option.PARKING, Option.FIBRE, Option.ASSURANCE), signature);
        Bail bE = attribution.attribuerLogement(emma, TypeLogement.COLOCATION, EnumSet.of(Option.FIBRE), signature);
        Bail bG = attribution.attribuerLogement(gaelle, TypeLogement.STUDIO, EnumSet.of(Option.MENAGE, Option.FIBRE), signature);
        Bail bH = attribution.attribuerLogement(hugo, TypeLogement.COLOCATION, EnumSet.of(Option.MENAGE, Option.ASSURANCE), signature);

        System.out.println();
        LocalDate rentree = LocalDate.of(2026, 9, 1);
        baux.entrerDansLesLieux(bA.getId(), rentree);
        baux.entrerDansLesLieux(bB.getId(), rentree);
        baux.entrerDansLesLieux(bC.getId(), rentree);
        baux.entrerDansLesLieux(bD.getId(), rentree);
        baux.entrerDansLesLieux(bE.getId(), LocalDate.of(2026, 8, 20));
        baux.entrerDansLesLieux(bE.getId(), rentree);
        if (bH != null) baux.annulerBail(bH.getId());
        if (bG != null) baux.annulerBail(bG.getId());

        System.out.println();
        baux.payerLoyer(bA.getId(), "2026-09", LocalDate.of(2026, 9, 3), bA.getLoyerChargesComprises());
        baux.payerLoyer(bB.getId(), "2026-09", LocalDate.of(2026, 9, 12), bB.getLoyerChargesComprises());
        baux.payerLoyer(bC.getId(), "2026-09", LocalDate.of(2026, 9, 5), bC.getLoyerChargesComprises());
        baux.payerLoyer(bD.getId(), "2026-09", LocalDate.of(2026, 9, 9), bD.getLoyerChargesComprises());
        baux.payerLoyer(bE.getId(), "2026-09", LocalDate.of(2026, 9, 2), 200);
        baux.payerLoyer(bA.getId(), "2026-10", LocalDate.of(2026, 10, 15), 300);
        baux.payerLoyer(bC.getId(), "2026-10", LocalDate.of(2026, 10, 1), 1000);

        System.out.println();
        System.out.println(quittances.generer(bD, "2026-09"));
        System.out.println(quittances.generer(bA, "2026-10"));

        System.out.println();
        relance.relancerImpayes();

        System.out.println();
        baux.donnerPreavis(bB.getId(), LocalDate.of(2026, 10, 20));
        baux.donnerPreavis(bD.getId(), LocalDate.of(2026, 10, 20));
        baux.donnerPreavis(bE.getId(), LocalDate.of(2026, 10, 20));
        baux.sortir(bD.getId(), LocalDate.of(2026, 11, 5), 0);
        baux.sortir(bB.getId(), LocalDate.of(2026, 11, 5), 40);
        baux.sortir(bD.getId(), LocalDate.of(2026, 11, 20), 120);
        baux.sortir(bE.getId(), LocalDate.of(2026, 11, 20), 30);

        System.out.println();
        System.out.println(rapport.generer());
    }
}
