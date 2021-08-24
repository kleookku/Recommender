package src;

public interface IGenerator {
    // build a decision tree to predict the named attribute
    public INode buildClassifier(String targetAttr);

    // produce the decision predicted for the given datum
    public Object lookupRecommendation(IAttributeDatum forVals);

    // print the decision tree
    public void printTree();
}