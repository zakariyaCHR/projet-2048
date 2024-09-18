package org.zakco.tp03_2048;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HelloController {

    @FXML
    private Label bestScoreLabel;

    @FXML
    private Button buttonBas;

    @FXML
    private Button buttonDroite;

    @FXML
    private Button buttonGauche;

    @FXML
    private Button buttonHaut;

    @FXML
    private Button buttonRejouer;

    @FXML
    private Label scoreLabel;

    @FXML
    private GridPane tileGrid;

    // Attribut pour stocker les labels
    private Label[][] tileLabels = new Label[4][4];

    // Attribut pour stocker l'instance de Grid
    private Grid grid;

    // Methode intialize()
    @FXML
    void initialize() {
        // Initialisation de l'instance de Grid
        grid = new Grid();
        scoreLabel.setText("0");
        bestScoreLabel.setText("0");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label();
                label.setText("0");
                label.setTextFill(Color.WHITE);
                label.setFont(new Font("Arial", 20));
                label.setAlignment(Pos.CENTER);
                label.setMinSize(60, 60);
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                tileGrid.add(label, j, i);
                tileLabels[i][j] = label;
            }
        }

        // Initialisation du jeu
        initializeGame();
    }

    // Question 16
    public void randonTileValue() {
        List<Tile> availableTiles = grid.availableTiles();
        if (!availableTiles.isEmpty()) {
            Random random = new Random();
            Tile randomTile = availableTiles.get(random.nextInt(availableTiles.size()));
            double value = random.nextDouble();
            randomTile.setValue(value < 0.9 ? 2 : 4);
        }
    }

    // Question 17
    public void updateView() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                Tile tile = grid.get(i, j);
                Label label = tileLabels[i][j];
                label.setText(tile.isEmpty() ? "" : String.valueOf(tile.getValue()));

                // Va mettre la couleur de fond
                if (!tile.isEmpty()) {
                    label.setBackground(computeBackground(tile.getValue()));
                } else {
                    label.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }
    }

    // Question 18
    public void initializeGame() {
        // Reinitialiser la grille
        grid.clear();

        // Ajouter aléatoirement une valeur à deux tuile de la grille
        randonTileValue();
        randonTileValue();

        // Mettre a jour l'affichage de la grille
        updateView();
    }

    // Question 19
    // Definition des mouvements

    @FXML
    public void moveUp() {
        MoveResult result = grid.moveUp();
        handleMoveResult(result);
    }

    @FXML
    public void moveDown() {
        MoveResult result = grid.moveDown();
        handleMoveResult(result);
    }

    @FXML
    public void moveLeft() {
        MoveResult result = grid.moveLeft();
        handleMoveResult(result);
    }

    @FXML
    public void moveRight() {
        MoveResult result = grid.moveRight();
        handleMoveResult(result);
    }

    // Methode handleMoveResult() pour pouvoir declarer les bouton
    private void handleMoveResult(MoveResult result) {
        if (result.hasMoved()) {
            // Mets a jour le score du joueur
            int newScore = Integer.parseInt(scoreLabel.getText()) + result.getMergeScore();
            updateScore(newScore);

            // Mets a jour le BestScore
            int bestScore = Integer.parseInt(bestScoreLabel.getText());
            if (newScore > bestScore) {
                updateBestScore(newScore);
            }

            // Ajoute une tuile aléatoire sur la grille
            randonTileValue();

            // Mets a jour l'affichage de la grille
            updateView();

            // Verifie si la grille est bloque
            if (grid.isBlocked()) {
                buttonHaut.setDisable(true);
                buttonBas.setDisable(true);
                buttonGauche.setDisable(true);
                buttonDroite.setDisable(true);
            }
        }
    }

    // Question 20
    // Methode restartGame()
    @FXML
    public void restartGame() {
        // Mets le score a 0
        scoreLabel.setText("0");
        bestScoreLabel.setText("0");

        // Efface le contenue de la grille
        grid.clear();

        // Mettre a jour la vue
        updateView();

        // Reactiver les boutons
        buttonHaut.setDisable(false);
        buttonBas.setDisable(false);
        buttonGauche.setDisable(false);
        buttonDroite.setDisable(false);

        // Initialise le jeu
        initializeGame();
    }

    // Question 21 // Different affichage pour les couleurs
    private static Background computeBackground(int value) {
        // Map pour les valeurs de couleur
        Map<Integer, Color> colorMap = new HashMap<>();
        colorMap.put(2, Color.web("#F6DDCC"));
        colorMap.put(4, Color.web("#EC7063"));
        colorMap.put(8, Color.web("#E74C3C"));
        colorMap.put(16, Color.web("#A93226"));
        colorMap.put(32, Color.web("#edcf72"));
        colorMap.put(64, Color.web("#edcc61"));
        colorMap.put(128, Color.web("#edc850"));
        colorMap.put(256, Color.web("#edc53f"));
        colorMap.put(512, Color.web("#edc22e"));

        // Couleur par default au cas ou la valeur n'est pas dans la map
        Color color = colorMap.getOrDefault(value, Color.web("#cdc1b4"));

        // Arriere plan pour la couleur
        return new Background(
                new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

    // Methode updateScore pour mettre a jour le score du joueur
    public void updateScore(int score) {
        // Cnvertis le score en String et le mets dans le label
        scoreLabel.setText(String.valueOf(score));
    }

    // Methode updateBestScore pour le meilleur score
    public void updateBestScore(int bestScore) {
        // Meme principe que la methode au dessus
        bestScoreLabel.setText(String.valueOf(bestScore));
    }

}