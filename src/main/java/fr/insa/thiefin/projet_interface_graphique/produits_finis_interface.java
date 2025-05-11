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

public class produits_finis_interface {

    public static void fenetrecreerProduitsFinis() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Créer Produits finis");

        Label labelID = new Label("Identifiant du Produit fini :");
        TextField champID = new TextField();
        champID.setPromptText("Ex : SB123");

        Label labelQuantite = new Label("Quantité :");
        TextField champQuantite = new TextField();
        champQuantite.setPromptText("Ex : 500");

        Button boutonValider = new Button("Créer");
        boutonValider.setOnAction(e -> {
            String id = champID.getText().trim();
            String quantite = champQuantite.getText().trim();

            if (id.isEmpty() || quantite.isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Champs manquants");
                alerte.setHeaderText(null);
                alerte.setContentText("Veuillez remplir tous les champs.");
                alerte.show();
            } else {
                afficherProduitFini(id, quantite);
                Alert succes = new Alert(Alert.AlertType.INFORMATION);
                succes.setTitle("Succès");
                succes.setHeaderText(null);
                succes.setContentText("Le produit fini a été ajouté avec succès !");
                succes.showAndWait();
                fenetre.close();
            }
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #f0f0f0;");
        vbox.getChildren().addAll(labelID, champID, labelQuantite, champQuantite, boutonValider);

        Scene scene = new Scene(vbox, 400, 300);
        fenetre.setScene(scene);
        fenetre.show();
    }
//-----------------------------------------------------------------------------------------------------------------
    public static void afficherProduitFini(String id, String quantite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("produitsfinis.txt", true))) {
            writer.write(String.format("%-15s | %-10s", id, quantite));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erreur d'enregistrement : " + e.getMessage());
        }
    }
//-----------------------------------------------------------------------------------------------------------------
    public static void fenetremodifierProduitFini() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Modifier Produits Finis");

        Label labelAncienID = new Label("Ancien identifiant :");
        TextField champAncienID = new TextField();
        champAncienID.setPromptText("Ex : SB123");

        Label labelNouvID = new Label("Nouvel identifiant :");
        TextField champNouvID = new TextField();
        champNouvID.setPromptText("Ex : SB124");

        Label labelQuantite = new Label("Nouvelle quantité :");
        TextField champQuantite = new TextField();
        champQuantite.setPromptText("Ex : 600");

        Button boutonValider = new Button("Modifier");
        boutonValider.setOnAction(e -> {
            String ancienID = champAncienID.getText().trim();
            String nouvID = champNouvID.getText().trim();
            String quantite = champQuantite.getText().trim();

            if (ancienID.isEmpty() || nouvID.isEmpty() || quantite.isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Champs manquants");
                alerte.setHeaderText(null);
                alerte.setContentText("Veuillez remplir tous les champs.");
                alerte.show();
            } else {
                modifierProduitFini(ancienID, nouvID, quantite);
                Alert succes = new Alert(Alert.AlertType.INFORMATION);
                succes.setTitle("Succès");
                succes.setHeaderText(null);
                succes.setContentText("Le produit fini a été modifié avec succès !");
                succes.showAndWait();
                fenetre.close();
            }
        });

        Button boutonAfficher = new Button("Afficher les produits finis");
        boutonAfficher.setOnAction(e -> {
            Stage affichage = new Stage();
            affichage.setTitle("Produits finis");

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setStyle("-fx-font-family: 'monospace';");

            try (BufferedReader reader = new BufferedReader(new FileReader("produitsfinis.txt"))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    textArea.appendText(ligne + "\n");
                }
            } catch (IOException ex) {
                textArea.setText("Erreur de lecture : " + ex.getMessage());
            }

            VBox box = new VBox(textArea);
            box.setPadding(new Insets(10));
            Scene scene = new Scene(box, 300, 200);
            affichage.setScene(scene);
            affichage.show();
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #f0f0f0;");
        vbox.getChildren().addAll(labelAncienID, champAncienID, labelNouvID, champNouvID,
                                  labelQuantite, champQuantite, boutonValider, boutonAfficher);

        Scene scene = new Scene(vbox, 450, 400);
        fenetre.setScene(scene);
        fenetre.show();
    }
//-----------------------------------------------------------------------------------------------------------------
    public static boolean modifierProduitFini(String ancienID, String nouvID, String nouvQuantite) {
        File file = new File("produitsfinis.txt");
        List<String> lignes = new ArrayList<>();
        boolean modifie = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith(ancienID)) {
                    lignes.add(String.format("%-15s | %-10s", nouvID, nouvQuantite));
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
    public static void fenetreSupprimerProduitFini() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Supprimer produit finis");

        Label labelID = new Label("Identifiant du produit fini à supprimer :");
        TextField champID = new TextField();

        Button boutonValider = new Button("Supprimer");
        boutonValider.setOnAction(e -> {
            String id = champID.getText().trim();
            boolean supprimee = supprimerProduitFini(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
        if (supprimee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le produit fini \"" + id + "\" a été supprimé avec succès.");
            alert.showAndWait();
            fenetre.close(); // ferme la fenêtre seulement si succès
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le produit fini \"" + id + "\" n'a pas été trouvé.");
            alert.show(); // on ne ferme pas la fenêtre
        }
        });

        Button boutonAfficher = new Button("Afficher les produits finis");
        boutonAfficher.setOnAction(e -> {
            Stage affichage = new Stage();
            affichage.setTitle("Produits finis");

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setStyle("-fx-font-family: 'monospace';");

            try (BufferedReader reader = new BufferedReader(new FileReader("produitsfinis.txt"))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    textArea.appendText(ligne + "\n");
                }
            } catch (IOException ex) {
                textArea.setText("Erreur de lecture : " + ex.getMessage());
            }

            VBox box = new VBox(textArea);
            box.setPadding(new Insets(10));
            Scene scene = new Scene(box, 300, 200);
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
    public static boolean supprimerProduitFini(String id) {
    File inputFile = new File("produitsfinis.txt");
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
                String idStock = morceaux[0].trim();
                if (idStock.equalsIgnoreCase(id)) {
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
