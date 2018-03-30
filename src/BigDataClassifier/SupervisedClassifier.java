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

/**
 *
 * @author lamogha
 */
public class SupervisedClassifier {
	    
      public void supervisedClassifier(String filePathName) {
        //call your supervised classifier here with the path to the file 
          
    }
      
      /**
       * TODO 
     * @param dataset
     * @return a probabilistic classifier type 
     * @throws java.lang.Exception 
      */
      public Classifier useNaiveBayes(Instances dataset) throws Exception{
    	  
    		  dataset.setClassIndex(dataset.numAttributes()-1);
    		  NaiveBayes nb = new NaiveBayes();
        	  nb.buildClassifier(dataset);
        	  return nb;
        	 // System.out.println(nb.distributionForInstance(dataset.instance(15)));
        	 // System.out.println(nb.getCapabilities().toString());
      } 
      
      public Classifier useClassifierWithFilter(Instances dataset) throws Exception{
    	  
		  dataset.setClassIndex(dataset.numAttributes()-1);
		  //the filter
		  Remove remove = new Remove();
		  //remove.setAttributeIndices ("1");
		  String[] opts = new String []{"-R", "1"};
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
      
     public Classifier adaBoost(Instances dataset) throws Exception{
    	  
          dataset.setClassIndex(dataset.numAttributes()-1);
    	  AdaBoostM1 m1 = new AdaBoostM1();
          m1.setClassifier(new DecisionStump());
          m1.setNumIterations(20);
    	  m1.buildClassifier(dataset);
    	  return m1;
      }
     
      public Classifier useZeroR (Instances dataset) throws Exception{
    	  
          dataset.setClassIndex(dataset.numAttributes()-1);
    	  ZeroR zeroR = new ZeroR();
    	  zeroR.buildClassifier(dataset);
    	  return zeroR;
      }
      
       public Classifier useM5P(Instances dataset) throws Exception{
    	  
          dataset.setClassIndex(dataset.numAttributes()-1);
    	  M5P m5P = new M5P();
    	  m5P.buildClassifier(dataset);
    	  return m5P;
      }
       
       public Classifier useRandomForest(Instances dataset) throws Exception{
    	  
          dataset.setClassIndex(dataset.numAttributes()-1);
    	  RandomForest randomForest = new RandomForest();
    	  randomForest.buildClassifier(dataset);
    	  return randomForest;
      }
       
       public Classifier useLinearRegression (Instances dataset) throws Exception{
           LinearRegression lr = new LinearRegression();
           lr.buildClassifier(dataset);
           return lr;
       }
      
      
}
