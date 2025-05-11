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
import java.util.Locale;
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
public class Operation_interface {
    
    // action du boutonoperation --> ouvre une fenetre "Operation" ainsi que les boutons modifer, creer,...
    public static void fenetreOperation() {
        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Operation");

        // creation de boutons dans cette nouvelle fenetre ainsi que les actions qu'ils effectuent
        Button retour = new Button("Fermer");
        retour.setOnAction(e -> nouvelleFenetre.close());
        
        Button CreerOpe = new Button("Creer operation");
        CreerOpe.setOnAction(e -> fenetreCreerOperation());
        
        Button ModifierOpe = new Button("Modifier Operation");
        ModifierOpe.setOnAction(e -> fenetreModifierOperation());
        

        //creation de la vbox pour alligner les bouton
        VBox vboxGamme = new VBox(10);
        vboxGamme.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #f0f0f0;");
        vboxGamme.getChildren().addAll(CreerOpe, ModifierOpe, retour);

        Scene scene = new Scene(vboxGamme, 500, 500);
        nouvelleFenetre.setScene(scene);
        nouvelleFenetre.show();
    }
//-------------------------------------------------------------------------------------------------------------
    
    public static void fenetreCreerOperation() {
    Stage fenetrecreerOP = new Stage();
    fenetrecreerOP.setTitle("Créer Operation");

    // Champ pour entrer le nom de l'opération
    Label labelNom = new Label("Nom de l'operation :");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: OP1");

    // Info sur la durée calculée
    Label labelDuree = new Label("Durée de l'opération (calculée automatiquement) :");
    Label valeurDuree = new Label(""); // sera mise à jour après validation
    
    // Champ pour entrer la désignation de l'opération
    Label labelDesignation = new Label("Désignation de l'operation :");
    TextField champDesignation = new TextField();
    champDesignation.setPromptText("Ex: USINAGE");

    // Champ pour entrer la liste des équipements
    Label labelEquipements = new Label("Liste des équipements (séparées par des virgules) :");
    TextField champEquipements = new TextField();
    champEquipements.setPromptText("Ex: Machine 1,...");

    // Bouton de confirmation 
    Button boutonValider = new Button("Créer");
    boutonValider.setOnAction(e -> {
    String nomOperation = champNom.getText().trim();
    String designationOperation = champDesignation.getText().trim();
    String equipementsTexte = champEquipements.getText().trim();

    // Vérifie que tous les champs sauf la durée sont remplis
    if (nomOperation.isEmpty() || designationOperation.isEmpty() || equipementsTexte.isEmpty()) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur - Champs vides");
        alerte.setHeaderText(null);
        alerte.setContentText("Veuillez remplir tous les champs avant de valider.");
        alerte.show();
        return;
    }

    double dureeCalculee = calculerDureeDepuisMachines(equipementsTexte);
    if (dureeCalculee < 0) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur - Machine inconnue");
        alerte.setHeaderText(null);
        alerte.setContentText("Une ou plusieurs machines n'existent pas dans le fichier machine.txt.");
        alerte.show();
        return;
    }

    // Formatage de la durée avec virgule
    String dureeStr = String.format(Locale.FRANCE, "%.2f", dureeCalculee).replace(".", ",");

    afficherOperation(nomOperation, designationOperation, equipementsTexte, dureeStr);

    Alert succes = new Alert(Alert.AlertType.INFORMATION);
    succes.setTitle("Succès");
    succes.setHeaderText(null);
    succes.setContentText("L'opération a été créée avec succès !");
    succes.showAndWait();

    fenetrecreerOP.close();
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
    
    
    // Mise en page
    VBox vboxOpe = new VBox(10);
    vboxOpe.setPadding(new Insets(20));
    vboxOpe.setAlignment(Pos.CENTER);
    vboxOpe.setStyle("-fx-background-color: #f0f0f0;");
    vboxOpe.getChildren().addAll(
        labelNom, champNom,
        labelDesignation, champDesignation,
        labelEquipements, champEquipements,
        labelDuree,valeurDuree,
        boutonValider, boutonAfficher
    );

    Scene scene = new Scene(vboxOpe, 500, 500);
    fenetrecreerOP.setScene(scene);
    fenetrecreerOP.show();
}

//-------------------------------------------------------------------------------------------------------------
    
