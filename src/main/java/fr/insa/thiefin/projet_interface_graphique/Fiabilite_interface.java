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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author sinsm
 */
public class Fiabilite_interface {
   
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
