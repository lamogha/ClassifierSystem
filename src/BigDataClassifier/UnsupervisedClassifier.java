/*
 * unsupervised classifier algorithm
 */
package src.BigDataClassifier;

import java.util.ArrayList;
import java.util.ListIterator;
import weka.clusterers.*;
import weka.core.Instances;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.clusterers.AbstractDensityBasedClusterer;
import weka.core.DenseInstance;
import java.math.*;
import java.util.Collections;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;

public class UnsupervisedClassifier {

    private static final int CLOSENESS_THRESHOLD = 1;
    private static ArrayList<DenseInstance> cloud = new ArrayList<>();
    private static ArrayList<String> cloudLabels = new ArrayList<>();
    private static ArrayList<Cloud> clouds = new ArrayList<>();
    private static ArrayList<DenseInstance> outliers = new ArrayList<>();
    private static ArrayList<Float> simPercent = new ArrayList<>();
    //private static ArrayList<DenseInstance> oldOutliers = new ArrayList<>();
    AbstractDensityBasedClusterer densityClass = new MakeDensityBasedClusterer();
    //CheckClusterer check = new CheckClusterer();
    ClusterEvaluator clustEval = new ClusterEvaluator();

    /**
     *
     */
    public void UnsupervisedClassifier() {
        //constructor 
    }

      /**
     *
     * @param dataset
     * @param clusterer
     * @throws Exception
     */
    public void evaluatorClusterer(Instances dataset, SimpleKMeans clusterer) throws Exception{
              
  	      ClusterEvaluation eval = new ClusterEvaluation ();
              eval.setClusterer(clusterer);
              eval.evaluateClusterer(dataset);
	      System.out.println(eval.clusterResultsToString());
              System.out.println("# of clusters" + eval.getNumClusters());
	      //System.out.print(eval.evaluateClusterer(testDataset, null, false);
    }
    
