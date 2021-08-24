GROUP: avonderg, kku2, mlee180, acai13
SUBGROUP: avonderg, kku2


CODE DESCRIPTION:


BUGS: We made ListObjsData and TreeGenerator specific to the Candidate objects, including CandidateNode, CandidateEdge, Candidate, and Hired. Thus, it is not as flexible as we’d like it to be, for example if we want to make a tree for a different type of data (vegetables, fruit, etc). This is a bug if we wanted to feed a different dataset with different attributes to the program. We could likely fix this by changing the interfaces, i.e. adding variables. We made ListObjsData and TreeGenerator specific to the Candidate objects because we needed to access certain variables (like allData) that couldn’t be accessed if we just used INode (the interface type) to make our objects. 


CLASSES:
* Candidate 
   * Extends IAttributeDatum
   * Represents each Candidate with its attributes
   * Can also be described as a “row” of data
* CandidateNode
   * Extends INode
   * Represents a node in the decision tree
   * Contains the attribute name (String), a list of edges (CandidateEdges), and the subset of Candidates that falls under that node (IAttributeDataset)
* Hired
   * Extends INode
   * Represents the leaf of the tree (the bottom), a.k.a. The final decision
   * Contains only one object, the result
      * For Candidate, the result is a boolean (hired or not hired)
* CandidateEdge
   * Doesn’t extend any interface
   * Represents an edge in the tree (edges extend from nodes)
   * Each edge is an attribute value (e.g. green for the color attribute)
   * This attribute value is store in the variable “edgeVal” (Object, because it can be a String, boolean, etc)
   * This object also contains a node (of type INode; in this case, it would connect to a CandidateNode), which is a random attribute that the edge connects to in the tree. 
   * This object also contains an IAttributeDataset of Candidates, which is a subset of the Candidates that fall under this edge category (i.e. the vegetable that are of the color green)
* ListObjsData
   * Extends IAttributeDataset
   * Contains a list of the attributes (String), and a set of data (T)
   * Represents all the datum
   * This object is how the subsets in CandidateEdge and CandidateNode are represented . 
   * It is also where the initial total data is represented.
* TreeGenerator
   * Extends IGenerator
   * This is where the tree is generated
   * Contains initTrainingData (ListObjsData), which is where the initial training data is stored
   * Contains attributes (linked list of strings), which is a list of the attributes (String)
   * Contains firstNode (CandidateNode), which is the node at the top of the tree that the tree starts on
      * It is also where the lookupDecision starts on
   * Contains targetAttr (String), which is the target attribute, which in the Candidate case is “hired”
* Testing
   * We test our methods in this class.
SUMMARY:
TreeGenerator is the class that creates the tree (buildClassifier, with additional helper functions). The objects that make up the tree are CandidateNode (forms each node of the tree; extends INode), CandidateEdge (the edges that extend from each Node), and Hired (the leaf at the bottom of the tree that is the result of the target attribute, “hired”; extends INode). The data that is fed into the tree is represented by the object ListObjsData (extends IAttributeDataset). This object also represents the subsets of data that fall under each edge in the tree. Each row of data is represented by the object Candidate (extends IAttributeDatum). 


SRC
PART 1:
Question 1: Choose one step of the hiring process that the article goes over and discuss how bais manifests itself. Think about underlying reasons for this. (Don’t forget to explain your reasoning!)


The step of the hiring process we chose from this article was, “Shaping the candidate pool”.  Essentially, this section of the article focused on hiring systems making predictions about potential candidates. However, these systems only make predictions based on who is most likely to click on the job ad-- so, the prediction is based upon whoever is most likely to actually accept that job, and not necessarily excel at it or be successful. For this reason, these algorithms often manifest bias through gender or racial stereotypes. An example that the article makes is a taxi driver job ad shown to an audience that was 75% black. Although this was a computer generated algorithm that was showing the ad, it still demonstrated bias. The algorithm presented the ad to people who were most likely to click on it (in this case, black people), and not those who needed it most. 


This bias created by hiring algorithms also likely causes there to be a lack of diversity in the workforce itself. The reason this bias appears in the first place is likely because of repetitive patterns shown in past data. These sourcing algorithms are created using past data; thus, if there is bias in the past, it will continue to be enforced. Rather than preventing future bias, these algorithms reinforce and replicate past patterns-- often without anyone even noticing. Hence, these sourcing algorithms create barriers in the workforce-- by not informing potential candidates of a job opening, candidates are put at a disadvantage. As the article mentions, “not informing people of a job opportunity is a highly effective barrier”. 


PART 2:
Question 2: What do you notice in the differences between cis male and cis female hired ratios printed out?


Ratios  (data/train_candidates_unequal.csv)
* Cis Female ratio hired 0.01                Cis Male ratio hired 0.23
* Cis Female ratio hired 0.0                Cis Male ratio hired 0.13
* Cis Female ratio hired 0.0                Cis Male ratio hired 0.21
* Cis Female ratio hired 0.06                Cis Male ratio hired 0.34
* Cis Female ratio hired 0.0                Cis Male ratio hired 0.47


