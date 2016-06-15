/*
 * unsupervised classifier algorithm
 */
package BigDataClassifier;

import java.io.Reader;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import weka.clusterers.*;
import weka.core.Instances;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.clusterers.AbstractDensityBasedClusterer;

public abstract class UnsupervisedClassifier extends  AbstractDensityBasedClusterer {
    private static final HashSet<Instances> cloud = new HashSet<>();
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
     
     public FarthestFirst useFarthestFirst(Instances dataset) throws Exception {
      	  
		  dataset.setClassIndex(dataset.numAttributes()-1);
		  FarthestFirst newFf = new FarthestFirst();
		  newFf.buildClusterer(dataset);
  	     return newFf;
  	 // System.out.println(nb.distributionForInstance(dataset.instance(15)));
  	 // System.out.println(nb.getCapabilities().toString());
     }
     
    /**
     *
     * @param dataset
     * @throws java.lang.Exception
     */
    public void autoProbClass(Instances dataset)throws Exception {
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
                 Instances nc = (Instances) xk.firstInstance();
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
                     for (Instances j : cloud) {
                         Integer cc = j.indexOf((int) (2*ncZI));
                         ncFocalpoint = (ncFocalpoint * ncPoints + (xk.get(k).numValues())) / (ncPoints + 1);
                         List<Double> attribute;
                         attribute = Arrays.asList((double)(xk.get(k).numValues()), ncFocalpoint);
                         Instances in = new Instances ((Reader) attribute);
                         ncZI = (ncZI * ncPoints + Math.pow(eu.distance(in.firstInstance(),in.lastInstance()),2)) / (ncPoints + 1);
                         ncPoints = ncPoints + 1;
                         System.out.println(cc.intValue());
                         cc++;
                     }                     
                 }
                 else
                 {
                     outliers.add((Instances) xk.get(k));
                     List <Instances> listout = new ArrayList<>(outliers);
                     Instances out = new Instances ((Reader) listout);
                     EuclideanDistance eu2 = new EuclideanDistance (out);
                    if(outliers.size() > min(100, k*0.05))
                    {
                        listout.remove(0);
                        out.delete(0);
                    }
                     //nOutliers equals maximum number of outliers close to each other in the outliers vector
                     int nOutliers = outliers.size();
                     List <Instances> cloudlist = new ArrayList<>(cloud);
                     Instances cloudInstances = new Instances ((Reader) cloudlist);
                     System.out.println(cloudlist.get(k));
                     
                        if (!(cloudInstances.instance(k-1).equals(cloudInstances.instance(k))))
                        {
                         Instance sc = cloudInstances.instance(k-1); //get the cloud with less points.
                         System.out.println(sc.toString());
                        }
                        double minPoints = max(3, (ncPoints*0.15) );
                        double density = this.logDensityForInstance(out.instance(k));
                     
                            int sumDense = 0;
                            int i = 0;
                            //to get the density of all existing clouds, to use in findind average density
                            while (i<cloudInstances.size())
                            {
                                double densityOfEach = this.logDensityForInstance(cloudInstances.instance(i));
                                sumDense += densityOfEach;
                                i++;
                             }
                        System.out.println("The sum of density for all existing cloud is: " + sumDense);
                        double averageDensity = sumDense / cloudInstances.size();
                    //if conditions are met, then create new cloud.
                    if ( (nOutliers > minPoints) && (density > averageDensity)) 
                    {
                        /*Create a new cloud*/
                        Instances nc =  (Instances) xk.instance(k); //new cloud
                        double meanOfNewCloud = nc.meanOrMode(0); // mean of all data samples associated with nc
                        ncFocalpoint = meanOfNewCloud; // focal point of nc equals mean of nc
                        double meanZI = cloudInstances.meanOrMode(1); //mean of ZI of all existing clouds
                        ncZI = meanZI*0.5 + initialZI;
                        ncPoints = nc.size(); //number of instances associated with nc
                        cloud.add(nc);
                        listout.remove(0);
                        out.delete(0);
                    } 
                    else
                    {
                        
                    }
                 }
             }
           k = k+1;
                
         }
         
     }
     
}
