# Diagnostic du code historique

Les emplacements renvoient aux fichiers de `residence-legacy/src/main/java/fr/efrei/residence`.

| Défaut | Emplacement | Principe SOLID violé |
|---|---|---|
| Une seule classe gère les inscriptions, l'attribution, le cycle de vie du bail, les relances, le rapport et les notifications | `GestionResidence.java:10-421` | **S** – Responsabilité unique |
| Un nouveau type de logement oblige à modifier la cascade de `if` du loyer de base | `CalculateurLoyer.java:12-23` | **O** – Ouvert/fermé |
| `Colocation` hérite de `loger()` et `liberer()` mais lève une exception à leur appel | `Colocation.java:17-24` | **L** – Substitution de Liskov |
| L'interface impose à tous les logements les méthodes de colocation, de balcon et d'entretien | `ILogement.java:31-43` | **I** – Ségrégation des interfaces |
| Le service récupère un singleton et crée lui-même son calculateur | `GestionResidence.java:14-15`, `Database.java:22-27` | **D** – Inversion des dépendances |
