import java.util.Scanner;

public class TicTacToe {
    private static char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    private static char playerSymbol, aiSymbol;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Allow players to choose symbols
        System.out.println("Choose your symbol (X or O): ");
        playerSymbol = scanner.next().toUpperCase().charAt(0);
        aiSymbol = (playerSymbol == 'X') ? 'O' : 'X';

        boolean isPlayerTurn = true;

        while (true) {
            printBoard();
            if (isPlayerTurn) {
                playerMove(scanner);
            } else {
                aiMove();
            }

            if (checkWin(playerSymbol)) {
                printBoard();
                System.out.println("You win!");
                break;
            } else if (checkWin(aiSymbol)) {
                printBoard();
                System.out.println("AI wins!");
                break;
            } else if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }

            isPlayerTurn = !isPlayerTurn;
        }
        scanner.close();
    }

    private static void printBoard() {
        System.out.println("  1 2 3");
        for (int i = 0; i < board.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < board[i].length - 1) System.out.print("|");
            }
            System.out.println();
            if (i < board.length - 1) System.out.println("  -----");
        }
    }

    private static void playerMove(Scanner scanner) {
        int row, col;
        while (true) {
            try {
                System.out.println("Enter your move (row and column: 1-3): ");
                row = scanner.nextInt() - 1;
                col = scanner.nextInt() - 1;

                // Validate input range
                if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                    System.out.println("Invalid input. Please enter numbers between 1 and 3.");
                } else if (board[row][col] != ' ') {
                    System.out.println("That spot is already taken. Try again.");
                } else {
                    board[row][col] = playerSymbol;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter valid numbers for row and column.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void aiMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = aiSymbol;
                    return;
                }
            }
        }
    }

    private static boolean checkWin(char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) return true;
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) return true;
        }
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
               (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private static boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') return false;
            }
        }
        return true;
    }
}
