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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Machine_interface {

    public static void fenetrecreerMachine() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Créer Machine");

        Label labelID = new Label("Identifiant de la machine :");
        TextField champID = new TextField();
        champID.setPromptText("Ex : SB123");

        Label labeldesi = new Label("Désignation :");
        TextField champDesi = new TextField();
        champDesi.setPromptText("Ex : USINAGE");
        
        Label labelType = new Label("Type de machine :");
        TextField champType = new TextField();
        champType.setPromptText("Ex : Découpage,...");
        
        Label labelcoordX = new Label("Position en X <120 :");
        TextField champX = new TextField();
        champX.setPromptText("Ex : 10");
        
        Label labelcoordY = new Label("Position en Y <100 :");
        TextField champY = new TextField();
        champY.setPromptText("Ex : 15");
        
        Label labelCout = new Label("Coût (en €) :");
        TextField champCout = new TextField();
        champCout.setPromptText("Ex : 300");
        
        Label labelDuree = new Label("Quantité :");
        TextField champDuree = new TextField();
        champDuree.setPromptText("Ex : 2,00");

        Button boutonValider = new Button("Créer");
        boutonValider.setOnAction(e -> {
            String id = champID.getText().trim();
            String Desi = champDesi.getText().trim();
            String Type = champType.getText().trim();
            String X = champX.getText().trim();
            String Y = champY.getText().trim();
            String Cout = champCout.getText().trim();
            String Duree = champDuree.getText().trim();

            if (id.isEmpty() || Desi.isEmpty()|| Type.isEmpty()|| X.isEmpty()
                    || Y.isEmpty()|| Cout.isEmpty()|| Duree.isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Champs manquants");
                alerte.setHeaderText(null);
                alerte.setContentText("Veuillez remplir tous les champs.");
                alerte.show();
            } else {
                afficherMachine(id,Desi,Type,X, Y, Cout, Duree);
                Alert succes = new Alert(Alert.AlertType.INFORMATION);
                succes.setTitle("Succès");
                succes.setHeaderText(null);
                succes.setContentText("La machine a été ajoutée avec succès !");
                succes.showAndWait();
                fenetre.close();
            }
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #f0f0f0;");
        vbox.getChildren().addAll(labelID, champID, 
                labeldesi, champDesi, 
                labelType, champType,
                labelcoordX, champX,
                labelcoordY, champY,
                labelCout, champCout,
                labelDuree, champDuree,
                boutonValider);

        Scene scene = new Scene(vbox, 500, 600);
        fenetre.setScene(scene);
        fenetre.show();
    }
//-----------------------------------------------------------------------------------------------------------------
    public static void afficherMachine(String id, String desi, String type, 
            String X, String Y, String cout, String duree) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("machine.txt", true))) {
            writer.write(String.format("%-15s | %-20s| %-20s| %-5s| %-5s| %-7s| %-7s", id, desi,type,X,Y,cout,duree));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erreur d'enregistrement : " + e.getMessage());
        }
    }
//-----------------------------------------------------------------------------------------------------------------
    public static void fenetremodifierMachine() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Modifier Machine");

        Label labelancienID = new Label("Ancien identifiant de la machine :");
        TextField champancienID = new TextField();
        champancienID.setPromptText("Ex : SB123");
        
        Label labelID = new Label("Identifiant de la machine :");
        TextField champID = new TextField();
        champID.setPromptText("Ex : SB124");

        Label labeldesi = new Label("Désignation :");
        TextField champDesi = new TextField();
        champDesi.setPromptText("Ex : USINAGE");
        
        Label labelType = new Label("Type de machine :");
        TextField champType = new TextField();
        champType.setPromptText("Ex : Découpage,...");
        
        Label labelcoordX = new Label("Position en X <120:");
        TextField champX = new TextField();
        champX.setPromptText("Ex : 10");
        
        Label labelcoordY = new Label("Position en Y <100 :");
        TextField champY = new TextField();
        champY.setPromptText("Ex : 15");
        
        Label labelCout = new Label("Coût (en €) :");
        TextField champCout = new TextField();
        champCout.setPromptText("Ex : 300");
        
        Label labelDuree = new Label("Durée :");
        TextField champDuree = new TextField();
        champDuree.setPromptText("Ex : 2,00");

        Button boutonValider = new Button("Modifier");
        boutonValider.setOnAction(e -> {
            String ancienid = champancienID.getText().trim();
            String id = champID.getText().trim();
            String Desi = champDesi.getText().trim();
            String Type = champType.getText().trim();
            String X = champX.getText().trim();
            String Y = champY.getText().trim();
            String Cout = champCout.getText().trim();
            String Duree = champDuree.getText().trim();

            if (ancienid.isEmpty() || id.isEmpty() || Desi.isEmpty()|| Type.isEmpty()|| X.isEmpty()
                    || Y.isEmpty()|| Cout.isEmpty()|| Duree.isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Champs manquants");
                alerte.setHeaderText(null);
                alerte.setContentText("Veuillez remplir tous les champs.");
                alerte.show();
            } else {
                modifierMachine(ancienid, id,Desi,Type,X, Y, Cout, Duree);
                Alert succes = new Alert(Alert.AlertType.INFORMATION);
                succes.setTitle("Succès");
                succes.setHeaderText(null);
                succes.setContentText("La machine a été ajoutée avec succès !");
                succes.showAndWait();
                fenetre.close();
            }
        });
        
        Button boutonAfficher = new Button("Afficher les machines");
        boutonAfficher.setOnAction(e -> {
            Stage affichage = new Stage();
            affichage.setTitle("Machine");

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setStyle("-fx-font-family: 'monospace';");

            try (BufferedReader reader = new BufferedReader(new FileReader("machine.txt"))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    textArea.appendText(ligne + "\n");
                }
            } catch (IOException ex) {
                textArea.setText("Erreur de lecture : " + ex.getMessage());
            }

            VBox box = new VBox(textArea);
            box.setPadding(new Insets(10));
            Scene scene = new Scene(box, 800, 200);
            affichage.setScene(scene);
            affichage.show();
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #f0f0f0;");
        vbox.getChildren().addAll(labelancienID, champancienID,
                labelID, champID, 
                labeldesi, champDesi, 
                labelType, champType,
                labelcoordX, champX,
                labelcoordY, champY,
                labelCout, champCout,
                labelDuree, champDuree,
                boutonValider,boutonAfficher);

        Scene scene = new Scene(vbox, 500, 600);
        fenetre.setScene(scene);
        fenetre.show();
    
    }
