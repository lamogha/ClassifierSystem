/*
 * unsupervised classifier algorithm
 */
package BigDataClassifier;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import weka.clusterers.*;
import weka.core.Instances;

public class UnsupervisedClassifier {
    private static final HashSet<String> cloud = new HashSet<>();
    
     public void unsupervisedClassifier(String filePathName) {
        //call your unsupervised classifier here with the path to the file 
    }
     
     
     public void useEMClusterer(Instances dataset) throws Exception{
   	  
		  //dataset.setClassIndex(dataset.numAttributes()-1);
		  EM newEm = new EM();
                  newEm.setNumClusters(4);
		  newEm.buildClusterer(dataset);
                  System.out.println(newEm);
                  
                  //to cluster a new instance
                  //newEm.clusterInstance(instance);
                  
                  //return an array of class membership probabilities
                  //newEm.distributionForInstance(instance);
          // System.out.println(nb.distributionForInstance(dataset.instance(15)));
   	 // System.out.println(nb.getCapabilities().toString());
     }
     
     public FarthestFirst useFarthestFirst(Instances dataset) throws Exception{
      	  
		  dataset.setClassIndex(dataset.numAttributes()-1);
		  FarthestFirst newFf = new FarthestFirst();
		  newFf.buildClusterer(dataset);
  	     return newFf;
  	 // System.out.println(nb.distributionForInstance(dataset.instance(15)));
  	 // System.out.println(nb.getCapabilities().toString());
     }
     
     public void autoProbClass(Instances dataset) throws Exception {
    	 //Define the intial zone of influence ZI
         double initialZI = 0.3;
         int k = 1;
         Instances xk = new Instances(dataset);
         double ncZI= initialZI;
         
         //start reading in the instances
         while (!dataset.isEmpty() && xk.equals(dataset.containsAll(dataset))){
             if (k == 1)
             {
                 String nc = dataset.get(k).toString() + " Class " + k;
                 double ncFocalpoint = xk.meanOrMode(k);
                 //ncZI= initialZI;
                 int ncPoints = 1;
                 cloud.add(nc);
             }
             else{
                 if (xk.get(k).numValues()<=(2*ncZI))
                 {
                     for (Iterator<String> it = cloud.iterator(); it.hasNext();) {
                         String j = it.next();
                         //double jFocalpoint = (jFocalpoint * jPoints + xk.get(k).numValues()) / (jPoints + 1);
                     }                     
                 }
                 
             }
           
                
         }
         
     }
     
}
