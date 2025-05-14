/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.thiefin.projet_interface_graphique;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sinsm
 */
public class SuiviMaintenanceG_interface {

    public static void fenetreSuiviMaintenance() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Gestion du suivi de maintenance");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_LEFT);

        Label titre = new Label("Modifier le fichier suiviMaintenance.txt");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField tfDate = new TextField();
        tfDate.setPromptText("Date");

        TextField tfHeure = new TextField();
        tfHeure.setPromptText("Heure");

        TextField tfMachine = new TextField();
        tfMachine.setPromptText("Nom machine");

        ComboBox<String> cbType = new ComboBox<>();
        cbType.getItems().addAll("A", "D");
        cbType.setPromptText("Type (A/D)");

        TextField tfOperateur = new TextField();
        tfOperateur.setPromptText("ID Opérateur");

        TextField tfRaison = new TextField();
        tfRaison.setPromptText("Raison (panne, ok ou arret)");

        HBox boutons = new HBox(15);
        boutons.setAlignment(Pos.CENTER);
        Button ajouter = new Button("Ajouter");
        Button modifier = new Button("Modifier ligne");
        Button supprimer = new Button("Supprimer ligne");

        boutons.getChildren().addAll(ajouter, modifier, supprimer);

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
                        lignes.add(nouvelleLigne); // Insérer après l'entête
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
                feedback.setText("Ligne insérée.");
            } catch (IOException ex) {
                feedback.setText("Erreur écriture : " + ex.getMessage());
            }
        });

        modifier.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modifier");
            dialog.setHeaderText("Indiquer la date et l'heure exactes (date;heure) de la ligne à modifier :");
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
                    feedback.setText("Ligne modifiée.");
                } catch (IOException ex) {
                    feedback.setText("rreur : " + ex.getMessage());
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
                    feedback.setText("Ligne supprimée.");
                } catch (IOException ex) {
                    feedback.setText("Erreur : " + ex.getMessage());
                }
            });
        });

        root.getChildren().addAll(titre, tfDate, tfHeure, tfMachine, cbType, tfOperateur, tfRaison, boutons, feedback);

        Scene scene = new Scene(root, 500, 450);
        fenetre.setScene(scene);
        fenetre.show();
    }
}
  
