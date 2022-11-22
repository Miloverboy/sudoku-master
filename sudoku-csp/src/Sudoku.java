import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sudoku {
  private Field[][] board;    //Field[ROW][COLUMN]

  Sudoku(String filename) {
    this.board = readsudoku(filename);
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
                if (g != f) {
                  neighbours.add(g);
                }
              }
            }

            // add all neighbours from the same collumn:

            for(int m = 0; m < 3; m++) {
              if (m != i) {
                for (int n = 0; n < 3; n++) {
                  Field g = grid[3*m + n][3*j + l];
                  neighbours.add(g);
                }
              }
            }

            // add all neighbours from the same row:

            for(int m = 0; m < 3; m++) {
              if (m != j) {
                for (int n = 0; n < 3; n++) {
                  Field g = grid[3*i + k][3*m + n];
                  neighbours.add(g);
                }
              }
            }

            f.setNeighbours(neighbours);
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
}