    public Clusterer useEMClusterer(Instances dataset) {
        EM newEm = new EM();
        try {
            dataset.setClassIndex(dataset.numAttributes()-1);
            newEm.setNumClusters(4);
            //set distance function 
            //newEm.setDistancefunction(new weka.core.ManhattanDistance);
            newEm.buildClusterer(dataset);
            System.out.println(newEm);
            clustEval.evaluatorClusterer(dataset, newEm);
            //return an array containing estimated membership probabilities of the test instance in each cluster
            //newEm.distributionForInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newEm;
    }

    public FarthestFirst useFarthestFirst(Instances dataset) {

        FarthestFirst newFf = new FarthestFirst();

        try {
            dataset.setClassIndex(dataset.numAttributes() - 1);
            newFf.buildClusterer(dataset);
            clustEval.evaluatorClusterer(dataset, newFf);
            // System.out.println(nb.distributionForInstance(dataset.instance(15)));
            // System.out.println(nb.getCapabilities().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newFf;
    }

    public static Cloud getCloudWithLessPoints(ArrayList<Cloud> clouds) {

        Cloud returnCloud = null;

        try {

            double point = Integer.MAX_VALUE;
            for (Cloud cloudLessPoint : clouds) {
                if (cloudLessPoint.getPoint() < point) {
                    point = cloudLessPoint.getPoint();
                    returnCloud = cloudLessPoint;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnCloud;
    }

    public Double getHighestDensityInOutliers(ArrayList<DenseInstance> outliers) {

        double highestDensity = Integer.MAX_VALUE;

        try {

            for (DenseInstance outlier : outliers) {
                double density = densityClass.logDensityForInstance(outlier);
                if (density > highestDensity) {
                    highestDensity = density;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return highestDensity;
    }

    public double getAverageDensityofClouds() {

        double averageDensity = 0.0;

        try {

            int sumDense = 0;
            int i = 0;
            //to get the density of all existing clouds, to use in findind average density
            while (i < clouds.size()) {
                double densityOfEach = densityClass.logDensityForInstance(clouds.get(i).getDenseInstance());
                sumDense += densityOfEach;
                i++;
            }
            System.out.println("The sum of density for all existing cloud is: " + sumDense);
            averageDensity = sumDense / clouds.size();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return averageDensity;
    }

    public static double getMeanofInstances(ArrayList<DenseInstance> outliers) {

        double mean = 0.0;

        try {
            double weights = 0;
            for (DenseInstance out : outliers) {
                weights += out.weight();
            }
            mean = weights / outliers.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mean;
    }

    public static double getMeanOfZI(ArrayList<Cloud> clouds) {

        double meanZI = 0.0;

        try {
            double zoneOfInfluence = 0;
            for (Cloud cloudZI : clouds) {
                zoneOfInfluence += cloudZI.getZoneOfInfluence();
            }
            meanZI = zoneOfInfluence / clouds.size();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return meanZI;
    }

    /**
     *
     * @param dataset
     */
    public void autoProbClass(Instances dataset) {

        try {

            //Define the initial zone of influence ZI
            double initialZI = 0.3;
            int k = 0;
            Instances xk = new Instances(dataset);
            double ncZI = initialZI;
            int ncPoints = 0;
            //nomenclature for the mean of the data samples
            double ncFocalpoint = 0;
            ListIterator iterator = xk.listIterator();
            EuclideanDistance eu = new EuclideanDistance();
            //start reading in the instances
            while (iterator.hasNext()) {
                if (k == 0) {
                    DenseInstance nc = (DenseInstance) iterator.next();
                    //nc.setClassValue(k);
                    ncFocalpoint = xk.meanOrMode(k);
                    ncZI = initialZI;
                    ncPoints = 1;
                    //first inference rule
                    cloud.add(nc);
                    cloudLabels.add("Class"+k);
                    System.out.println(cloudLabels);
                    Cloud cloudtoadd = new Cloud(ncFocalpoint,ncZI,ncPoints, (DenseInstance) xk.instance(k));
                    clouds.add(cloudtoadd);
                    System.out.println("First cloud added");
                    System.out.println("Instance identifier for first cloud " + nc + " is: " + getInstanceIdentifier(nc));
                    System.out.println("==========");
                } else {

                    DenseInstance nc = (DenseInstance) iterator.next();
                    //System.out.println(nc.numValues());
                    System.out.println("\n" + "Instance identifier for " + nc + " is: " + getInstanceIdentifier(nc));
                    //System.out.println(nc.weight());
                    System.out.println("==========");

                    ArrayList<Boolean> booleanList = compareInstancesTest(nc);
//                    ArrayList<String> similarListMeasure = compareInstancesTest(nc);
                    System.out.println("List of how close they are: " + booleanList);
                    System.out.println("List of how close they are: " + simPercent);
                    
                    for (int i = 0; i<booleanList.size();i++){
                        if(booleanList.get(i).equals(true)){
                        float maximum = Collections.max(simPercent);
                        System.out.println("The closest is " + maximum + "% at index point " + simPercent.indexOf(maximum) ); 
                        }  
                        else{
                            System.out.println("Not relevant");
                        }
                    }
                    
                }
                k = k + 1;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static ArrayList<Boolean> compareInstancesTest(DenseInstance instanceNew) {
        //String instanceAIdentifier = getInstanceIdentifier(instanceA);
        int trueScore = 0;
        int falseScore = 0;
        int j = 0;
        ArrayList<Boolean> howCloseList = new ArrayList<>();
        ArrayList<String> trueList = new ArrayList<>();
        
        for (DenseInstance instanceOld : cloud) {
            System.out.println("==================");
            //String instanceBIdentifier = getInstanceIdentifier(instanceB);
            for (int i = 0; i < instanceNew.numAttributes(); i++) {
               double instanceNewIdentifier = instanceNew.value(i);
//               System.out.println("INSTANCE VALUE OF NEW data " + getInstanceIdentifier(instanceNew) +" @ INDEX POINT " + i + " IS  = " + instanceNewIdentifier );
               double instanceOldIdentifier = instanceOld.value(i);
//               System.out.println("INSTANCE VALUE OF EXISTING data " + getInstanceIdentifier(instanceOld) + " @ INDEX POINT " + i + " IS  = " + instanceOldIdentifier );
                if (instanceNewIdentifier == instanceOldIdentifier) {
//                	System.out.println("true they are same \n");
                        trueScore = trueScore + 1;
                }
                else if(instanceNewIdentifier != instanceOldIdentifier) {
                    falseScore = falseScore + 1;
//                	System.out.println("false they are the same \n");
                }
            }
            
            System.out.println("Disimilar estimate " + (float)falseScore/instanceOld.numAttributes());
            //find the fraction of the number of dissimilar values in %
            float diSimilarityMeasure = (float)falseScore/instanceOld.numAttributes() * 100;
            float similarityMeasure = 100 - diSimilarityMeasure;
            System.out.println("THEY ARE " + similarityMeasure + "% SIMLIAR");
            System.out.println("SCORE PERCENTAGE OF THE NUMBER OF FALSE MATCHES " + diSimilarityMeasure + "%");
            
            if (diSimilarityMeasure > 20){
                howCloseList.add(false);
                if (j==0){
                    simPercent.add(0.0f);
                }
                else {simPercent.set(j-1, 0.0f);}
                trueList.add("No Similarity with any previous Instance");
            }
            else{
                howCloseList.add(true);
                if (j==0){
                    simPercent.add(similarityMeasure);
                }
                else
                {simPercent.set(j-1, similarityMeasure);}
                trueList.add("With a similarity of: " + similarityMeasure + "%, "+true);
            }

            System.out.println("--TRUE SCORE = " + trueScore);
            System.out.println("--FALSE SCORE = " + falseScore);
            trueScore = 0;
            falseScore = 0;
            j++;
        }
       
        cloud.add(instanceNew);
        
        return howCloseList;
//          return trueList;

    } 

    private static String getInstanceIdentifier(Instance instance) {

        String identifier = "";

        try {
            for (int i = 0; i < instance.numAttributes(); i++) {
                //System.out.println(identifier);
                identifier += (int) instance.value(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return identifier;
    }

}
