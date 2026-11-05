package fr.efrei.residence;

import java.util.Optional;

/**
 * Vérifie qu'une demande d'inscription respecte les règles.
 */
public class ValidateurInscription {

    private final EtudiantRepository etudiants;

    public ValidateurInscription(EtudiantRepository etudiants) {
        this.etudiants = etudiants;
    }

    /** @return le motif du refus, ou vide si la demande est valide */
    public Optional<String> motifRefus(String nom, String email, String telephone, String adresse, Profil profil,
                                       int echelonBourse, Canal canalPrefere) {
        if (nom == null || nom.trim().isEmpty()) {
            return Optional.of("nom obligatoire");
        }
        if (!Utils.emailValide(email)) {
            return Optional.of("email invalide (" + email + ")");
        }
        if (etudiants.trouverEtudiantParEmail(email) != null) {
            return Optional.of(email + " déjà inscrit");
        }
        if (profil == null) {
            return Optional.of("profil inconnu " + profil);
        }
        Optional<String> motifProfil = FabriqueProfils.pour(profil, echelonBourse).motifRefus(telephone);
        if (motifProfil.isPresent()) {
            return motifProfil;
        }
        if (canalPrefere == Canal.SMS && telephone == null) {
            return Optional.of("téléphone obligatoire pour les SMS");
        }
        if (canalPrefere == Canal.COURRIER && (adresse == null || adresse.isEmpty())) {
            return Optional.of("adresse obligatoire pour le courrier");
        }
        return Optional.empty();
    }
}
