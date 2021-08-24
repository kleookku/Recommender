package sol;

import src.IAttributeDataset;
import src.IAttributeDatum;

import java.util.LinkedList;

/*
 * Class for a specific representation of rows in a data table. This uses a list
 * of objects (one object per row).
 * The type T is the object that forms the "rows" of the data table
 */
public class ListObjsData<T extends IAttributeDatum>
        implements IAttributeDataset<T> {
    public LinkedList<String> attributes;
    public LinkedList<T> allData;


    /**
     * constructor for ListObjsData
     * @param canAttr - list of the attributes that all candidate have
     * @param allCandidates - list of all the candidates
     */
    public ListObjsData(LinkedList<String> canAttr, LinkedList<T> allCandidates) {
        attributes = canAttr;
        allData = allCandidates;
    }

    @Override // all the attributes in the dataset
    public LinkedList<String> getAttributes() {
        return this.attributes;
    }

    @Override    // does every row/datum have the same value for the given attribute/column
    public boolean allSameValue(String ofAttribute) {
        Object attrVal = allData.get(0).getValueOf(ofAttribute);
        for(int i=1; i<allData.size(); i++){
            if(attrVal != allData.get(i).getValueOf(ofAttribute)) return false;
        }
        return true;
    }

    @Override // number of data/rows in the dataset
    public int size() {
        return allData.size();
    }

    @Override // partition rows into subsets such that each subset has same value of
    // onAttribute
    public LinkedList<IAttributeDataset<T>> partition(String onAttribute) {
        LinkedList<Object> attrValList = new LinkedList<Object>();
        LinkedList<LinkedList<IAttributeDatum>> subsets = new LinkedList<LinkedList<IAttributeDatum>>();
        // parallel lists: attributeVals is a list of the possible values for the attributes. Each
        // value corresponds with a IAttributeDataset subset that has the specified value
        // for the onAttribute.
        //int count = 0;
        for(int i=0; i<allData.size(); i++){
            IAttributeDatum datum = allData.get(i); // current datum/row that we're looking at as we parse
            Object val = datum.getValueOf(onAttribute); // the datum's value for specified attribute
            if(attrValList.contains(val)){
                int index = attrValList.indexOf(val); // get index for specified value
                subsets.get(index).add(datum); // add the datum to the subset's linkedList of data
            }
            else{
                attrValList.addLast(val);
                LinkedList<IAttributeDatum> thisSubset = new LinkedList<IAttributeDatum>();
                thisSubset.add(datum);
                subsets.add(thisSubset); // add the row to the new list in subsets
            }
        }
        LinkedList<IAttributeDataset<T>> subsetList = new LinkedList<IAttributeDataset<T>>(); // new empty list
        for(int i=0; i<subsets.size(); i++){ // form the ListObjsData list
            ListObjsData<T> dataset = new ListObjsData<T>(this.attributes, (LinkedList<T>) subsets.get(i));
            // make a new ListObjsData for each subset
            subsetList.add(dataset); // add it to the list of ListObjsData subsets
        }
        return subsetList;
    }

    @Override // get the value for ofAttribute, which is assumed to be common across all
    // rows
    public Object getSharedValue(String ofAttribute) {
        return allData.get(0).getValueOf(ofAttribute);
    }

    @Override // get the most common value for ofAttribute in the dataset
    public Object mostCommonValue(String ofAttribute) {
        LinkedList<Object> valueNames = new LinkedList<Object>(); // list of names for found values
        LinkedList<Integer> valueCounts = new LinkedList<Integer>(); // list of counts for found values
        // these are 2 parallel lists; each name in valueNames corresponds with a count in n
        // valueCounts with the same index.
        for(int i=0; i<allData.size(); i++){ // go through entire data set
            Object value = allData.get(i).getValueOf(ofAttribute); // find attribute value for given datum
            if(valueNames.contains(value)){ // if valueNames already contains this attribute value for given datum
                int index = valueNames.indexOf(value); // get index of value in valueCounts list
                Integer num = valueCounts.get(index) + 1; // add 1 to count of value
                valueCounts.set(index, num); // set specified index to new count value
            }
            else{
                valueNames.addLast(value);
                valueCounts.addLast(new Integer(1));
            }
        }
        // find the value with the max counts
        int max = 0;
        int index = 0;
        for(int i=0; i<valueCounts.size(); i++){
            if(valueCounts.get(i) > max){
                max = valueCounts.get(i);
                index = i;
            }
        }
        return valueNames.get(index);
    }
}
