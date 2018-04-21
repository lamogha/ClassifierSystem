/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 *
 * @author u1457710
 */
public class DirectoryChooser extends javax.swing.JFrame {
    private static String trainFileName, testFileName;
    FileTypeEnablerAndProcessor fp = new FileTypeEnablerAndProcessor();
    File trainFile,testFile;
    
    /**
     * Creates new form DirectoryChooser
     */
    public DirectoryChooser() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        trainSetSelectButton = new javax.swing.JButton();
        testSetSelectButton = new javax.swing.JButton();
        trainSetTextfield = new javax.swing.JTextField();
        testDatasetField = new javax.swing.JTextField();
        classLabelMenu = new javax.swing.JComboBox<>();
        selectClassLabelText = new javax.swing.JTextField();
        modelBuildProceedButton = new javax.swing.JButton();
        predictButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 0, 0));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 0, 0)));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        trainSetSelectButton.setBackground(new java.awt.Color(102, 0, 0));
        trainSetSelectButton.setForeground(new java.awt.Color(204, 204, 204));
        trainSetSelectButton.setText("Train Dataset");
        trainSetSelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainSetSelectButtonActionPerformed(evt);
            }
        });

        testSetSelectButton.setBackground(new java.awt.Color(102, 0, 0));
        testSetSelectButton.setForeground(new java.awt.Color(204, 204, 204));
        testSetSelectButton.setText("Test Dataset");
        testSetSelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSetSelectButtonActionPerformed(evt);
            }
        });

        trainSetTextfield.setText("Select your dataset for training your model");

        testDatasetField.setText("Select your dataset for testing your model");

        classLabelMenu.setMaximumRowCount(50);
        classLabelMenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        classLabelMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classLabelMenuActionPerformed(evt);
            }
        });

        selectClassLabelText.setBackground(new java.awt.Color(204, 255, 255));
        selectClassLabelText.setText("Class index ");
        selectClassLabelText.setBorder(null);
        selectClassLabelText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectClassLabelTextActionPerformed(evt);
            }
        });

        modelBuildProceedButton.setBackground(new java.awt.Color(102, 0, 0));
        modelBuildProceedButton.setForeground(new java.awt.Color(204, 204, 204));
        modelBuildProceedButton.setText("Model Build Proceed");
        modelBuildProceedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelBuildProceedButtonActionPerformed(evt);
            }
        });

        predictButton.setBackground(new java.awt.Color(102, 0, 0));
        predictButton.setForeground(new java.awt.Color(204, 204, 204));
        predictButton.setText("Predict");
        predictButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predictButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(selectClassLabelText, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(classLabelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(testSetSelectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(trainSetSelectButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(modelBuildProceedButton)
                        .addGap(33, 33, 33)
                        .addComponent(predictButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(trainSetTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(testDatasetField, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainSetSelectButton)
                    .addComponent(trainSetTextfield))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectClassLabelText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classLabelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testSetSelectButton)
                    .addComponent(testDatasetField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modelBuildProceedButton)
                    .addComponent(predictButton)))
        );

        trainSetTextfield.getAccessibleContext().setAccessibleName("test textfield");
        testDatasetField.getAccessibleContext().setAccessibleName("train text field");
        classLabelMenu.getAccessibleContext().setAccessibleName("jComboBox");

        textArea.setColumns(20);
        textArea.setForeground(new java.awt.Color(153, 153, 153));
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void trainSetSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trainSetSelectButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooseTrainData = new JFileChooser();
        chooseTrainData.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //chooser1.showOpenDialog(null);
        int returnVal = chooseTrainData.showOpenDialog(this);
        redirectSystemStreams();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            trainFile = chooseTrainData.getSelectedFile();
            trainFileName = trainFile.getPath();
            trainSetTextfield.setText(trainFileName);
            //fp = new BigDataClassifier.FileTypeEnablerAndProcessor();
            if(!trainFile.isDirectory()){
                fp.getFileExtension(trainFileName);
                    try {
                        ArrayList items = fp.showSummary(trainFile);
                        Object[] obj = items.toArray();
                        classLabelMenu.setModel(new DefaultComboBoxModel(obj));
//                      jComboBox1.setSelectedIndex(jComboBox1.getItemCount()-1);
                    } catch (IOException ex) {
                    System.out.println("problem accessing file "+trainFile.getAbsolutePath());
                    Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                
            }
            else{
                System.out.println("Processing Datasets");
            }
            
        }
        else {
             System.out.println("File access cancelled by user.");
        }
        
    }//GEN-LAST:event_trainSetSelectButtonActionPerformed

    private void testSetSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testSetSelectButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooseTestData = new JFileChooser();
        chooseTestData.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //chooser2.showOpenDialog(null);
        int returnVal = chooseTestData.showOpenDialog(this);
        redirectSystemStreams();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            testFile = chooseTestData.getSelectedFile();
            testFileName = testFile.getPath();
            testDatasetField.setText(testFileName);
            try {
                System.out.println("Test Data Exists");
                //fp.testFileEntry(testFile);
                //fp = new BigDataClassifier.FileTypeEnablerAndProcessor(testFileName); 
                System.out.println("=====================This is a List of the Attributes in the test dataset===================== " 
                        + "\n" + fp.showSummary(testFile) );
            } catch (IOException ex) {
                System.out.println("Problem accessing file "+testFile.getAbsolutePath());
                Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        else{
             System.out.println("File access cancelled by user.");
        }
        
    }//GEN-LAST:event_testSetSelectButtonActionPerformed

    private void selectClassLabelTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectClassLabelTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectClassLabelTextActionPerformed

    private void modelBuildProceedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelBuildProceedButtonActionPerformed
        // TODO add your handling code here:
        //BigDataClassifier.FileTypeEnablerAndProcessor enabler = null;
        if (trainSetTextfield.getText().equalsIgnoreCase(trainFileName)&&
                testDatasetField.getText().equalsIgnoreCase(testFileName) &&
                classLabelMenu.getSelectedIndex()!=(fp.getClassIndex())) {
            //start the file type enabler and processor class
            try {
                //System.out.println("enabler class index = " + fp.getClassIndex());
//                if (classLabelMenu.getSelectedIndex()!=(fp.getClassIndex())){
//                    //this.classLabelMenuActionPerformed(evt);
//                    fp.setClassIndex(classLabelMenu.getSelectedIndex());
//                } else {
//                    System.out.println("Class index was not selected");
//                }
                fp.setClassIndex(classLabelMenu.getSelectedIndex());
                fp.testFileEntry(testFile);
                fp.fileEntry(trainFile);
            } catch (Exception ex) {
                Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else {
            try {
                //fp = new BigDataClassifier.FileTypeEnablerAndProcessor();
                //System.out.println("FP class index = " + fp.getClassIndex());
                //System.out.println("chooser class index = " + classLabelMenu.getSelectedIndex());
                if (classLabelMenu.getSelectedIndex()!=(fp.getClassIndex())){
                    System.out.println("Class index was not selected");
                    fp.fileEntry(trainFile);
                } else {
                    //this.classLabelMenuActionPerformed(evt);
                    //fp.setClassIndex(classLabelMenu.getSelectedIndex());
                    fp.fileEntry(trainFile);
                }
//                fp.fileEntry(trainSetTextfield.getText());
            } catch (Exception ex) {
                Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_modelBuildProceedButtonActionPerformed

//    public int getSelectedClassIndex(){
//         //get selected item as an index
////         int index = jComboBox1.getSelectedIndex();
//        int index = classLabelMenu.getSelectedIndex();
//        return index;
//    }
    
    private void classLabelMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classLabelMenuActionPerformed
        // TODO add your handling code here:
//        src.BigDataClassifier.FileTypeEnablerAndProcessor enabler 
//                = new src.BigDataClassifier.FileTypeEnablerAndProcessor();
        //get selected item as an index
        if(classLabelMenu.getAction()==null){
            classLabelMenu.setSelectedIndex(classLabelMenu.getSelectedIndex());
            fp.setClassIndex(classLabelMenu.getSelectedIndex());
        } else {
            System.out.println("No Action of selecting class index performed");
        }  
    }//GEN-LAST:event_classLabelMenuActionPerformed

    private void predictButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predictButtonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_predictButtonActionPerformed

    private void updateTextArea(final String text) {
  SwingUtilities.invokeLater(new Runnable() {
    @Override
    public void run() {
      textArea.append(text);
    }
  });
}
 
private void redirectSystemStreams() {
  OutputStream out = new OutputStream() {
    @Override
    public void write(int b) throws IOException {
      updateTextArea(String.valueOf((char) b));
    }
 
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      updateTextArea(new String(b, off, len));
    }
 
    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }
  };
 
  System.setOut(new PrintStream(out, true));
  System.setErr(new PrintStream(out, true));
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> classLabelMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modelBuildProceedButton;
    private javax.swing.JButton predictButton;
    private javax.swing.JTextField selectClassLabelText;
    private javax.swing.JTextField testDatasetField;
    private javax.swing.JButton testSetSelectButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JButton trainSetSelectButton;
    private javax.swing.JTextField trainSetTextfield;
    // End of variables declaration//GEN-END:variables
}
