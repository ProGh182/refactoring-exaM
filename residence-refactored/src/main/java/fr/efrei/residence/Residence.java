package fr.efrei.residence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Le parc de logements de la résidence.
 */
public class Residence {

    public static final String NOM = "Résidence Campus Vert";

    // TYPE;NUMERO;SURFACE;ETAGE;MEUBLE;PARAM (PARAM = nb de places en coloc, surface du balcon en T2)
    public static final String[] CATALOGUE = {
            "STUDIO;A001;26;0;true;0",
            "STUDIO;A102;22;1;true;0",
            "STUDIO;B103;20;1;false;0",
            "T1;A104;28;1;true;0",
            "T1;B105;30;1;false;0",
            "T2;A201;45;2;false;6",
            "T2;B202;48;2;true;0",
            "COLOCATION;B301;80;3;true;4",
            "COLOCATION;C001;63;0;true;3",
    };

    private final List<Logement> logements = new ArrayList<>();

    /** Crée le parc initial à partir du catalogue. */
    public Residence() {
        for (String ligne : CATALOGUE) {
            String[] t = ligne.split(";");
            String numero = t[1];
            double surface = Double.parseDouble(t[2]);
            int etage = Integer.parseInt(t[3]);
            boolean meuble = Boolean.parseBoolean(t[4]);
            int param = Integer.parseInt(t[5]);
            Optional<TypeLogement> type = TypeLogement.depuis(t[0]);
            if (type.isEmpty()) {
                System.out.println("Type inconnu ignoré : " + t[0]);
                continue;
            }
            // la colonne PARAM sert de nombre de places ou de surface de balcon : la fabrique prend celle qui la concerne
            logements.add(FabriqueLogements.creer(type.get(), numero, surface, etage, meuble, param, param));
        }
    }

    public void ajouter(Logement l) {
        logements.add(l);
    }

    public Logement trouver(String numero) {
        for (Logement l : logements) {
            if (l.getNumero().equals(numero)) return l;
        }
        return null;
    }

    public List<Logement> logements() {
        return Collections.unmodifiableList(logements);
    }
}
