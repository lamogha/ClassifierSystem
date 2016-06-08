/*
 * unsupervised classifier algorithm
 */
package BigDataClassifier;

import java.io.Reader;
import static java.lang.Math.min;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import weka.clusterers.*;
import weka.core.Instances;
import weka.core.EuclideanDistance;

public class UnsupervisedClassifier {
    private static final HashSet<String> cloud = new HashSet<>();
    private static final HashSet<Instances> outliers = new HashSet<>();
    
     public void unsupervisedClassifier(String filePathName) {
        //call your unsupervised classifier here with the path to the file 
    }
     
     
     public void useEMClusterer(Instances dataset) throws Exception{
   	  
		  //dataset.setClassIndex(dataset.numAttributes()-1);
		  EM newEm = new EM();
                  newEm.setNumClusters(4);
		  newEm.buildClusterer(dataset);
                  System.out.println(newEm);
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
         int ncPoints = 0; double ncFocalpoint=0;
         ListIterator iterator = dataset.listIterator();
         EuclideanDistance eu = new EuclideanDistance();
         //start reading in the instances
         while (iterator.hasNext())
         {
             if (k == 1)
             {
                 String nc = dataset.get(k).toString() + " Class " + k;
                 ncFocalpoint = xk.meanOrMode(k);
                 ncZI= initialZI;
                 ncPoints = 1;
                 cloud.add(nc);
             }
             else
             {
                 if (xk.get(k).numValues()<=(2*ncZI))//xk is close to an existing cloud
                 {
                     System.out.println(xk.get(k).numValues());
                     System.out.println(xk.get(k).value(k));
                     System.out.println(xk.get(k).weight());
                     //update close clouds
                     for (String j : cloud) {
                         String cc = j;
                         ncFocalpoint = (ncFocalpoint * ncPoints + (xk.get(k).numValues())) / (ncPoints + 1);
                         List<Double> attribute;
                         attribute = Arrays.asList((double)(xk.get(k).numValues()), ncFocalpoint);
                         Instances in = new Instances ((Reader) attribute);
                         ncZI = (ncZI * ncPoints + Math.pow(eu.distance(in.firstInstance(),in.lastInstance()),2)) / (ncPoints + 1);
                         ncPoints = ncPoints + 1;
                     }                     
                 }
                 else
                 {
                     outliers.add(xk);
                     if(outliers.size() > min(100, k*0.05))
                     {
                         outliers.remove(0);
                     }
                     //double nOutliers = maximum number of outliers close to each other in the outlier set
                     
                 }
             }
           k = k+1;
                
         }
         
     }
     
}
