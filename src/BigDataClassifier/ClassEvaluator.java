package src.BigDataClassifier;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import static weka.core.Attribute.NOMINAL;
import static weka.core.Attribute.RELATIONAL;
import static weka.core.Attribute.STRING;
import weka.core.Instances;
import weka.core.Utils;

public class ClassEvaluator {
    
    Instances trainDataset2;
    Instances testDataset2;
    int trainDatasetSize;
    int testDatasetSize;
    SupervisedClassifier sc = new SupervisedClassifier();
    UnsupervisedClassifier uc = new UnsupervisedClassifier();
    //ClusterEvaluator clusterEval = new ClusterEvaluator();

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
                    trainDataset.setClassIndex(trainDataset.numAttributes()-1);
                    System.out.println("The number of class labels is:- " + trainDataset.numClasses());
                    this.callClassifier();     
                }
                
         }
        
        public int numOfNominalAtt(){
            int nom = 0;
            for (int i=0; i<trainDataset2.numAttributes(); i++){
                //boolean nominal = trainDataset2.checkForAttributeType(i);
                if (trainDataset2.attribute(i).isNominal()){
                    nom++;
                }
            }
            System.out.println(nom);
            return nom;
        }
        
        public void callClassifier(){
            if((trainDataset2.size() >= testDataset2.size()) && trainDataset2.numClasses()!= 0)
            {
                if(this.numOfNominalAtt() < (trainDataset2.numAttributes()/2))
                {
                    try {
                     this.evaluatorClassifier(trainDataset2, testDataset2, sc.useRandomForest(trainDataset2));
                     System.out.println("Random Forest used");
                    } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                else if (trainDataset2.checkForAttributeType(RELATIONAL))
                {
                    try {
                     this.evaluatorClassifier(trainDataset2, testDataset2, sc.useRandomForest(trainDataset2));
                     System.out.println("Random Forest used");
                    } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }
                
                else if (trainDataset2.checkForAttributeType(STRING))
                {
                    try {
                     this.evaluatorClassifier(trainDataset2, testDataset2, sc.useZeroR(trainDataset2));
                     System.out.println("Zero R used");
                    } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                else if (trainDataset2.checkForAttributeType(NOMINAL))
                {
                   try {
                     this.evaluatorClassifier(trainDataset2, testDataset2, sc.useNaiveBayes(trainDataset2));
                     System.out.println("Naive Bayes used");
                 } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                 } 
                   
                }
                 
                else
                {
                    try {
                     this.evaluatorClassifier(trainDataset2, testDataset2, sc.useRandomForest(trainDataset2));
                     System.out.println("Random Forest used");
                    } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
            else
            {
                try {
                    //use unsupervised classifier
                    uc.autoProbClass(testDataset2);
                    //uc.evaluatorClusterer(trainDataset2, (SimpleKMeans) uc.useEMClusterer(trainDataset2));
                } catch (Exception ex) {
                    Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
	/** 
        public int getTrainDataSize(){
          return trainDatasetSize;
      }
      
        public int getTestDataSize(){
          return testDatasetSize;
      }
      * */
        Evaluation eval;
        public void evaluatorClassifier(Instances trainDataset, Instances testDataset, Classifier cs) throws Exception
        {
              testDataset.setClassIndex(testDataset.numAttributes()-1);
   	      eval = new Evaluation (trainDataset);
              eval.evaluateModel(cs, testDataset);
 	      System.out.println(eval.toSummaryString("Evaluation results:\n", false));
              System.out.println(eval.areaUnderROC(NOMINAL));
 	      //System.out.println(eval.toMatrixString("Confusion Matrix for this"));
        }   
      
}