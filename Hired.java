package sol;

import src.IAttributeDatum;
import src.INode;

public class Hired implements INode{

    Object result;

    /**
     * constructor for Hired object
     * @param result - leaf of the tree; bottom decision
     */
    public Hired(Object result){
        this.result = result;
    }

    @Override
    public CandidateEdge lookupDecision(IAttributeDatum attrVals) {
        return new CandidateEdge(result, null, null);
    }

    @Override
    public void printNode(String leadspace) {
        System.out.println("Result: " + result);
    }
}
