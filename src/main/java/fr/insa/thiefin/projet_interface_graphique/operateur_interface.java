/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.thiefin.projet_interface_graphique;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Sacha
 */
public class operateur_interface {
    
    public static void fenetrecreerOperateur() {
    Stage fenetrecreeroperateur = new Stage();
    fenetrecreeroperateur.setTitle("Créer Opérateur");

    Label labelCode = new Label("Code de l'opérateur:");
    TextField champCode = new TextField();
    champCode.setPromptText("63DH");
    
    Label labelNom = new Label("Nom de l'opérateur:");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: Dupont");

    Label labelPrenom = new Label("Prénom de l'opérateur:");
    TextField champPrenom = new TextField();
    champPrenom.setPromptText("Ex: Jean");
    
    Label labelComp = new Label("Compétences (séparées par des virgules) :");
    TextField champComp = new TextField();
    champComp.setPromptText("Ex: Soudeur,...");
    
    Label labelDispo = new Label("Disponibilité :");
    TextField champDispo = new TextField();
    champDispo.setPromptText("Ex: Matin/ Après-midi");
    

    Button boutonValider = new Button("Créer");
    boutonValider.setOnAction(e -> {
        String code = champCode.getText().trim();
        String nom = champNom.getText().trim();
        String prenom = champPrenom.getText().trim();
        String competences = champComp.getText().trim();
        String dispo = champDispo.getText().trim();

        if (code.isEmpty() || nom.isEmpty() || prenom.isEmpty()|| competences.isEmpty()|| dispo.isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Champs manquants");
            alerte.setHeaderText(null);
            alerte.setContentText("Veuillez remplir tous les champs avant de valider.");
            alerte.show();
        }
        
        else{ 
        afficherOperateur(code,nom,prenom,competences,dispo);
        
        Alert succes = new Alert(Alert.AlertType.INFORMATION);
        succes.setTitle("Succès");
        succes.setHeaderText(null);
        succes.setContentText("L'opérateur a été ajouté avec succès !");
        succes.showAndWait();

        fenetrecreeroperateur.close();
        }
        
        });

    VBox vboxoperateur = new VBox(10);
    vboxoperateur.setPadding(new Insets(20));
    vboxoperateur.setAlignment(Pos.CENTER);
    vboxoperateur.setStyle("-fx-background-color: #f0f0f0;");
    vboxoperateur.getChildren().addAll(
        labelCode,champCode,
        labelNom, champNom,
        labelPrenom, champPrenom,
        labelComp, champComp,
        labelDispo,champDispo,
        boutonValider
    );

    Scene scene = new Scene(vboxoperateur, 500, 500);
    fenetrecreeroperateur.setScene(scene);
    fenetrecreeroperateur.show();
}
    
//-------------------------------------------------------------------------------------------------------------------- 
    
    public static void afficherOperateur(String Code,String Nom,String Prenom,String competences,String dispo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("operateurs.txt", true))) {
            writer.write(String.format("%-10s | %-15s | %-15s | %-20s | %-30s",
                     Code, Nom, Prenom, competences, dispo));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }
    
