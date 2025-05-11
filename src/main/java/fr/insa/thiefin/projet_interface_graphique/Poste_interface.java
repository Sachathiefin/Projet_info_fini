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

public class Poste_interface {

    public static void fenetrecreerPoste() {
    Stage fenetre = new Stage();
    fenetre.setTitle("Créer Poste");

    Label labelID = new Label("Identifiant du poste :");
    TextField champID = new TextField();
    champID.setPromptText("Ex : SB123");

    Label labeldesi = new Label("Désignation :");
    TextField champDesi = new TextField();
    champDesi.setPromptText("Ex : USINAGE");

    Label labelList = new Label("Liste de machines (séparées par des virgules) :");
    TextField champList = new TextField();
    champList.setPromptText("Ex : DH24, FZ30,...");

    Button boutonValider = new Button("Créer");
    boutonValider.setOnAction(e -> {
        String id = champID.getText().trim();
        String Desi = champDesi.getText().trim();
        String ListeMachine = champList.getText().trim();

        if (id.isEmpty() || Desi.isEmpty() || ListeMachine.isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Champs manquants");
            alerte.setHeaderText(null);
            alerte.setContentText("Veuillez remplir tous les champs.");
            alerte.show();
        } else {
            // Vérification des machines
            List<String> machinesUtilisateur = List.of(ListeMachine.split("\\s*,\\s*"));
            List<String> machinesExistantes = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader("machine.txt"))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    String idMachine = ligne.split("\\|")[0].trim();
                    machinesExistantes.add(idMachine);
                }
            } catch (IOException ex) {
                Alert erreurFichier = new Alert(Alert.AlertType.ERROR);
                erreurFichier.setTitle("Erreur de fichier");
                erreurFichier.setHeaderText(null);
                erreurFichier.setContentText("Impossible de lire le fichier des machines : " + ex.getMessage());
                erreurFichier.show();
                return;
            }

            List<String> machinesIntrouvables = new ArrayList<>();
            for (String machine : machinesUtilisateur) {
                if (!machinesExistantes.contains(machine)) {
                    machinesIntrouvables.add(machine);
                }
            }

            if (!machinesIntrouvables.isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.ERROR);
                alerte.setTitle("Machines introuvables");
                alerte.setHeaderText(null);
                alerte.setContentText("Les machines suivantes n'existent pas :\n" + String.join(", ", machinesIntrouvables));
                alerte.show();
                return;
            }

            // Toutes les machines sont valides
            afficherPoste(id, Desi, ListeMachine);
            Alert succes = new Alert(Alert.AlertType.INFORMATION);
            succes.setTitle("Succès");
            succes.setHeaderText(null);
            succes.setContentText("Le poste a été ajouté avec succès !");
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
    vbox.getChildren().addAll(labelID, champID,
            labeldesi, champDesi,
            labelList, champList,
            boutonValider, boutonAfficher);

    Scene scene = new Scene(vbox, 500, 600);
    fenetre.setScene(scene);
    fenetre.show();
}
//-----------------------------------------------------------------------------------------------------------------
    public static void afficherPoste(String id, String desi, String listmachine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("poste.txt", true))) {
            writer.write(String.format("%-15s | %-20s| %-30s", id, desi,listmachine));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erreur d'enregistrement : " + e.getMessage());
        }
    }
