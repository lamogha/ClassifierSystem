/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import weka.classifiers.*;
import weka.clusterers.*;
import weka.core.*;
import java.math.*;

/**
 *
 * @author u1457710
 */
public class AutoProbClass extends weka.clusterers.AbstractDensityBasedClusterer implements TechnicalInformationHandler,
        NumberOfClustersRequestable, UpdateableClusterer, OptionHandler, WeightedInstancesHandler {

    /**
     * Minimum improvement in log likelihood when iterating
     */
    protected double m_minLogLikelihoodImprovementIterating = 1e-6;

    private static final int CLOSENESS_THRESHOLD = 1;
    private static final ArrayList<DenseInstance> cloud = new ArrayList<>();
    private static final ArrayList<String> cloudLabels = new ArrayList<>();
    private static final ArrayList<String> labels = new ArrayList<>();
    private static final ArrayList<Double> euD = new ArrayList<>();
    private static final ArrayList<DenseInstance> outliers = new ArrayList<>();
    private static ArrayList<Float> simPercent = new ArrayList<>();
    private static EuclideanDistance eu;
    private static double instanceNewIdentifier, instanceOldIdentifier;
    private static double tLogT = 0.0;
    private static double fLogF = 0.0;
    private static double entropy = 0.0;

    //private static ArrayList<DenseInstance> oldOutliers = new ArrayList<>();
    AbstractDensityBasedClusterer densityClass = new MakeDensityBasedClusterer();
    //CheckClusterer check = new CheckClusterer();
    ClusterEvaluator clustEval = new ClusterEvaluator();

    /**
     * Returns a string describing this clusterer.
     *
     * @return a description of the evaluator suitable for displaying in the
     * explorer/experimenter gui
     */
    public String globalInfo() {
        return "Cluster data using the autoProbClass clustering algorithm, which requires just "
                + "one pass over the data. Can run in either"
                + "batch or incremental mode. Results are generally not as good when "
                + "running incrementally as the min/max for each numeric attribute is not "
                + "known in advance. Has a heuristic (based on attribute std. deviations), "
                + "that can be used in batch mode, for setting the T2 distance. The T2 distance "
                + "determines how many canopies (clusters) are formed. When the user specifies "
                + "a specific number (N) of clusters to generate, the algorithm will return the "
                + "top N canopies (as determined by T2 density) when N < number of canopies "
                + "(this applies to both batch and incremental learning); "
                + "when N > number of canopies, the difference is made up by selecting training "
                + "instances randomly (this can only be done when batch training). For more "
                + "information see:\n\n" + getTechnicalInformation().toString();

    }

    @Override
    public TechnicalInformation getTechnicalInformation() {
        TechnicalInformation result;

        result = new TechnicalInformation(TechnicalInformation.Type.INPROCEEDINGS);
        result.setValue(TechnicalInformation.Field.AUTHOR, "Lamogha Ighoroje");
        result
                .setValue(
                        TechnicalInformation.Field.TITLE,
                        "Efficient Clustering of High Dimensional Data Sets with Application to Similarity measures");
        result.setValue(TechnicalInformation.Field.BOOKTITLE,
                "Proceedings of the sixth ACM SIGKDD internation conference on "
                + "knowledge discovery and data mining "
                + "ACM-SIAM symposium on Discrete algorithms");
        result.setValue(TechnicalInformation.Field.YEAR, "2018");
        result.setValue(TechnicalInformation.Field.PAGES, "169-178");

        return result;
    }

    @Override
    public void setNumClusters(int i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateClusterer(Instance instnc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateFinished() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildClusterer(Instances data) throws Exception {
        try {
            //data.setClassIndex(data.numAttributes()-1);
            //Define the initial zone of influence ZI
            double initialZI = 0.3;
            int k = 0;
            int newLabelCounter = 0;
            Instances xk = new Instances(data);
            //xk.deleteWithMissingClass();
            eu = new EuclideanDistance(xk);
            double ncZI = initialZI;
            int ncPoints = 0;
            //nomenclature for the mean of the data samples
            double ncFocalpoint = 0;
            ListIterator iterator = xk.listIterator();
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
                    cloudLabels.add("Class" + k);
                    labels.add("Class" + k);
                    //System.out.println(cloudLabels);
//                    Cloud cloudtoadd = new Cloud(ncFocalpoint,ncZI,ncPoints, (DenseInstance) xk.instance(k));
//                    clouds.add(cloudtoadd);
                    System.out.println("First instance added");
                    //System.out.println("==========");
                    System.out.println("The Instance identifier for first instance is: " + getInstanceIdentifier(nc));
                    //System.out.println("==========");
                } else {
                    //read next instance
                    DenseInstance nc = (DenseInstance) iterator.next();
                    //System.out.println(nc.numValues());
                    //get the identifier for that instance
                    System.out.println("\n" + "Instance identifier for the new instance is: " + getInstanceIdentifier(nc));
                    //System.out.println(nc.weight());
                    //System.out.println("==========");

                    ArrayList<Boolean> booleanList = compareInstancesTest(nc);
//                    ArrayList<String> similarListMeasure = compareInstancesTest(nc);
                    System.out.println("List of how close they are: " + booleanList);
                    //System.out.println("==========");
                    //System.out.println("Euclidean Distance estimates: " + euD);
                    //System.out.println("==========");
                    System.out.println("Similarity percentage: " + simPercent);
                    float maximum = Collections.max(simPercent);
                    System.out.println("The closest is " + maximum + "% at index point " + simPercent.indexOf(maximum));

                    System.out.println("==========");
                    if (maximum != 0.0) {
                        //cloudLabels.add("Class"+ simPercent.indexOf(maximum));
                        labels.add(labels.get(simPercent.indexOf(maximum)));
                        //System.out.println(labels);
                    } else {
                        cloudLabels.add("Class" + cloudLabels.size());
                        labels.add(cloudLabels.get(cloudLabels.size() - 1));
                        //System.out.println(labels);
                        //newLabelCounter++;
//                        System.out.println("THE NUMBER OF CLUSTERS CREATED: " +"\n"+cloudLabels.size());
//                        
//                        //System.out.println("==========");
//                        System.out.println("THE CLASS LABELS CREATED ARE: " +"\n"+ cloudLabels);
                    }

                }
                k = k + 1;

            }
            System.out.println("THE NUMBER OF CLUSTERS CREATED: " + "\n" + this.numberOfClusters());

            //System.out.println("==========");
            System.out.println("THE CLASS LABELS CREATED ARE: " + "\n" + cloudLabels);
            System.out.println("THE MIN LOG LIKELIHOOD: " + "\n" + this.getMinLogLikelihoodImprovementIterating());

        } catch (Exception e) {
        }
    }

    @Override
    public int clusterInstance(Instance instance) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numberOfClusters() throws Exception {
        return cloudLabels.size();

    }

    @Override
    public Capabilities getCapabilities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the tip text for this property
     *
     * @return tip text for this property suitable for displaying in the
     * explorer/experimenter gui
     */
    public String minLogLikelihoodImprovementIteratingTipText() {
        return "The minimum improvement in log likelihood required to "
                + "perform another iteration of the E and M steps";
    }

    /**
     * Set the minimum improvement in log likelihood necessary to perform
     * another iteration of the E and M steps.
     *
     * @param min the minimum improvement in log likelihood
     */
    public void setMinLogLikelihoodImprovementIterating(double min) {
        m_minLogLikelihoodImprovementIterating = min;
    }

    /**
     * Get the minimum improvement in log likelihood necessary to perform
     * another iteration of the E and M steps.
     *
     * @return the minimum improvement in log likelihood
     */
    public double getMinLogLikelihoodImprovementIterating() {
        return m_minLogLikelihoodImprovementIterating;
    }

    private static ArrayList<Boolean> compareInstancesTest(DenseInstance instanceNew) {
        //String instanceAIdentifier = getInstanceIdentifier(instanceA);
        double trueScore = 0;
        double falseScore = 0;
        ArrayList<Boolean> howCloseList = new ArrayList<>();
        simPercent = new ArrayList<>();

        for (DenseInstance instanceOld : cloud) {
            //System.out.println("==================");
            //String instanceBIdentifier = getInstanceIdentifier(instanceB);
            int numOfAtt = (int) instanceOld.numAttributes();
            //System.out.println("------------------ " + numOfAtt);
            for (int i = 0; i < numOfAtt; i++) {
                instanceNewIdentifier = instanceNew.value(i);
//               System.out.println("INSTANCE VALUE OF NEW data " + getInstanceIdentifier(instanceNew) +" @ INDEX POINT " + i + " IS  = " + instanceNewIdentifier );
                instanceOldIdentifier = instanceOld.value(i);
//               System.out.println("INSTANCE VALUE OF EXISTING data " + getInstanceIdentifier(instanceOld) + " @ INDEX POINT " + i + " IS  = " + instanceOldIdentifier );
                if (instanceNewIdentifier == instanceOldIdentifier) {
//                	System.out.println("true they are same \n");
                    trueScore = trueScore + 1;
                     tLogT = trueScore * ((float)Math.log(trueScore) / (float) Math.log(2));
                    //System.out.println("+++++++++++++++ T ENTROPY +++++++++++++ ===== " + tLogT);
                   // System.out.println("+++++++++++++++ T +++++++++++++ ===== " + trueScore);
                   
                } else if (instanceNewIdentifier != instanceOldIdentifier) {
                    falseScore = falseScore + 1;
                     fLogF = falseScore * ((float) Math.log(falseScore) / (float)Math.log(2));
                    //System.out.println("+++++++++++++++ F ENTROPY +++++++++++++ ===== " + fLogF);
//                	System.out.println("false they are the same \n");
                }
                
       
                entropy = -fLogF - tLogT;
                //System.out.println("+++++++++++++++ ENTROPY SCORE +++++++++++++ ===== " + entropy);
            }

//            System.out.println("Disimilar estimate " + (float)falseScore/instanceOld.numAttributes());
            //find the fraction of the number of dissimilar values in %
            float diSimilarityMeasure = (float) falseScore / instanceOld.numAttributes() * 100;
            float similarityMeasure = 100 - diSimilarityMeasure;
            // Instance instance1 = instanceNew; Instance instance2 = instanceOld;
//            NormalizableDistance nd = new EuclideanDistance();
//            double euclideandistanceMeasure = eu.distance(instance1, instance2);
//            System.out.println("---------------- EUCdist " + euclideandistanceMeasure );
//            System.out.println("THEY ARE " + similarityMeasure + "% SIMLIAR");
//            System.out.println("SCORE PERCENTAGE OF THE NUMBER OF FALSE MATCHES " + diSimilarityMeasure + "%");

            if (diSimilarityMeasure > 20) {
                howCloseList.add(false);
                //The distance measure between 
                double distanceMeasure = eu.distance(instanceOld, instanceNew);
                euD.add(distanceMeasure);
                //System.out.println("---EUCLIDEAN DISTANCE MEASURE IS--- = " + distanceMeasure);
                simPercent.add(0.0f);
            } else {
                howCloseList.add(true);
                double distanceMeasure = eu.distance(instanceOld, instanceNew);
                euD.add(distanceMeasure);
                //System.out.println("---EUCLIDEAN DISTANCE MEASURE IS--- = " + distanceMeasure);
                simPercent.add(similarityMeasure);
            }

            //System.out.println("--TRUE SCORE = " + trueScore);
            //System.out.println("--FALSE SCORE = " + falseScore);
            trueScore = 0;
            falseScore = 0;
        }
        cloud.add(instanceNew);
        return howCloseList;
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

    public String toSource(String string) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] clusterPriors() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] logDensityPerClusterForInstance(Instance instnc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
