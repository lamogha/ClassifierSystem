/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author u1457710
 */
public class DirectoryChooser extends javax.swing.JFrame {

    private static String trainFileName, testFileName;
    FileTypeEnablerAndProcessor fp = new FileTypeEnablerAndProcessor();
    ClassEvaluator ce = new ClassEvaluator();
    File trainFile, testFile, lastPath;
    java.awt.event.ActionEvent event;

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
        jTextPane1 = new javax.swing.JTextPane();

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
                        .addComponent(trainSetTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(testDatasetField)
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

        jScrollPane1.setViewportView(jTextPane1);
        jTextPane1.getAccessibleContext().setAccessibleName("textPane");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
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

        JFileChooser chooseTrainData = new JFileChooser();
        chooseTrainData.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //chooseTrainData.showOpenDialog(this);
        if (lastPath != null) {
            chooseTrainData.setCurrentDirectory(lastPath);
        }
        int returnVal = chooseTrainData.showOpenDialog(this);
        redirectSystemStreams();
        event = evt;
        //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX "+evt.getActionCommand());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            trainFile = chooseTrainData.getSelectedFile();
            trainFileName = trainFile.getPath();
            String ext = fp.getFileExtension(trainFileName);
            System.out.println("file Extension ===: " + ext);
            lastPath = trainFile.getParentFile();
            trainSetTextfield.setText(trainFile.getPath());
            //fp = new BigDataClassifier.FileTypeEnablerAndProcessor();
            if (!trainFile.isDirectory()) {
                fp.getFileExtension(trainFile.getPath());
//                if (!ext.equalsIgnoreCase("txt") || !ext.equalsIgnoreCase("csv")) {
//                    
//                }
                if (ext.equalsIgnoreCase("txt") || ext.equalsIgnoreCase("csv")) {
                    try {
                        fp.fileEntry(trainFile);
                    } catch (Exception ex) {
                        Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    try {
                        ArrayList items = fp.showSummary(trainFile);
                        Object[] obj = items.toArray();
                        classLabelMenu.setModel(new DefaultComboBoxModel(obj));
//                      jComboBox1.setSelectedIndex(jComboBox1.getItemCount()-1);
                        System.out.println("problem accessing file " + trainFile.getAbsolutePath());
                    } catch (IOException ex) {
                        Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else {
                System.out.println("Ready to Process datasets, please click the button to build model.....");
//                try {
//                    fp.processFolder(trainFile.getCanonicalFile());
//                } catch (Exception ex) {
//                    Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }

        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event_trainSetSelectButtonActionPerformed

    private void testSetSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testSetSelectButtonActionPerformed
        // TODO add your handling code here:
        event = evt;
        JFileChooser chooseTestData = new JFileChooser();
        chooseTestData.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //chooser2.showOpenDialog(null);
        if (lastPath != null) {
            chooseTestData.setCurrentDirectory(lastPath);
        }
        int returnVal = chooseTestData.showOpenDialog(this);
        redirectSystemStreams();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            testFile = chooseTestData.getSelectedFile();
            //testFileName = testFile.getPath();
            testDatasetField.setText(testFile.getPath());
            if (!testFile.isDirectory()) {
                fp.getFileExtension(testFile.getPath());
                try {
                    System.out.println("Test Data Exists");
                    //fp.testFileEntry(testFile);
                    //fp = new BigDataClassifier.FileTypeEnablerAndProcessor(testFileName); 
                    System.out.println("=====================This is a List of the Attributes in the test dataset===================== "
                            + "\n" + fp.showSummary(testFile));
                } catch (IOException ex) {
                    System.out.println("Problem accessing file " + testFile.getAbsolutePath());
                    Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Processing Datasets........");
            }
        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event_testSetSelectButtonActionPerformed

    private void selectClassLabelTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectClassLabelTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectClassLabelTextActionPerformed

    private void modelBuildProceedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelBuildProceedButtonActionPerformed
        // TODO add your handling code here:
        //BigDataClassifier.FileTypeEnablerAndProcessor enabler = null; 
        if (event.getActionCommand().equalsIgnoreCase("Train Dataset")) {
            //fp = new BigDataClassifier.FileTypeEnablerAndProcessor();
            //fp.setClassIndex(-1);
            System.out.println("FP class index = " + fp.getClassIndex());
            System.out.println("chooser class index = " + classLabelMenu.getSelectedIndex());
            if (classLabelMenu.getSelectedIndex() != (fp.getClassIndex())) {
                try {
                    System.out.println("Class index was not selected");
                    //fp.setClassIndex(fp.getClassIndex());
                    fp.setClassIndex(classLabelMenu.getSelectedIndex());
                    fp.fileEntry(trainFile);
                } catch (Exception ex) {
                    Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    //this.classLabelMenuActionPerformed(evt);
                    fp.setClassIndex(classLabelMenu.getSelectedIndex());
                    //fp.setClassIndex(classLabelMenu.getComponentCount());
                    fp.fileEntry(trainFile);
                } catch (Exception ex) {
                    Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//                fp.fileEntry(trainSetTextfield.getText());

        } else {
//            if (!trainSetTextfield.getText().isEmpty() &&
//                !testDatasetField.getText().isEmpty() &&
//                classLabelMenu.getSelectedIndex()!=(fp.getClassIndex())) {

            System.out.println("ClASSINDX === " + classLabelMenu.getSelectedIndex());
            //start the file type enabler and processor class
            try {
                fp.setClassIndex(classLabelMenu.getSelectedIndex());
                fp.testFileEntry(testFile, trainFile);
                //fp.fileEntry(trainFile);
            } catch (Exception ex) {
                Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
//            }
            }
        }
        //fp.setClassIndex(-1);
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
        if (classLabelMenu.getAction() == null) {
            classLabelMenu.setSelectedIndex(classLabelMenu.getSelectedIndex());
            fp.setClassIndex(classLabelMenu.getSelectedIndex());
        } else {
            System.out.println("No Action of selecting class index performed");
        }
    }//GEN-LAST:event_classLabelMenuActionPerformed

    private void predictButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predictButtonActionPerformed
        // TODO add your handling code here:
        //System.out.println("Predict button is working");
        //ce.setPredictions();
        //start the file type enabler and processor class
        try {
            //System.out.println("enabler class index = " + fp.getClassIndex());
//                if (classLabelMenu.getSelectedIndex()!=(fp.getClassIndex())){
//                    //this.classLabelMenuActionPerformed(evt);
//                    fp.setClassIndex(classLabelMenu.getSelectedIndex());
//                } else {
//                    System.out.println("Class index was not selected");
//                }
            fp.showPredictions();
            //fp.setClassIndex(classLabelMenu.getSelectedIndex());
            //fp.testFileEntry(testFile,trainFile);
            //fp.fileEntry(trainFile);
        } catch (Exception ex) {
            Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_predictButtonActionPerformed
    String txt;

    private void updateTextArea(String txt) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (txt.contains("<b>")) {
                    for (String s : txt.split("\n")) {
                        if (s.contains("<b>")) {
                            s = s.replaceAll("<b>", "");
                            s = s.replace("</b>", "");
                            appendToPane(jTextPane1, s + "\n", Color.RED);

                        } else {
                            appendToPane(jTextPane1, s + "\n", Color.BLACK);
                        }
                    }
                } else {
                    appendToPane(jTextPane1, txt, Color.BLACK);
                }
            }
            //jTextPane1.addStyle(text, style).setText(text);
//    }
        });
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                txt = String.valueOf((char) b);
//        if(txt.contains("<b>")){ 
//           txt = txt.replaceAll("<b>", " ");
//           txt = txt.replace("</b>", " ");
//           colorRed(txt);
//        }
//        else{
                updateTextArea(txt);
//        }
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                txt = new String(b, off, len);
//        if(txt.contains("<b>")){ 
//           txt = txt.replaceAll("<b>", " ");
//           txt = txt.replace("</b>", " ");
//           colorRed(txt);
//        }
//        else{
                updateTextArea(txt);
//        }
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
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton modelBuildProceedButton;
    private javax.swing.JButton predictButton;
    private javax.swing.JTextField selectClassLabelText;
    private javax.swing.JTextField testDatasetField;
    private javax.swing.JButton testSetSelectButton;
    private javax.swing.JButton trainSetSelectButton;
    private javax.swing.JTextField trainSetTextfield;
    // End of variables declaration//GEN-END:variables
}
