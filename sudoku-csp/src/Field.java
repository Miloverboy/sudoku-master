import java.util.ArrayList;
import java.util.List;

public class Field {
  private int value = 0;
  private List<Integer> domain;
  private List<Field> neighbours; //A list of all fields that this field is constrained by
  private List<Field> rowNeighbours;
  private List<Field> columnNeighbours;

  /*
   * ==============
   *  CONSTRUCTORS
   * ==============
   */

  // Constructor in case the field is unknown
  Field() {
    this.domain = new ArrayList<>(9);
    for (int i = 1; i < 10; i++)
      this.domain.add(i);
  }

  // Constructor in case the field is known, i.e., it contains a value
  Field(int initValue) {
    this.value = initValue;
    this.domain = new ArrayList<>();
  }

  /*
   * =================
   *  VALUE FUNCTIONS
   * =================
   */
  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void updateNeighbourValues(int value) {
    for(Field f: this.neighbours) {
      f.removeFromDomain(value);
    }
  }

  /*
   * =====================
   *  NEIGHBOUR FUNCTIONS
   * =====================
   */
  public void setNeighbours(List<Field> neighbours) {
    this.neighbours = neighbours;
  }

  public void setRowNeighbours(List<Field> rowNeighbours) {
    this.rowNeighbours = rowNeighbours;
  }

  public void setColumnNeighbours(List<Field> columnNeighbours) {
    this.columnNeighbours = columnNeighbours;
  }

  public List<Field> getNeighbours() {
    return neighbours;
  }

  public List<Field> getRowNeighbours() {
    return rowNeighbours;
  }

  public List<Field> getColumnNeighbours() {
    return columnNeighbours;
  }

  public List<Field> getOtherNeighbours(Field b) {
    List<Field> newNeighbours = new ArrayList<>(neighbours);
    newNeighbours.remove(b);
    return newNeighbours;
  }

  /*
   * ==================
   *  DOMAIN FUNCTIONS
   * ==================
   */

  public List<Integer> getDomain() {
    return domain;
  }

  public int getDomainSize() {
    return domain.size();
  }

   /**
   * Removes the given value from the domain, and possibly assigns the last value to the field.
   * 
   * @param value
   * @return true if the value was removed
   */
  public boolean removeFromDomain(int value) {
    boolean b = this.domain.remove(Integer.valueOf(value));

    // If there is only one value left in the domain, sets the value of the field to the last domain value.
    if (domain.size() == 1) {
      setValue(domain.get(0));
    }
    
    return b;
  }

  /*
   * ================
   *  MISC FUNCTIONS
   * ================
   */

  @Override
  public String toString() {
    return (value==0)? "." : String.valueOf(value);
  }

public List<Field> getBlockNeighbours() {
    return null;
}
}
