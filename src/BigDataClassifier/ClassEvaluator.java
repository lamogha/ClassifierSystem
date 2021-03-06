package BigDataClassifier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import static weka.core.Attribute.NOMINAL;
import static weka.core.Attribute.NUMERIC;
import static weka.core.Attribute.RELATIONAL;
import static weka.core.Attribute.STRING;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

public class ClassEvaluator {
    
    Instances trainDataset2, testDataset2;
    int trainDatasetSize, testDatasetSize, testDatasetIndex;
    Classifier classifierModel;
    SupervisedClassifier sc = new SupervisedClassifier();
    UnsupervisedClassifier uc = new UnsupervisedClassifier();
    //ClusterEvaluator clusterEval = new ClusterEvaluator();
    private static FileTypeEnablerAndProcessor fp;
    private static ArrayList<Classifier> classifiers = new ArrayList<>();

    //set folds
    int folds = 3;
    int classIndex = 0;
    
    public ClassEvaluator() {
        fp = new FileTypeEnablerAndProcessor();
    }
    
    public void generateFolds(Instances trainDataset, int classIndexPassed) throws Exception {
        //randomize data
        Random rand = new Random(System.currentTimeMillis());
        //Random rand = new Random();

        //create random dataset
        Instances randData = new Instances(trainDataset);
        classIndex = classIndexPassed;
        //int classIndex = trainDataset.numAttributes()-1;
        randData.randomize(rand);
        //cross-validate with 3 folds
        for (int n = 0; n < folds; n++) {
            trainDataset = randData.trainCV(folds, n);
            System.out.println("Train dataset size is = " + trainDataset.size());
            Instances testDatasetGen = randData.testCV(folds, n);
            System.out.println("Test dataset size is = " + testDatasetGen.size());
            trainDataset2 = trainDataset;
            testDataset2 = testDatasetGen;
            trainDataset2.setClassIndex(classIndex);
            System.out.println("--------The number of class labels is:- " + trainDataset2.numClasses());
            //this.callClassifier(trainDataset2,testDataset2,classIndex);
        }
        System.out.println("--------Calling the right Classifier ------ ");
//        classifiers = sc.getClassifiersLists(trainDataset2, classIndex);
//        for (int i = 0; i < classifiers.size(); i++) {
//            classifierModel = classifiers.get(i);
//            this.evaluatorClassifier(trainDataset2, testDataset2, classifierModel);
//        }
        //classifierModel = sc.useStacking(trainDataset2, classIndex);
        //this.evaluatorClassifier(trainDataset2, testDataset2, classifierModel);
        this.callClassifier(trainDataset2, testDataset2, classIndex);
        
    }
    
    public int numOfNominalAtt(Instances trainDataset, Instances testDataset) {
        int nom = 0;
        for (int i = 0; i < trainDataset.numAttributes(); i++) {
            //boolean nominal = trainDataset2.checkForAttributeType(i);
            if (trainDataset.attribute(i).isNominal()) {
                nom++;
            }
        }
        //System.out.println(nom);
        return nom;
    }
    
    public int numOfNumericAtt(Instances trainDataset, Instances testDataset) {
        int num = 0;
        for (int i = 0; i < trainDataset.numAttributes(); i++) {
            //boolean nominal = trainDataset2.checkForAttributeType(i);
            if (trainDataset.attribute(i).isNumeric()) {
                num++;
            }
        }
        //System.out.println(num);
        return num;
    }
    
