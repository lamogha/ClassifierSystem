package BigDataClassifier;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.core.Instances;

public class ClassifierEvaluator {
	
	 public void evaluatorClassifier(Instances trainDataset, Instances testDataset, Classifier cs) throws Exception{
   	  
     	  testDataset.setClassIndex(testDataset.numAttributes()-1);
   	      Evaluation eval = new Evaluation (trainDataset);
 		  eval.evaluateModel(cs, testDataset);
 	      System.out.println(eval.toSummaryString("Evaluation results:\n", false));
 	      System.out.println(eval.toMatrixString("Confusion Matrix for this"));
     }
	 
	 public void evaluatorClusterer(Instances trainDataset, Instances testDataset) throws Exception{
	   	  
		 UnsupervisedClassifier uc = new UnsupervisedClassifier();
		 uc.useFarthestFirst(trainDataset);
		 testDataset.setClassIndex(testDataset.numAttributes()-1);
  	      ClusterEvaluation eval = new ClusterEvaluation ();
		  eval.evaluateClusterer(testDataset);
	      System.out.println(eval.clusterResultsToString());
	      //System.out.print(eval.evaluateClusterer(testDataset, null, false);
    }

}
