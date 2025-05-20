/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.thiefin.projet_interface_graphique;
import java.io.*;
import java.util.*;
/**
 *
 * @author sinsm
 */
public class suiviMaintenance {

// Classe pour stocker les données de chaque machine
    static class StatMachine {
        float tempsArret = 0;
        String dernierArret = null;
    }

    public static void suiviMaintenance(String cheminFichier) {
        Map<String, StatMachine> stats = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            while ((ligne = reader.readLine()) != null) {
                if (ligne.trim().isEmpty()) continue;

                String[] elements = ligne.split(";");
                if (elements.length != 6) continue;

                String date = elements[0];
                String heure = elements[1];
                String machine = elements[2];
                String type = elements[3]; // A (arrêt) ou D (démarrage)

                // Récupérer ou créer les stats de la machine
                StatMachine stat = stats.getOrDefault(machine, new StatMachine());

                if (type.equals("A")) {
                    stat.dernierArret = heure;
                } else if (type.equals("D") && stat.dernierArret != null) {
                    float duree = calculerDuree(stat.dernierArret, heure);
                    stat.tempsArret += duree;
                    stat.dernierArret = null;
                }

                stats.put(machine, stat);
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            return;
        }

        // Calcul de la fiabilité
        float dureeTotale = (float) 14.0; // de 06h00 à 20h00

        Map<String, Float> fiabilites = new HashMap<>();
        for (Map.Entry<String, StatMachine> entry : stats.entrySet()) {
            float fonctionnement = dureeTotale - entry.getValue().tempsArret;
            float fiabilite = fonctionnement / dureeTotale;
            fiabilites.put(entry.getKey(), fiabilite);
        }

        // Tri des machines par fiabilité décroissante
        List<Map.Entry<String, Float>> classement = new ArrayList<>(fiabilites.entrySet());
        classement.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));

        // Affichage
        System.out.println("----- Fiabilité des machines -----");
        for (Map.Entry<String, Float> entry : classement) {
            float pourcentage = Math.round(entry.getValue() * 10000) / (float) 100.0;
            System.out.println("Machine " + entry.getKey() + " : " + pourcentage + "% de fiabilité");
        }
    }

    //Calcul de la durée entre deux heures
    static float calculerDuree(String debut, String fin) {
        try {
            int h1 = Integer.parseInt(debut.substring(0, 2));
            int m1 = Integer.parseInt(debut.substring(3, 5));
            int h2 = Integer.parseInt(fin.substring(0, 2));
            int m2 = Integer.parseInt(fin.substring(3, 5));

            return (float) (((h2 * 60 + m2) - (h1 * 60 + m1)) /60.0);
        } catch (NumberFormatException e) {
            return 0;
        }
 
    }    
}