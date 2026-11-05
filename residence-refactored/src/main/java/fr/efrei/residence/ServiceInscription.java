package fr.efrei.residence;

import java.util.Optional;

/**
 * Inscrit un étudiant dont la demande est valide.
 */
public class ServiceInscription {

    private final ValidateurInscription validateur;
    private final EtudiantRepository etudiants;
    private final GenerateurIdentifiants identifiants;

    public ServiceInscription(ValidateurInscription validateur, EtudiantRepository etudiants,
                              GenerateurIdentifiants identifiants) {
        this.validateur = validateur;
        this.etudiants = etudiants;
        this.identifiants = identifiants;
    }

    public Etudiant inscrire(String nom, String prenom, String email, String telephone, String adresse,
                             Profil profil, int echelonBourse, double ressources, boolean pmr, Canal canalPrefere) {
        Optional<String> motif = validateur.motifRefus(nom, email, telephone, adresse, profil, echelonBourse,
                canalPrefere);
        if (motif.isPresent()) {
            System.out.println("Inscription refusée : " + motif.get());
            return null;
        }
        Etudiant e = new Etudiant(identifiants.prochainEtudiant(), nom, prenom, email, telephone, adresse, profil,
                FabriqueProfils.pour(profil, echelonBourse), ressources, pmr, canalPrefere);
        etudiants.sauvegarderEtudiant(e);
        System.out.println("Étudiant inscrit : " + e.getId() + " " + e.getNomComplet() + " (" + profil + ")");
        return e;
    }
}
