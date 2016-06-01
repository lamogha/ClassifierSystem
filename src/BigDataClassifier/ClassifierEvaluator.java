package BigDataClassifier;

import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.core.Instances;

public class ClassifierEvaluator {
    
    public int trainSize;
    public int testSize;
	
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
                for(int n=0; n<folds; n++)
                {
                   Evaluation eval = new Evaluation (trainDataset);
                    trainDataset = randData.trainCV(folds, n);
                    System.out.println("Train dataset size is = "+ trainDataset.size());
                    testDataset = randData.testCV(folds, n);
                    System.out.println("Test dataset size is = "+ testDataset.size());
                    eval.evaluateModel(cs, testDataset);
                    System.out.println(eval.toSummaryString("Evaluation results:\n", false));
                    System.out.println(eval.toMatrixString("Confusion Matrix for this " + (n+1) +"/" + folds));
                    this.trainSize = trainDataset.size();
                    this.testSize = testDataset.size();
                }
              //eval.crossValidateModel(cs, trainDataset, folds, rand);
         }

    /**
     *
     * @param trainDataset
     * @param testDataset
     * @param clusterer
     * @throws Exception
     */
    public void evaluatorClusterer(Instances trainDataset, Instances testDataset, Clusterer clusterer) throws Exception{
	   
  	      ClusterEvaluation eval = new ClusterEvaluation ();
              eval.setClusterer(clusterer);
              eval.evaluateClusterer(testDataset);
	      System.out.println(eval.clusterResultsToString());
	      //System.out.print(eval.evaluateClusterer(testDataset, null, false);
    }

}
