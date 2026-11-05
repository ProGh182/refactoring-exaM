package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

/**
 * Base commune des logements : tous accueillent de 1 à {@code places} occupants de la même manière.
 */
public abstract class Logement implements FicheLogement, Hebergement, Exterieur {

    protected final String numero;
    private final TypeLogement type;
    private final TarifLogement tarif;
    private final double surface;
    private final int etage;
    private final boolean meuble;
    private final int places;
    private final List<Etudiant> occupants = new ArrayList<>();

    protected Logement(String numero, TypeLogement type, TarifLogement tarif, double surface, int etage,
                       boolean meuble, int places) {
        this.numero = numero;
        this.type = type;
        this.tarif = tarif;
        this.surface = surface;
        this.etage = etage;
        this.meuble = meuble;
        this.places = places;
    }

    @Override public String getNumero() { return numero; }
    @Override public TypeLogement getType() { return type; }
    public TarifLogement getTarif() { return tarif; }
    @Override public double getSurface() { return surface; }
    @Override public int getEtage() { return etage; }
    @Override public boolean isMeuble() { return meuble; }

    @Override
    public boolean estAccessiblePMR() {
        // seul le bâtiment A a un ascenseur
        return etage == 0 || numero.startsWith("A");
    }

    @Override
    public int getNombrePlaces() {
        return places;
    }

    @Override
    public int nombreOccupants() {
        return occupants.size();
    }

    @Override
    public boolean estLibre() {
        return occupants.size() < places;
    }

    @Override
    public void accueillir(Etudiant e) {
        if (!estLibre()) {
            throw new IllegalStateException("Logement " + numero + " complet");
        }
        occupants.add(e);
    }

    @Override
    public void liberer(Etudiant e) {
        occupants.remove(e);
    }

    /** Par défaut, rien à faire au départ d'un occupant. */
    @Override
    public void apresDepart() {
    }

    /** Par défaut, pas de balcon : 0 m² est une vraie valeur, pas une exception. */
    @Override
    public double getSurfaceBalcon() {
        return 0;
    }
}
