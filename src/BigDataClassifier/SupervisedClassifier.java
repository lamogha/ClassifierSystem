/*
 * supervisedClassifier algorithm
 */
package BigDataClassifier;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.classifiers.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SGD;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.meta.RandomSubSpace;
import weka.classifiers.meta.Stacking;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveUseless;
import weka.classifiers.meta.GridSearch;
import weka.classifiers.meta.AutoWEKAClassifier;
import weka.classifiers.trees.REPTree;
import weka.classifiers.functions.Dl4jMlpClassifier;
import weka.classifiers.meta.MultiClassClassifier;
import weka.classifiers.pmml.consumer.NeuralNetwork;
import weka.classifiers.pmml.consumer.PMMLClassifier;
import weka.classifiers.meta.AutoWEKAClassifier;
import weka.core.Utils;

/**
 *
 * @author lamogha
 */
public class SupervisedClassifier {

    private static FileTypeEnablerAndProcessor fp = new FileTypeEnablerAndProcessor();
    private static ArrayList<Classifier> classifiers = new ArrayList<>();

    public void supervisedClassifier(String filePathName) {

        //call your supervised classifier here with the path to the file 
    }

    /**
     * TODO
     *
     * @param dataset
     * @return a probabilistic classifier type
     * @throws java.lang.Exception
     */
    public Classifier useNaiveBayes(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        NaiveBayes nb = new NaiveBayes();
        long starttime = System.currentTimeMillis();
        nb.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return nb;
        // System.out.println(nb.distributionForInstance(dataset.instance(15)));
        // System.out.println(nb.getCapabilities().toString());
    }

    public Classifier useSGD(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        SGD sgd = new SGD();
        long starttime = System.currentTimeMillis();
        sgd.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return sgd;
    }

    public Classifier useJ48(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        J48 j48 = new J48();
        long starttime = System.currentTimeMillis();
        j48.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return j48;
    }

    public Classifier useZeroR(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        ZeroR zeroR = new ZeroR();
        long starttime = System.currentTimeMillis();
        zeroR.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return zeroR;
    }

    public Classifier useRandomForest(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        RandomForest randomForest = new RandomForest();
        long starttime = System.currentTimeMillis();
        randomForest.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return randomForest;
    }

    public Classifier useStacking(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);

