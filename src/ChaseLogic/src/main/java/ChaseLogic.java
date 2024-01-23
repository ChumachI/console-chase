import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class ChaseLogic {
    public ChaseResult chase(String[][] matrix, TransferData properties, int playerY, int playerX, FieldPrinter printer)  {
        String[][] newMap = new String[matrix.length][matrix.length];
        int[][] distMap = buildDistMap(matrix, properties, playerX, playerY);
        boolean isCought = false;
        for(int i = 0; i < newMap.length; i++){
            System.arraycopy(matrix[i], 0, newMap[i], 0, newMap.length);
        }
        for (int y = 0; y < matrix.length && !isCought; y++) {
            for (int x = 0; x < matrix[y].length && !isCought; x++) {
                if(matrix[y][x].equals(properties.getEnemyChar())){
                    Point step = findShortestWay(distMap, newMap, x, y, properties);
                    newMap[y][x] = properties.getEmptyChar();
                    newMap[step.y][step.x] = properties.getEnemyChar();
                    if(distMap[step.y][step.x] == 0){
                        isCought = true;
                    }
                    if(properties.getProfile().equals("dev")){
                        Scanner scanner = new Scanner(System.in);
                        String input;
                        printer.printField(newMap,properties);
                        System.out.println("Подтвердите ход противника");
                        do{
                            input = scanner.next();
                            if(input.equals("9")){
                                isCought = true;
                            }
                        }while (!input.equals("8") && !isCought);
                    }
                }
            }
        }
        return new ChaseResult(newMap, isCought);
    }

    private Point findShortestWay(int[][] distMap, String[][] matrix, int x, int y, TransferData properties)  {
        Point result = new Point(x, y);
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if(newY < 0 || newX < 0 || newX == distMap.length || newY == distMap.length){
                continue;
            }
            if(distMap[newY][newX] == -1){
                continue;
            }
            if(matrix[newY][newX].equals(properties.getEnemyChar())){
                continue;
            }
            if(minValue > distMap[newY][newX]){
                result.x = newX;
                result.y = newY;
                minValue = distMap[newY][newX];
            }
        }
        return result;
    }

    private int[][] buildDistMap(String[][] matrix, TransferData properties, int playerX, int playerY) {
        int[][] distMap = new int[matrix.length][matrix.length];
        Point destination = new Point(playerX,playerY);
        for (int i = 0; i < distMap.length; i++) {
            for (int j = 0; j < distMap.length; j++) {
                distMap[i][j] = -1;
            }
        }
        Queue<Point> queue = new ArrayDeque<>();
        queue.add(destination);
        distMap[destination.y][destination.x] = 0;

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (isValidCoordinate(newX, newY, matrix, properties) && distMap[newY][newX] == -1) {
                    queue.add(new Point(newX, newY));
                    distMap[newY][newX] = distMap[current.y][current.x] + 1;
                }
            }
        }
        return distMap;
    }

    private boolean isValidCoordinate(int x, int y, String[][] matrix, TransferData properties) {
        boolean a = x >= 0 && x < matrix.length;
        boolean b = y >= 0 && y < matrix.length;
        boolean c = false;
        if(a && b){
             c = matrix[y][x].equals(properties.getEmptyChar());
        }
        return c ;
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
