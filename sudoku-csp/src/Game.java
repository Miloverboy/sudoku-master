public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void showSudoku() {
    System.out.println(sudoku);
    /*
    for (Field f: this.sudoku.getBoard()[1][7].getNeighbours()) {
      System.out.println(f.getValue());
    }
    System.out.println(this.sudoku.getBoard()[6][0].getValue()); */
  }

  /**
   * Implementation of the AC-3 algorithm
   * 
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {
    // TODO: implement AC-3

    this.sudoku.updateDomains();

    /* 
    sudoku.getBoard()[0][5].updateNeighbourValues(6);
    sudoku.getBoard()[2][8].updateNeighbourValues(6);
    sudoku.getBoard()[8][3].updateNeighbourValues(6);
    System.out.println(sudoku.getBoard()[0][1].getDomain());
    */

    return true;
  }

  /**
   * Checks the validity of a sudoku solution
   * 
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    // TODO: implement validSolution function
    return true;
  }
}
