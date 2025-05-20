package fr.insa.thiefin.projet_interface_graphique;


import static fr.insa.thiefin.projet_interface_graphique.Atelier_interface.fenetreAtelier;
import static fr.insa.thiefin.projet_interface_graphique.Equipement_interface.fenetreEquipement;
import static fr.insa.thiefin.projet_interface_graphique.Gamme_interface.fenetreGamme;
import static fr.insa.thiefin.projet_interface_graphique.Machine_interface.afficherPlanMachine;
import static fr.insa.thiefin.projet_interface_graphique.Operation_interface.fenetreOperation;
import static fr.insa.thiefin.projet_interface_graphique.Produit_interface.fenetreProduit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bienvenue dans l'Atelier");

        // Titre de bienvenue
        Label labelBienvenue = new Label("Bienvenue dans l'atelier !");
        labelBienvenue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Question à l'utilisateur
        Label labelChoix = new Label("Que souhaitez-vous faire ?");

        // Bouton Gestion
        Button boutonGestion = new Button("Gestion");
        boutonGestion.setOnAction(e -> ouvrirFenetreGestion());

        // Bouton Affichage
        Button boutonAffichage = new Button("Affichage");
        boutonAffichage.setOnAction(e -> ouvrirFenetreAffichage());

        // Mise en page
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelBienvenue, labelChoix, boutonGestion, boutonAffichage);

        Scene scene = new Scene(layout, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
        
//-----------------------------------------------------------------------------------------------------------------
   public void ouvrirFenetreAffichage() {
    Stage fenetreAffichage = new Stage();
    fenetreAffichage.setTitle("Affichage");

    Label labelQuestion = new Label("Que voulez-vous afficher ?");
    labelQuestion.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-underline: true;");
    
    // Création des boutons avec emoji (selon le type de fichier)
    Button boutonAfficherProduit = new Button("📦 Produit");
    Button boutonAfficherGamme = new Button("📋 Gamme");
    Button boutonAfficherOperation = new Button("⚙️ Opération");
    Button boutonAfficherOperateur = new Button("👷 Opérateur");
    Button boutonAfficherChefs = new Button("👨‍💼 Chefs d'atelier");
    Button boutonAfficherStock = new Button("📦 Stock brut");
    Button boutonAfficherProduitFinis = new Button("✅ Produits finis");
    Button boutonAfficherPoste = new Button("🪑 Poste");
    Button boutonAfficherMachine = new Button("🔩 Machine");
    Button boutonAfficherSuivis = new Button("📈 Suivis");
    Button boutonAfficherFiabilite = new Button("Fiabilité");

    // Actions
    boutonAfficherProduit.setOnAction(e -> afficherContenuFichier("produits.txt", "Produit"));
    boutonAfficherGamme.setOnAction(e -> afficherContenuFichier("Gamme.txt", "Gamme"));
    boutonAfficherOperation.setOnAction(e -> afficherContenuFichier("operations.txt", "Opération"));
    boutonAfficherOperateur.setOnAction(e -> afficherContenuFichier("operateurs.txt", "Opérateur"));
    boutonAfficherChefs.setOnAction(e -> afficherContenuFichier("chefs.txt", "Chefs d'atelier"));
    boutonAfficherStock.setOnAction(e -> afficherContenuFichier("stocksBruts.txt", "Stock brut"));
    boutonAfficherProduitFinis.setOnAction(e -> afficherContenuFichier("produitsfinis.txt", "Produits finis"));
    boutonAfficherPoste.setOnAction(e -> afficherContenuFichier("poste.txt", "Poste"));
    boutonAfficherMachine.setOnAction(e ->{afficherContenuFichier("machine.txt", "Machine");
                                           afficherPlanMachine();});
    boutonAfficherSuivis.setOnAction(e -> afficherContenuFichier("suiviMaintenance.txt", "Suivi Maintenance"));
    boutonAfficherFiabilite.setOnAction(e-> afficherContenuFichier("fiabilite_machines.txt", "Fiabilité"));


    // Uniformiser la largeur des boutons
    Button[] tousLesBoutons = {
        boutonAfficherProduit, boutonAfficherGamme,
        boutonAfficherOperation, boutonAfficherOperateur, boutonAfficherChefs, boutonAfficherStock,
        boutonAfficherProduitFinis, boutonAfficherPoste, boutonAfficherMachine, boutonAfficherSuivis, boutonAfficherFiabilite
    };
    for (Button b : tousLesBoutons) {
        b.setPrefWidth(200);
    }

    // Organisation en 2 colonnes via GridPane
    GridPane grid = new GridPane();
    grid.setHgap(20);
    grid.setVgap(15);
    grid.setAlignment(Pos.CENTER);

    for (int i = 0; i < tousLesBoutons.length; i++) {
        grid.add(tousLesBoutons[i], i % 2, i / 2);
    }

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.TOP_CENTER);
    layout.setPadding(new Insets(20));
    layout.getChildren().addAll(labelQuestion, grid);

    Scene scene = new Scene(layout, 550, 500);
    fenetreAffichage.setScene(scene);
    fenetreAffichage.show();
}
    
//-----------------------------------------------------------------------------------------------------------------
        
       public void afficherContenuFichier(String nomFichier, String titreFenetre) {
    Stage fenetre = new Stage();
    fenetre.setTitle("Affichage : " + titreFenetre);

    TextArea zoneTexte = new TextArea();
    zoneTexte.setStyle("-fx-font-family: 'monospace';");
    zoneTexte.setEditable(false);

    try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            zoneTexte.appendText(ligne + "\n");
        }
    } catch (IOException e) {
        zoneTexte.setText("Erreur lors de la lecture du fichier : " + nomFichier + "\n" + e.getMessage());
    }

    VBox layout = new VBox(zoneTexte);
    layout.setPadding(new Insets(10));

    Scene scene = new Scene(layout, 800, 200);
    fenetre.setScene(scene);
    fenetre.show();
}
 
//---------------------------------------------------------------------------------------------------------------       
      
    public void ouvrirFenetreGestion() {
        Stage fenetreGestion = new Stage();
        fenetreGestion.setTitle("Gestion");
        
        // Création des boutons
        Button boutonGamme = new Button("📋 Gamme");
        Button boutonEquipement = new Button("🛠️ Equipement");
        Button boutonProduit = new Button("📦 Produit");
        Button boutonAtelier = new Button("🏭 Atelier"); // permet de voir l atelier (position des machine, poste, fichier txt...
        Button boutonOperation = new Button("⚙️ Operation");
        
//Action des autres bouton
boutonGamme.setOnAction(e -> fenetreGamme());
boutonProduit.setOnAction(e -> fenetreProduit());
boutonOperation.setOnAction(e -> fenetreOperation());
boutonAtelier.setOnAction(e -> fenetreAtelier());
boutonEquipement.setOnAction(e -> fenetreEquipement());

  
        // Organisation verticale
        VBox vbox = new VBox(30); // 20 px d'espacement entre les boutons
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #f0f0f0;");
        vbox.getChildren().addAll(
            boutonAtelier,
            boutonProduit,
            boutonGamme,
            boutonOperation,
            boutonEquipement
        );

        // Affichage
        Scene scene = new Scene(vbox, 600, 500);
        fenetreGestion.setScene(scene);
        fenetreGestion.show();
    }
//----------------------------------------------------------------------------------------------------------------- 

    
    
    public static void main(String[] args) {
        launch();
    }

}