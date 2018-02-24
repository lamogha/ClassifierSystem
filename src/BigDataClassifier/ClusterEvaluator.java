/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.BigDataClassifier;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.core.Instances;
/**
 *
 * @author u1457710
 */
public class ClusterEvaluator {
    
     /**
     *
     * @param dataset
     * @param clusterer
     * @throws Exception
     */
    public void evaluatorClusterer(Instances dataset, Clusterer clusterer) throws Exception{
	   
  	      ClusterEvaluation eval = new ClusterEvaluation ();
              eval.setClusterer(clusterer);
              eval.evaluateClusterer(dataset);
	      System.out.println(eval.clusterResultsToString());
	      //System.out.print(eval.evaluateClusterer(testDataset, null, false);
    }

    
}
