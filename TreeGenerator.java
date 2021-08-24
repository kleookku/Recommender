package sol;

import src.IAttributeDataset;
import src.IAttributeDatum;
import src.IGenerator;
import src.INode;

import javax.management.relation.RelationNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
 * Class for creating and interacting with a decision tree given a dataset.
 *
 * T is the type of object that we are trying to classify.
 * (like src.Vegetable)
 */
public class TreeGenerator<T extends IAttributeDatum> implements IGenerator {

    public ListObjsData<T> initTrainingData; // ListObjsData - has allData and attributes as variables
    public LinkedList<String> attributes;
    public CandidateNode firstNode;
    public String targetAttr;

    /**
     * Constructor for this class.
     *
     * @param initTrainingData - IAttributeDataset of the data table
     */

    public TreeGenerator(IAttributeDataset<T> initTrainingData) {
        this.initTrainingData = (ListObjsData<T>) initTrainingData;
        this.attributes = ((ListObjsData<T>) initTrainingData).attributes;
    }

    /**
     * pick a random attribute from the list
     * once it has been chosen, remove it from the list
     *
     * @return random attribute name
     */
    public String pickRanAttr(){
        attributes.remove(targetAttr);
        String ranAttr = initTrainingData.attributes.get((int) (Math.random()*attributes.size()));
        //attributes.remove(ranAttr);
        //find random attribute
        while(!attributes.contains(ranAttr)){ // if attribute doesn't contain this attribute, we've already checked it,
            // so we don't need to find another one
            if(attributes.size() == 0) { // if we've checked all the attributes (list is empty)
                return null;
            }
            ranAttr = initTrainingData.attributes.get((int) (Math.random()*attributes.size())); // set it to a new one
            attributes.remove(ranAttr); // remove checked attribute from attribute list
        }
        return ranAttr;
    }

    /**
     * recursive method for making the tree
     * passes in an attribute name and data set and returns a node
     * the node that is returned is attached tot he edge that is made
     *
     * @param attr - random attribute name
     * @param data - dataset/subset
     * @return a node that is attached to an edge
     */
    public INode findEdges(String attr, IAttributeDataset<Candidate> data){
        if(data.size() == 0) {
            throw new RuntimeException("Empty dataset");
        }
        CandidateNode node = new CandidateNode(attr, null, data);
        // make a new node that has the random passed in attr and the passed in dataset
        LinkedList<CandidateEdge> edges = new LinkedList<CandidateEdge>();
        // make a list of edges (later will be added to the node)
        LinkedList<IAttributeDataset<Candidate>> subsets = data.partition(attr);
        // make a list of the subsets for the random attribute we have partitioned the data on

        if(pickRanAttr() == null){ // if there are no more attributes to look at
            INode result = (INode) new Hired(data.mostCommonValue(targetAttr));
            return result;// return the most common result (hired or not)
        }
        if(data.allSameValue(targetAttr)){ // if all the data have the same result
            INode result = (INode) new Hired(data.mostCommonValue(targetAttr));
            return result;  // return their shared result
        }

        for(int i=0; i < subsets.size(); i++){ // go through the subsets
            ListObjsData thisSubset = (ListObjsData) subsets.get(i); // set thisSubset to the current subset
            Object attrVal = thisSubset.getSharedValue(attr); // set the shared attribute value of this subset to attrVal
            String newRanAttr = pickRanAttr();
            CandidateEdge thisEdge =
                    new CandidateEdge(attrVal, findEdges(pickRanAttr(), thisSubset), subsets.get(i));
            // make a new edge that is of the *** shared attribute value
            // *** it connects to a new node that is made recursively (pass in the next random attribute
            // and this subset to be further partitioned)
            // *** and contains the current subset
            edges.add(thisEdge); // add this edge to the list of edges (after recursion has finished)
        }
        node.edges = edges; // add edges list to the node we are making
        return node; // return the node
    }

    @Override // build a decision tree to predict the named attribute
    public INode buildClassifier(String targetAttr){
        this.targetAttr = targetAttr;
        firstNode = (CandidateNode) findEdges(pickRanAttr(), (IAttributeDataset<Candidate>) initTrainingData);
        // printTree();
        return firstNode;
    }


    @Override // produce the decision predicted for the given datum
    public Object lookupRecommendation(IAttributeDatum forVals) {
        INode checkNode = firstNode;
        CandidateEdge edge = null;
        while (!(checkNode.lookupDecision(forVals) instanceof Hired)) {// while we have not reached the end decision
            // hired or not:
            edge = (CandidateEdge) checkNode.lookupDecision(forVals); // set the edge to the corresponding attribute val
            if (edge == null) { // if attribute value doesn't match any of the edges
                return ((CandidateNode) checkNode).dataset.mostCommonValue(targetAttr); // return the most common result of the subset
            }
            if(edge.node == null) return checkNode;
            checkNode = edge.node;
        }
        return checkNode;
    }

    @Override // print the decision tree
    public void printTree() {
        System.out.println(firstNode.attribute);
        for(int i=0; i<firstNode.edges.size(); i++){
            printNodes(firstNode.edges.get(i));
        }
    }

    /**
     * recursive method for printing the nodes and edges of the tree
     * @param edge - edge that connects to a node
     */
    public void printNodes(CandidateEdge edge){
        System.out.println("Edge--- " + edge.edgeVal);
        INode node = edge.node;
        if (node instanceof Hired){
            node.printNode("");
            return;
        }
        System.out.println("Node--- " + ((CandidateNode) node).attribute);
        for(int i=0; i<((CandidateNode) node).edges.size(); i++){
            printNodes(((CandidateNode) node).edges.get(i));

        }
    }
}