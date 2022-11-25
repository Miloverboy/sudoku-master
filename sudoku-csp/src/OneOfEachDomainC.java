import java.util.List;

public class OneOfEachDomainC implements Constraint{

    private List<Field> members;
    private boolean complete;

    public OneOfEachDomainC(List<Field> members) {
        this.members = members;
        complete = false;  
    }

    @Override
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

    @Override
    public boolean adjustDomains() {
        boolean changedSomething = false;
        for(int i = 1; i < 10; i++) {
            Field lastMemberWithI = null;
            int iCount = 0;
            for (Field member: members) {
                if (member.getDomain().contains(Integer.valueOf(i))) {
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
        return changedSomething;
    }

    public int lowestDomainSize() {
        // TODO Auto-generated method stub
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