//---------------------------------------------------------------------------------------------------------------------
public static void fenetremodifierOperateur() {
    Stage fenetremodifieroperateur = new Stage();
    fenetremodifieroperateur.setTitle("Modifier Opérateur");

    Label labelancienCode = new Label("Ancien code de l'opérateur:");
    TextField champancienCode = new TextField();
    champancienCode.setPromptText("H6D8");
    
    Label labelCode = new Label("Nouveau code de l'opérateur:");
    TextField champCode = new TextField();
    champCode.setPromptText("63DH");
    
    Label labelNom = new Label("Nouveau nom de l'opérateur:");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: Dupont");

    Label labelPrenom = new Label("Nouveau prénom de l'opérateur:");
    TextField champPrenom = new TextField();
    champPrenom.setPromptText("Ex: Jean");
    
    Label labelComp = new Label("Nouvelle(s) compétences (séparées par des virgules) :");
    TextField champComp = new TextField();
    champComp.setPromptText("Ex: Soudeur,...");
    
    Label labelDispo = new Label("Nouvelle disponibilité :");
    TextField champDispo = new TextField();
    champDispo.setPromptText("Ex: Matin/ Après-midi");
    

    Button boutonValider = new Button("Modifier");
    boutonValider.setOnAction(e -> {
        String ancienCode = champancienCode.getText().trim();
        String code = champCode.getText().trim();
        String nom = champNom.getText().trim();
        String prenom = champPrenom.getText().trim();
        String competences = champComp.getText().trim();
        String dispo = champDispo.getText().trim();

        if (code.isEmpty() || nom.isEmpty() || prenom.isEmpty()|| competences.isEmpty()|| dispo.isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Champs manquants");
            alerte.setHeaderText(null);
            alerte.setContentText("Veuillez remplir tous les champs avant de valider.");
            alerte.show();
        }
        else{
        modifieroperateur(ancienCode, code,nom,prenom,competences,dispo);
        
        Alert succes = new Alert(Alert.AlertType.INFORMATION);
        succes.setTitle("Succès");
        succes.setHeaderText(null);
        succes.setContentText("L'opérateur a été modifier avec succès !");
        succes.showAndWait();

        fenetremodifieroperateur.close();
        }
    });
    
    Button boutonAfficherOpe = new Button("Afficher les opérateurs existants");
    boutonAfficherOpe.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des opérateurs");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("operateurs.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier operateurs.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 700, 200);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });
    
    VBox vboxoperateur = new VBox(10);
    vboxoperateur.setPadding(new Insets(20));
    vboxoperateur.setAlignment(Pos.CENTER);
    vboxoperateur.setStyle("-fx-background-color: #f0f0f0;");
    vboxoperateur.getChildren().addAll(
        labelancienCode,champancienCode,
        labelCode,champCode,
        labelNom, champNom,
        labelPrenom, champPrenom,
        labelComp, champComp,
        labelDispo,champDispo,
        boutonValider,boutonAfficherOpe
    );

    Scene scene = new Scene(vboxoperateur, 500, 500);
    fenetremodifieroperateur.setScene(scene);
    fenetremodifieroperateur.show();
}

//---------------------------------------------------------------------------------------------------------------------
public static boolean modifieroperateur(String AncienCode, String nouvCode, String nouvNom, String nouvPrenom, 
                                        String nouvCompetence, String nouvDispo) {
    File inputFile = new File("operateurs.txt");
    List<String> lignes = new ArrayList<>();
    boolean modifiee = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            if (ligne.startsWith(AncienCode.trim())) {
                // Remplacer la ligne par la nouvelle définition
                String nouvelleLigne = String.format("%-10s | %-15s | %-15s | %-20s | %-30s",
                     nouvCode, nouvNom, nouvPrenom, nouvCompetence, nouvDispo);
                lignes.add(nouvelleLigne);
                modifiee = true;
            } else {
                lignes.add(ligne);
            }
        }
    } catch (IOException e) {
        System.out.println("Erreur de lecture : " + e.getMessage());
        return false;
    }

    if (!modifiee) {
        return false;
    }

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
//------------------------------------------------------------------------------------------------------------------
public static void fenetreSupprimerOperateur() {
    Stage fenetresupope = new Stage();
    fenetresupope.setTitle("Supprimer opérateur");

    Label labelCode = new Label("Code de l'opérateur à supprimer:");
    TextField champCode = new TextField();
    champCode.setPromptText("Ex: DH56");

    Button boutonValider = new Button("Supprimer");
    boutonValider.setOnAction(e -> {
        String CodeOpe = champCode.getText().trim();

        boolean supprimee = supprimerOperateur(CodeOpe);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (supprimee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'opérateur \"" + CodeOpe + "\" a été supprimé avec succès.");
            alert.showAndWait();
            fenetresupope.close(); // ferme la fenêtre seulement si succès
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'opérateur \"" + CodeOpe + "\" n'a pas été trouvé.");
            alert.show(); // on ne ferme pas la fenêtre
        }
    });

    Button boutonAfficherOperateurs = new Button("Afficher les opérateurs existants");
    boutonAfficherOperateurs.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des opérateurs");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("operateurs.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier operateurs.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 700, 200);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });

    VBox vboxOpe = new VBox(10);
    vboxOpe.setPadding(new Insets(20));
    vboxOpe.setAlignment(Pos.CENTER);
    vboxOpe.setStyle("-fx-background-color: #f0f0f0;");
    vboxOpe.getChildren().addAll(
        labelCode, champCode,
        boutonValider,
        boutonAfficherOperateurs
    );

    Scene scene = new Scene(vboxOpe, 500, 500);
    fenetresupope.setScene(scene);
    fenetresupope.show();
}

//-------------------------------------------------------------------------------------------------------------------

public static boolean supprimerOperateur(String codeOpe) {
    File inputFile = new File("operateurs.txt");
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
                String idOP = morceaux[0].trim();
                if (idOP.equalsIgnoreCase(codeOpe)) {
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
