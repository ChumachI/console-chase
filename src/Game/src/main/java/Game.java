

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private final Args argsObj;
    private final FileProperties properties;
    private String[][] matrix;
    private final String[][] checkedMatrix;
    private final ChaseLogic chaseLogic;
    Scanner scanner;
    private int playerX;
    private int playerY;
    private boolean youLose;
    private boolean youWin;
    private FieldPrinter printer;

    public Game(Args argsObj, FileProperties properties) {
        this.argsObj = argsObj;
        this.properties = properties;
        matrix = new String[argsObj.getSize()][argsObj.getSize()];
        checkedMatrix = new String[argsObj.getSize()][argsObj.getSize()];
        youLose = false;
        youWin = false;
        scanner = new Scanner(System.in);
        chaseLogic = new ChaseLogic();
        printer = new Printer();
    }

    public void start() {
        checkParamsAndArgs();
        generateMap();
        while (!youWin && !youLose){
            printer.printField(matrix, properties);
            playerMove();
            if(youWin || youLose){
                break;
            }
            chase();
        }
        if(youLose){
            System.out.println("You Lose =(");
        }
        if(youWin){
            System.out.println("You Win!");
        }
    }

    private void chase() {
        ChaseResult result = chaseLogic.chase(matrix, properties, playerY, playerX, printer);
        youLose = result.getIsCaught();
        matrix = result.getMatrix();
    }


    private void playerMove() {
        if(properties.getProfile().equals("dev")){
            System.out.println("Ваш ход");
        }
        String inputChar;
        try {
            inputChar = scanner.next();
        } catch (Exception e) {
            return;
        }

        switch (inputChar.charAt(0)) {
            case 'w':
            case 'W':
                if(isMovePossible(playerX, playerY - 1)){
                    this.playerY = playerY - 1;
                }
                break;
            case 'a':
            case 'A':
                if(isMovePossible(playerX - 1, playerY)){
                    this.playerX = playerX - 1;
                }
                break;
            case 's':
            case 'S':
                if(isMovePossible(playerX, playerY + 1)){
                    this.playerY = playerY + 1;
                }
                break;
            case 'd':
            case 'D':
                if(isMovePossible(playerX + 1, playerY)){
                    this.playerX = playerX + 1;
                }
                break;
            case '9':
                youLose = true;
                break;
            default:
                break;
        }
        if(youLose){
            printer.printField(matrix, properties);
        }
    }

    private boolean isMovePossible(int playerX, int playerY) {
        if(playerX < 0 || playerX >= matrix[0].length) {
            return false;
        }
        if(playerY < 0 || playerY >= matrix.length){
            return false;
        }
        if(matrix[playerY][playerX].equals(properties.getWallChar())){
            return false;
        }
        if(matrix[playerY][playerX].equals(properties.getGoalChar())){
            youWin = true;
        } else if(matrix[playerY][playerX].equals(properties.getEnemyChar())){
            youLose = true;
        } else {
            matrix[playerY][playerX] = properties.getPlayerChar();
        }
        matrix[this.playerY][this.playerX] = properties.getEmptyChar();
        return true;

    }

    private void checkParamsAndArgs() {
        int size = argsObj.getSize();
        if (argsObj.getWallsCount() + argsObj.getEnemiesCount() > size * size / 2 - size) {
            throw new IllegalParametersException("Map size is to small for given num of elements");
        }
    }

    private void generateMap() {
        boolean isPlayerSet = false;
        do{
            clearMap();
            placeEnemies();
            placeWalls();
            placeGoal();
            isPlayerSet = placePlayer();
        }while (!isPlayerSet);
    }

    private boolean placePlayer() {
        int counter = 10;
        while (counter > 0) {
            int x = (int) (Math.random() * argsObj.getSize());
            int y = (int) (Math.random() * argsObj.getSize());
            if (matrix[y][x].equals(properties.getEmptyChar())) {
                if (checkAccessToGoal(x, y)) {
                    matrix[y][x] = properties.getPlayerChar();
                    playerX = x;
                    playerY = y;
                    break;
                } else {
                    counter--;
                }
            }
        }
        return counter != 0;
    }

    private void placeGoal() {
        while (true) {
            int x = (int) (Math.random() * argsObj.getSize());
            int y = (int) (Math.random() * argsObj.getSize());
            if (matrix[y][x].equals(properties.getEmptyChar())) {
                matrix[y][x] = properties.getGoalChar();
                break;
            }
        }
    }

    private void placeWalls() {
        for (int i = 0; i < argsObj.getWallsCount();) {
            int x = (int) (Math.random() * argsObj.getSize());
            int y = (int) (Math.random() * argsObj.getSize());
            if (matrix[y][x].equals(properties.getEmptyChar())) {
                matrix[y][x] = properties.getWallChar();
                i++;
            }

        }
    }

    private void placeEnemies() {
        for (int i = 0; i < argsObj.getEnemiesCount(); i++) {
            int x = (int) (Math.random() * argsObj.getSize());
            int y = (int) (Math.random() * argsObj.getSize());
            matrix[y][x] = properties.getEnemyChar();
        }
    }

    private void clearMap() {
        for (String[] strings : matrix) {
            Arrays.fill(strings, properties.getEmptyChar());
        }
    }

    private boolean checkAccessToGoal(int x, int y) {
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;

        try {
            checkedMatrix[y][x] = "checked";
            if (matrix[y][x].equals(properties.getGoalChar())) {
                return true;
            } else if (matrix[y][x].equals(properties.getWallChar())) {
                return false;
            } else if (matrix[y][x].equals(properties.getEnemyChar())) {
                return false;
            }
            if (y != 0 && checkedMatrix[y - 1][x] == null) {
                up = checkAccessToGoal(x, y - 1);
            }
            if (y != matrix.length - 1 && checkedMatrix[y + 1][x] == null) {
                down = checkAccessToGoal(x, y + 1);
            }
            if (x != 0 && checkedMatrix[y][x - 1] == null) {
                left = checkAccessToGoal(x - 1, y);
            }
            if (x != matrix.length - 1 && checkedMatrix[y][x + 1] == null) {
                right = checkAccessToGoal(x + 1, y);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        return right || left || up || down;
    }


}
