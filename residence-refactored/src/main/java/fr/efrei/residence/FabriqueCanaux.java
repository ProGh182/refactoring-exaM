package fr.efrei.residence;

/**
 * Seul endroit qui crée les services de notification.
 */
public class FabriqueCanaux {

    private final CanalNotification email;
    private final CanalNotification sms;
    private final CanalNotification courrier;
    private final CanalNotification push;

    public FabriqueCanaux(JournalNotifications journal) {
        this.email = new EmailService(journal);
        this.sms = new SmsService(journal);
        this.courrier = new CourrierService(journal);
        this.push = new PushService(journal);
    }

    public CanalNotification pour(Canal canal) {
        return switch (canal) {
            case EMAIL -> email;
            case SMS -> sms;
            case COURRIER -> courrier;
            case PUSH -> push;
        };
    }
}
