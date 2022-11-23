import java.util.ArrayList;

public class OneOfEachC implements Constraint {
    
    private ArrayList<Field> members;
    private boolean complete;

    public boolean isComplete() {
        return this.complete;
    }

    public boolean holds() {

        members.sort( (a,b) -> a.getValue() - b.getValue());    // sort all members from small to big based on value.

        for (int i = 0; i < 9; i++) {
            if (members.get(i).getValue() != i + 1) {           // check if all values are present
                return false;
            }
        }
        this.complete = true;
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
        return changedSomething;
    }
}
