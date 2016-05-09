/*
 * unsupervised classifier algorithm
 */
package BigDataClassifier;

import weka.clusterers.*;
import weka.core.Instances;

public class UnsupervisedClassifier {
    
     public void unsupervisedClassifier(String filePathName) {
        //call your unsupervised classifier here with the path to the file 
    }
     
     
     public void useEMClusterer(Instances dataset) throws Exception{
   	  
		  dataset.setClassIndex(dataset.numAttributes()-1);
		  EM newEm = new EM();
		  newEm.buildClusterer(dataset);
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
     
}
