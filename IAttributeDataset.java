package src;

import java.util.LinkedList;

public interface IAttributeDataset<T extends IAttributeDatum> {
    // all the attributes in the dataset
    public LinkedList<String> getAttributes();

    // does every row/datum have the same value for the given attribute/column
    public boolean allSameValue(String ofAttribute);

    // number of data/rows in the dataset
    public int size();

    // partition rows into subsets such that each subset has same value of
    // onAttribute
    public LinkedList<IAttributeDataset<T>> partition(String onAttribute);

    // get the value for ofAttribute, which is assumed to be common across all
    // rows
    public Object getSharedValue(String ofAttribute);

    // get the most common value for ofAttribute in the dataset
    public Object mostCommonValue(String ofAttribute);
}
