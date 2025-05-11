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
public class Produit_interface {
    
    // action du boutonProduit --> ouvre une fenetre "Produit" ainsi que ses composants
    public static void fenetreProduit() {
        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Produit");

        // creation de boutons dans cette nouvelle fenetre ainsi que les actions qu'ils effectuent
        Button retour = new Button("Fermer");
        retour.setOnAction(e -> nouvelleFenetre.close());
        
        Button CreeProduit = new Button("Creer Produit");
        CreeProduit.setOnAction(e -> fenetreCreerProduit());
        
        Button ModifierProduit = new Button("Modifier Produit");
        ModifierProduit.setOnAction(e -> fenetreModifierProduit());
        
        Button SupprimerProduit = new Button("Supprimer Produit");
        SupprimerProduit.setOnAction(e -> fenetreSupprimerProduit());
        

        //creation de la vbox pour alligner les bouton
        VBox vboxProduit = new VBox(10);
        vboxProduit.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #f0f0f0;");
        vboxProduit.getChildren().addAll(CreeProduit, ModifierProduit, SupprimerProduit, retour);

        Scene scene = new Scene(vboxProduit, 500, 500);
        nouvelleFenetre.setScene(scene);
        nouvelleFenetre.show();
    }
    
    //----------------------------------------------------------------------------------------------------------------
    
    public static void fenetreCreerProduit() {
    Stage fenetrecreerProduit = new Stage();
    fenetrecreerProduit.setTitle("Créer Produit");

    Label labelNom = new Label("Nom du produit :");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: Chaise");

    Label labelAttributs = new Label("Liste d'attributs (séparées par des virgules) :");
    TextField champAttributs = new TextField();
    champAttributs.setPromptText("Ex: Bois, 4 pieds,...");

    Label labelGamme = new Label("Gamme associée :");
    TextField champGamme = new TextField();
    champGamme.setPromptText("Ex: Gamme1");

    Button boutonValider = new Button("Créer");
    boutonValider.setOnAction(e -> {
        String nomProduit = champNom.getText().trim();
        String attributsTexte = champAttributs.getText().trim();
        String gamme = champGamme.getText().trim();

        if (nomProduit.isEmpty() || attributsTexte.isEmpty() || gamme.isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Champs manquants");
            alerte.setHeaderText(null);
            alerte.setContentText("Veuillez remplir tous les champs avant de valider.");
            alerte.show();
            return;
        }

        // Vérifie si la gamme existe (inversé ici par rapport à ton code précédent)
        if (!verifierGammeExistante(gamme)) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Produit inconnu");
            alerte.setHeaderText(null);
            alerte.setContentText("La gamme '" + gamme + "' n'existe pas.");
            alerte.show();
            return;
        }

        // Si gamme valide, créer le produit
        afficherProduit(nomProduit, attributsTexte, gamme);

        Alert succes = new Alert(Alert.AlertType.INFORMATION);
        succes.setTitle("Succès");
        succes.setHeaderText(null);
        succes.setContentText("Le produit a été créé avec succès !");
        succes.showAndWait();

        fenetrecreerProduit.close();
    });

    Button boutonAfficherGamme = new Button("Afficher les gammes existantes");
    boutonAfficherGamme.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des Gammes");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("Gamme.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier Gamme.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 500, 500);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });

    Button retour = new Button("Fermer");
    retour.setOnAction(e -> fenetrecreerProduit.close());

    VBox vboxProduit = new VBox(10);
    vboxProduit.setPadding(new Insets(20));
    vboxProduit.setAlignment(Pos.CENTER);
    vboxProduit.setStyle("-fx-background-color: #f0f0f0;");
    vboxProduit.getChildren().addAll(
        labelNom, champNom,
        labelAttributs, champAttributs,
        labelGamme, champGamme,
        boutonAfficherGamme,
        boutonValider
    );

    Scene scene = new Scene(vboxProduit, 500, 500);
    fenetrecreerProduit.setScene(scene);
    fenetrecreerProduit.show();
}
    
    //-----------------------------------------------------------------------------------------------------------------
    public static void afficherProduit(String idProduit, String listAttributs, String gamme) {

    // Écriture dans un fichier txt
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("produits.txt", true))) {
        // Construction de la ligne avec un format similaire à celui de l'affichage
        String ligne = String.format("%-20s | %-35s | %-10s", idProduit, listAttributs, gamme);

        // Écriture dans le fichier
        writer.write(ligne);
        writer.newLine(); // Retour à la ligne pour le prochain produit
    } catch (IOException e) {
        System.out.println("Erreur lors de l'enregistrement du produit : " + e.getMessage());
    }
}
    
