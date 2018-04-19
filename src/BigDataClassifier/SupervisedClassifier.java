 /*
 * supervisedClassifier algorithm
 */
package BigDataClassifier;
import weka.core.Instances;
import weka.classifiers.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SGD;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
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
      
      public Classifier useSGD (Instances dataset, int classIndex) throws Exception{
    	  
          dataset.setClassIndex(classIndex);
    	  SGD sgd = new SGD();
    	  sgd.buildClassifier(dataset);
    	  return sgd;
      }
      
      public Classifier useJ48 (Instances dataset, int classIndex) throws Exception{
    	  
          dataset.setClassIndex(classIndex);
    	  J48 j48 = new J48();
    	  j48.buildClassifier(dataset);
    	  return j48;
      }
      
      public Classifier useZeroR (Instances dataset, int classIndex) throws Exception{
    	  
          dataset.setClassIndex(classIndex);
    	  ZeroR zeroR = new ZeroR();
    	  zeroR.buildClassifier(dataset);
    	  return zeroR;
      }
       
     public Classifier useRandomForest(Instances dataset, int classIndex) throws Exception{
    	  
          dataset.setClassIndex(classIndex);
    	  RandomForest randomForest = new RandomForest();
    	  randomForest.buildClassifier(dataset);
    	  return randomForest;
      }
      
}
