package com.csc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private boolean gameEnded;
    private boolean humanVsComputer;
    private Scanner scanner;
    private Random random;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        gameEnded = false;
        scanner = new Scanner(System.in);
        random = new Random();
        initializeBoard();
    }

    private void initializeBoard() {
        int counter = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (char) (counter++ + '0'); // Fill with '1' to '9' for easy move selection
            }
        }
    }

    public void playGame() {
        showMainMenu();

        while (!gameEnded) {
            displayBoard();
            if (humanVsComputer && currentPlayer == 'O') {
                makeRandomMove();
            } else {
                playerMove();
            }
            checkGameStatus();
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        showExitMenu();
    }

    private void showMainMenu() {
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("Please choose a game mode:");
        System.out.println("(1) Human vs. Human");
        System.out.println("(2) Human vs. Computer");

        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("That is not a valid choice!");
            System.out.println("(1) Human vs. Human");
            System.out.println("(2) Human vs. Computer");
            choice = scanner.nextInt();
        }
        humanVsComputer = (choice == 2);
        System.out.println("Let's begin!");
    }

    private void showExitMenu() {
        System.out.println("Would you like to play again?");
        System.out.println("(1) Yes");
        System.out.println("(2) No");

        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("That is not a valid choice!");
            System.out.println("(1) Yes");
            System.out.println("(2) No");
            choice = scanner.nextInt();
        }
        if (choice == 1) {
            resetGame();
            playGame();
        } else {
            System.out.println("Goodbye!");
        }
    }

    private void resetGame() {
        initializeBoard();
        currentPlayer = 'X';
        gameEnded = false;
    }

    private void displayBoard() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.print(" ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) System.out.print(" | ");
            }
            System.out.println();
            if (i < 2) System.out.println("-----------");
        }
        System.out.println();
    }

    private void playerMove() {
        System.out.println("Player " + currentPlayer + " - where would you like to move?");
        int move = scanner.nextInt();

        while (!isValidMove(move)) {
            System.out.println("Invalid move. Try again.");
            move = scanner.nextInt();
        }
        placeMove(move);
    }

    private void makeRandomMove() {
        List<Integer> availableMoves = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (isValidMove(i)) {
                availableMoves.add(i);
            }
        }
        int randomMove = availableMoves.get(random.nextInt(availableMoves.size()));
        System.out.println("The computer player moved in space " + randomMove + ".");
        placeMove(randomMove);
    }

    private boolean isValidMove(int move) {
        int row = (move - 1) / 3;
        int col = (move - 1) % 3;
        return board[row][col] != 'X' && board[row][col] != 'O';
    }

    private void placeMove(int move) {
        int row = (move - 1) / 3;
        int col = (move - 1) % 3;
        board[row][col] = currentPlayer;
    }

    private void checkGameStatus() {
        if (isWinner(currentPlayer)) {
            displayBoard();
            System.out.println("Player " + currentPlayer + " wins!");
            gameEnded = true;
        } else if (isBoardFull()) {
            displayBoard();
            System.out.println("It's a draw!");
            gameEnded = true;
        }
    }

    private boolean isWinner(char player) {
        // Check rows and columns for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        // Check diagonals for a win
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 'X' && board[i][j] != 'O') return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.playGame();
    }
}
