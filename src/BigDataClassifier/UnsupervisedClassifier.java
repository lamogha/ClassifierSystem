/*
 * unsupervised classifier algorithm
 */
package BigDataClassifier;

import BigDataClassifier.AutoProbClass;
import java.util.ArrayList;
import java.util.ListIterator;
import weka.clusterers.*;
import weka.core.Instances;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.clusterers.AbstractDensityBasedClusterer;
import weka.core.DenseInstance;
import java.util.Collections;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.core.NormalizableDistance;
import weka.core.Utils;
import weka.core.converters.ArffLoader;

public class UnsupervisedClassifier {

    private static final int CLOSENESS_THRESHOLD = 1;
    private static ArrayList<DenseInstance> cloud = new ArrayList<>();
    private static ArrayList<String> cloudLabels = new ArrayList<>();
    private static ArrayList<String> labels = new ArrayList<>();
    private static ArrayList<Double> euD = new ArrayList<>();
    private static ArrayList<DenseInstance> outliers = new ArrayList<>();
    private static ArrayList<Float> simPercent = new ArrayList<>();
    private static EuclideanDistance eu;
    private static double instanceNewIdentifier, instanceOldIdentifier;
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
    public void evaluatorClusterer(Instances dataset, Clusterer clusterer) throws Exception {

        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(clusterer);
        eval.evaluateClusterer(dataset);
        System.out.println(eval.clusterResultsToString());
        System.out.println("# of clusters" + eval.getNumClusters());
        //System.out.print(eval.evaluateClusterer(testDataset, null, false);
    }

    public Clusterer useEMClusterer(Instances dataset) {
        EM newEm = new EM();
        try {
//            String[] options= new String[2];
//            //number of folds to use when cross-validating to find best number of clusters
//            options[0]="-X"; 
//            options[1]="10";
////            //set the number of clusters (-1 to select by cross validation)
////            newEm.setNumClusters(-1);
//            newEm.setOptions(options);
            //set distance function 
            //newEm.setDistancefunction(new weka.core.ManhattanDistance);
            newEm.buildClusterer(dataset);
            //System.out.println(newEm);
            // output predictions
            System.out.println("# - cluster - distribution");
            for (int i = 0; i < dataset.numInstances(); i++) {
                int cluster = newEm.clusterInstance(dataset.instance(i));
                double[] dist = newEm.distributionForInstance(dataset.instance(i));
                System.out.print((i + 1));
                System.out.print(" - ");
                System.out.print(cluster);
                System.out.print(" - ");
                System.out.print(Utils.arrayToString(dist));
                System.out.println();
            }
            clustEval.evaluatorClusterer(dataset, newEm);
            //return an array containing estimated membership probabilities of the test instance in each cluster
            //newEm.distributionForInstance(instance);
        } catch (Exception e) {
        }
        return newEm;
    }
//    

    public Clusterer useFarthestFirst(Instances dataset) {
        FarthestFirst ff = new FarthestFirst();
        try {
            ff.buildClusterer(dataset);
            System.out.println(ff);
        } catch (Exception e) {
        }
        return ff;
    }

    public Clusterer useCanopy(Instances dataset) {
        Canopy canopy = new Canopy();
        try {
            canopy.buildClusterer(dataset);
            System.out.println(canopy);
        } catch (Exception e) {
        }
        return canopy;
    }

    public Clusterer useCobweb(Instances dataset) {
        Cobweb cobweb = new Cobweb();
        try {
            cobweb.buildClusterer(dataset);
            System.out.println(cobweb);
            Instance current;
            ArffLoader loader = new ArffLoader();
            while ((current = loader.getNextInstance(dataset)) != null) {
                cobweb.updateClusterer(current);
            }
            cobweb.updateFinished();
            // output generated model
            System.out.println(cobweb);
        } catch (Exception e) {
        }
        return cobweb;
    }

    public Clusterer useSimpleKMeans(Instances dataset) {
        SimpleKMeans simpleK = new SimpleKMeans();
        try {
            simpleK.buildClusterer(dataset);
            System.out.println(simpleK);
        } catch (Exception e) {
        }
        return simpleK;
    }

    //The default is using the simpleKmeans clusterer.
    public Clusterer useMakeDensityBasedClusterer(Instances dataset) {
        MakeDensityBasedClusterer densityBased = new MakeDensityBasedClusterer();
        try {
            densityBased.buildClusterer(dataset);
            System.out.println(densityBased);
        } catch (Exception e) {
        }
        return densityBased;
    }
    
     //The default is using the simpleKmeans clusterer.
    public Clusterer useMakeDBCWithEM(Instances dataset) {
        MakeDensityBasedClusterer densityBasedEM = new MakeDensityBasedClusterer(new EM());
        try {
            densityBasedEM.buildClusterer(dataset);
            System.out.println(densityBasedEM);
        } catch (Exception e) {
        }
        return densityBasedEM;
    }

    public Clusterer useAutoProbClass(Instances dataset) throws Exception {

        AutoProbClass autoClust = new AutoProbClass(dataset);

        try {
            autoClust.buildClusterer(dataset);
            System.out.println(autoClust);
            // System.out.println(nb.distributionForInstance(dataset.instance(15)));
            // System.out.println(nb.getCapabilities().toString());
        } catch (Exception e) {
            throw e;
        }

        return autoClust;
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
            int newLabelCounter = 0;
            Instances xk = new Instances(dataset);
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
            System.out.println(String.format("<b> THE NUMBER OF CLUSTERS CREATED:== " + cloudLabels.size() + "</b>", 0));

            //System.out.println("==========");
            System.out.println(String.format("<b> THE CLASS LABELS CREATED ARE: " + cloudLabels + "</b>"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static ArrayList<Boolean> compareInstancesTest(DenseInstance instanceNew) {
        //String instanceAIdentifier = getInstanceIdentifier(instanceA);
        int trueScore = 0;
        int falseScore = 0;
        ArrayList<Boolean> howCloseList = new ArrayList<>();
        simPercent = new ArrayList<>();

        for (DenseInstance instanceOld : cloud) {
            //System.out.println("==================");
            //String instanceBIdentifier = getInstanceIdentifier(instanceB);
            for (int i = 0; i < instanceNew.numAttributes(); i++) {
                instanceNewIdentifier = instanceNew.value(i);
//               System.out.println("INSTANCE VALUE OF NEW data " + getInstanceIdentifier(instanceNew) +" @ INDEX POINT " + i + " IS  = " + instanceNewIdentifier );
                instanceOldIdentifier = instanceOld.value(i);
//               System.out.println("INSTANCE VALUE OF EXISTING data " + getInstanceIdentifier(instanceOld) + " @ INDEX POINT " + i + " IS  = " + instanceOldIdentifier );
                if (instanceNewIdentifier == instanceOldIdentifier) {
//                	System.out.println("true they are same \n");
                    trueScore = trueScore + 1;
                } else if (instanceNewIdentifier != instanceOldIdentifier) {
                    falseScore = falseScore + 1;
//                	System.out.println("false they are the same \n");
                }
            }

//            System.out.println("Disimilar estimate " + (float)falseScore/instanceOld.numAttributes());
            //find the fraction of the number of dissimilar values in %
            float diSimilarityMeasure = (float) falseScore / instanceOld.numAttributes() * 100;
            float similarityMeasure = 100 - diSimilarityMeasure;
            Instance instance1 = instanceNew;
            Instance instance2 = instanceOld;
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

}
