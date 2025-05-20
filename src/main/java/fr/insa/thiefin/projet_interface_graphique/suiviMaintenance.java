/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.thiefin.projet_interface_graphique;
import java.io.*;
import java.util.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author sinsm
 */


public class suiviMaintenance {

    public static class StatMachine {
        public float tempsArret = 0;
        public String dernierArret = null;
    }

    public static void fenetreSuiviMaintenance() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Gestion du suivi de maintenance");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_LEFT);

        Label titre = new Label("Modifier le fichier suiviMaintenance.txt");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField tfDate = new TextField();
        tfDate.setPromptText("Date (JJMMAAAA)");

        TextField tfHeure = new TextField();
        tfHeure.setPromptText("Heure (HH:mm)");

        TextField tfMachine = new TextField();
        tfMachine.setPromptText("Nom machine (ex: Mach_1)");

        ComboBox<String> cbType = new ComboBox<>();
        cbType.getItems().addAll("A", "D");
        cbType.setPromptText("Type (A/D)");

        TextField tfOperateur = new TextField();
        tfOperateur.setPromptText("ID Opérateur (ex: OP102)");

        TextField tfRaison = new TextField();
        tfRaison.setPromptText("Raison (ex: panne)");

        HBox boutons = new HBox(15);
        boutons.setAlignment(Pos.CENTER);
        Button ajouter = new Button("Ajouter");
        Button modifier = new Button("Modifier ligne");
        Button supprimer = new Button("Supprimer ligne");
        Button genererFiabilite = new Button("Générer Fiabilité");

        boutons.getChildren().addAll(ajouter, modifier, supprimer, genererFiabilite);

        Label feedback = new Label();

