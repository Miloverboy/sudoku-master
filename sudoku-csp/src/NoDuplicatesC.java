import java.util.ArrayList;

public class NoDuplicatesC implements Constraint {
    
    private ArrayList<Field> members;
    private boolean complete;

    public NoDuplicatesC(ArrayList<Field> members) {
        this.members = members;
        complete = false;
        this.holds();           // maybe the constraint is finished from the start.
    }

    public boolean isComplete() {
        return this.complete;
    }

    public boolean holds() {
        
        members.sort( (a,b) -> a.getValue() - b.getValue());
        int lastValue = -1;
        boolean zeroValuePresent = false;

        for(int i = 0; i < 9; i++) {
            int value = members.get(i).getValue();
            if(value == lastValue) {
                return false;
            }
            lastValue = value;
            if (value == 0) {
                zeroValuePresent = true;
            }
        }
        if (!zeroValuePresent) {
            this.complete = true;
        }
        return true;
    }

    public boolean adjustDomains() {
        boolean changedSomething = false;
        for (Field member: members) {
            if (member.getValue() != 0) {
                for (Field f: members) {
                    if (!f.equals(member)) {
                        changedSomething = f.removeFromDomain(member.getValue());
                    }
                }
            }
        }
        return changedSomething;
    }



}
