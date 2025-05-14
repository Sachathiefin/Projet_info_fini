/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.thiefin.projet_interface_graphique;


import static fr.insa.thiefin.projet_interface_graphique.Fiabilite_interface.afficherFiabilite;
import static fr.insa.thiefin.projet_interface_graphique.Machine_interface.fenetreSupprimerMachine;
import static fr.insa.thiefin.projet_interface_graphique.Machine_interface.fenetrecreerMachine;
import static fr.insa.thiefin.projet_interface_graphique.Machine_interface.fenetremodifierMachine;
import static fr.insa.thiefin.projet_interface_graphique.Poste_interface.fenetreSupprimerPoste;
import static fr.insa.thiefin.projet_interface_graphique.Poste_interface.fenetrecreerPoste;
import static fr.insa.thiefin.projet_interface_graphique.Poste_interface.fenetremodifierPoste;
import static fr.insa.thiefin.projet_interface_graphique.SuiviMaintenanceG_interface.fenetreSuiviMaintenance;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Sacha
 */
public class Equipement_interface {
    public static void fenetreEquipement() {
         Stage fenetre = new Stage();
         fenetre.setTitle("Gestion Equipement");

    HBox conteneurPrincipal = new HBox(40);
        conteneurPrincipal.setPadding(new Insets(20));
        conteneurPrincipal.setAlignment(Pos.CENTER);

        // Création des 4 VBox
        VBox vboxMachine = new VBox(30);
        VBox vboxPoste = new VBox(30);
        VBox vboxSuivis = new VBox(30);
        

        // Alignement interne
        vboxMachine.setAlignment(Pos.TOP_CENTER);
        vboxPoste.setAlignment(Pos.TOP_CENTER);
        vboxSuivis.setAlignment(Pos.TOP_CENTER);

        // Ajout des VBox dans la HBox
        conteneurPrincipal.getChildren().addAll(vboxMachine, vboxPoste, vboxSuivis);

        //creation des titre de chaque colones
        Label Machine = new Label("⚙️ Machine");
        Machine.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label Poste = new Label("🪑 Poste");
        Poste.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label Suivis = new Label("📈 Suivi de la maintenance");
        Suivis.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); 
        
        
        //création des bouton ainsi que leurs actions
        
        Button boutoncreerMachine = new Button("Ajouter");
        boutoncreerMachine.setOnAction(e ->fenetrecreerMachine() );
        
        Button boutonmodifMachine = new Button("Modifier");
        boutonmodifMachine.setOnAction(e ->fenetremodifierMachine() );
        
        Button boutonsupMachine = new Button("Supprimer");
        boutonsupMachine.setOnAction(e -> fenetreSupprimerMachine());
        
        Button boutoncreerPoste = new Button("Ajouter");
        boutoncreerPoste.setOnAction(e -> fenetrecreerPoste());
        
        Button boutonModifPoste = new Button("Modifier");
        boutonModifPoste.setOnAction(e ->fenetremodifierPoste());
        
        Button boutonSupPoste = new Button("Supprimer");
        boutonSupPoste.setOnAction(e ->fenetreSupprimerPoste());
        
         Button boutonFiabilite = new Button("Gérer le suivi des maintenances");
        boutonFiabilite.setOnAction(e -> fenetreSuiviMaintenance());

        vboxMachine.getChildren().addAll(Machine, boutoncreerMachine, boutonmodifMachine, boutonsupMachine);
        vboxPoste.getChildren().addAll(Poste, boutoncreerPoste, boutonModifPoste, boutonSupPoste);
        vboxSuivis.getChildren().addAll(Suivis, boutonFiabilite);

        Scene scene = new Scene(conteneurPrincipal, 800, 400);
        fenetre.setScene(scene);
        fenetre.show();
        
       }
}
  