        ajouter.setOnAction(e -> {
            String nouvelleLigne = tfDate.getText() + ";" + tfHeure.getText() + ";" + tfMachine.getText() + ";" +
                    cbType.getValue() + ";" + tfOperateur.getText() + ";" + tfRaison.getText();
            File fichier = new File("suiviMaintenance.txt");
            List<String> lignes = new ArrayList<>();
            boolean insere = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                String ligne;
                boolean enteteTrouvee = false;
                while ((ligne = reader.readLine()) != null) {
                    if (!enteteTrouvee && ligne.toLowerCase().startsWith("date")) {
                        lignes.add(ligne);
                        enteteTrouvee = true;
                        continue;
                    }
                    if (ligne.startsWith("-") && enteteTrouvee) {
                        lignes.add(ligne);
                        lignes.add(nouvelleLigne); // Insère juste après l'entête
                        insere = true;
                        continue;
                    }
                    lignes.add(ligne);
                }
            } catch (IOException ex) {
                feedback.setText("Erreur lecture : " + ex.getMessage());
                return;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
                for (String l : lignes) {
                    writer.write(l);
                    writer.newLine();
                }
                if (!insere) {
                    writer.write(nouvelleLigne);
                    writer.newLine();
                }
                feedback.setText("Ligne insérée après l'en-tête.");
            } catch (IOException ex) {
                feedback.setText("Erreur écriture : " + ex.getMessage());
            }
        });

        modifier.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modifier");
            dialog.setHeaderText("Indiquer la date et l'heure exactes (JJMMAAAA;HH:mm) de la ligne à modifier :");
            dialog.setContentText("Clé :");
            dialog.showAndWait().ifPresent(cle -> {
                String ligneModifiee = tfDate.getText() + ";" + tfHeure.getText() + ";" + tfMachine.getText() + ";" +
                        cbType.getValue() + ";" + tfOperateur.getText() + ";" + tfRaison.getText();
                try {
                    File fichier = new File("suiviMaintenance.txt");
                    List<String> lignes = new ArrayList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                        String ligne;
                        while ((ligne = reader.readLine()) != null) {
                            if (ligne.startsWith(cle)) {
                                lignes.add(ligneModifiee);
                            } else {
                                lignes.add(ligne);
                            }
                        }
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
                        for (String l : lignes) {
                            writer.write(l);
                            writer.newLine();
                        }
                    }
                    feedback.setText("✏️ Ligne modifiée.");
                } catch (IOException ex) {
                    feedback.setText("❌ Erreur : " + ex.getMessage());
                }
            });
        });

        supprimer.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Suppression");
            dialog.setHeaderText("Indiquer la date et l'heure exactes (JJMMAAAA;HH:mm) de la ligne à supprimer :");
            dialog.setContentText("Clé :");
            dialog.showAndWait().ifPresent(cle -> {
                try {
                    File fichier = new File("suiviMaintenance.txt");
                    List<String> lignes = new ArrayList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                        String ligne;
                        while ((ligne = reader.readLine()) != null) {
                            if (!ligne.startsWith(cle)) {
                                lignes.add(ligne);
                            }
                        }
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
                        for (String l : lignes) {
                            writer.write(l);
                            writer.newLine();
                        }
                    }
                    feedback.setText("🗑️ Ligne supprimée.");
                } catch (IOException ex) {
                    feedback.setText("❌ Erreur : " + ex.getMessage());
                }
            });
        });

        genererFiabilite.setOnAction(e -> {
            suiviMaintenance("suiviMaintenance.txt");
            feedback.setText("📈 Fiabilité calculée et enregistrée dans fiabilite_machines.txt");
        });

        root.getChildren().addAll(titre, tfDate, tfHeure, tfMachine, cbType, tfOperateur, tfRaison, boutons, feedback);

        Scene scene = new Scene(root, 600, 500);
        fenetre.setScene(scene);
        fenetre.show();
    }

    public static void suiviMaintenance(String cheminFichier) {
        Map<String, StatMachine> stats = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.trim().isEmpty() || ligne.toLowerCase().startsWith("date") || ligne.startsWith("-")) continue;
                String[] elements = ligne.split(";");
                if (elements.length != 6) continue;

                String heure = elements[1].trim();
                String machine = elements[2].trim();
                String type = elements[3].trim();

                StatMachine stat = stats.getOrDefault(machine, new StatMachine());
                if (type.equalsIgnoreCase("A")) {
                    stat.dernierArret = heure;
                } else if (type.equalsIgnoreCase("D") && stat.dernierArret != null) {
                    float duree = calculerDuree(stat.dernierArret, heure);
                    stat.tempsArret += duree;
                    stat.dernierArret = null;
                }
                stats.put(machine, stat);
            }
        } catch (IOException e) {
            System.out.println("Erreur lecture : " + e.getMessage());
        }

        float dureeTotale = 14.0f;
        Map<String, Float> fiabilites = new HashMap<>();
        for (Map.Entry<String, StatMachine> entry : stats.entrySet()) {
            float fonctionnement = dureeTotale - entry.getValue().tempsArret;
            float fiabilite = fonctionnement / dureeTotale;
            fiabilites.put(entry.getKey(), fiabilite);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("fiabilite_machines.txt"))) {
            writer.write("Machine       | Fiabilité (%)");
            writer.newLine();
            writer.write("-----------------------------");
            writer.newLine();
            for (Map.Entry<String, Float> entry : fiabilites.entrySet()) {
                float pourcentage = Math.round(entry.getValue() * 10000) / 100.0f;
                writer.write(String.format("%-13s | %-13.2f", entry.getKey(), pourcentage));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur écriture fiabilité : " + e.getMessage());
        }
    }

    public static float calculerDuree(String debut, String fin) {
        try {
            int h1 = Integer.parseInt(debut.substring(0, 2));
            int m1 = Integer.parseInt(debut.substring(3, 5));
            int h2 = Integer.parseInt(fin.substring(0, 2));
            int m2 = Integer.parseInt(fin.substring(3, 5));
            return (float) (((h2 * 60 + m2) - (h1 * 60 + m1)) / 60.0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
