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
    				("H:\\NetBeansProjects\\BigDataClassification\\data\\data2")));
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
    	                    }
                            else if (!fileName.startsWith(".") && fileName.contains(".mdf")){
                                DatabaseLoader loader = new DatabaseLoader();
                                loader.connectToDatabase();
    	                    	loader.setSource("jdbc:mysql://adegokeobasa.me:3306/classic_models", "lamogha", "l@mmyPHD" );
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                            }
                        
    	                  }
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
            /**We can use either a supervised or an un-supervised algorithm if a class attribute already
             * exists in the dataset (meaning some labeled instances exists),
             * depending on the size of the training set, the decision is taken.
             */
            //traindata.setClassIndex(traindata.numAttributes()-1);
            if( traindata.attribute("class") != null || traindata.attribute("Class") != null
                     && traindata.size()>= testdata.size())
            {
    	        System.out.println("class attribute found...." );
                System.out.println("Initial training set is larger than the test set...." + traindata.size() );
                
                //Go ahead to generate folds, then call classifier
                try {
                    ce.generateFolds(traindata);
                } catch (Exception ex) {
                    Logger.getLogger(FileTypeEnablerAndProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
    	    }
            /**
             * When there is no class attribute to show labeled instances exists
             * then use an un-supervised algorithm straight;
             * no need for the cross-validation folds.
            */
            else 
            {
    	        System.out.println("class attribute not found");
                try {
                    ce.generateFolds(traindata); //still generate folds, 
                    //class index set to last att index
                    //another decision made to use either supervised or unsupervised
                } catch (Exception ex) {
                    Logger.getLogger(FileTypeEnablerAndProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
    	    }
            
        }
}