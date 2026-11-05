package fr.efrei.residence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Garde la trace des notifications envoyées (demandé par l'administration).
 */
public class JournalNotifications {

    private final List<Envoi> envois = new ArrayList<>();

    public void enregistrer(Envoi envoi) {
        envois.add(envoi);
    }

    /** @return les envois regroupés par canal, dans l'ordre d'envoi au sein de chaque canal */
    public List<Envoi> parCanal() {
        List<Envoi> tries = new ArrayList<>(envois);
        tries.sort(Comparator.comparing(Envoi::canal)); // tri stable : l'ordre d'envoi est conservé
        return tries;
    }

    public long nombre(Canal canal) {
        return envois.stream().filter(e -> e.canal() == canal).count();
    }

    public double coutTotal() {
        double total = 0;
        for (Envoi envoi : envois) {
            total += envoi.cout();
        }
        return total;
    }
}
