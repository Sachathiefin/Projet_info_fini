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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class Gamme_interface {
    
    //------------------------------------------------------------------------------------------------------------------
    // action du boutonGamme --> ouvre une fenetre "Gamme" ainsi que les boutons modifer, creer,...
    public static void fenetreGamme() {
        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Gamme");

        // creation de boutons dans cette nouvelle fenetre ainsi que les actions qu'ils effectuent
        Button retour = new Button("Fermer");
        retour.setOnAction(e -> nouvelleFenetre.close());
        
        Button CreerGamme = new Button("Creer Gamme");
        CreerGamme.setOnAction(e -> fenetreCreerGamme());
        
        Button SupprimerGamme = new Button("Supprimer Gamme");
        SupprimerGamme.setOnAction(e -> fenetreSupprimerGamme());
        

        //creation de la vbox pour alligner les bouton
        VBox vboxGamme = new VBox(10);
        vboxGamme.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #f0f0f0;");
        vboxGamme.getChildren().addAll(CreerGamme, SupprimerGamme, retour);

        Scene scene = new Scene(vboxGamme, 500, 500);
        nouvelleFenetre.setScene(scene);
        nouvelleFenetre.show();
    }
//-------------------------------------------------------------------------------------------------------------
    
   public static void fenetreCreerGamme() {
    Stage fenetrecreergamme = new Stage();
    fenetrecreergamme.setTitle("Créer Gamme");

    // Champ pour entrer le nom de la gamme
    Label labelNom = new Label("Nom de la Gamme :");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: gamme1");

    // Champ pour entrer la liste d'opérations
    Label labelOperations = new Label("Liste d'opérations (séparées par des virgules) :");
    TextField champOperations = new TextField();
    champOperations.setPromptText("Ex: OP1,OP2");

    // Bouton de confirmation 
    Button boutonValider = new Button("Créer");
    boutonValider.setOnAction(e -> {
    String nomGamme = champNom.getText().trim();
    String operationsTexte = champOperations.getText().trim();

    if (nomGamme.isEmpty() || operationsTexte.isEmpty()) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Champs manquants");
        alerte.setHeaderText(null);
        alerte.setContentText("Veuillez remplir tous les champs avant de valider.");
        alerte.show();
        return;
    }

    // Vérifie si les opérations existent déjà dans operations.txt
    if (!verifierOperationsExistantes(operationsTexte)) {
        return; // Interrompt l’action si des opérations sont manquantes
    }

    // Si tout est bon, on affiche la gamme (ou on la crée)
    afficheGamme(nomGamme, operationsTexte);

    // Message de succès
    Alert succes = new Alert(Alert.AlertType.INFORMATION);
    succes.setTitle("Succès");
    succes.setHeaderText(null);
    succes.setContentText("La gamme a été créée avec succès !");
    succes.showAndWait();

    fenetrecreergamme.close();
});

    
    // Bouton pour afficher les opérations existantes
    Button boutonAfficherOperations = new Button("Afficher les opérations existantes");
    boutonAfficherOperations.setOnAction(e -> {
        Stage fenetreAffichage = new Stage();
        fenetreAffichage.setTitle("Liste des opérations");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'monospace';");
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("operations.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture du fichier operations.txt : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 700, 200);
        fenetreAffichage.setScene(scene);
        fenetreAffichage.show();
    });

    // Mise en page
    VBox vboxGamme = new VBox(10);
    vboxGamme.setPadding(new Insets(20));
    vboxGamme.setAlignment(Pos.CENTER);
    vboxGamme.setStyle("-fx-background-color: #f0f0f0;");
    vboxGamme.getChildren().addAll(
        labelNom, champNom,
        labelOperations, champOperations,
        boutonAfficherOperations,
        boutonValider
    );

    Scene scene = new Scene(vboxGamme, 500, 500);
    fenetrecreergamme.setScene(scene);
    fenetrecreergamme.show();
}

//-------------------------------------------------------------------------------------------------------------
    
   public static void fenetreSupprimerGamme() {
    Stage fenetresupgamme = new Stage();
    fenetresupgamme.setTitle("Supprimer Gamme");

    Label labelNom = new Label("Nom de la Gamme à supprimer:");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: gamme1");

    Button boutonValider = new Button("Supprimer");
    boutonValider.setOnAction(e -> {
        String nomGamme = champNom.getText().trim();

        boolean supprimee = supprimerGamme(nomGamme);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (supprimee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La gamme \"" + nomGamme + "\" a été supprimée avec succès.");
            alert.showAndWait();
            fenetresupgamme.close(); // ferme la fenêtre seulement si succès
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La gamme \"" + nomGamme + "\" n'a pas été trouvée.");
            alert.show(); // on ne ferme pas la fenêtre
        }
    });

    Button boutonAfficherGamme = new Button("Afficher Gammes existantes");
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
        Scene scene = new Scene(box, 500, 200);
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
        boutonAfficherGamme
    );

    Scene scene = new Scene(vboxGamme, 500, 500);
    fenetresupgamme.setScene(scene);
    fenetresupgamme.show();
}

