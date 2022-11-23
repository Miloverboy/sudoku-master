import java.util.ArrayList;
import java.util.List;

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
    //print domains
    Field[][] fields = sudoku.getBoard();
    for(Field[] f : fields){
      for(Field field :f){
        
          System.out.println(field.getDomain().toString());
      }
     }

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
    
    Field[][] fields = sudoku.getBoard();
    for(Field[] f : fields){
      for(Field field :f){
        List<Field> neigbs = field.getNeighbours();
          for(Field n: neigbs){
            if(n.getValue() == field.getValue()){
              return false;
            }

          }
      }
     }
    return true;
  }

  public boolean checkIfTwoEqualDomains(List<Integer> domain1, List<Integer> domain2){
      return domain1.equals(domain2);
 }
}


 