//-----------------------------------------------------------------------------------------------------------------
    public static void fenetremodifierPoste() {
    Stage fenetre = new Stage();
    fenetre.setTitle("Modifier Poste");

    Label labelancienID = new Label("Ancien identifiant du poste :");
    TextField champancienID = new TextField();
    champancienID.setPromptText("Ex : SB123");

    Label labelID = new Label("Nouvel identifiant du poste :");
    TextField champID = new TextField();
    champID.setPromptText("Ex : SB124");

    Label labeldesi = new Label("Désignation :");
    TextField champDesi = new TextField();
    champDesi.setPromptText("Ex : USINAGE");

    Label labelList = new Label("Liste de machines :");
    TextField champList = new TextField();
    champList.setPromptText("Ex : SG45,...");
    
    

    Button boutonValider = new Button("Modifier");
    boutonValider.setOnAction(e -> {
        String ancienid = champancienID.getText().trim();
        String id = champID.getText().trim();
        String Desi = champDesi.getText().trim();
        String list = champList.getText().trim();

        if (ancienid.isEmpty() || id.isEmpty() || Desi.isEmpty() || list.isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Champs manquants");
            alerte.setHeaderText(null);
            alerte.setContentText("Veuillez remplir tous les champs.");
            alerte.show();
            return;
        }

        // 🔎 Vérification de l'existence de l'ancien poste
        boolean posteExiste = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("poste.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parties = ligne.split("\\|");
                if (parties[0].trim().equals(ancienid)) {
                    posteExiste = true;
                    break;
                }
            }
        } catch (IOException ex) {
            Alert erreurFichier = new Alert(Alert.AlertType.ERROR);
            erreurFichier.setTitle("Erreur de fichier");
            erreurFichier.setHeaderText(null);
            erreurFichier.setContentText("Impossible de lire le fichier des postes : " + ex.getMessage());
            erreurFichier.show();
            return;
        }

        if (!posteExiste) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Poste introuvable");
            alerte.setHeaderText(null);
            alerte.setContentText("Le poste avec l'identifiant \"" + ancienid + "\" n'existe pas.");
            alerte.show();
            return;
        }

        List<String> machinesUtilisateur = List.of(list.split("\\s*,\\s*"));
        List<String> machinesExistantes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("machine.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String idMachine = ligne.split("\\|")[0].trim();
                machinesExistantes.add(idMachine);
            }
        } catch (IOException ex) {
            Alert erreurFichier = new Alert(Alert.AlertType.ERROR);
            erreurFichier.setTitle("Erreur de fichier");
            erreurFichier.setHeaderText(null);
            erreurFichier.setContentText("Impossible de lire le fichier des machines : " + ex.getMessage());
            erreurFichier.show();
            return;
        }

        List<String> machinesIntrouvables = new ArrayList<>();
        for (String machine : machinesUtilisateur) {
            if (!machinesExistantes.contains(machine)) {
                machinesIntrouvables.add(machine);
            }
        }

        if (!machinesIntrouvables.isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Machines introuvables");
            alerte.setHeaderText(null);
            alerte.setContentText("Les machines suivantes n'existent pas :\n" + String.join(", ", machinesIntrouvables));
            alerte.show();
            return;
        }

        
        modifierPoste(ancienid, id, Desi, list);
        Alert succes = new Alert(Alert.AlertType.INFORMATION);
        succes.setTitle("Succès");
        succes.setHeaderText(null);
        succes.setContentText("Le poste a été modifié avec succès !");
        succes.showAndWait();
        fenetre.close();
        
    });

    // Boutons d'affichage
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

    Button boutonAfficherPoste = new Button("Afficher les postes");
    boutonAfficherPoste.setOnAction(e -> {
        Stage affichage = new Stage();
        affichage.setTitle("Poste");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setStyle("-fx-font-family: 'monospace';");

        try (BufferedReader reader = new BufferedReader(new FileReader("poste.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                textArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Erreur de lecture : " + ex.getMessage());
        }

        VBox box = new VBox(textArea);
        box.setPadding(new Insets(10));
        Scene scene = new Scene(box, 600, 200);
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
            labelList, champList,
            boutonValider, boutonAfficher, boutonAfficherPoste);

    Scene scene = new Scene(vbox, 500, 600);
    fenetre.setScene(scene);
    fenetre.show();
}

//-----------------------------------------------------------------------------------------------------------------
    public static boolean modifierPoste(String ancienid, String id, String desi, String list) {
        File file = new File("poste.txt");
        List<String> lignes = new ArrayList<>();
        boolean modifie = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith(ancienid)) {
                    lignes.add(String.format("%-15s | %-20s| %-30s", id, desi,list));
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
    public static void fenetreSupprimerPoste() {
        Stage fenetre = new Stage();
        fenetre.setTitle("Supprimer Poste");

        Label labelID = new Label("Identifiant du poste à supprimer :");
        TextField champID = new TextField();

        Button boutonValider = new Button("Supprimer");
        boutonValider.setOnAction(e -> {
            String id = champID.getText().trim();
            boolean supprimee = supprimerPoste(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
        if (supprimee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le poste  \"" + id + "\" a été supprimé avec succès.");
            alert.showAndWait();
            fenetre.close(); // ferme la fenêtre seulement si succès
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le poste \"" + id + "\" n'a pas été trouvé.");
            alert.show(); // on ne ferme pas la fenêtre
        }
        });

        Button boutonAfficher = new Button("Afficher les postes");
        boutonAfficher.setOnAction(e -> {
            Stage affichage = new Stage();
            affichage.setTitle("Machine");

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setStyle("-fx-font-family: 'monospace';");

            try (BufferedReader reader = new BufferedReader(new FileReader("poste.txt"))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    textArea.appendText(ligne + "\n");
                }
            } catch (IOException ex) {
                textArea.setText("Erreur de lecture : " + ex.getMessage());
            }

            VBox box = new VBox(textArea);
            box.setPadding(new Insets(10));
            Scene scene = new Scene(box, 600, 200);
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
    public static boolean supprimerPoste(String id) {
    File inputFile = new File("poste.txt");
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
}