//-----------------------------------------------------------------------------------------------------------------------------------------
    public static void afficheGamme(String refGamme, String listOperation) {
    try {
        BufferedWriter Gammelist = new BufferedWriter(new FileWriter("Gamme.txt", true));
        File file = new File("Gamme.txt");

        if (file.length() == 0) {
            Gammelist.write(String.format("%-20s | %-20s | %-10s", "ID", "Operations", "Duree"));
            Gammelist.newLine();
            Gammelist.write("-------------------------------------------------------------");
            Gammelist.newLine();
        }

        float duree = dureeGamme(listOperation);
        Gammelist.write(String.format("%-20s | %-20s | %-10.2f", refGamme, listOperation, duree));
        Gammelist.newLine();

        Gammelist.close();
    } catch (IOException err) {
        System.out.println("Erreur :\n" + err);
    }
}
    
    //-----------------------------------------------------------------------------------------------------------------------------------------
    public static float dureeGamme(String operationsTexte) {
    float dureeTotale = 0;
    String[] operationsDemandées = operationsTexte.split(",");

    try (BufferedReader reader = new BufferedReader(new FileReader("operations.txt"))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            for (String opDemandée : operationsDemandées) {
                if (ligne.startsWith(opDemandée.trim())) {
                    // Séparer les colonnes avec au moins deux espaces
                    String[] colonnes = ligne.trim().split("\\s{2,}");
                    if (colonnes.length >= 4) {
                        String dureeStr = colonnes[3].replace(",", ".").trim(); // "3,00" → "3.00"
                        try {
                            float duree = Float.parseFloat(dureeStr);
                            dureeTotale += duree;
                        } catch (NumberFormatException e) {
                            System.out.println("Durée invalide pour " + opDemandée + ": " + dureeStr);
                        }
                    }
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Erreur de lecture du fichier operations.txt : " + e.getMessage());
    }

    return dureeTotale;
}

//---------------------------------------------------------------------------------------------------------
    public static boolean supprimerGamme(String refGammeASupprimer) {
    File inputFile = new File("Gamme.txt");
    List<String> lignes = new ArrayList<>();
    boolean gammeTrouvee = false;

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
                String idGamme = morceaux[0].trim();
                if (idGamme.equalsIgnoreCase(refGammeASupprimer)) {
                    gammeTrouvee = true;
                    continue; // Ne pas ajouter cette ligne → suppression
                }
            }

            lignes.add(ligne); // Garder la ligne sinon
        }

    } catch (IOException e) {
        System.out.println("Erreur de lecture : " + e.getMessage());
        return false;
    }

    if (!gammeTrouvee) {
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
    
//-------------------------------------------------------------------------------------------------------------------
    
    public static boolean verifierOperationsExistantes(String operationsTexte) {
    // Liste des opérations saisies par l'utilisateur (trim pour enlever les espaces)
    String[] operationsEntrees = operationsTexte.split(",");
    Set<String> operationsRecherchees = new HashSet<>();
    for (String op : operationsEntrees) {
        operationsRecherchees.add(op.trim());
    }

    // Lire toutes les lignes du fichier pour trouver les noms des opérations existantes
    Set<String> operationsExistantes = new HashSet<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("operations.txt"))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            // Suppose que le nom de l'opération est au début de la ligne, avant le premier espace ou point-virgule
            String[] parties = ligne.split("[ ;]");
            if (parties.length > 0) {
                operationsExistantes.add(parties[0].trim());
            }
        }
    } catch (IOException e) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur de lecture");
        alerte.setHeaderText(null);
        alerte.setContentText("Impossible de lire le fichier operations.txt : " + e.getMessage());
        alerte.show();
        return false;
    }

    // Identifier les opérations manquantes
    List<String> manquantes = new ArrayList<>();
    for (String op : operationsRecherchees) {
        if (!operationsExistantes.contains(op)) {
            manquantes.add(op);
        }
    }

    // Affichage en cas d'erreurs
    if (!manquantes.isEmpty()) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Opérations manquantes");
        alerte.setHeaderText(null);
        alerte.setContentText("Les opérations suivantes n'existent pas :\n" + String.join(", ", manquantes));
        alerte.show();
        return false;
    }

    return true;
}
    
}
