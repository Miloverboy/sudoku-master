public class App {
    public static void main(String[] args) throws Exception {
        start("Sudoku5.txt");
    }

    /**
     * Start AC-3 using the sudoku from the given filepath, and reports whether the sudoku could be solved or not, and how many steps the algorithm performed
     * 
     * @param filePath
     */
    public static void start(String filePath){
        Game game1 = new Game(new Sudoku(filePath));
        game1.showSudoku();
        game1.setHeuristic("MRV");
        //game1.setHeuristic("Degree");

        final long startTime = System.currentTimeMillis();

        if (game1.solve() && game1.validSolution()){
            System.out.println("Solved!");
        }
        else{
            System.out.println("Could not solve this sudoku :(");
        }

        final long endTime = System.currentTimeMillis();


        game1.showSudoku();

        System.out.println("\n Total execution time: " + (endTime - startTime));
    }
}