   public static void fenetreModifierOperation() {
    Stage fenetremodifOP = new Stage();
    fenetremodifOP.setTitle("Modifier operation");

    // Champ pour entrer le nom de l'opération à modifier
    Label labelAncienNom = new Label("Nom de l'operation à modifier :");
    TextField champAncienNom = new TextField();
    champAncienNom.setPromptText("Ex: OP1");

    // Champ pour entrer le nom de l'opération
    Label labelNom = new Label("Nom de l'operation :");
    TextField champNom = new TextField();
    champNom.setPromptText("Ex: OP1");

    // Champ pour entrer la désignation de l'opération
    Label labelDesignation = new Label("Désignation de l'operation :");
    TextField champDesignation = new TextField();
    champDesignation.setPromptText("Ex: USINAGE");

    // Champ pour entrer la liste des équipements
    Label labelEquipements = new Label("Liste des équipements (séparées par des virgules) :");
    TextField champEquipements = new TextField();
    champEquipements.setPromptText("Ex: Machine 1,...");

    // Affichage de la durée calculée
    Label labelDuree = new Label("Durée recalculée automatiquement :");
    Label valeurDuree = new Label("");

    Button boutonValider = new Button("Modifier");
    boutonValider.setOnAction(e -> { //recuperer les valeur des champs de textes
        String ancienNom = champAncienNom.getText().trim();
        //vérifie si l opération existe
        boolean existe = operationExiste(ancienNom);
        if (!existe) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur - Opération introuvable");
            alerte.setHeaderText(null);
            alerte.setContentText("L'opération \"" + ancienNom + "\" n'existe pas dans le fichier operations.txt.");
            alerte.show();
            return;
        } 
        
        String nomOperation = champNom.getText().trim();
        String designationOperation = champDesignation.getText().trim();
        String equipementsTexte = champEquipements.getText().trim();

        // Calcul de la durée à partir des équipements
        double dureeTotale = calculerDureeDepuisMachines(equipementsTexte);
        if (dureeTotale < 0) {
            Alert alertErreur = new Alert(Alert.AlertType.ERROR);
            alertErreur.setTitle("Erreur");
            alertErreur.setHeaderText(null);
            alertErreur.setContentText("Une ou plusieurs machines sont introuvables dans machine.txt.");
            alertErreur.show();
            return;
        }

        String dureeOp = String.format("%.2f", dureeTotale).replace(".", ",");
        valeurDuree.setText(dureeOp + " h");

        // Vérification que tous les champs sont remplis
        if (ancienNom.isEmpty() || nomOperation.isEmpty() || designationOperation.isEmpty()
                || equipementsTexte.isEmpty() || dureeOp.isEmpty()) {
            Alert alertErreur = new Alert(Alert.AlertType.ERROR);
            alertErreur.setTitle("Erreur - Champs vides");
            alertErreur.setHeaderText(null);
            alertErreur.setContentText("Veuillez remplir tous les champs avant de valider.");
            alertErreur.show();
            return;
        }
        // vérification que les machines ajoutée existent
        Set<String> machinesExistantes = lireMachines();
        String[] equipements = equipementsTexte.split(",");
        for (String equipement : equipements) {
           if (!machinesExistantes.contains(equipement.trim())) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur - Equipement introuvable");
            alerte.setHeaderText(null);
            alerte.setContentText("L'équipement \"" + equipement.trim() + "\" n'existe pas dans machine.txt.");
            alerte.show();
           return;
            }
        }
        // si tout est bon, on modifie
        boolean modifiee = modifierOperation(ancienNom, nomOperation, designationOperation, equipementsTexte, dureeOp);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (modifiee) {
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'opération \"" + ancienNom + "\" a été modifiée avec succès.");
            alert.showAndWait();
            fenetremodifOP.close(); // ferme la fenêtre seulement si succès
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'opération \"" + ancienNom + "\" n'a pas été trouvée.");
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
    
    // bouton pour afficher les opérations existantes
    Button boutonAfficherOP = new Button("Afficher les opérations existantes");
    boutonAfficherOP.setOnAction(e -> {
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

    VBox vboxOp = new VBox(10);
    vboxOp.setPadding(new Insets(20));
    vboxOp.setAlignment(Pos.CENTER);
    vboxOp.setStyle("-fx-background-color: #f0f0f0;");
    vboxOp.getChildren().addAll(
        labelAncienNom, champAncienNom,
        labelNom, champNom,
        labelDesignation, champDesignation,
        labelEquipements, champEquipements,
        labelDuree,valeurDuree ,
        boutonValider, boutonAfficherOP,boutonAfficher
    );

    Scene scene = new Scene(vboxOp, 500, 500);
    fenetremodifOP.setScene(scene);
    fenetremodifOP.show();
}
   
//-------------------------------------------------------------------------------------------------------------------
   public static void afficherOperation(String idOperation, String dOperation, String refEquipement, String dureeOperation) {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("operations.txt", true))) {
            // Si le fichier est vide, on écrit l'en-tête du tableau
            File file = new File("operations.txt");
            if (file.length() == 0) {
                writer.write(String.format("%-15s | %-25s | %-30s | %-15s", "ID", "Designation", "Equipement", "Duree"));
                writer.newLine();
                writer.write("-------------------------------------------------------------------------------------------");
                writer.newLine();
            }
            
            // Réécriture des opérations sous un format de tableau
            writer.write(String.format("%-15s | %-25s | %-30s | %-15s", 
                idOperation, dOperation, refEquipement, dureeOperation));
            writer.newLine();
        } catch (IOException err) {
            System.out.println("Erreur lors de l'écriture de l'opération : " + err.getMessage());
        }
    }
   
//-------------------------------------------------------------------------------------------------------------------------------------------------------
public static boolean modifierOperation(String AncienNom, String nomOP, String designationOperation, String equipementsTexte, String dureeOp) {
    File inputFile = new File("operations.txt");
    List<String> lignes = new ArrayList<>();
    boolean modifiee = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            if (ligne.startsWith(AncienNom.trim())) {
                // Remplacer la ligne par la nouvelle définition
                String nouvelleLigne = String.format("%-15s | %-25s | %-30s | %s",
                        nomOP, designationOperation, equipementsTexte, dureeOp.replace(".", ","));
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

//-----------------------------------------------------------------------------------------------------------------------------

public static Set<String> lireMachines() {
    Set<String> machines = new HashSet<>();
    File fichier = new File("machine.txt");

    if (!fichier.exists()) {
        System.out.println("Le fichier machine.txt est introuvable.");
        return machines;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            String[] tokens = ligne.split("\\|");
            if (tokens.length > 0) {
                machines.add(tokens[0].trim()); // on suppose que l’ID machine est au début de chaque ligne
            }
        }
    } catch (IOException e) {
        System.out.println("Erreur de lecture du fichier machine.txt : " + e.getMessage());
    }

    return machines;
}
//------------------------------------------------------------------------------------------------------------------------------
public static double calculerDureeDepuisMachines(String listeEquipements) {
    double dureeTotale = 0.0;
    String[] machinesDemandées = listeEquipements.split(",");

    try (BufferedReader reader = new BufferedReader(new FileReader("machine.txt"))) {
        String ligne;
        // On saute l'en-tête
        reader.readLine(); // ligne ID | Designation...
        reader.readLine(); // ligne ------------------

        while ((ligne = reader.readLine()) != null) {
            for (int i = 0; i < machinesDemandées.length; i++) {
                String machineDemandee = machinesDemandées[i].trim();
                if (ligne.startsWith(machineDemandee + " ")) {
                    String[] parties = ligne.split("\\|");
                    if (parties.length >= 7) {
                        String dureeStr = parties[6].trim().replace(",", ".");
                        try {
                            double dureeMachine = Double.parseDouble(dureeStr);
                            dureeTotale += dureeMachine;
                            machinesDemandées[i] = ""; // marquer comme trouvée
                        } catch (NumberFormatException e) {
                            System.out.println("Durée invalide pour la machine : " + machineDemandee);
                            return -1; // erreur de format
                        }
                    }
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Erreur de lecture machine.txt : " + e.getMessage());
        return -1;
    }

    // Vérifie qu'aucune machine n'a été laissée sans correspondance
    for (String m : machinesDemandées) {
        if (!m.isEmpty()) {
            return -1; // machine non trouvée
        }
    }

    return dureeTotale;
}

//--------------------------------------------------------------------------------------------------------------
public static boolean operationExiste(String nomOperation) {
    try (BufferedReader reader = new BufferedReader(new FileReader("operations.txt"))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            if (ligne.startsWith(nomOperation + " ")) {
                return true;
            }
        }
    } catch (IOException e) {
        System.err.println("Erreur lors de la lecture du fichier operations.txt : " + e.getMessage());
    }
    return false;
}
}
