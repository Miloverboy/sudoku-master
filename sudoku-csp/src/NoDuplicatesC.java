import java.util.ArrayList;
import java.util.List;

public class NoDuplicatesC implements Constraint {
    
    private List<Field> members;
    private boolean complete;

    public NoDuplicatesC(List<Field> members) {
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
            if(value == lastValue && value != 0) {
                return false;
            }
            lastValue = value;
            if (value == 0) {
                zeroValuePresent = true;
            }
        }
        this.complete = !zeroValuePresent;
        return true;
    }

    public boolean adjustDomains() {
        boolean changedSomething = false;
        boolean zeroValuePresent = false;
        for (Field member: members) {
            if (member.getValue() != 0) {
                for (Field f: members) {
                    if (!f.equals(member)) {
                        changedSomething = f.removeFromDomain(member.getValue());
                    }
                }
            } else {
                zeroValuePresent = true;
            }
        }
        this.complete = !zeroValuePresent;
        return changedSomething;
    }

    public int lowestDomainSize() {
        int lowestDomainSize = Integer.MAX_VALUE;
        for (Field member: members) {
            if (member.getDomainSize() > lowestDomainSize && member.getValue() == 0)
                lowestDomainSize = member.getDomainSize();
        }
        return lowestDomainSize;
    }



}