        Stacking stacker = new Stacking();
        stacker.setMetaClassifier(new J48());
        Classifier[] classifiers = {
            new J48(), new NaiveBayes(), new RandomForest()
        };
        stacker.setClassifiers(classifiers);
        long starttime = System.currentTimeMillis();
        stacker.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return stacker;
    }

    public Classifier useBagging(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        Bagging bagger = new Bagging();
        bagger.setClassifier(new REPTree());
        long starttime = System.currentTimeMillis();
        bagger.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return bagger;
    }

    public Classifier useAdaBoostM1(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        AdaBoostM1 m1 = new AdaBoostM1();
        m1.setClassifier(new DecisionStump());
        m1.setNumIterations(classIndex + 1);
        long starttime = System.currentTimeMillis();
        m1.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return m1;
    }

    public Classifier useLWL(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        LWL lwl = new LWL();
        lwl.setClassifier(new DecisionStump());
        long starttime = System.currentTimeMillis();
        lwl.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return lwl;
    }

    public Classifier useAttBfJ48(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        AttributeSelectedClassifier attSelClass = new AttributeSelectedClassifier();
        attSelClass.setSearch(new BestFirst());
        attSelClass.setClassifier(new J48());
        long starttime = System.currentTimeMillis();
        attSelClass.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return attSelClass;
    }

    public Classifier useAttGswJ48(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        AttributeSelectedClassifier attSelClass = new AttributeSelectedClassifier();
        attSelClass.setSearch(new GreedyStepwise());
        attSelClass.setClassifier(new J48());
        long starttime = System.currentTimeMillis();
        attSelClass.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return attSelClass;
    }

    public Classifier useAttBfNB(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        AttributeSelectedClassifier attSelClass = new AttributeSelectedClassifier();
        attSelClass.setSearch(new BestFirst());
        attSelClass.setClassifier(new NaiveBayes());
        long starttime = System.currentTimeMillis();
        attSelClass.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return attSelClass;
    }

    public Classifier useAttBfRF(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        AttributeSelectedClassifier attSelClass = new AttributeSelectedClassifier();
        attSelClass.setSearch(new BestFirst());
        attSelClass.setClassifier(new RandomForest());
        long starttime = System.currentTimeMillis();
        attSelClass.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return attSelClass;
    }

    public Classifier useAttBfZeroR(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        AttributeSelectedClassifier attSelClass = new AttributeSelectedClassifier();
        attSelClass.setSearch(new BestFirst());
        attSelClass.setClassifier(new ZeroR());
        long starttime = System.currentTimeMillis();
        attSelClass.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return attSelClass;
    }

    public Classifier useRandomSubspace(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        RandomSubSpace randomSub = new RandomSubSpace();
        randomSub.setClassifier(new REPTree());
        long starttime = System.currentTimeMillis();
        randomSub.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return randomSub;
    }

    public Classifier useLinearRegression(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        LinearRegression lr = new LinearRegression();
        long starttime = System.currentTimeMillis();
        lr.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return lr;
    }

    public Classifier useLibSVM(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        LibSVM libSVM = new LibSVM();
        long starttime = System.currentTimeMillis();
        libSVM.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return libSVM;
    }

    public Classifier useMultilayerPerceptron(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        MultilayerPerceptron multiPercep = new MultilayerPerceptron();
        long starttime = System.currentTimeMillis();
        multiPercep.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return multiPercep;
    }

    public Classifier useREPTree(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        REPTree repTree = new REPTree();
        long starttime = System.currentTimeMillis();
        repTree.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return repTree;
    }

    public Classifier useKStar(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        KStar kStar = new KStar();
        long starttime = System.currentTimeMillis();
        kStar.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return kStar;
    }

    public Classifier useDl4j(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        Dl4jMlpClassifier dl4j = new Dl4jMlpClassifier();
        long starttime = System.currentTimeMillis();
        dl4j.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return dl4j;
    }

    public Classifier useMCCWithNB(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        MultiClassClassifier mccWNB = new MultiClassClassifier();
        mccWNB.setClassifier(new NaiveBayes());
        long starttime = System.currentTimeMillis();
        mccWNB.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return mccWNB;
    }

    public Classifier useMCCWithSGD(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        MultiClassClassifier mccWNB = new MultiClassClassifier();
        mccWNB.setClassifier(new SGD());
        long starttime = System.currentTimeMillis();
        mccWNB.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return mccWNB;
    }

    public Classifier useMCCWithJ48(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        MultiClassClassifier mccWNB = new MultiClassClassifier();
        mccWNB.setClassifier(new J48());
        long starttime = System.currentTimeMillis();
        mccWNB.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return mccWNB;
    }

    public Classifier useMCCWithRF(Instances dataset, int classIndex) throws Exception {

        dataset.setClassIndex(classIndex);
        MultiClassClassifier mccWNB = new MultiClassClassifier();
        mccWNB.setClassifier(new RandomForest());
        long starttime = System.currentTimeMillis();
        mccWNB.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return mccWNB;
    }
    
    public Classifier useAutoWeka(Instances dataset, int classIndex) throws Exception{
        dataset.setClassIndex(classIndex);
        AutoWEKAClassifier autoW = new AutoWEKAClassifier();
        long starttime = System.currentTimeMillis();
        autoW.buildClassifier(dataset);
        long stoptime = System.currentTimeMillis();
        long elapsedtime = stoptime - starttime;
        System.out.println("\nTime taken to build model: " + Utils.doubleToString(elapsedtime / 1000.0, 2) + " seconds\n");
        return autoW;
    }
    /**
     *
     * @param dataset
     * @param classIndex
     * @return
     */
    public ArrayList getClassifiersLists(Instances dataset, int classIndex){
        try {
            classifiers.add(this.useAdaBoostM1(dataset, classIndex));
            classifiers.add(this.useAttBfJ48(dataset, classIndex));
            classifiers.add(this.useAttBfNB(dataset, classIndex));
            classifiers.add(this.useAttBfRF(dataset, classIndex));
            classifiers.add(this.useAttBfZeroR(dataset, classIndex));
            classifiers.add(this.useAttGswJ48(dataset, classIndex));
            classifiers.add(this.useBagging(dataset, classIndex));
            //classifiers.add(this.useDl4j(dataset, classIndex));
            classifiers.add(this.useJ48(dataset, classIndex));
            classifiers.add(this.useKStar(dataset, classIndex));
            classifiers.add(this.useLWL(dataset, classIndex));
            //classifiers.add(this.useLibSVM(dataset, classIndex));
            classifiers.add(this.useLinearRegression(dataset, classIndex));
            classifiers.add(this.useMCCWithJ48(dataset, classIndex));
            classifiers.add(this.useMCCWithNB(dataset, classIndex));
            classifiers.add(this.useMCCWithRF(dataset, classIndex));
            classifiers.add(this.useMCCWithSGD(dataset, classIndex));
            classifiers.add(this.useMultilayerPerceptron(dataset, classIndex));
            classifiers.add(this.useNaiveBayes(dataset, classIndex));
            classifiers.add(this.useREPTree(dataset, classIndex));
            classifiers.add(this.useRandomForest(dataset, classIndex));
            classifiers.add(this.useRandomSubspace(dataset, classIndex));
            classifiers.add(this.useSGD(dataset, classIndex));
            classifiers.add(this.useStacking(dataset, classIndex));
            classifiers.add(this.useZeroR(dataset, classIndex));
            
        } catch (Exception ex) {
            Logger.getLogger(SupervisedClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classifiers;
    }
}
