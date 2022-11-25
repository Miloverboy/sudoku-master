import java.util.ArrayList;
import java.util.List;

public class OneOfEachC implements Constraint {
    
    private List<Field> members;
    private boolean complete;

    public OneOfEachC(List<Field> members) {
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

        for(int i = 0; i < 9; i++) {
            int value = members.get(i).getValue();
            if(value == lastValue && value != 0) {
                return false;
            }
            lastValue = value;

        }
        return true;
    }

    public boolean adjustDomains() {
        boolean changedSomething = false;
        for(int i = 1; i < 10; i++) {
            Field lastMemberWithI = null;
            int iCount = 0;
            for (Field member: members) {
                if (member.getValue() == i) {
                    lastMemberWithI = member;
                    iCount++;
                }
                if (iCount > 1) {
                    break;
                }
            }
            if (iCount == 1) {
                lastMemberWithI.setValue(i);
            }
            
        }
        boolean complete = true;
        if (changedSomething) {
            for (Field member : members) {
                if (member.getValue() == 0) {
                    complete = false;
                }
            }
        }

        this.complete = complete;

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

    public int maxDependentVariable(Sudoku sudoku) {
        int maxConstraints = Integer.MIN_VALUE;
        int constraints;

        for (Field member : members) {
            constraints = 0;
            for (Constraint c : sudoku.getConstraints()) {
                if (c.getMembers().contains(member) && !c.isComplete())
                    constraints++;
            }
            if (constraints > maxConstraints)
                maxConstraints = constraints;
        }
        return maxConstraints;
    }

    
    public List<Field> getMembers() {
        return this.members;
    }
}
