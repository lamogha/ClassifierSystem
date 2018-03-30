 /*
 * supervisedClassifier algorithm
 */
package BigDataClassifier;
import weka.core.Instances;
import weka.classifiers.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.RandomForest;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveUseless;

/**
 *
 * @author lamogha
 */
public class SupervisedClassifier {
    private static FileTypeEnablerAndProcessor fp = new FileTypeEnablerAndProcessor();
	    
    public void supervisedClassifier(String filePathName) {
          
        //call your supervised classifier here with the path to the file 
          
    }
      
      /**
       * TODO 
     * @param dataset
     * @return a probabilistic classifier type 
     * @throws java.lang.Exception 
      */
      public Classifier useNaiveBayes(Instances dataset, int classIndex) throws Exception{
    	  
    		  dataset.setClassIndex(classIndex);
    		  NaiveBayes nb = new NaiveBayes();
        	  nb.buildClassifier(dataset);
        	  return nb;
        	 // System.out.println(nb.distributionForInstance(dataset.instance(15)));
        	 // System.out.println(nb.getCapabilities().toString());
      } 
      
      public Classifier useClassifierWithFilter(Instances dataset, int classIndex) throws Exception{
    	  
		  dataset.setClassIndex(classIndex);
		  //the filter
		  RemoveUseless remove = new RemoveUseless();
		  //remove.setAttributeIndices ("1");
		  String[] opts = new String []{"-R", "first-last"};
		  //set filter options
		  remove.setOptions(opts);
		  //create filtered classifier object
    	  FilteredClassifier fc = new FilteredClassifier();
    	  //specify filter
    	  fc.setFilter(remove);
    	  //specify the base classifier 
    	  fc.setClassifier(new NaiveBayes());
    	  //build the meta-classifier
    	  fc.buildClassifier(dataset);
    	  return fc;	  
      } 
      /*
     * @param dataset
     * @return 
     * @throws java.lang.Exception */
      
     public Classifier adaBoost(Instances dataset, int classIndex) throws Exception{
    	  
          dataset.setClassIndex(classIndex);
    	  AdaBoostM1 m1 = new AdaBoostM1();
          m1.setClassifier(new DecisionStump());
          m1.setNumIterations(20);
    	  m1.buildClassifier(dataset);
    	  return m1;
      }
     
      public Classifier useZeroR (Instances dataset, int classIndex) throws Exception{
    	  
          dataset.setClassIndex(classIndex);
    	  ZeroR zeroR = new ZeroR();
    	  zeroR.buildClassifier(dataset);
    	  return zeroR;
      }
      
       public Classifier useM5P(Instances dataset, int classIndex) throws Exception{
    	  
          dataset.setClassIndex(classIndex);
    	  M5P m5P = new M5P();
    	  m5P.buildClassifier(dataset);
    	  return m5P;
      }
       
       public Classifier useRandomForest(Instances dataset, int classIndex) throws Exception{
    	  
          dataset.setClassIndex(classIndex);
    	  RandomForest randomForest = new RandomForest();
    	  randomForest.buildClassifier(dataset);
    	  return randomForest;
      }
       
       public Classifier useLinearRegression (Instances dataset, int classIndex) throws Exception{
           dataset.setClassIndex(classIndex);
           LinearRegression lr = new LinearRegression();
           lr.buildClassifier(dataset);
           return lr;
       }
      
      
}
