package srcjava.Console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Board board;
    private List<Player> players;
    private Deck deck;
    private List<Card> exposedCards;

    public Game(int boardSize, List<String> playerNames, List<String> playerColors) {
        board = new Board(boardSize);
        players = new ArrayList<>();
        deck = new Deck();
        exposedCards = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Card card = deck.drawCard();
            exposedCards.add(card);
        }

        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new Player(playerNames.get(i), playerColors.get(i));
            players.add(player);
        }
    }


    public void initialize() {
        deck.shuffle();
        drawInitialExposedCards();
        displayInitialSetup();
    }

    private void drawInitialExposedCards() {
        for (int i = 0; i < 12; i++) {
            Card card = deck.drawCard();
            if (card != null) {
                exposedCards.add(card);
            }
        }
    }

    private void displayInitialSetup() {
        System.out.println("Initial game setup:");
        for (int i = 0; i < 12; i++) {
            System.out.println("Exposed Card " + (i + 1) + ": " + exposedCards.get(i).toString());
        }
    }

    public void play() {
        initialize();

        while (!isGameOver()) {
            for (Player player : players) {
                player.takeTurn(board);
                if (isGameOver()) {
                    displayFinalScores();
                    return;
                }
            }
        }
    }

    public boolean isGameOver() {
        return deck.isEmpty() || exposedCards.isEmpty();
    }

    public void displayFinalScores() {
        List<Player> winners = determineWinners();

        System.out.println("Final scores:");
        for (Player player : players) {
            System.out.println(player.getName() + " - Score: " + player.getTotalScore());
        }

        if (winners.size() == 1) {
            System.out.println("Winner: " + winners.get(0).getName());
        } else {
            System.out.println("It's a tie between:");
            for (Player winner : winners) {
                System.out.println(winner.getName());
            }
        }
    }

    private List<Player> determineWinners() {
        List<Player> winners = new ArrayList<>();
        int highestScore = -1;

        for (Player player : players) {
            if (player.getTotalScore() > highestScore) {
                highestScore = player.getTotalScore();
                winners.clear();
                winners.add(player);
            } else if (player.getTotalScore() == highestScore) {
                winners.add(player);
            }
        }

        return winners;
    }

}
