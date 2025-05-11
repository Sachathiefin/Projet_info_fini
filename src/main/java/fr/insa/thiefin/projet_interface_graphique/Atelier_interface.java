/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.thiefin.projet_interface_graphique;

import static fr.insa.thiefin.projet_interface_graphique.Chef_Atelier_interface.fenetreModifierChef;
import static fr.insa.thiefin.projet_interface_graphique.Chef_Atelier_interface.fenetreSupprimerChef;
import static fr.insa.thiefin.projet_interface_graphique.Chef_Atelier_interface.fenetrecreerChef;
import static fr.insa.thiefin.projet_interface_graphique.operateur_interface.fenetreSupprimerOperateur;
import static fr.insa.thiefin.projet_interface_graphique.operateur_interface.fenetrecreerOperateur;
import static fr.insa.thiefin.projet_interface_graphique.operateur_interface.fenetremodifierOperateur;
import static fr.insa.thiefin.projet_interface_graphique.produits_finis_interface.fenetreSupprimerProduitFini;
import static fr.insa.thiefin.projet_interface_graphique.produits_finis_interface.fenetrecreerProduitsFinis;
import static fr.insa.thiefin.projet_interface_graphique.produits_finis_interface.fenetremodifierProduitFini;
import static fr.insa.thiefin.projet_interface_graphique.stockBrut_interface.fenetreSupprimerStockBrut;
import static fr.insa.thiefin.projet_interface_graphique.stockBrut_interface.fenetrecreerStockBrut;
import static fr.insa.thiefin.projet_interface_graphique.stockBrut_interface.fenetremodifierStockBrut;
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
public class Atelier_interface {
    // action du boutonGamme --> ouvre une fenetre "Gamme" ainsi que les boutons modifer, creer,...
    public static void fenetreAtelier() {
         Stage fenetre = new Stage();
         fenetre.setTitle("Gestion Atelier");

    HBox conteneurPrincipal = new HBox(40);
        conteneurPrincipal.setPadding(new Insets(20));
        conteneurPrincipal.setAlignment(Pos.CENTER);

        // Création des 4 VBox
        VBox vboxOperateur = new VBox(30);
        VBox vboxChef = new VBox(30);
        VBox vboxstock = new VBox(30);
        VBox vboxprodfini = new VBox(30);

        // Alignement interne
        vboxOperateur.setAlignment(Pos.TOP_CENTER);
        vboxChef.setAlignment(Pos.TOP_CENTER);
        vboxstock.setAlignment(Pos.TOP_CENTER);
        vboxprodfini.setAlignment(Pos.TOP_CENTER);

        // Ajout des VBox dans la HBox
        conteneurPrincipal.getChildren().addAll(vboxOperateur, vboxChef, vboxstock, vboxprodfini);

        //creation des titre de chaque colones
        Label operateur = new Label("👷 Opérateurs");
        operateur.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label chef = new Label("👨‍💼 Chefs de l'atelier");
        chef.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label Stock = new Label("📦 Stock brut");
        Stock.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); 
        
        Label prod = new Label("✅ Produits finis");
        prod.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); 
        
        
        
        //création des bouton ainsi que leurs actions
        
        Button boutoncreerOpe = new Button("Ajouter");
        boutoncreerOpe.setOnAction(e ->fenetrecreerOperateur() );
        
        Button boutonmodifOpe = new Button("Modifier");
        boutonmodifOpe.setOnAction(e ->fenetremodifierOperateur() );
        
        Button boutonsupOpe = new Button("Supprimer");
        boutonsupOpe.setOnAction(e -> fenetreSupprimerOperateur());
        
        Button boutoncreerChef = new Button("Ajouter");
        boutoncreerChef.setOnAction(e -> fenetrecreerChef());
        
        Button boutonModifChef = new Button("Modifier");
        boutonModifChef.setOnAction(e ->fenetreModifierChef());
        
        Button boutonSupChef = new Button("Supprimer");
        boutonSupChef.setOnAction(e ->fenetreSupprimerChef());
        
        Button boutoncreerStock = new Button("Ajouter");
        boutoncreerStock.setOnAction(e ->fenetrecreerStockBrut() );
        
        Button boutonmodifStock = new Button("Modifier");
        boutonmodifStock.setOnAction(e -> fenetremodifierStockBrut());
        
        Button boutonsupStock = new Button("Supprimer");
        boutonsupStock.setOnAction(e -> fenetreSupprimerStockBrut());
        
        Button boutoncreerprod = new Button("Ajouter");
        boutoncreerprod.setOnAction(e -> fenetrecreerProduitsFinis());
        
        Button boutonmodifprod = new Button("Modifier");
        boutonmodifprod.setOnAction(e -> fenetremodifierProduitFini());
        
        Button boutonsupProd = new Button("Supprimer");
        boutonsupProd.setOnAction(e -> fenetreSupprimerProduitFini());
        
        
        // ajout des titres et boutons en colone 
        vboxOperateur.getChildren().addAll(operateur, boutoncreerOpe, boutonmodifOpe, boutonsupOpe);
        vboxChef.getChildren().addAll(chef, boutoncreerChef,boutonModifChef ,boutonSupChef );
        vboxstock.getChildren().addAll(Stock, boutoncreerStock,boutonmodifStock ,boutonsupStock );
        vboxprodfini.getChildren().addAll(prod, boutoncreerprod,boutonmodifprod , boutonsupProd);
        
        
        // Création et affichage de la scène
        Scene scene = new Scene(conteneurPrincipal, 800, 400);
        fenetre.setScene(scene);
        fenetre.show();
    }
}
