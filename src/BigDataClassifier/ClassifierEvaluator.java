package BigDataClassifier;

import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.core.Instances;

public class ClassifierEvaluator {
	
	 public void evaluatorClassifier(Instances trainDataset, Instances testDataset, Classifier cs) throws Exception{
   	  
     	 // testDataset.setClassIndex(testDataset.numAttributes()-1);
   	      //Evaluation eval = new Evaluation (trainDataset);
              //randomize data
              Random rand = new Random(1);
              //set folds
              int folds = 3;
              //create random dataset
              Instances randData = new Instances(trainDataset);
              randData.randomize(rand);
              //stratify
              if (randData.classAttribute().isNominal())
                  randData.stratify(folds);
              //cross-validate
              for(int n=0; n<folds; n++){
                   Evaluation eval = new Evaluation (trainDataset);
                    trainDataset = randData.trainCV(folds, n);
                    testDataset = randData.testCV(folds, n);
                    eval.evaluateModel(cs, testDataset);
                    System.out.println(eval.toSummaryString("Evaluation results:\n", false));
                    System.out.println(eval.toMatrixString("Confusion Matrix for this " + (n+1) +"/" + folds));
              }
              //eval.crossValidateModel(cs, trainDataset, folds, rand);
 		  
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