//------------------------------------------------------------------------------------------------------------------
    
    public static boolean verifierGammeExistante(String nomGamme) {
    String gammeRecherchee = nomGamme.trim();

    try (BufferedReader reader = new BufferedReader(new FileReader("Gamme.txt"))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            // Suppose que le nom de la gamme est au début de chaque ligne
            String[] parties = ligne.split("[ ;|]");
            if (parties.length > 0 && parties[0].trim().equalsIgnoreCase(gammeRecherchee)) {
                return true; // La gamme existe, pas besoin d'alerte ici
            }
        }
    } catch (IOException e) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur de lecture");
        alerte.setHeaderText(null);
        alerte.setContentText("Impossible de lire le fichier Gamme.txt : " + e.getMessage());
        alerte.show();
        return false;
    }

    return false; // La gamme n'existe pas
}
    
//------------------------------------------------------------------------------------------------------------------
    public static void fenetreModifierProduit() {
    Stage fenetremodifprod = new Stage();
    fenetremodifprod.setTitle("Modifier produit");

    // Champ pour entrer le nom du produit à modifier
    Label labelAncienNom = new Label("Nom du produit à modifier :");
    TextField champAncienNom = new TextField();
    champAncienNom.setPromptText("Ex: Chaise");

    // Champ pour entrer le nouveau nom du produit
    Label labelNom = new Label("Nouveau nom du produit :");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: Chaise2");

    // Champ pour entrer les attributs
    Label labelAttributs = new Label("Liste des attributs (séparées par des virgules) :");
    TextField champAttributs = new TextField();
    champAttributs.setPromptText("Ex: Bois,...");

    // Champ pour entrer la nouvelle gamme
    Label labelGamme = new Label("Nouvelle gamme associée :");
    TextField champGamme = new TextField();
    champGamme.setPromptText("Ex: Gamme2");

    Button boutonValider = new Button("Modifier");
    boutonValider.setOnAction(e -> {
        String ancienNom = champAncienNom.getText().trim();
        String nomProduit = champNom.getText().trim();
        String AttributsTexte = champAttributs.getText().trim();
        String nouvgamme = champGamme.getText().trim();

        // Vérification que tous les champs sont remplis
        if (ancienNom.isEmpty() || nomProduit.isEmpty() || AttributsTexte.isEmpty() || nouvgamme.isEmpty()) {
            Alert alertErreur = new Alert(Alert.AlertType.ERROR);
            alertErreur.setTitle("Erreur - Champs vides");
            alertErreur.setHeaderText(null);
            alertErreur.setContentText("Veuillez remplir tous les champs avant de valider.");
            alertErreur.show();
            return;
        }

        // Vérification de l'existence de la gamme
        if (!verifierGammeExistante(nouvgamme)) {
            Alert alertErreur = new Alert(Alert.AlertType.ERROR);
            alertErreur.setTitle("Erreur - Gamme inexistante");
            alertErreur.setHeaderText(null);
            alertErreur.setContentText("La gamme \"" + nouvgamme + "\" n'existe pas. Veuillez en créer une ou en choisir une autre.");
            alertErreur.show();
            return;
        }

        boolean modifiee = modifierProduit(ancienNom, nomProduit, AttributsTexte, nouvgamme);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (modifiee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le produit \"" + ancienNom + "\" a été modifié avec succès.");
            alert.showAndWait();
            fenetremodifprod.close();
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le produit \"" + ancienNom + "\" n'a pas été trouvé.");
            alert.show();
        }
    });
    //bouton affichant la liste des produits existant pour aider l utilisateur
    Button boutonAfficherProduits = new Button("Afficher les produits existants");
    boutonAfficherProduits.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des produits");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("produits.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier produits.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 600, 200);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });
    //bouton affichant la liste des gamme existante pour aider l utilisateur
    Button boutonAfficherGamme = new Button("Afficher les gammes existantes");
    boutonAfficherGamme.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des gammes");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("Gamme.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier Gamme.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 500, 200);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });

    Button retour = new Button("Fermer");
    retour.setOnAction(e -> fenetremodifprod.close());

    VBox vboxOp = new VBox(10);
    vboxOp.setPadding(new Insets(20));
    vboxOp.setAlignment(Pos.CENTER);
    vboxOp.setStyle("-fx-background-color: #f0f0f0;");
    vboxOp.getChildren().addAll(
        labelAncienNom, champAncienNom,
        labelNom, champNom,
        labelAttributs, champAttributs,
        labelGamme, champGamme,
        boutonValider, boutonAfficherProduits, boutonAfficherGamme
    );

    Scene scene = new Scene(vboxOp, 500, 500);
    fenetremodifprod.setScene(scene);
    fenetremodifprod.show();
}
    
