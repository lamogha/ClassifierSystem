/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.BigDataClassifier;

import java.util.Random;
import weka.core.Instances;

/**
 *
 * @author u1457710
 */
public class GenerateFolds {
    Instances trainDataset2;
    Instances testDataset2;
    int trainDatasetSize;
    int testDatasetSize;
      
    
      public void generateFolds(Instances trainDataset) throws Exception{
            
            //randomize data
              Random rand = new Random(1);
              //set folds
              int folds = 3;
              //create random dataset
              Instances randData = new Instances(trainDataset);
              randData.randomize(rand);
              
              Instances[] result = new Instances[folds*2];
              //cross-validate
                for(int n=0; n<folds; n++)
                {
                    trainDataset = randData.trainCV(folds, n);
                    System.out.println("Train dataset size is = "+ trainDataset.size());
                    Instances testDataset = randData.testCV(folds, n);
                    System.out.println("Test dataset size is = "+ testDataset.size());
                    result[n] = trainDataset;
                    result[n+1] = testDataset;
                    trainDataset2 = trainDataset;
                    testDataset2 = testDataset;
                }
                trainDatasetSize = trainDataset2.size();
                testDatasetSize = testDataset2.size();
         }
      
      public int getTrainDataSize(){
          return trainDatasetSize;
      }
      
      public int getTestDataSize(){
          return testDatasetSize;
      }
    
}