//-----------------------------------------------------------------------------------------------------------------
    public static boolean modifierMachine(String ancienid, String id, String desi, String type, 
            String X, String Y, String cout, String duree) {
        File file = new File("machine.txt");
        List<String> lignes = new ArrayList<>();
        boolean modifie = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith(ancienid)) {
                    lignes.add(String.format("%-15s | %-20s| %-20s| %-5s| %-5s| %-7s| %-7s", id, desi,type,X,Y,cout,duree));
                    modifie = true;
                } else {
                    lignes.add(ligne);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lecture : " + e.getMessage());
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : lignes) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur écriture : " + e.getMessage());
            return false;
        }

        return modifie;
    }
//-----------------------------------------------------------------------------------------------------------------
    public static void fenetreSupprimerMachine() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Supprimer Machine");

        Label labelID = new Label("Identifiant de la machine à supprimer :");
        TextField champID = new TextField();

        Button boutonValider = new Button("Supprimer");
        boutonValider.setOnAction(e -> {
            String id = champID.getText().trim();
            boolean supprimee = supprimerMachine(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
        if (supprimee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La machine  \"" + id + "\" a été supprimée avec succès.");
            alert.showAndWait();
            fenetre.close(); // ferme la fenêtre seulement si succès
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La machine \"" + id + "\" n'a pas été trouvée.");
            alert.show(); // on ne ferme pas la fenêtre
        }
        });

        Button boutonAfficher = new Button("Afficher les machines");
        boutonAfficher.setOnAction(e -> {
            Stage affichage = new Stage();
            affichage.setTitle("Machine");

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setStyle("-fx-font-family: 'monospace';");

            try (BufferedReader reader = new BufferedReader(new FileReader("machine.txt"))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    textArea.appendText(ligne + "\n");
                }
            } catch (IOException ex) {
                textArea.setText("Erreur de lecture : " + ex.getMessage());
            }

            VBox box = new VBox(textArea);
            box.setPadding(new Insets(10));
            Scene scene = new Scene(box, 800, 200);
            affichage.setScene(scene);
            affichage.show();
        });
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #f0f0f0;");
        vbox.getChildren().addAll(labelID, champID, boutonValider, boutonAfficher);

        Scene scene = new Scene(vbox, 400, 300);
        fenetre.setScene(scene);
        fenetre.show();
    }
//-----------------------------------------------------------------------------------------------------------------
    public static boolean supprimerMachine(String id) {
    File inputFile = new File("machine.txt");
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
                String ID = morceaux[0].trim();
                if (ID.equalsIgnoreCase(id)) {
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
    
//-----------------------------------------------------------------------------------------------------------------
    
//Ouvre une fenêtre “plan” avec un rectangle par machine positionné selon les coordonnées lues dans machine.txt
public static void afficherPlanMachine() {
    Stage fenetre = new Stage();
    fenetre.setTitle("Plan des machines");

    Pane pane = new Pane();
    double scale = 4; // Réduction d’échelle pour affichage correct

    try (BufferedReader reader = new BufferedReader(new FileReader("machine.txt"))) {
        String ligne;

        while ((ligne = reader.readLine()) != null) {
            // Ignorer les lignes de séparation ou les lignes vides
            if (ligne.trim().isEmpty() || ligne.contains("----") || ligne.toLowerCase().contains("designation")) {
                continue;
            }

            // Découper par le séparateur "|"
            String[] parts = ligne.split("\\|");

            if (parts.length >= 6) {
                String id = parts[0].trim();
                double x = Double.parseDouble(parts[3].trim()) * scale;
                double y = Double.parseDouble(parts[4].trim()) * scale;

                Rectangle r = new Rectangle(x, y, 40, 30);
                r.setFill(Color.LIGHTGRAY);
                r.setStroke(Color.BLACK);

                Text label = new Text(x + 5, y + 15, id);

                pane.getChildren().addAll(r, label);
            }
        }
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }

    Scene scene = new Scene(pane, 800, 600);
    fenetre.setScene(scene);
    fenetre.show();
}
}
