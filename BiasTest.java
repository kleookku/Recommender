package sol;


import src.IAttributeDataset;
import src.IAttributeDatum;
import tester.Tester;

import java.util.LinkedList;

/*
 * Test to show the bias of the datasets
 */
public class BiasTest {
    BiasTest() {
    }

    /**
     * NOTE: Do not modify anything in the files except TODO
     */

    // the list of candidate objects to generate the tree on
    static ListObjsData<Candidate> candidates;

    /**
     * A method to set up candidate attributes and training data
     */
    public static void setupCandidates() {
        LinkedList<String> canAttr = new LinkedList<String>();
        // different attributes to consider similar to the Candidate class
        canAttr.add("gender");
        canAttr.add("leadershipExperience");
        canAttr.add("lastPositionDuration");
        canAttr.add("numWorkExperiences");
        canAttr.add("programmingLanguages");
        canAttr.add("gpa");
        canAttr.add("location");
        canAttr.add("hired");

        /**
         * TODO: change this filepath
         */
        //String filepath = "data/train_candidates_unequal.csv";
        String filepath = "data/train_candidates_equal.csv";

        RecommenderCSVParser<Candidate> parser = new RecommenderCSVParser<Candidate>();

        LinkedList<Candidate> allCandidates = new LinkedList<>();
        // parsing the dataset in the form of a CSV file, CommaSeparatedValues.
        allCandidates = (LinkedList<Candidate>) parser.parse(Candidate.class, filepath, ',', true);
        // if the filename is the correlated variable, we're not looking at the gender
        // variable at all while looking
        // at different Candidate objects while building the tree.
        if (filepath.equals("data/train_candidates_correlated.csv")) {
            canAttr.remove("gender");
        }

        // TODO : Uncomment the following line.
        BiasTest.candidates = new ListObjsData<Candidate>(canAttr, allCandidates);
    }

    /**
     * Main method for BiasTest
     *
     * @param args - the arguments to the program
     */
    public static void main(String[] args) {
        // set up the candidates from the CSV file
        BiasTest.setupCandidates();
        Tester.run(new BiasTest());

        double male_ratio = 0.0;
        double female_ratio = 0.0;
        double distance = -Double.MIN_VALUE;

        LinkedList<Candidate> newCandMale = new LinkedList<>();
        LinkedList<Candidate> newCandFemale = new LinkedList<>();
        RecommenderCSVParser<Candidate> parser = new RecommenderCSVParser<Candidate>();

        /**
         * TODO: While testing correlated variables, change filepath from
         * "data/testing_cis_male.csv" to "data/testing_cis_male_correlated.csv"
         */
        // parse the male testing dataset
        //newCandMale = (LinkedList<Candidate>) parser.parse(Candidate.class,
        // "data/testing_cis_male.csv", ',', true);
        newCandMale = (LinkedList<Candidate>) parser.parse(Candidate.class,
                "data/testing_cis_male_correlated.csv", ',', true);
        // parse the female testing dataset
        /**
         * TODO: While testing correlated variables, change filepath from
         * "data/testing_cis_female.csv" to "data/testing_cis_female_correlated.csv"
         */
        //newCandFemale = (LinkedList<Candidate>) parser.parse(Candidate.class,
        //        "data/testing_cis_female.csv", ',', true);
        newCandFemale = (LinkedList<Candidate>) parser.parse(Candidate.class,
                "data/testing_cis_female_correlated.csv", ',', true);

        // loop to test female and male hiring ratios with the same test files but large
        // number of times
        // to determine a better ratio by volume
        for (int j = 0; j < 10; j++) {
            // build the tree
            TreeGenerator<Candidate> builder = new TreeGenerator<Candidate>(BiasTest.candidates);
            builder.buildClassifier("hired");

            // loop to find out the number of males hired
            double hiredYes = 0;
            for (int i = 0; i < newCandMale.size(); i++) {
                if (builder.lookupRecommendation(newCandMale.get(i)).equals(Boolean.TRUE)) {
                    hiredYes++;
                }
            }

            // loop to find out number of females hired
            double hiredYes1 = 0;
            for (int i = 0; i < newCandFemale.size(); i++) {
                if (builder.lookupRecommendation(newCandFemale.get(i)).equals(Boolean.TRUE)) {
                    hiredYes1++;
                }
            }

            // update the highest distances between male and female hired ratios and male
            // and female ratios respectively
            if (distance < (hiredYes / newCandMale.size() - hiredYes1 / newCandFemale.size())) {
                distance = hiredYes / newCandMale.size() - hiredYes1 / newCandFemale.size();
                male_ratio = hiredYes / newCandMale.size();
                female_ratio = hiredYes1 / newCandFemale.size();
            }

        }
        /**
         * NOTE: run the file again if you get ratios as zero.
         */
        System.out.println("Cis Female ratio hired " + female_ratio);
        System.out.println("Cis Male ratio hired " + male_ratio);
    }
}