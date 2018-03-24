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

        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        attachButton = new javax.swing.JButton();
        attachButton1 = new javax.swing.JButton();
        textField = new javax.swing.JTextField();
        textField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        attachButton.setText("Train Dataset");
        attachButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachButtonActionPerformed(evt);
            }
        });

        attachButton1.setText("Test Dataset");
        attachButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachButton1ActionPerformed(evt);
            }
        });

        textField.setText("Select your dataset for testing your model");

        textField1.setText("Select your dataset for training your model");

        jComboBox1.setMaximumRowCount(50);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextField1.setText("Set Class index ");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Model Build Proceed");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Predict");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(attachButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(attachButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(textField)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(127, 127, 127)
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(attachButton)
                    .addComponent(textField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(attachButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)))
                    .addComponent(textField1)))
        );

        textField.getAccessibleContext().setAccessibleName("test textfield");
        textField1.getAccessibleContext().setAccessibleName("train text field");
        jComboBox1.getAccessibleContext().setAccessibleName("jComboBox");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void attachButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser1 = new JFileChooser();
        chooser1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser1.showOpenDialog(null);
        File file1 = chooser1.getSelectedFile();
        trainFileName = file1.getPath();
        textField.setText(trainFileName);
        redirectSystemStreams();
        src.BigDataClassifier.FileTypeEnablerAndProcessor fp = new src.BigDataClassifier.FileTypeEnablerAndProcessor();
        try {
            ArrayList items = fp.showSummary(file1);
            Object[] obj = items.toArray();
            jComboBox1.setModel(new DefaultComboBoxModel(obj));
//            jComboBox1.setSelectedIndex(jComboBox1.getItemCount()-1);
        } catch (IOException ex) {
            Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_attachButtonActionPerformed

    private void attachButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser2 = new JFileChooser();
        chooser2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser2.showOpenDialog(null);
        File file2 = chooser2.getSelectedFile();
        testFileName = file2.getPath();
        textField1.setText(testFileName);
        redirectSystemStreams();
        src.BigDataClassifier.FileTypeEnablerAndProcessor fp = new src.BigDataClassifier.FileTypeEnablerAndProcessor();
        try {
            fp.showSummary(file2);
        } catch (IOException ex) {
            Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_attachButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        src.BigDataClassifier.FileTypeEnablerAndProcessor enabler = null;
        this.jComboBox1ActionPerformed(evt);
        if (textField.getText().equalsIgnoreCase(trainFileName)&&
                textField1.getText().equalsIgnoreCase(testFileName)) {
            //start the file type enabler and processor class
            try {
                enabler = new src.BigDataClassifier.FileTypeEnablerAndProcessor(textField1.getText());
//                System.out.println("enabler class index = " + enabler.getClassIndex());
                if (jComboBox1.getSelectedIndex()!=(enabler.getClassIndex())){
                    enabler.setClassIndex(jComboBox1.getSelectedIndex());
                } else {
                    System.out.println("Class index was not selected");
                }
                enabler.fileEntry(textField.getText());
            } catch (Exception ex) {
                Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else {
            try {
                enabler = new src.BigDataClassifier.FileTypeEnablerAndProcessor();
//                System.out.println("enabler class index = " + enabler.getClassIndex() );
                if (jComboBox1.getSelectedIndex()!=(enabler.getClassIndex())){
                    enabler.setClassIndex(jComboBox1.getSelectedIndex());
                } else {
                    System.out.println("Class index was not selected");
                }
                enabler.fileEntry(textField.getText());
            } catch (IOException ex) {
                Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(DirectoryChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public int getSelectedClassIndex(){
         //get selected item as an index
//         int index = jComboBox1.getSelectedIndex();
        int index = jComboBox1.getSelectedIndex();
        return index;
    }
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
//        src.BigDataClassifier.FileTypeEnablerAndProcessor enabler 
//                = new src.BigDataClassifier.FileTypeEnablerAndProcessor();
        //get selected item as an index
        if(jComboBox1.getAction()==null){
            jComboBox1.setSelectedIndex(jComboBox1.getSelectedIndex());
        } else {
            System.out.println("No Action of selecting class index performed");
        }  
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JButton attachButton;
    private javax.swing.JButton attachButton1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField textField;
    private javax.swing.JTextField textField1;
    // End of variables declaration//GEN-END:variables
}
