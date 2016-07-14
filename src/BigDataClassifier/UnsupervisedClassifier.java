/*
 * unsupervised classifier algorithm
 */
package src.BigDataClassifier;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import weka.clusterers.*;
import weka.core.Instances;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.clusterers.AbstractDensityBasedClusterer;
import weka.core.DenseInstance;

public class UnsupervisedClassifier {

    private static final int CLOSENESS_THRESHOLD = 1;
    private static ArrayList<DenseInstance> cloud = new ArrayList<>();
    private static ArrayList<Cloud> clouds = new ArrayList<>();
    private static ArrayList<DenseInstance> outliers = new ArrayList<>();
    //private static ArrayList<DenseInstance> oldOutliers = new ArrayList<>();
    AbstractDensityBasedClusterer densityClass = new MakeDensityBasedClusterer();

    /**
     *
     */
    public void UnsupervisedClassifier() {
        //constructor 
    }

    public void useEMClusterer(Instances dataset) throws Exception {

        //dataset.setClassIndex(dataset.numAttributes()-1);
        EM newEm = new EM();
        newEm.setNumClusters(4);
        newEm.buildClusterer(dataset);
        System.out.println(newEm);
    }

    public FarthestFirst useFarthestFirst(Instances dataset) throws Exception {

        dataset.setClassIndex(dataset.numAttributes() - 1);
        FarthestFirst newFf = new FarthestFirst();
        newFf.buildClusterer(dataset);
        return newFf;
        // System.out.println(nb.distributionForInstance(dataset.instance(15)));
        // System.out.println(nb.getCapabilities().toString());
    }

    public void probClass(Instances dataset) throws Exception {
        int k = 0;
        //Define the intial zone of influence ZI
        double initialZI = 0.3;
        Instances xk = new Instances(dataset);
        ListIterator iterator = xk.listIterator();
        EuclideanDistance eu = new EuclideanDistance();

        while (iterator.hasNext()) {
            DenseInstance instance = (DenseInstance) iterator.next();
            if (k == 0) {
                /**
                 * Create First Cloud *
                 */
                Cloud nc = new Cloud();
                nc.setFocalPoint(Double.parseDouble(getInstanceIdentifier(instance)));
                nc.setZoneOfInfluence(initialZI);
                nc.setPoint(1);
                clouds.add(nc);
            } else {
                Cloud existingCloud = clouds.get(k);
                if (compareInstances(instance, existingCloud.getDenseInstance())) {
                    for (int j = 0; j < clouds.size(); j++) {
                        Cloud cc = clouds.get(j);
                        double identifier = Double.parseDouble(getInstanceIdentifier(cc.getDenseInstance()));
                        double denominator = (cc.getPoint() + 1);
                        cc.setFocalPoint((cc.getFocalPoint() * cc.getPoint() + identifier) / denominator);
                        cc.setZoneOfInfluence((cc.getZoneOfInfluence() * cc.getPoint() + eu.distance(existingCloud.getDenseInstance(), instance)) / denominator);
                        cc.setPoint(cc.getPoint() + 1);
                    }
                } else {
                    /**
                     * It is an outlier
                     */
                    double identifier = Double.parseDouble(getInstanceIdentifier(instance));
                    outliers.add(instance);
                    if (outliers.size() > Math.min(100, k * 0.05)) {
                        outliers.remove(0);
                    }
                    int nOutliers = 2; //TODO Calculate this
                    Cloud sc = getCloudWithLessPoints(clouds);
                    double minPoints = Math.max(3, sc.getPoint() * 0.15);
                    double density = getHighestDensityInOutliers(outliers);
                    double averageDensity = getAverageDensityofClouds();
                    if (nOutliers > minPoints &&  density > averageDensity){
                        Cloud nc = new Cloud();
                        double meanOfDataSamples = getMeanofInstances(outliers);
                        nc.setFocalPoint(meanOfDataSamples);
                        double meanZIforAllExistingClouds = getMeanOfZI(clouds);
                        nc.setZoneOfInfluence(meanZIforAllExistingClouds * 0.5 + initialZI);
                        nc.setPoint(clouds.size());
                        clouds.add(nc);
                    }
                    else{
                        //do nothing, because it is an outlier
                    }
                }
            }
            k++;
        }
    }

    public static Cloud getCloudWithLessPoints(ArrayList<Cloud> clouds) {
        Cloud returnCloud = null;
        double point = Integer.MAX_VALUE;
        for (Cloud cloudLessPoint : clouds) {
            if (cloudLessPoint.getPoint() < point) {
                point = cloudLessPoint.getPoint();
                returnCloud = cloudLessPoint;
            }
        }
        return returnCloud;
    }

    public Double getHighestDensityInOutliers(ArrayList<DenseInstance> outliers) throws Exception {
        double highestDensity = Integer.MAX_VALUE;
        for (DenseInstance outlier : outliers) {
            double density = densityClass.logDensityForInstance(outlier);
            if (density > highestDensity) {
                highestDensity = density;
            }
        }
        return highestDensity;
    }
    
    public double getAverageDensityofClouds() throws Exception{
        int sumDense = 0;
        int i = 0;
        //to get the density of all existing clouds, to use in findind average density
            while (i<clouds.size())
                {
                    double densityOfEach = densityClass.logDensityForInstance(clouds.get(i).getDenseInstance());
                    sumDense += densityOfEach;
                    i++;
                }
        System.out.println("The sum of density for all existing cloud is: " + sumDense);
        double averageDensity = sumDense / clouds.size();
        return averageDensity;
    }
     
     public static double getMeanofInstances(ArrayList<DenseInstance> outliers) {
        double mean;
        double weights = 0;
        for (DenseInstance out : outliers) {
            weights += out.weight();
            }
        mean = weights / outliers.size();
        return mean;
    }
    
