/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;

import BigDataClassifier.DirectoryChooser;
import weka.core.Utils;

/**
 *Starts the processing of the dataset to be classified or clustered
 * @author lamogha
 */
public class ClassifierMain {
     private static DirectoryChooser chooseDirectory =  new DirectoryChooser();

	public static void main(String args[]) throws Exception {
          
             /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DirectoryChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DirectoryChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DirectoryChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DirectoryChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new DirectoryChooser().setVisible(true);
            //System.out.println("\n==========Total System build time is==========: " + System.currentTimeMillis());
        });
        
        /*call the FileTypeEnablerAndProcessor class*/
//            src.BigDataClassifier.FileTypeEnablerAndProcessor enabler = new src.BigDataClassifier.FileTypeEnablerAndProcessor("H:\\NetBeansProjects\\BigDataClassification\\data\\data3\\soy-test.arff");
//            enabler.fileEntry("H:\\NetBeansProjects\\BigDataClassification\\data\\data3\\soy-test.arff");
  
    }
}
