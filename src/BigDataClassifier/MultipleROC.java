/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import weka.core.*;
import weka.gui.visualize.*;
/**
 *
 * @author u1457710
 */
public class MultipleROC {
    
    /**
   * takes arbitrary number of arguments: 
   * previously saved ROC curve data (ARFF file)
     * @param args
     * @throws java.lang.Exception
   */
  public static void main(String[] args) throws Exception {
    boolean first = true;
    ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
    for (int i = 0; i < args.length; i++) {
      Instances result = new Instances(
                            new BufferedReader(
                              new FileReader("H:\\NetBeansProjects\\BigDataClassification\\data\\data2\\lami.arff")));
      result.setClassIndex(result.numAttributes() - 1);
      // method visualize
      PlotData2D tempd = new PlotData2D(result);
      tempd.setPlotName(result.relationName());
      tempd.addInstanceNumberAttribute();
      // specify which points are connected
      boolean[] cp = new boolean[result.numInstances()];
      for (int n = 1; n < cp.length; n++)
        cp[n] = true;
      tempd.setConnectPoints(cp);
      // add plot
      if (first)
        vmc.setMasterPlot(tempd);
      else
        vmc.addPlot(tempd);
      first = false;
    }
    // method visualizeClassifierErrors
    final javax.swing.JFrame jf = 
      new javax.swing.JFrame("Weka Classifier ROC");
    jf.setSize(500,400);
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
