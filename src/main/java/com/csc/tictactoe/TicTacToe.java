package com.csc.tictactoe;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static final char[][] board = { // Made the board field final
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };
    private static char player1Mark;
    private static char player2Mark;
    private static char currentPlayer;
    private static boolean aiEnabled = false;

    public static void main(String[] args) {
        System.out.println("Welcome to Tic-Tac-Toe!");
        setupGame();
        playGame();
    }

    private static void setupGame() {
        Scanner scanner = new Scanner(System.in);

        // Choose custom marks for players
        player1Mark = chooseMark(scanner, "Player 1");
        System.out.println("Do you want to play against AI? (yes/no)");
        String aiChoice = scanner.nextLine();
        aiEnabled = aiChoice.equalsIgnoreCase("yes");

        if (aiEnabled) {
            player2Mark = chooseMark(scanner, "AI");
        } else {
            player2Mark = chooseMark(scanner, "Player 2");
        }

        currentPlayer = player1Mark;
    }

    private static char chooseMark(Scanner scanner, String playerName) {
        char mark;
        while (true) {
            System.out.println(playerName + ", choose your custom mark (1 character): ");
            String input = scanner.nextLine();
            if (input.length() == 1 && !Character.isWhitespace(input.charAt(0))) {
                mark = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid mark! Please choose a single non-whitespace character.");
            }
        }
        return mark;
    }

    private static void playGame() {
        boolean gameRunning = true;
        while (gameRunning) {
            printBoard();
            if (aiEnabled && currentPlayer == player2Mark) {
                aiMove();
            } else {
                playerMove();
            }
            if (checkWin()) {
                printBoard();
                System.out.println("Player " + (currentPlayer == player1Mark ? "1" : "2") + " wins!");
                gameRunning = false;
            } else if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                gameRunning = false;
            } else {
                switchPlayer();
            }
        }
        askReplay();
    }

    private static void printBoard() {
        System.out.println("  0   1   2");
        for (int i = 0; i < 3; i++) {
            System.out.println(i + " " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) {
                System.out.println(" ---+---+---");
            }
        }
    }

    private static void playerMove() {
    Scanner scanner = new Scanner(System.in);
    int row, col;

    while (true) {
        try {
            System.out.println("Player " + (currentPlayer == player1Mark ? "1" : "2") + ", enter your move (row and column): ");
            
            // Read row and column as integers
            row = scanner.nextInt();
            col = scanner.nextInt();

            // Check if the move is within bounds and on an empty cell
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        } catch (InputMismatchException e) {
            // Handle non-integer input
            System.out.println("Invalid input. Please enter numbers for row and column.");
            scanner.next(); // Clear the invalid input
        }
    }
}

    private static void aiMove() {
        Random random = new Random();
        int row, col;
        while (true) {
            row = random.nextInt(3);
            col = random.nextInt(3);
            if (board[row][col] == ' ') {
                board[row][col] = player2Mark;
                System.out.println("AI played at position (" + row + ", " + col + ")");
                break;
            }
        }
    }

    private static boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
            return true;
        }
        return false;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == player1Mark) ? player2Mark : player1Mark;
    }

    private static void askReplay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play again? (yes/no)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            resetBoard();
            playGame();
        } else {
            System.out.println("Thanks for playing Tic-Tac-Toe!");
        }
    }

    private static void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        currentPlayer = player1Mark;
    }
}
