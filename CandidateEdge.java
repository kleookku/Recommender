package sol;

import src.IAttributeDataset;
import src.INode;

import java.util.LinkedList;

public class CandidateEdge {
    public Object edgeVal; // attribute value name (e.g. color is "green")
    public INode node; // the node that this edge directs to in the tree
    IAttributeDataset candidates;

    /**
     * constructor for CandidateEdge class
     * @param attributeVal - attribute value that is the name of this edge
     * @param node - node that this edge connects to
     * @param candidates - list of candidates that fall under this edge
     */
    public CandidateEdge(Object attributeVal, INode node, IAttributeDataset candidates){
        this.edgeVal = attributeVal;
        this.node = node;
        this.candidates = candidates;
    }
}
