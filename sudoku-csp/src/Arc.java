public class Arc {
    
    private Field f1;
    private Field f2;

    public Arc(Field f1, Field f2){
        this.f1 = f1;
        this.f2 = f2;
    }

    public int getDomainSize() {
        return f1.getDomainSize();
    }

    public boolean testValues() {
        /*
        Field arcSecond = null;
        if (arc == arc1) 
            arcSecond = arc2;
        else if (arc == arc2)
            arcSecond = arc1;
        else
            System.out.println("E: field not in arc!");

            */

        Boolean changedSomething = false;
        
        for(int value : f1.getDomain()) {
            if(f2.getDomain().contains(Integer.valueOf(value))) {
                if (f2.getDomain().size() == 2) {
                    f1.removeFromDomain(value);
                    changedSomething = true;
                }
            }
        }
        return changedSomething;
    }

    public String toString(){
        return String.valueOf(f1.getValue()) + " -> " + String.valueOf(f2.getValue());
    }

    public Field getMainField() {
        return f1;
    }

    public Field getSecondField() {
        return f2;
    }
}
