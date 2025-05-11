/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.thiefin.projet_interface_graphique;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Chef_Atelier_interface {

    public static void fenetrecreerChef() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Créer Chef d'Atelier");

        Label labelID = new Label("Identifiant du chef :");
        TextField champID = new TextField();
        champID.setPromptText("Ex: CA123");

        Label labelNom = new Label("Nom du chef :");
        TextField champNom = new TextField();
        champNom.setPromptText("Ex: Martin");

        Label labelPrenom = new Label("Prénom du chef :");
        TextField champPrenom = new TextField();
        champPrenom.setPromptText("Ex: Paul");

        Label labelSpec = new Label("Spécialité :");
        TextField champSpec = new TextField();
        champSpec.setPromptText("Ex: Mécanique");

        Button boutonValider = new Button("Créer");
        boutonValider.setOnAction(e -> {
            String id = champID.getText().trim();
            String nom = champNom.getText().trim();
            String prenom = champPrenom.getText().trim();
            String spec = champSpec.getText().trim();

            if (id.isEmpty() || nom.isEmpty() || prenom.isEmpty() || spec.isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Champs manquants");
                alerte.setHeaderText(null);
                alerte.setContentText("Veuillez remplir tous les champs.");
                alerte.show();
            } else {
                afficherChef(id, nom, prenom, spec);
                Alert succes = new Alert(Alert.AlertType.INFORMATION);
                succes.setTitle("Succès");
                succes.setHeaderText(null);
                succes.setContentText("Le chef d'atelier a été ajouté !");
                succes.showAndWait();
                fenetre.close();
            }
        });

        VBox vbox = new VBox(10, 
                labelID, champID, 
                labelNom, champNom, 
                labelPrenom, champPrenom, 
                labelSpec, champSpec, 
                boutonValider);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #f0f0f0;");

        fenetre.setScene(new Scene(vbox, 500, 500));
        fenetre.show();
    }

    public static void afficherChef(String id, String nom, String prenom, String spec) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chefs.txt", true))) {
            writer.write(String.format("%-10s | %-15s | %-15s | %-20s", id, nom, prenom, spec));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erreur d'enregistrement : " + e.getMessage());
        }
    }

    public static void fenetreModifierChef() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Modifier Chef d'Atelier");

        Label labelID = new Label("ID du chef à modifier :");
        TextField champID = new TextField();
        
        Label labelnewID = new Label("ID du chef à modifier :");
        TextField champnewID = new TextField();

        Label labelNom = new Label("Nouveau nom :");
        TextField champNom = new TextField();

        Label labelPrenom = new Label("Nouveau prénom :");
        TextField champPrenom = new TextField();

        Label labelSpec = new Label("Nouvelle spécialité :");
        TextField champSpec = new TextField();

        Button boutonValider = new Button("Modifier");
        boutonValider.setOnAction(e -> {
            String id = champID.getText().trim();
            String newid = champnewID.getText().trim();
            String nouveauNom = champNom.getText().trim();
            String nouveauPrenom = champPrenom.getText().trim();
            String nouvelleSpec = champSpec.getText().trim();

            if (id.isEmpty() ||newid.isEmpty() || nouveauNom.isEmpty() || nouveauPrenom.isEmpty() || nouvelleSpec.isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Champs manquants");
                alerte.setHeaderText(null);
                alerte.setContentText("Veuillez remplir tous les champs.");
                alerte.show();
            }
            else if (modifierChef(id, newid, nouveauNom, nouveauPrenom, nouvelleSpec)) {
                Alert succes = new Alert(Alert.AlertType.INFORMATION);
                succes.setTitle("Succès");
                succes.setHeaderText(null);
                succes.setContentText("Le chef d'atelier a été modifié !");
                succes.showAndWait();
                fenetre.close();
            } else {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle("Erreur");
                erreur.setHeaderText(null);
                erreur.setContentText("ID non trouvé.");
                erreur.show();
            }
        });
        
        Button boutonAfficherChef = new Button("Afficher les chefs existants");
        boutonAfficherChef.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des chefs de l'atelier");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("chefs.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier chefs.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 500, 200);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });

        VBox vbox = new VBox(10, 
                labelID, champID,
                labelnewID, champnewID,
                labelNom, champNom, 
                labelPrenom, champPrenom, 
                labelSpec, champSpec, 
                boutonValider,boutonAfficherChef);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        fenetre.setScene(new Scene(vbox, 400, 400));
        fenetre.show();
    }

    private static boolean modifierChef(String id,String newid, String nom, String prenom, String spec) {
        File inputFile = new File("chefs.txt");
        File tempFile = new File("chefs_temp.txt");

        boolean modifie = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith(id)) {
                    writer.write(String.format("%-10s | %-15s | %-15s | %-20s", newid, nom, prenom, spec));
                    writer.newLine();
                    modifie = true;
                } else {
                    writer.write(ligne);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }

        return modifie;
    }

    public static void fenetreSupprimerChef() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Supprimer Chef d'Atelier");

        Label labelID = new Label("ID du chef à supprimer :");
        TextField champID = new TextField();

        Button boutonValider = new Button("Supprimer");
        boutonValider.setOnAction(e -> {
            String id = champID.getText().trim();
            boolean supprimee = supprimerChef(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
        if (supprimee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le chef \"" + id + "\" a été supprimé avec succès.");
            alert.showAndWait();
            fenetre.close(); // ferme la fenêtre seulement si succès
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le chef \"" + id + "\" n'a pas été trouvé.");
            alert.show(); // on ne ferme pas la fenêtre
        }
        });

        Button boutonAfficherChef = new Button("Afficher les chefs existants");
        boutonAfficherChef.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des chefs de l'atelier");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("chefs.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier chefs.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 500, 200);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });
        
        
        VBox vbox = new VBox(10, labelID, champID, boutonValider, boutonAfficherChef);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        fenetre.setScene(new Scene(vbox, 300, 200));
        fenetre.show();
    }

   public static boolean supprimerChef(String codeChef) {
    File inputFile = new File("chefs.txt");
    List<String> lignes = new ArrayList<>();
    boolean Supprimer = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        String ligne;
        boolean premiereLigne = true;

        while ((ligne = reader.readLine()) != null) {
            if (premiereLigne || ligne.startsWith("-") || ligne.trim().isEmpty()) {
                lignes.add(ligne); // Garder l'en-tête et séparateurs
                premiereLigne = false;
                continue;
            }

            String[] morceaux = ligne.split("\\|");
            if (morceaux.length > 0) {
                String idChef = morceaux[0].trim();
                if (idChef.equalsIgnoreCase(codeChef)) {
                    Supprimer = true;
                    continue; // Ne pas ajouter cette ligne → suppression
                }
            }

            lignes.add(ligne); // Garder la ligne sinon
        }

    } catch (IOException e) {
        System.out.println("Erreur de lecture : " + e.getMessage());
        return false;
    }

    if (!Supprimer) {
        return false;
    }

    // Réécriture complète du fichier après suppression
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
        for (String l : lignes) {
            writer.write(l);
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Erreur d'écriture : " + e.getMessage());
        return false;
    }

    return true;
}    
}