import java.util.ArrayList;
import java.util.PriorityQueue;

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

    ArrayList<Arc> arcs = this.sudoku.setArcs();

    PriorityQueue<Arc> prioQ = new PriorityQueue<Arc>(arcs.size(), (a,b) -> a.getDomainSize() - b.getDomainSize());
    prioQ.addAll(arcs);

    while (prioQ.size() > 0) {
      Arc arc = prioQ.remove();
      boolean hasChanged = arc.testValues();
      if (arc.getMainField().getDomainSize() == 0) {
        return false;
      }
      if (hasChanged) {
        for (Field neighbour : arc.getMainField().getNeighbours()) {
          for(Arc arc2 : neighbour.getArcs()) {
            if (arc2.getSecondField().equals(arc.getMainField())) {
              if (!prioQ.contains(arc2)) {
                prioQ.add(arc2);
              }
            }
          }
        }
      }
    }

  

    

    return true;
  }

  /**
   * Checks the validity of a sudoku solution
   * 
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    // TODO: implement validSolution function

    // check row for duplicates & check if all values are present:

    for(int i=0; i < 9; i++) {
      for(int j=0; j < 9; j++) {

        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int k = 1; k < 10; k++)
          values.add(k);
        
        if(!values.remove(Integer.valueOf(this.sudoku.getBoard()[i][j].getValue()))) 
          return false;
        
      }
    }

    // check column for duplicates & check if all values are present:

    for(int i=0; i < 9; i++) {
      for(int j=0; j < 9; j++) {

        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int k = 1; k < 10; k++)
          values.add(k);
        
        if(!values.remove(Integer.valueOf(this.sudoku.getBoard()[j][i].getValue()))) 
          return false;
        
      }
    }

    // check blocks for duplicates & check if all values are present:

    for(int i=0; i < 3; i++) {
      for(int j=0; j < 3; j++) {
        for(int l=0; l < 3; l++) {
          for(int m=0; m < 3; m++) {

            ArrayList<Integer> values = new ArrayList<Integer>();
            for (int k = 1; k < 10; k++)
              values.add(k);
            
            if(!values.remove(Integer.valueOf(this.sudoku.getBoard()[j*3 + l][i*3 + m].getValue()))) 
              return false;
          }
        }
      }
    }

    return true;
  }
}
