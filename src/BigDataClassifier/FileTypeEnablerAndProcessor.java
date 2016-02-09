/*
 * Class to enable filetypes from input
and determine what classifier to use based on filetype
 */
package BigDataClassifier;
import java.io.*;
import java.util.*;
/**
 *
 * @author lamogha
 */
public class FileTypeEnablerAndProcessor {
    private static HashSet<String> structuredDataSetExt = new HashSet<String>();
    private static HashSet<String> unstructuredDataSetExt = new HashSet<String>();
    
    public void fileEntry () throws Exception{
    File folder = new File("path_to_folder_containing_files");
    
    FileTypeEnablerAndProcessor fp = new FileTypeEnablerAndProcessor();
        
    fp.enableFileTypes();
        
    for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                fp.listFilesForFolder(fileEntry);
            } else {
                //manipulate file here
                String fileName = fileEntry.getName();
                
                callClassifier(fileEntry.getAbsolutePath(), fp.getFileExtension(fileName));
            }
    }
    
    }
        
     public void enableFileTypes() {
        //structured data sets
        structuredDataSetExt.add("csv");
        structuredDataSetExt.add("xls");
        
        //unstructured data sets
        unstructuredDataSetExt.add("mp3");
        unstructuredDataSetExt.add("txt");
        unstructuredDataSetExt.add("json");
        unstructuredDataSetExt.add("xml");
    }
     
       public void listFilesForFolder(File fileEntry) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            //manipulate file here
            String fileName = fileEntry.getName();
            
            callClassifier(fileEntry.getAbsolutePath(), this.getFileExtension(fileName));
        }
    }
       
        private  String getFileExtension(String fileName) {
        String ext = "";
        
        int indexOfDot = fileName.indexOf(".");
            
        if (indexOfDot != -1) {
            ext = fileName.substring(indexOfDot + 1);
        }
            
        return ext;
    }
    
          public static void callClassifier(String filePathName, String ext) {
        if (unstructuredDataSetExt.contains(ext)) {
            UnsupervisedClassifier uc = new UnsupervisedClassifier();
            uc.unsupervisedClassifier(filePathName);
        }
        
        if (structuredDataSetExt.contains(ext)) {
            SupervisedClassifier sc = new SupervisedClassifier();
            sc.supervisedClassifier(filePathName);
        }
    }
}
