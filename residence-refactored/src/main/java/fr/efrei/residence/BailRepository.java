package fr.efrei.residence;

import java.util.List;

/**
 * Stockage des baux.
 */
public interface BailRepository {

    void sauvegarderBail(Bail b);

    void mettreAJourBail(Bail b);

    Bail trouverBail(String id);

    /** @return tous les baux, triés par identifiant */
    List<Bail> tousLesBaux();
}
