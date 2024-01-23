public class ChaseResult {
    private final String[][] matrix;
    private final boolean isCaught;
    ChaseResult(String[][] matrix, boolean isCaught){
        this.isCaught = isCaught;
        this.matrix = matrix;
    }

    public String[][] getMatrix() {
        return matrix;
    }
    public boolean getIsCaught(){
        return isCaught;
    }
}
