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

    @Override
    public boolean holds() {
        // TODO Auto-generated method stub
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
    
}