//----------------------------------------------------------------------------------------------------------------
    
    public static boolean modifierProduit(String AncienNom, String nouvNom, String listAttributs, String gamme) {
    File inputFile = new File("produits.txt");
    List<String> lignes = new ArrayList<>();
    boolean modifiee = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            if (ligne.startsWith(AncienNom.trim())) {
                // Remplacer la ligne par la nouvelle définition
                String nouvelleLigne = String.format("%-20s | %-35s | %-10s", nouvNom, listAttributs,gamme );
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
    
//---------------------------------------------------------------------------------------------------------------------
    public static void fenetreSupprimerProduit() {
    Stage fenetresupproduit = new Stage();
    fenetresupproduit.setTitle("Supprimer produit");

    Label labelNom = new Label("Nom du produit à supprimer:");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: chaise");

    Button boutonValider = new Button("Supprimer");
    boutonValider.setOnAction(e -> {
        String nomProduit = champNom.getText().trim();

        boolean supprimee = supprimerProduit(nomProduit);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (supprimee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le produit \"" + nomProduit + "\" a été supprimé avec succès.");
            alert.showAndWait();
            fenetresupproduit.close(); // ferme la fenêtre seulement si succès
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le produit \"" + nomProduit + "\" n'a pas été trouvé. Assurez-vous qu'il n'a pas déjà été supprimé");
            alert.show(); // on ne ferme pas la fenêtre
        }
    });

    Button boutonAfficherProduit = new Button("Afficher les produits existants");
    boutonAfficherProduit.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des produits");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("produits.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier produits.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 600, 200);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });

    VBox vboxGamme = new VBox(10);
    vboxGamme.setPadding(new Insets(20));
    vboxGamme.setAlignment(Pos.CENTER);
    vboxGamme.setStyle("-fx-background-color: #f0f0f0;");
    vboxGamme.getChildren().addAll(
        labelNom, champNom,
        boutonValider,
        boutonAfficherProduit
    );

    Scene scene = new Scene(vboxGamme, 500, 500);
    fenetresupproduit.setScene(scene);
    fenetresupproduit.show();
}
//-------------------------------------------------------------------------------------------------------------------

public static boolean supprimerProduit(String refProduitASupprimer) {
    File inputFile = new File("produits.txt");
    List<String> lignes = new ArrayList<>();
    boolean ProduitTrouvee = false;

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
                String idProduit = morceaux[0].trim();
                if (idProduit.equalsIgnoreCase(refProduitASupprimer)) {
                    ProduitTrouvee = true;
                    continue; // Ne pas ajouter cette ligne → suppression
                }
            }

            lignes.add(ligne); // Garder la ligne sinon
        }

    } catch (IOException e) {
        System.out.println("Erreur de lecture : " + e.getMessage());
        return false;
    }

    if (!ProduitTrouvee) {
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
