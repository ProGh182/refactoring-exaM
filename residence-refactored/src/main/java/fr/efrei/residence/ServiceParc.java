package fr.efrei.residence;

import java.util.Optional;

/**
 * Ajoute un nouveau logement au parc de la résidence.
 */
public class ServiceParc {

    private final Residence residence;

    public ServiceParc(Residence residence) {
        this.residence = residence;
    }

    /**
     * @param type  texte du type ; un type inconnu est refusé
     * @param places        nombre de places, utilisé seulement pour une colocation
     * @param surfaceBalcon surface du balcon en m², utilisée seulement pour un T2
     */
    public Logement ajouterLogement(String type, String numero, double surface, int etage, boolean meuble,
                                    int places, double surfaceBalcon) {
        if (residence.trouver(numero) != null) {
            System.out.println("Logement " + numero + " existe déjà");
            return null;
        }
        Optional<TypeLogement> typeLogement = TypeLogement.depuis(type);
        if (typeLogement.isEmpty()) {
            System.out.println("Type de logement inconnu : " + type);
            return null;
        }
        Logement l = FabriqueLogements.creer(typeLogement.get(), numero, surface, etage, meuble, places, surfaceBalcon);
        residence.ajouter(l);
        System.out.println("Logement ajouté : " + numero + " (" + type + ")");
        return l;
    }
}
