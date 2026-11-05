package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class SmsService implements CanalNotification {

    private static final int LONGUEUR_MAX = 160;
    private static final int LONGUEUR_MIN_NUMERO = 10;
    private static final String SUITE = "...";

    public static int compteurSms = 0;
    public static List<String[]> journal = new ArrayList<String[]>();

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        envoyerSms(etudiant.getTelephone(), sujet + " - " + message);
    }

    @Override
    public void relancer(Etudiant etudiant, String idBail, double solde) {
        envoyerSms(etudiant.getTelephone(), "Campus Vert : " + Utils.euros(solde) + " impayés sur " + idBail);
    }

    private void envoyerSms(String numero, String message) {
        if (numero == null || numero.length() < LONGUEUR_MIN_NUMERO) {
            System.out.println("[SMS] numéro invalide : " + numero);
            return;
        }
        if (message.length() > LONGUEUR_MAX) {
            message = message.substring(0, LONGUEUR_MAX - SUITE.length()) + SUITE;
        }
        compteurSms++;
        journal.add(new String[]{numero, message});
        System.out.println("[SMS] -> " + numero + " : " + message);
    }
}
