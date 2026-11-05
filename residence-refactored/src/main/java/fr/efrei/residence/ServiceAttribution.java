package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

/**
 * Choisit le premier logement qui convient à un étudiant pour lui faire signer un bail.
 */
public class ServiceAttribution {

    private final Residence residence;
    private final CalculateurLoyer calculateur;
    private final BailRepository baux;
    private final GenerateurIdentifiants identifiants;
    private final ServiceNotification notification;

    public ServiceAttribution(Residence residence, CalculateurLoyer calculateur, BailRepository baux,
                              GenerateurIdentifiants identifiants, ServiceNotification notification) {
        this.residence = residence;
        this.calculateur = calculateur;
        this.baux = baux;
        this.identifiants = identifiants;
        this.notification = notification;
    }

    public Bail attribuerLogement(Etudiant e, TypeLogement typeSouhaite, Set<Option> options, LocalDate dateSignature) {
        System.out.println("--- Attribution d'un " + typeSouhaite + " à " + e.getNomComplet());
        Logement choisi = null;
        double loyer = 0;
        double charges = 0;
        for (Logement l : residence.logements()) {
            if (l.getType() != typeSouhaite) continue;
            if (!l.estLibre()) continue;
            if (e.isPmr() && !l.estAccessiblePMR()) continue;

            double loyerCandidat = calculateur.calculerLoyer(l, e.getStrategie(), options);
            double chargesCandidat = calculateur.calculerCharges(l);
            double total = loyerCandidat + chargesCandidat;

            if (!e.getStrategie().estSolvable(e.getRessourcesMensuelles(), total)) {
                System.out.println("  " + l.getNumero() + " : ressources insuffisantes (" + Utils.euros(total) + "/mois)");
                continue;
            }
            choisi = l;
            loyer = loyerCandidat;
            charges = chargesCandidat;
            break;
        }
        if (choisi == null) {
            System.out.println("  Aucun logement disponible pour " + e.getNomComplet());
            return null;
        }

        if (choisi instanceof Colocation) {
            choisi.ajouterColocataire(e);
        } else {
            choisi.loger(e);
        }

        String id = identifiants.prochainBail();
        Bail b = new Bail(id, e, choisi, options, dateSignature);
        b.setLoyerHorsCharges(loyer);
        b.setCharges(charges);
        b.setCaution(Utils.arrondir(e.getStrategie().depotGarantie(loyer)));
        b.setFraisDossier(e.getStrategie().fraisDossier());

        baux.sauvegarderBail(b);
        System.out.println("  Bail " + id + " signé : " + choisi.getNumero() + ", " + Utils.euros(b.getLoyerChargesComprises())
                + " CC/mois, caution " + Utils.euros(b.getCaution()) + ", frais de dossier " + Utils.euros(b.getFraisDossier()));
        notification.notifier(e, "Votre bail " + id,
                "Bienvenue à la " + Residence.NOM + " ! Logement " + choisi.getNumero() + ", loyer "
                        + Utils.euros(b.getLoyerChargesComprises()) + " charges comprises.");
        return b;
    }
}
