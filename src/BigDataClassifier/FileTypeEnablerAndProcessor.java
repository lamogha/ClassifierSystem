/*
 * Class to enable filetypes from input
and determine what classifier to use based on filetype
 */
package BigDataClassifier;
import java.io.*;

import weka.core.Instances;
import weka.core.UnassignedClassException;
import weka.core.converters.CSVLoader;
import weka.core.converters.TextDirectoryLoader;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.core.converters.DatabaseLoader;
import weka.core.converters.JSONLoader;
import weka.core.converters.XRFFLoader;
/**
 *
 * @author lamogha
 */
public class FileTypeEnablerAndProcessor {
    private static final HashSet<String> structuredDataSetExt = new HashSet<>();
    private static final HashSet<String> unstructuredDataSetExt = new HashSet<>();
    FileTypeEnablerAndProcessor fp;
    SupervisedClassifier sc = new SupervisedClassifier();
    UnsupervisedClassifier uc = new UnsupervisedClassifier();
    ClassifierEvaluator ce = new ClassifierEvaluator();
    Instances traindata, testdata;
    
    public void fileEntry () throws Exception{
    	
    	File folder = new File("H:\\NetBeansProjects\\BigDataClassification\\data\\data2\\data3");
    	fp  = new FileTypeEnablerAndProcessor();
    	fp.enableFileTypes();
        fp.processFolder(folder);
        
    }
    
    public void processFolder(File folder) throws Exception{
    	
    	if(!folder.isDirectory()){
    		traindata = new Instances(new BufferedReader(new FileReader(folder)));
    		testdata = new Instances(new BufferedReader(new FileReader
    				("H:\\NetBeansProjects\\BigDataClassification\\data\\data2\\data3")));
        	System.out.println(traindata.toSummaryString());	
    	}
    	else{
    		
    		 for (final File fileEntry : folder.listFiles()) 
    	        {
    	                if (fileEntry.isDirectory()) {
    	                    this.processFolder(fileEntry);
    	                } else
    	                {
    	                    //manipulate file here
    	                    String fileName = fileEntry.getName();
    	                    System.out.println(fileName);
    	                  
    	                    if(!fileName.startsWith(".") && (fileName.contains(".csv")||fileName.contains(".xls")))
    	                    {
    	                    	CSVLoader loader = new CSVLoader();
    	                    	loader.setSource(new File (fileEntry.getAbsolutePath()));
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
    	                    }
    	                    
    	                    else if (!fileName.startsWith(".") && fileName.contains(".txt"))
                            {
    	                    	
    	                    	TextDirectoryLoader loader = new TextDirectoryLoader ();
    	                    	System.out.println( "About to load text file " + fileName);
    	                    	System.out.println("Name of path " + fileEntry.getAbsolutePath());
    	                    	loader.setSource(folder);
    	                    	traindata = loader.getDataSet();
    	                	System.out.println(traindata.toSummaryString());
    	                		
    	                    } 
                            else if (!fileName.startsWith(".") && fileName.contains(".json")){
                                JSONLoader loader = new JSONLoader();
    	                    	loader.setSource(new File (fileEntry.getAbsolutePath()));
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                            }
                            else if (!fileName.startsWith(".") && fileName.contains(".xrff")){
                                XRFFLoader loader = new XRFFLoader();
    	                    	loader.setSource(new File (fileEntry.getAbsolutePath()));
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                            }
                            else if (!fileName.startsWith("."))
                            {
    	                    	traindata = new Instances(new BufferedReader(new FileReader
    	                    			(fileEntry.getAbsolutePath())));
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
                                //sc.useNaiveBayes(traindata);
                                //uc.useEMClusterer(traindata);
                                //ce.evaluatorClassifier(traindata, testdata, sc.useNaiveBayes(traindata));
    	                    }
                            else if (!fileName.startsWith(".") && fileName.contains(".mdf")){
                                DatabaseLoader loader = new DatabaseLoader();
                                loader.connectToDatabase();
    	                    	loader.setSource("jdbc:mysql://adegokeobasa.me:3306/classic_models", "lamogha", "l@mmyPHD" );
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                            }
                        
    	                  }
    	                      	
    	                    //callClassifier(fileEntry.getAbsolutePath(), fp.getFileExtension(fileName));
    	             }
    		
    	}
       
   }
    	        
     public void enableFileTypes() {
        //structured data sets
        structuredDataSetExt.add("csv");
        structuredDataSetExt.add("xls");
        structuredDataSetExt.add("arff");
        
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
            System.out.println(fileName);
            
            //callClassifier(fileEntry.getAbsolutePath(), this.getFileExtension(fileName));
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
        
        public void chooseClassifier(){
            /**Choose supervised classifier if a class attribute already exists in the dataset
             OR if the size of the training data is greater than or equal to the test data size
             */
            
            if(traindata.attribute("class") != null || traindata.attribute("Class") != null 
                    && traindata.size()>= testdata.size()) 
            {
    	        System.out.println("class attribute found....");
                try {
                    //use supervised algorithm
                    ce.evaluatorClassifier(traindata, testdata, sc.useNaiveBayes(traindata));
                } catch (Exception ex) {
                    Logger.getLogger(FileTypeEnablerAndProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
    	    } else 
            {
    	        System.out.println("class attribute not found");
                 //use unsupervised
    	    }
            
        }
}
