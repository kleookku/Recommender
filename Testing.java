package sol;

import src.IAttributeDataset;
import tester.Tester;

import java.util.LinkedList;

public class Testing {

    /**
     * sets up a list of attributes (type String)
     * @return a list of attributes (type String)
     */
    public LinkedList<String> setupAttributes() {
        LinkedList<String> attributes = new LinkedList<String>();
        attributes.add("gender");
        attributes.add("leadershipExpereience");
        attributes.add("lastPositionDuration");
        attributes.add("numWorkExperiences");
        attributes.add("programmingLanguages");
        attributes.add("gpa");
        attributes.add("location");
        attributes.add("hired");
        return attributes;
    }

    /**
     * set up a dataset of candidates
     * @return ListObjsData extends IAttributeDataset - returns a dataset of datum/candidates
     */
    public ListObjsData setupTestCandidates(){
        //(String gender, boolean leadershipExperience,
        //                   String lastPositionDuration, String numWorkExperiences,
        //                   String programmingLanguages, String gpa, String location, boolean hired)
        Candidate c1 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "4.0", "local", true);
        Candidate c2 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "4.0", "local", true);
        Candidate c3 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "4.0", "international", true);
        Candidate c4 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "3.0", "international", true);
        LinkedList<Candidate> candidates = new LinkedList<Candidate>();
        candidates.add(c1);
        candidates.add(c2);
        candidates.add(c3);
        candidates.add(c4);

        return new ListObjsData(setupAttributes(), candidates);
    }

    /**
     * setup result for partition test
     * @return LinkedList<IAttributeDataset>
     */
    public LinkedList<IAttributeDataset> setupPartitionResult(){
        Candidate c1 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "4.0", "local", true);
        Candidate c2 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "4.0", "local", true);
        Candidate c3 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "4.0", "international", true);
        Candidate c4 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "3.0", "international", true);
        LinkedList<Candidate> candidates1 = new LinkedList<Candidate>();
        candidates1.add(c1);
        candidates1.add(c2);

        LinkedList<Candidate> candidates2 = new LinkedList<Candidate>();
        candidates2.add(c3);
        candidates2.add(c4);

        ListObjsData list1 = new ListObjsData(setupAttributes(), candidates1);
        ListObjsData list2 = new ListObjsData(setupAttributes(), candidates2);

        LinkedList<IAttributeDataset> partitionedData = new LinkedList<IAttributeDataset>();
        partitionedData.add(list1);
        partitionedData.add(list2);

        return partitionedData;
    }

    /**
     * setup a CandidateNode
     * @return CandidateNode
     */
    public CandidateNode setupNode(){
        LinkedList<CandidateEdge> edges = new LinkedList<CandidateEdge>();
        CandidateEdge e1 = new CandidateEdge("local", null, null);
        CandidateEdge e2 = new CandidateEdge("international", null, null);
        edges.add(e1);
        edges.add(e2);
        CandidateNode node = new CandidateNode("location", edges, null);
        return node;
    }

    /**
     * setup Hired object
     * @return Hired object
     */
    public Hired setupHired(){
        Hired h = new Hired(true);
        return h;
    }

    /**
     * test the methods in ListObjsData, CandidateNode, and Hired
     * @param t - Tester
     */
    public void testMethods(Tester t){
        // methods to test:
        // ListObjsData: allSameValue, size, getAttributes, partition, getSharedValue, mostCommonValue
        // CandidateNode: lookupDecision
        // Hired: lookupDecision

        ListObjsData data = setupTestCandidates();

        // TEST allSameValue() IN ListObjsData
        t.checkExpect(data.allSameValue("gender"), true);
        t.checkExpect(data.allSameValue("location"), false);

        // TEST size() IN ListObjsData
        t.checkExpect(data.size(), 4);

        // TEST getAttributes() IN ListObjsData
        t.checkExpect(data.getAttributes(), setupAttributes());

        // TEST getSharedValue() IN ListObjsData
        t.checkExpect(data.getSharedValue("gender"), "female");

        // TEST mostCommonValue() IN ListObjsData
        t.checkExpect(data.mostCommonValue("gpa"), "4.0");

        // TEST partition() IN ListObjsData
        ListObjsData data1 = setupTestCandidates();
        t.checkExpect(data1.partition("location"), setupPartitionResult());


        Candidate c1 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "3.0", "international", true);
        // TEST lookupDecision() in CandidateNode
        t.checkExpect(setupNode().lookupDecision(c1),
                new CandidateEdge("international", null , null));

        Candidate c2 = new Candidate("female", true,
                "<1", "1-2",
                "1-2", "3.0", "international", true);
        // TEST lookupDecision() in Hired
        t.checkExpect(setupHired().lookupDecision(c2),
                new CandidateEdge(true, null , null));
    }

    /**
     * main method
     * @param args
     */
    public static void main(String[] args){
        Tester.run(new Testing());
    }
}
