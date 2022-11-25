import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Game {
  private Sudoku sudoku;
  private String heuristic;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void setHeuristic(String heuristic) {
    this.heuristic = heuristic;
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

    this.sudoku.setConstraints();

    LinkedList<Constraint> PrioQ = new LinkedList<Constraint>();
    PrioQ.addAll(this.sudoku.getConstraints());
    if (this.heuristic == "MRV")
      PrioQ.sort( (a,b) -> a.lowestDomainSize() - b.lowestDomainSize());
    else if (this.heuristic == "Degree")
      PrioQ.sort( (a,b) -> a.maxDependentVariable(sudoku) - b.maxDependentVariable(sudoku));
    Constraint first = PrioQ.getFirst();
    while (PrioQ.size() > 0) {
      Constraint c = PrioQ.remove();
      boolean changed = c.adjustDomains();
      if (!c.holds()) 
        return false;
      if (!c.isComplete()) {
        PrioQ.addLast(c);
      }
      if (c == first) {

        this.showSudoku();
      }
    }

    return true;
/*
    for(int i = 0; i < 100; i++) {
      for (Constraint c: this.sudoku.getConstraints()) {
        if(c.adjustDomains()) {
          if(c.isComplete()) {
            if(!this.sudoku.removeConstraint(c)) {
              System.out.println("E: Constraint didn't exist");
            }
          }
        }
      }
      if (true) {
        for (Constraint c: this.sudoku.getAdvancedConstraints()) {
          if(c.adjustDomains()) {
            if(c.isComplete()) {
              if(!this.sudoku.removeConstraint(c)) {
                System.out.println("E: Constraint didn't exist");
              }
            }
          }
        }
      }
    }

    return true; */
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
