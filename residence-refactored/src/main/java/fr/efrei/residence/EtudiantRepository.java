package fr.efrei.residence;

/**
 * Stockage des étudiants inscrits.
 */
public interface EtudiantRepository {

    void sauvegarderEtudiant(Etudiant e);

    Etudiant trouverEtudiantParEmail(String email);

    Etudiant trouverEtudiantParId(String id);
}
