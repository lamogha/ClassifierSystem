/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SGD;
import weka.classifiers.functions.SGDText;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.Stacking;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import static weka.core.Attribute.NOMINAL;
import static weka.core.Attribute.NUMERIC;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;
//import weka.dl4j.layers.DenseLayer;
//import weka.dl4j.layers.OutputLayer;

/**
 *
 * @author u1457710
 */
public class TestROC {
    
    /**
   * takes one argument: dataset in ARFF format (expects class to be last attribute)
   */
public static void main(String[] args) throws Exception {
    // load data
    Instances data = new Instances(new BufferedReader(new FileReader("H:\\ProgramFiles\\Weka-3-8\\data\\weather.numeric.arff")));
    System.out.println(data.toSummaryString());
    data.setClassIndex(data.numAttributes() - 1);
    // train classifier
    Classifier cl = new NaiveBayes();
    //Classifier cl = new SGD();
    //Classifier cl = new SGDText();
    //Classifier cl = new LinearRegression();
    //Classifier cl = new Bagging();
    //Classifier cl = new Stacking();
    //Classifier cl = new ZeroR();
    //Classifier cl = new J48();
    //Classifier cl = new RandomForest();
    // cl1.setSAXParams(42, 5, 5, "CLASSIC"); // CBF
    // Evaluation eval = new Evaluation(data);
    // eval.crossValidateModel(cl, data, 8, new Random(1));
    Evaluation eval = new Evaluation(data);
    eval.crossValidateModel(cl, data, 10, new Random(1));
    System.out.println(eval.areaUnderROC(NOMINAL));
    // generate curve
    ThresholdCurve tc = new ThresholdCurve();
    int classIndex = 0;
    Instances result = tc.getCurve(eval.predictions(), classIndex);
//    ArffSaver saver = new ArffSaver();
//    saver.setInstances(result);
//    saver.setFile(new File("H:\\NetBeansProjects\\BigDataClassification\\data\\data2\\results.arff"));
//    saver.writeBatch();
    //f = System.currentTimeMillis();
    // plot curve
    ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
    vmc.setROCString("(Area under ROC = " + Utils.doubleToString(ThresholdCurve.getROCArea(result), 4) + ")");
    vmc.setName(result.relationName());
    PlotData2D tempd = new PlotData2D(result);
    tempd.setPlotName(result.relationName());
    tempd.addInstanceNumberAttribute();
    // specify which points are connected
    boolean[] cp = new boolean[result.numInstances()];
    for (int n = 1; n < cp.length; n++) cp[n] = true;
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
    jf.setVisible(false);
}
    
}
