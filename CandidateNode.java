package sol;

import src.IAttributeDataset;
import src.IAttributeDatum;
import src.INode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class CandidateNode implements INode {
    public String attribute; // attribute name
    public LinkedList<CandidateEdge> edges; // different attribute options
    public IAttributeDataset<Candidate> dataset; // subset of data falls under the node

    /**
     * constructor for the CandidateNode class
     * @param attr - attribute name
     * @param edges - edges that extend from the node
     * @param dataset - subset that falls under this node (with corresponding attribute values)
     */
    public CandidateNode(String attr, LinkedList<CandidateEdge> edges, IAttributeDataset<Candidate> dataset){
        this.attribute = attr;
        this.edges = edges;
        this.dataset = dataset;
    }

    @Override
    public CandidateEdge lookupDecision(IAttributeDatum attrVals) {
        CandidateEdge edge = null;
        Object val = attrVals.getValueOf(attribute); // get the value for attribute being checked
        for (int i = 0; i < this.edges.size(); i++) { // find which edge this value corresponds to
            if (this.edges.get(i).edgeVal.equals(val)) { // if the edge is found
                edge = this.edges.get(i); // set edge to this edge
            }
        }
        if(edge == null){ // if attribute value doesn't match any of the edges
            // get the subset
            ListObjsData<Candidate> data = (ListObjsData<Candidate>) ((CandidateNode) attrVals).dataset;
            ArrayList<CandidateEdge> edges = new ArrayList<CandidateEdge>();
            ArrayList<Integer> edgesCount = new ArrayList<Integer>();
            // iterate through the subset to find the most common edge
            for(int i=0; i<data.size(); i++){
                CandidateEdge thisEdge = (CandidateEdge) data.allData.get(i).getValueOf(attribute);
                if(edges.contains(thisEdge)){
                    // if edge is already in the list, add 1 to its associated integer in the other list
                    int index = edges.indexOf(thisEdge);
                    int thisCount = edgesCount.get(index);
                    edgesCount.set(index, thisCount + 1);
                }
                else { // if edge is not in the list, add it to the edge list and add 1 to the integer list
                    edges.add(thisEdge);
                    edgesCount.add(new Integer(1));
                }
            }
            // get the edge with the max count
            Integer max = 0;
            for(Integer i : edgesCount){
                if(i > max) max = i;
            }
            edge = edges.get(edgesCount.indexOf(max));
        }
        return edge;
    }


    @Override
    public void printNode(String leadspace) {
        System.out.println("Attribute: " + attribute);
        System.out.println("Edges: ");
        for(int i=0; i<edges.size(); i++){
            System.out.println("   - " + edges.get(i));
        }
        System.out.println("Dataset: ");
        System.out.println("   - " + dataset);
    }
}