     public static double getMeanOfZI(ArrayList<Cloud> clouds) {
        double zoneOfInfluence = 0;
        for (Cloud cloudZI : clouds) {
            zoneOfInfluence += cloudZI.getZoneOfInfluence();
        }
        double meanZI = zoneOfInfluence / clouds.size();
        return meanZI;
    }
    /**
     *
     * @param dataset
     * @throws java.lang.Exception
     */
    public void autoProbClass(Instances dataset) throws Exception {
        //Define the intial zone of influence ZI
        double initialZI = 0.3;
        int k = 0;
        Instances xk = new Instances(dataset);
        double ncZI = initialZI;
        int ncPoints = 0;
        double ncFocalpoint = 0;
        ListIterator iterator = xk.listIterator();
        EuclideanDistance eu = new EuclideanDistance();
        //start reading in the instances
        while (iterator.hasNext()) {
            if (k == 0) {
                DenseInstance nc = (DenseInstance) iterator.next();
                nc.setClassValue(k);
                ncFocalpoint = xk.meanOrMode(k);
                ncZI = initialZI;
                cloud.add(nc);
                ncPoints = 1;
                System.out.println("First cloud added");
            } else {
                DenseInstance nc = (DenseInstance) iterator.next();
                //System.out.println(nc.numValues());
                System.out.println("Instance identifier for " + nc + " is: " + getInstanceIdentifier(nc));
                //System.out.println(nc.weight());
                System.out.println("==========");

                //update close clouds
                for (DenseInstance cloudInstance : cloud) {
                    if (compareInstances(nc, cloudInstance)) {
                    	System.out.println("Instance is close to previous instance, based on score");
                    }
                    else{
                    	System.out.println("Instance is not close to previous");
                    }
                }
//                    Integer cc = cloudInstance.indexOf((int) (2 * ncZI));
//                    ncFocalpoint = (ncFocalpoint * ncPoints + (xk.get(k).numValues())) / (ncPoints + 1);
//                    List<Double> attribute;
//                    attribute = Arrays.asList((double) (xk.get(k).numValues()), ncFocalpoint);
//                    Instances in = new Instances((Reader) attribute);
//                    ncZI = (ncZI * ncPoints + Math.pow(eu.distance(in.firstInstance(), in.lastInstance()), 2)) / (ncPoints + 1);
//                    ncPoints = ncPoints + 1;
//                    System.out.println(cc.intValue());
//                    cc++;
//                }
//                /**
//                 * else { outliers.add((Instances) xk.get(k)); List<Instances>
//                 * listout = new ArrayList<>(outliers); Instances out = new
//                 * Instances((Reader) listout); EuclideanDistance eu2 = new
//                 * EuclideanDistance(out); if (outliers.size() > min(100, k *
//                 * 0.05)) { listout.remove(0); out.delete(0); } //nOutliers
//                 * equals maximum number of outliers close to each other in the
//                 * outliers vector int nOutliers = outliers.size();
//                 * List<Instances> cloudlist = new ArrayList<>(cloud); Instances
//                 * cloudInstances = new Instances((Reader) cloudlist);
//                 * System.out.println(cloudlist.get(k));
//                 *
//                 * if (!(cloudInstances.instance(k -
//                 * 1).equals(cloudInstances.instance(k)))) { Instance sc =
//                 * cloudInstances.instance(k - 1); //get the cloud with less
//                 * points. System.out.println(sc.toString()); } double minPoints
//                 * = max(3, (ncPoints * 0.15)); double density =
//                 * densityClass.logDensityForInstance(out.instance(k));
//                 *
//                 * int sumDense = 0; int i = 0; //to get the density of all
//                 * existing clouds, to use in findind average density while (i < cloudInstances.size()) {
//                 * double densityOfEach = densityClass.logDensityForInstance(cloudInstances.instance(i));
//                 * sumDense += densityOfEach;
//                 * i++;
//                 * }
//                 * System.out.println("The sum of density for all existing cloud is: " + sumDense);
//                 * double averageDensity = sumDense / cloudInstances.size();
//                 * //if conditions are met, then create new cloud.
//                 * if ((nOutliers > minPoints) && (density > averageDensity)) {
//                 * // Create a new cloud Instances nc = (Instances)
//                 * xk.instance(k); //new cloud double meanOfNewCloud =
//                 * nc.meanOrMode(0); // mean of all data samples associated with
//                 * nc ncFocalpoint = meanOfNewCloud; // focal point of nc equals
//                 * mean of nc double meanZI = cloudInstances.meanOrMode(1);
//                 * //mean of ZI of all existing clouds ncZI = meanZI * 0.5 +
//                 * initialZI; ncPoints = nc.size(); //number of instances
//                 * associated with nc cloud.add(nc); listout.remove(0);
//                 * out.delete(0); } else {
//                 *
//                 * }
//                 * } *
//                 */
            }
            k = k + 1;

            }

    }


    private static boolean compareInstances(Instance instanceA, Instance instanceB) {
        String instanceAIdentifier = getInstanceIdentifier(instanceA);
        String instanceBIdentifier = getInstanceIdentifier(instanceB);
        int score = 0;
        for (int i = 0; i < instanceBIdentifier.length(); i++) {
            if (instanceAIdentifier.charAt(i) == instanceBIdentifier.charAt(i)) {
                score = score+1;
            }
        }
        System.out.println(score);
        return instanceA.numAttributes() - score <= CLOSENESS_THRESHOLD;
    }

    private static String getInstanceIdentifier(Instance instance) {
        String identifier = "";
        for (int i = 0; i < instance.numAttributes(); i++) {
            identifier += (int) instance.value(i);
        }
        return identifier;
    }

}
