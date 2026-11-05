package fr.efrei.residence;

import fr.efrei.residence.contrat.Canal;
import fr.efrei.residence.contrat.DemandeAttribution;
import fr.efrei.residence.contrat.DemandeDevis;
import fr.efrei.residence.contrat.DemandeInscription;
import fr.efrei.residence.contrat.DemandeLogement;
import fr.efrei.residence.contrat.EtatBail;
import fr.efrei.residence.contrat.EtatLogement;
import fr.efrei.residence.contrat.Notification;
import fr.efrei.residence.contrat.Option;
import fr.efrei.residence.contrat.StatutBail;
import fr.efrei.residence.contrat.SystemeResidence;
import fr.efrei.residence.contrat.TypeLogement;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

/**
 * Branche le nouveau code sur les tests de contrat. Cette classe ne contient que de la traduction.
 */
public class NouveauSystemeAdapter implements SystemeResidence {

    private Database db;
    private Residence residence;
    private JournalNotifications journal;
    private CalculateurLoyer calculateur;
    private ServiceParc parc;
    private ServiceInscription inscription;
    private ServiceAttribution attribution;
    private ServiceBail baux;
    private ServiceRelance relance;
    private GenerateurQuittance quittances;
    private GenerateurRapport rapport;

    /** Réinitialiser = construire un nouveau graphe d'objets. */
    @Override
    public void reinitialiser() {
        db = new Database();
        residence = new Residence();
        journal = new JournalNotifications();
        GenerateurIdentifiants identifiants = new GenerateurIdentifiants();
        ServiceNotification notification = new ServiceNotification(new FabriqueCanaux(journal));
        calculateur = new CalculateurLoyer(new AssembleurLoyer());

        parc = new ServiceParc(residence);
        inscription = new ServiceInscription(new ValidateurInscription(db), db, identifiants);
        attribution = new ServiceAttribution(residence, calculateur, db, identifiants, notification);
        baux = new ServiceBail(db, notification);
        relance = new ServiceRelance(db, notification);
        quittances = new GenerateurQuittance();
        rapport = new GenerateurRapport(residence, db, journal);
    }

    @Override
    public Optional<String> inscrire(DemandeInscription d) {
        Etudiant e = inscription.inscrire(d.nom(), d.prenom(), d.email(), d.telephone(), d.adresse(),
                profil(d.profil()), d.echelonBourse(), d.ressourcesMensuelles(), d.pmr(), canal(d.canalPrefere()));
        return Optional.ofNullable(e).map(Etudiant::getId);
    }

    @Override
    public boolean ajouterLogement(DemandeLogement d) {
        return parc.ajouterLogement(d.type().name(), d.numero(), d.surface(), d.etage(), d.meuble(),
                d.places(), d.surfaceBalcon()) != null;
    }

    @Override
    public Optional<EtatLogement> logement(String numero) {
        Logement l = residence.trouver(numero);
        if (l == null) {
            return Optional.empty();
        }
        return Optional.of(new EtatLogement(l.getNumero(), TypeLogement.valueOf(l.getType().name()), l.getNombrePlaces(),
                l.nombreOccupants(), l.estAccessiblePMR()));
    }

    @Override
    public OptionalDouble devisLoyer(DemandeDevis d) {
        Logement l = residence.trouver(d.numeroLogement());
        if (l == null) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(calculateur.calculerLoyer(l,
                FabriqueProfils.pour(profil(d.profil()), d.echelonBourse()), options(d.options())));
    }

    @Override
    public Optional<String> attribuer(DemandeAttribution d) {
        Etudiant e = db.trouverEtudiantParId(d.refEtudiant());
        if (e == null) {
            return Optional.empty();
        }
        Bail b = attribution.attribuerLogement(e, type(d.type()), options(d.options()), d.dateSignature());
        return Optional.ofNullable(b).map(Bail::getId);
    }

    @Override
    public Optional<EtatBail> consulter(String idBail) {
        Bail b = db.trouverBail(idBail);
        if (b == null) {
            return Optional.empty();
        }
        return Optional.of(new EtatBail(b.getId(), b.getLogement().getNumero(), StatutBail.valueOf(b.getStatut().name()),
                b.getLoyerHorsCharges(), b.getCharges(), b.getLoyerChargesComprises(), b.getCaution(),
                b.getFraisDossier(), b.getDateFin(), b.soldeDu, b.cautionRestituee));
    }

    @Override
    public boolean entrer(String idBail, LocalDate date) {
        return baux.entrerDansLesLieux(idBail, date);
    }

    @Override
    public boolean payer(String idBail, YearMonth mois, LocalDate date, double montant) {
        return baux.payerLoyer(idBail, mois.toString(), date, montant);
    }

    @Override
    public boolean donnerPreavis(String idBail, LocalDate date) {
        return baux.donnerPreavis(idBail, date);
    }

    @Override
    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return baux.sortir(idBail, date, retenuesDegats);
    }

    @Override
    public boolean annuler(String idBail) {
        return baux.annulerBail(idBail);
    }

    @Override
    public Optional<String> quittance(String idBail, YearMonth mois) {
        return Optional.ofNullable(db.trouverBail(idBail)).map(b -> quittances.generer(b, mois.toString()));
    }

    @Override
    public void relancerImpayes() {
        relance.relancerImpayes();
    }

    @Override
    public String rapport() {
        return rapport.generer();
    }

    @Override
    public List<Notification> notifications() {
        return journal.parCanal().stream()
                .map(envoi -> new Notification(Canal.valueOf(envoi.canal().name()), envoi.destinataire(), envoi.objet()))
                .toList();
    }

    private static Profil profil(fr.efrei.residence.contrat.Profil p) {
        return Profil.valueOf(p.name());
    }

    private static fr.efrei.residence.Canal canal(Canal c) {
        return fr.efrei.residence.Canal.valueOf(c.name());
    }

    private static fr.efrei.residence.TypeLogement type(TypeLogement t) {
        return fr.efrei.residence.TypeLogement.valueOf(t.name());
    }

    private static Set<fr.efrei.residence.Option> options(Set<Option> options) {
        Set<fr.efrei.residence.Option> traduites = EnumSet.noneOf(fr.efrei.residence.Option.class);
        for (Option option : options) {
            traduites.add(fr.efrei.residence.Option.valueOf(option.name()));
        }
        return traduites;
    }
}
