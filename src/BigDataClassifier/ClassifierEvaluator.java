package BigDataClassifier;

import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.core.Instances;

public class ClassifierEvaluator {
    
    Instances trainDataset2;
    Instances testDataset2;
    int trainDatasetSize;
    int testDatasetSize;
    SupervisedClassifier sc = new SupervisedClassifier();
    //set folds
    int folds = 3;
    
	public void generateFolds(Instances trainDataset) throws Exception{
            
            //randomize data
              Random rand = new Random(1);
             
              //create random dataset
              Instances randData = new Instances(trainDataset);
              randData.randomize(rand);
            
              //cross-validate
                for(int n=0; n<folds; n++)
                {
                    trainDataset = randData.trainCV(folds, n);
                    System.out.println("Train dataset size is = "+ trainDataset.size());
                    Instances testDataset = randData.testCV(folds, n);
                    System.out.println("Test dataset size is = "+ testDataset.size());
                    trainDataset2 = trainDataset;
                    testDataset2 = testDataset;
                   if(trainDataset2.size() >= testDataset2.size())
                   {
                       this.evaluatorClassifier(trainDataset, testDataset, sc.useNaiveBayes(trainDataset));
                   }
                   else
                   {
                       //use unsupervised classifier
                   }
                }
                
         }
	 
        public int getTrainDataSize(){
          return trainDatasetSize;
      }
      
        public int getTestDataSize(){
          return testDatasetSize;
      }
        
        public void evaluatorClassifier(Instances trainDataset, Instances testDataset, Classifier cs) throws Exception
        {
              testDataset.setClassIndex(testDataset.numAttributes()-1);
   	      Evaluation eval = new Evaluation (trainDataset);
              eval.evaluateModel(cs, testDataset);
 	      System.out.println(eval.toSummaryString("Evaluation results:\n", false));
 	      System.out.println(eval.toMatrixString("Confusion Matrix for this"));
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