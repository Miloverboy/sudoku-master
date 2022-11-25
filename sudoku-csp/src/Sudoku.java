import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sudoku {
  private Field[][] board;    //Field[ROW][COLUMN]
  private ArrayList<Constraint> constraints;
  private ArrayList<Constraint> advancedConstraints;

  Sudoku(String filename) {
    this.board = readsudoku(filename);
    this.constraints = new ArrayList<Constraint>();
    this.advancedConstraints = new ArrayList<Constraint>();
  }

  @Override
  public String toString() {
    String output = "╔═══════╦═══════╦═══════╗\n";
		for(int i=0;i<9;i++){
      if(i == 3 || i == 6) {
		  	output += "╠═══════╬═══════╬═══════╣\n";
		  }
      output += "║ ";
		  for(int j=0;j<9;j++){
		   	if(j == 3 || j == 6) {
          output += "║ ";
		   	}
         output += board[i][j] + " ";
		  }
		  
      output += "║\n";
	  }
    output += "╚═══════╩═══════╩═══════╝\n";
    return output;
  }

  /**
	 * Reads sudoku from file
	 * @param filename
	 * @return 2d int array of the sudoku
	 */
	public static Field[][] readsudoku(String filename) {
		assert filename != null && filename != "" : "Invalid filename";
		String line = "";
		Field[][] grid = new Field[9][9];
		try {
		FileInputStream inputStream = new FileInputStream("sudoku-csp\\" + filename);
        Scanner scanner = new Scanner(inputStream);
        for(int i = 0; i < 9; i++) {
        	if(scanner.hasNext()) {
        		line = scanner.nextLine();
        		for(int j = 0; j < 9; j++) {
              int numValue = Character.getNumericValue(line.charAt(j));
              if(numValue == 0) {
                grid[i][j] = new Field();
              } else if (numValue != -1) {
                grid[i][j] = new Field(numValue);
        			}
        		}
        	}
        }
        scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("error opening file: "+filename);
		}
    addNeighbours(grid);
		return grid;
	}

  /**
   * Adds a list of neighbours to each field, i.e., arcs to be satisfied
   * @param grid
   */
  private static void addNeighbours(Field[][] grid) {
    for (int i = 0; i < 3; i++) {                 // i = row of 3x3 block we are considering.
      for (int j = 0; j < 3; j++){                // j = column of 3x3 block we are considering.
        for (int k = 0; k < 3; k++) {             // k = row of field within 3x3 block.
          for (int l = 0; l < 3; l++) {           // l = column of field within 3x3 block
            Field f = grid[3*i + k][3*j + l];     // f = the field to which we will add the neighbours.
            ArrayList<Field> neighbours = new ArrayList<Field>();

            for(int m = 0; m < 3; m++) {
              for (int n = 0; n < 3; n++) {
                Field g = grid[3*i + m][3*j + n]; // g = a neighbor in the same 3x3 block as f.
                neighbours.add(g);
                /*if (g != f) {
                  neighbours.add(g);
                }*/
              }
            }

            // add all neighbours from the same column:

            ArrayList<Field> columnNeighbours = new ArrayList<Field>();

            for(int m = 0; m < 3; m++) {
              for (int n = 0; n < 3; n++) {
                Field g = grid[3*m + n][3*j + l];
                /*if (m != i) {     // to prevent duplicates
                  neighbours.add(g);
                }*/
                columnNeighbours.add(g);
              }
            }

            // add all neighbours from the same row:

            ArrayList<Field> rowNeighbours = new ArrayList<Field>();

            for(int m = 0; m < 3; m++) {
              for (int n = 0; n < 3; n++) {
                Field g = grid[3*i + k][3*m + n];
                /*if (m != j) {     // to prevent duplicates
                  neighbours.add(g);
                }*/
                rowNeighbours.add(g);
              }
            }

            f.setNeighbours(neighbours);
            f.setColumnNeighbours(columnNeighbours);
            f.setRowNeighbours(rowNeighbours);
          }
        }
      }
    }

  }

  /**
   * Adds a list of row-neighbours to each field, i.e., arcs to be satisfied
   * @param grid
   */
  private static void addRowNeighbours(Field[][] grid) {
    

  }
  



  /**
	 * Generates fileformat output
	 */
	public String toFileString(){
    String output = "";
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        output += board[i][j].getValue();
      }
      output += "\n";
    }
    return output;
	}

  public Field[][] getBoard(){
    return board;
  }

  public void updateDomains() {
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        Field f = this.board[i][j];
        if (f.getValue() != 0) {
          f.updateNeighbourValues(f.getValue());
        }
      }
    }
  }

  public void setConstraints() {
    for(int i = 0; i < 9; i++) {  // add basic row- and column-constraints to list
      constraints.add(new NoDuplicatesC(board[i][0].getRowNeighbours()));
      constraints.add(new NoDuplicatesC(board[0][i].getColumnNeighbours()));
      constraints.add(new OneOfEachC(board[i][0].getRowNeighbours()));
      constraints.add(new OneOfEachC(board[0][i].getRowNeighbours()));
    }
    for(int i = 0; i < 3; i++) {
      for(int j = 0; j < 3; j++) {
        constraints.add(new NoDuplicatesC(board[i*3][j*3].getNeighbours()));
        constraints.add(new OneOfEachC(board[i*3][j*3].getNeighbours()));
      }
    }
  }

  public ArrayList<Constraint> getConstraints() {
    return this.constraints;
  }

  public ArrayList<Constraint> getAdvancedConstraints() {
    return this.advancedConstraints;
  }

  public boolean removeConstraint(Constraint c) {
    if (this.constraints.contains(c)) {
      this.constraints.remove(c);
      return true;
    }
    if (this.advancedConstraints.contains(c)) {
      this.constraints.remove(c);
      return true;
    }
    return false;
  }
}