As demonstrated above, the ratio for female candidates hired is much lower than that for male candidates. More often than not, the hire ratio for female candidates was zero, thus demonstrating an extreme bias towards male candidates. 


Question 3: Do you still see the bias in the different hiring ratio? If so, what differences do you notice between the training datasets (also presented on the Google sheet)? How do you think these differences affect the hiring bias of the algorithm?


        Ratios (data/train_candidates_equal.csv)
* Cis Female ratio hired 0.05                Cis Male ratio hired 0.12
* Cis Female ratio hired 0.0                Cis Male ratio hired 0.01
* Cis Female ratio hired 0.04                Cis Male ratio hired 0.07
* Cis Female ratio hired 0.04                Cis Male ratio hired 0.09
* Cis Female ratio hired 0.02                Cis Male ratio hired 0.07


As demonstrated above, there is still a bias towards male candidates. This is present in the data from the Google Sheet, where 17.5% of male candidates and 16% of female candidates were hired (within the ‘equal’ dataset). However, this percentage difference is much lower than that of the unequal dataset, where 52% male candidates and 24% female candidates were hired. This is reflected in the ratio found from running the program. The ratio is likely still biased for the equal dataset, however, because there are so few female candidates in the dataset in total, and thus very few in number hired, although the percentage is similar to male candidates. For the unequal dataset, there was a much stronger ratio bias towards male candidates than there is for the equal dataset. 


Question 4: How does the approach your code used to choose which attribute to split on impact the resulting bias, if at all?


Our code chose which attribute to split on randomly. Given that this chosen attribute is random, the tree is built differently each time. For this reason, the hiring rates are always different due to the tree constantly changing. Had we chosen a static attribute to split on for the dataset every time, this would have prevented the hiring rates from being different each time, and the bias ratio would have remained consistent each time the program was run. This does not necessarily mean that there would no longer be any bias-- rather, it would have always had the same ratio for female candidates and male candidates hired. 


Question 5: If your hiring rates vary each time you build a new classifier, why does this occur? If we fix the algorithm to choose the same attribute to split on each time, could we eliminate the bias?


This occurs due to the fact that our code chooses a random attribute to split the tree on each time the program is run. Hence, given that the program builds a new tree every time, this causes the hiring ratios to be constantly changing. However, even if we chose the same attribute to split on each time, this would only prevent the hiring rates from varying, and not necessarily eliminate the bias. Even if the tree is built differently each time, affecting the hiring ratios, the fact that the ratios are uneven each time it is run demonstrates that the bias is not being caused by the random attribute being chosen each time; rather, it is elsewhere in the dataset and program. 


 
PART 3
Question 6: Do you still see evidence of hiring bias? Why or why not?
Ratios with         "data/testing_cis_male_correlated.csv"
"data/testing_cis_female_correlated.csv"
"data/train_candidates_correlated.csv"
* Cis Female ratio hired 0.04                Cis Male ratio hired 0.11
* Cis Female ratio hired 0.0                Cis Male ratio hired 0.04
* Cis Female ratio hired 0.04                Cis Male ratio hired 0.17
* Cis Female ratio hired 0.04                Cis Male ratio hired 0.13
* Cis Female ratio hired 0.25                2Cis Male ratio hired 0.31


Yes, we still see evidence of bias. The results above show that although gender has been removed from the training data, the other attributes of leadership position and last position duration are still correlated with gender. As mentioned in the project, due to workplace hostility, maternity leave, and the disparity of women in corporate leadership positions, the attributes of leadership position and last position duration are correlated with gender, and tend to favor male candidates.  Thus, there is still a bias toward male candidates. 




PART 4
Question 7: What are some limitations of BiasTest (and other statistical measures of bias), if any, as a metric for fairness in the hiring process or as a metric for diversity and inclusion (in the workspace)? What are some strengths, if any? Consider providing an example of fairness/unfairness or potential benefit/harm that wouldn’t be captured by the results of BiasTest.


To begin with, a significant limitation of BiasTest (and other statistical measures of bias) is that it does not take into consideration the factors that play into female and male candidates. For example, if we did not find that female candidates generally have lower leadership position and last position durations, we would assume that the employers are biased towards male candidates solely because of their gender.  To fix this, another factor should be included that ‘evens the playing field’-- that is, BiasTest should rely less on the attributes of ‘last position held’ and ‘last position duration’, and more on the others. Or, a new attribute should be introduced.


One strength we found that BiasTest has is that it can even find bias to begin with (if it exists at all). Also, if we use different sets of training data, as we did for question 6, we will be able to find how other factors play into a bias. For example, in question 6, we were able to see that leadership experience and last position duration are correlated with gender.


An example of unfairness that wouldn’t be captured by the results is that female candidates likely take more parental leave than male candidates do. This would cause their last position duration to be shorter, and BiasTest would not be able to recognize that. However, these women do not easily opt out of spending more time at their last position-- many times, they have no choice but to take leave in order to raise their child. Hence, women are harmed by this assumption made by the program given that they have no control over this decision. The attribute “last position duration” is impacted by this situation, even though it should not play a factor in the BiasTest algorithm.