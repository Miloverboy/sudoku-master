interface Constraint {

    /**
   * Checks if all members that belong to this constraint have a value
   */
    public boolean isComplete();
    
    /**
   * Checks if this constraint is not violated
   */
    public boolean holds();

    /**
   * Adjusts all domains of all members of this constraint according to the constraint itself
   */
    public boolean adjustDomains();

    public int lowestDomainSize();
}
