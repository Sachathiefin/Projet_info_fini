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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    public static void afficherFiabilite() {
    Stage fenetreFiabilite = new Stage();
    fenetreFiabilite.setTitle("Fiabilité des machines");

    VBox zoneCentrale = new VBox(15);
    zoneCentrale.setPadding(new Insets(20));
    zoneCentrale.setAlignment(Pos.TOP_LEFT);

    Label titreFiabilite = new Label("Fiabilité des machines (06h00 - 20h00)");
    titreFiabilite.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    TextArea affichageResultat = new TextArea();
    affichageResultat.setEditable(false);
    affichageResultat.setPrefSize(400, 300);

    StringBuilder resultat = new StringBuilder();

    File fichier = new File("suiviMaintenance.txt");
    if (!fichier.exists()) {
        resultat.append("Fichier suiviMaintenance.txt introuvable.");
    } else {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            Map<String, suiviMaintenance.StatMachine> stats = new HashMap<>();
            String ligne;

            while ((ligne = reader.readLine()) != null) {
                if (ligne.trim().isEmpty()) continue;

                // Ignore l'en-tête ou les séparateurs
                if (ligne.toLowerCase().startsWith("date") || ligne.startsWith("-")) continue;

                String[] elements = ligne.split(";");
                if (elements.length != 6) continue;

                String heure = elements[1].trim();
                String machine = elements[2].trim();
                String type = elements[3].trim();

                suiviMaintenance.StatMachine stat = stats.getOrDefault(machine, new suiviMaintenance.StatMachine());

                if (type.equalsIgnoreCase("A")) {
                    stat.dernierArret = heure;
                } else if (type.equalsIgnoreCase("D") && stat.dernierArret != null) {
                    float duree = suiviMaintenance.calculerDuree(stat.dernierArret, heure);
                    stat.tempsArret += duree;
                    stat.dernierArret = null;
                }

                stats.put(machine, stat);
            }

            float dureeTotale = 14.0f;

            Map<String, Float> fiabilites = new HashMap<>();
            for (Map.Entry<String, suiviMaintenance.StatMachine> entry : stats.entrySet()) {
                float fonctionnement = dureeTotale - entry.getValue().tempsArret;
                float fiabilite = fonctionnement / dureeTotale;
                fiabilites.put(entry.getKey(), fiabilite);
            }

            List<Map.Entry<String, Float>> classement = new ArrayList<>(fiabilites.entrySet());
            classement.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));

            if (classement.isEmpty()) {
                resultat.append("Aucune donnée de fiabilité trouvée.");
            } else {
                for (Map.Entry<String, Float> entry : classement) {
                    float pourcentage = Math.round(entry.getValue() * 10000) / 100.0f;
                    resultat.append("Machine ").append(entry.getKey())
                            .append(" : ").append(pourcentage).append(" % de fiabilité\n");
                }
            }

        } catch (IOException ex) {
            resultat.append("Erreur lors de la lecture du fichier : ").append(ex.getMessage());
        }
    }

    affichageResultat.setText(resultat.toString());
    zoneCentrale.getChildren().addAll(titreFiabilite, affichageResultat);

    Scene scene = new Scene(zoneCentrale, 500, 400);
    fenetreFiabilite.setScene(scene);
    fenetreFiabilite.show();
}

}
  