    public void callClassifier(Instances trainData, Instances testData, int classIndexPass) {
        classIndex = classIndexPass;
        trainDataset2 = trainData;
        trainData.setClassIndex(classIndex);
        testDatasetIndex = classIndex;
        testDataset2 = testData;
        //System.out.println("CLASS INDEX " + classIndex);
        System.out.println("-------------------------------------------------------");
        if ((trainData.size() >= testData.size()) && trainData.numClasses() != 0) {
            this.classifierChooser(trainData, testData, classIndexPass);
        } else {
            try {
                //we can use unsupervised classifier here, semi supervised or a very good supervised
                //uc.autoProbClass(testDataset2);
                this.classifierChooser(trainData, testData, classIndexPass);
//                classifierModel = sc.useAutoWeka(trainData, classIndex);
//                this.evaluatorClassifier(trainData, testData, classifierModel);
//                System.out.println("AutoWEKA engaged here" + "\n"
//                            + "-------------------------------------------------------");
                //uc.evaluatorClusterer(trainDataset2, (SimpleKMeans) uc.useEMClusterer(trainDataset2));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void classifierChooser(Instances trainData, Instances testData, int classIndexPass) {
        if (trainData.classAttribute().isNumeric()) {
            
            try {
                classifierModel = sc.useRandomForest(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> Random Forest used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else if (trainData.classAttribute().isNominal() && trainData.numInstances() <= 50 && trainData.numAttributes() <= 10
                && trainData.numAttributes() == this.numOfNominalAtt(trainData, testData)) {
            try {
                classifierModel = sc.useJ48(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> J48 used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } 
        else if (trainData.classAttribute().isNominal() && trainData.numInstances() >= 500 && trainData.numAttributes() > 10
                && this.numOfNumericAtt(trainData, testData) >= (this.numOfNominalAtt(trainData, testData)) / 2) {
            try {
                classifierModel = sc.useRandomForest(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> Random Forest used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else if (trainData.classAttribute().isNominal() && trainData.numInstances() < 100
                && this.numOfNominalAtt(trainData, testData) > this.numOfNumericAtt(trainData, testData)) {
            try {
                classifierModel = sc.useNaiveBayes(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> Naive Bayes used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else if (trainData.classAttribute().isNominal() && trainData.numInstances() > 500
                && trainData.numAttributes() <= 10 && this.numOfNumericAtt(trainData, testData) >= trainData.numAttributes() - 1) {
            try {
                classifierModel = sc.useSGD(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> SGD used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                    Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    classifierModel = sc.useAutoWeka(trainData, classIndex);
                    this.evaluatorClassifier(trainData, testData, classifierModel);
                    System.out.println(String.format("<b> AutoWEKA Engaged used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
                } catch (Exception ex1) {
                    Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            
        } 
        else if (trainData.classAttribute().isNominal() && trainData.numInstances() < 500
                && trainData.numAttributes() > 10 && this.numOfNumericAtt(trainData, testData) >= trainData.numAttributes() - 1) {
            try {
                classifierModel = sc.useRandomForest(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> Random Forest used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } //                 else if(trainData.classAttribute().isNominal() && trainData.numInstances()> 500 
        //                        && trainData.numAttributes()> 10 
        //                         && this.numOfNumericAtt(trainData, testData) >= this.numOfNominalAtt(trainData, testData))
        //                {
        //                    try {
        //                     this.evaluatorClassifier(trainData, testData, sc.useRandomForest(trainData, classIndex));
        //                     System.out.println("Random Forest Used used"+ "\n"
        //                             + "-------------------------------------------------------");
        //                    } catch (Exception ex) {
        //                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        //                    }
        //                    
        //                }
        else if (trainData.classAttribute().isNominal() && trainData.numInstances() > 500
                && trainData.numAttributes() < 50
                && this.numOfNominalAtt(trainData, testData) == trainData.numAttributes()) {
            try {
                classifierModel = sc.useNaiveBayes(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> Naive Bayes used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } 
        else if (trainData.classAttribute().isNominal() && trainData.numInstances() > 500
                && trainData.numAttributes() > 100
                && this.numOfNominalAtt(trainData, testData) == trainData.numAttributes()) {
            try {
                classifierModel = sc.useSGD(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> SGD used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                 try {
                    classifierModel = sc.useAutoWeka(trainData, classIndex);
                    this.evaluatorClassifier(trainData, testData, classifierModel);
                    System.out.println(String.format("<b> AutoWEKA Engaged used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
                } catch (Exception ex1) {
                    Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            
        } //                 
        //                else if (trainData.classAttribute().isNominal() && 
        //                        trainData.checkForStringAttributes())
        //                {
        //                    try {
        //                     this.evaluatorClassifier(trainData, testData, sc.useZeroR(trainData, classIndex));
        //                     System.out.println("Zero R used"+ "\n"
        //                             + "-------------------------------------------------------");
        //                    } catch (Exception ex) {
        //                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        //                    }
        //                    
        //                }
        else if (trainData.classAttribute().isNominal()
                && trainData.checkForAttributeType(STRING) == true) {
            try {
                classifierModel = sc.useZeroR(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println("Zero R used" + "\n"
                        + "-------------------------------------------------------");
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } //                
        //                else if (trainData.checkForAttributeType(RELATIONAL) 
        //                        && this.numOfNumericAtt(trainData, testData) >= trainData.numAttributes()-1)
        //                {
        //                    try {
        //                     this.evaluatorClassifier(trainData, testData, sc.useRandomForest(trainData, classIndex));
        //                     System.out.println("Random Forest used"+ "\n"
        //                             + "-------------------------------------------------------");
        //                    } catch (Exception ex) {
        //                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        //                    }   
        //                }
        //                
        else if (trainData.classAttribute().isNominal()
                && this.numOfNominalAtt(trainData, testData) >= 2 * (this.numOfNumericAtt(trainData, testData))) {
            try {
                classifierModel = sc.useNaiveBayes(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> Naive Bayes used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } 
        else {
            try {
                classifierModel = sc.useRandomForest(trainData, classIndex);
                //classifierModel = sc.useAutoWeka(trainData, classIndex);
                this.evaluatorClassifier(trainData, testData, classifierModel);
                System.out.println(String.format("<b> Random Forest used </b>" + "\n"
                        + "<b> -------------------------------------------------------</b>", 0));
            } catch (Exception ex) {
                Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//        public void useAutoProbClass(Instances data){
//            try {
//                 //AutoProbClass autoClust = new AutoProbClass();
//                    //use unsupervised classifier
//                    classifierModel = uc.useAutoProbClass(data);
//                    this.evaluatorClassifier(trainDataset2, testDataset2, classifierModel);
//                    //uc.evaluatorClusterer(trainDataset2, (SimpleKMeans) uc.useEMClusterer(trainDataset2));
//                } catch (Exception ex) {
//                    Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
//                }
//        }

    public String getPredictions() throws Exception {
        String s = "";
        //DirectoryChooser dirChooser = new DirectoryChooser();
        testDataset2.setClassIndex(testDatasetIndex);
        for (int i = 0; i < testDataset2.numInstances(); i++) {
            String actual = testDataset2.classAttribute().value((int) testDataset2.instance(i).classValue());
            Instance newInst = testDataset2.instance(i);
            String prediction = testDataset2.classAttribute().value((int) classifierModel.classifyInstance(newInst));
            if (actual.equalsIgnoreCase(prediction)) {
                s += "ACTUAL ====== " + actual + " , PREDICTED ====== " + prediction + "\n";
            } else {
                
                s += String.format("ACTUAL ====== <b>" + actual + " </b>, PREDICTED ====== " + prediction + "\n", 0);
            }

            //String.format("<b>" + s + "</b>", 1);
        }
        return s;
    }
    
    public void setPredictions(String s) throws Exception {
        s = this.getPredictions();
        System.out.println(s);
    }
    /**
     * public int getTrainDataSize(){ return trainDatasetSize; }
     *
     * public int getTestDataSize(){ return testDatasetSize; }
     *
     */
    Evaluation eval;
    
    public void evaluatorClassifier(Instances trainDataset, Instances testDataset, Classifier cs) throws Exception {
        testDataset.setClassIndex(classIndex);
        eval = new Evaluation(trainDataset);
        eval.evaluateModel(cs, testDataset);
        System.out.println(eval.toSummaryString("\nEvaluation results:\n", false));
        
        if (testDataset.classAttribute().isNominal()) {
            System.out.println(eval.areaUnderROC(NOMINAL));
            this.plotROC();
        } else if (testDataset.classAttribute().isNumeric()) {
            //this.numericToNominal(testDataset, cs);
            System.out.println("DONE");
            //System.out.println(eval.areaUnderROC(NUMERIC));
        }
//            System.out.println(this.getPredictions());
        //System.out.println(eval.toMatrixString("Confusion Matrix for this"));
    }
    
    public void numericToNominal(Instances dataset, Classifier cs) throws Exception {
        NumericToNominal convert = new NumericToNominal();
        String[] options = new String[2];
        options[0] = "-R";
        options[1] = "first-last";  //range of variables to make nominal

        convert.setOptions(options);
        convert.setInputFormat(dataset);
        
        Instances newData = Filter.useFilter(dataset, convert);
        
        System.out.println("Before");
        for (int i = 0; i < dataset.numAttributes(); i = i + 1) {
            System.out.println("Nominal? " + dataset.attribute(i).isNominal());
        }
        
        System.out.println("After");
        for (int i = 0; i < dataset.numAttributes(); i = i + 1) {
            System.out.println("Nominal? " + newData.attribute(i).isNominal());
        }
        System.out.println(newData.toSummaryString());
        newData.setClassIndex(newData.numAttributes() - 1);
        this.generateFolds(newData, classIndex);
        eval = new Evaluation(newData);
        eval.evaluateModel(cs, newData);
        System.out.println(eval.toSummaryString("Evaluation results:\n", false));
    }
    
    public void plotROC() throws Exception {
        // generate curve
        ThresholdCurve tc = new ThresholdCurve();
        int indexC = 0;
        Instances result = tc.getCurve(eval.predictions());
//            ArffSaver saver = new ArffSaver();
//            saver.setInstances(result);
//            saver.setFile(new File("H:\\NetBeansProjects\\BigDataClassification\\data\\result.arff"));
//            saver.writeBatch();
        //f = System.currentTimeMillis();
        // plot curve
        ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
        vmc.setROCString("(AUC for '" + testDataset2.relationName() + "' dataset = " + Utils.doubleToString(ThresholdCurve.getROCArea(result), 4) + ")");
        vmc.setName(result.relationName());
        PlotData2D tempd = new PlotData2D(result);
        tempd.setPlotName(result.relationName());
        tempd.addInstanceNumberAttribute();
        // specify which points are connected
        boolean[] cp = new boolean[result.numInstances()];
        for (int n = 1; n < cp.length; n++) {
            cp[n] = true;
        }
        tempd.setConnectPoints(cp);
        // add plot
        vmc.addPlot(tempd);
        // display curve
        String plotName = vmc.getName();
        final javax.swing.JFrame jf = new javax.swing.JFrame("ROC Curve: " + plotName);
        jf.setSize(500, 400);
        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(vmc, BorderLayout.CENTER);
        jf.addWindowListener(new java.awt.event.WindowAdapter() {
            
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                jf.dispose();
            }
        });
        jf.setVisible(true);
    }
    
}
