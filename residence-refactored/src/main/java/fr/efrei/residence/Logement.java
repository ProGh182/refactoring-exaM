package fr.efrei.residence;

public abstract class Logement implements ILogement {

    protected String numero;
    protected TypeLogement type;
    protected double surface;
    protected int etage;
    protected boolean meuble;
    protected Etudiant occupant;
    private final TarifLogement tarif;

    public Logement(String numero, TypeLogement type, TarifLogement tarif, double surface, int etage, boolean meuble) {
        this.numero = numero;
        this.type = type;
        this.tarif = tarif;
        this.surface = surface;
        this.etage = etage;
        this.meuble = meuble;
    }

    public String getNumero() { return numero; }
    public TypeLogement getType() { return type; }
    public TarifLogement getTarif() { return tarif; }
    public double getSurface() { return surface; }
    public int getEtage() { return etage; }
    public boolean isMeuble() { return meuble; }
    public Etudiant getOccupant() { return occupant; }

    public boolean estLibre() {
        return occupant == null;
    }

    public int nombreOccupants() {
        return occupant == null ? 0 : 1;
    }

    public boolean estAccessiblePMR() {
        // seul le bâtiment A a un ascenseur
        return etage == 0 || numero.startsWith("A");
    }

    public void loger(Etudiant e) {
        if (occupant != null) {
            throw new IllegalStateException("Logement " + numero + " déjà occupé");
        }
        occupant = e;
    }

    public void liberer(Etudiant e) {
        occupant = null;
    }
}
