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
        tfDate.setPromptText("Date (JJMMAAAA)");

        TextField tfHeure = new TextField();
        tfHeure.setPromptText("Heure (HH:mm)");

        TextField tfMachine = new TextField();
        tfMachine.setPromptText("Nom machine");

        ComboBox<String> cbType = new ComboBox<>();
        cbType.getItems().addAll("A", "D");
        cbType.setPromptText("Type (A/D)");

        TextField tfOperateur = new TextField();
        tfOperateur.setPromptText("ID Opérateur");

        TextField tfRaison = new TextField();
        tfRaison.setPromptText("Raison");

        HBox boutons = new HBox(15);
        boutons.setAlignment(Pos.CENTER);
        Button ajouter = new Button("Ajouter");
        Button modifier = new Button("Modifier ligne");
        Button supprimer = new Button("Supprimer ligne");

        boutons.getChildren().addAll(ajouter, modifier, supprimer);

        Label feedback = new Label();

        ajouter.setOnAction(e -> {
            String ligne = tfDate.getText() + ";" + tfHeure.getText() + ";" + tfMachine.getText() + ";" +
                    cbType.getValue() + ";" + tfOperateur.getText() + ";" + tfRaison.getText();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("suiviMaintenance.txt", true))) {
                writer.write(ligne);
                writer.newLine();
                feedback.setText("Ligne ajoutée.");
            } catch (IOException ex) {
                feedback.setText("Erreur : " + ex.getMessage());
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
                    feedback.setText("Ligne modifiée.");
                } catch (IOException ex) {
                    feedback.setText("Erreur : " + ex.getMessage());
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
  
