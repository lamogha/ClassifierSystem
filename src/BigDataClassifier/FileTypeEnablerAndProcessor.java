/*
 * Class to enable filetypes from input
and determine what classifier to use based on filetype
 */
package BigDataClassifier;
import java.io.*;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.TextDirectoryLoader;
import java.util.*;
/**
 *
 * @author lamogha
 */
public class FileTypeEnablerAndProcessor {
    private static HashSet<String> structuredDataSetExt = new HashSet<String>();
    private static HashSet<String> unstructuredDataSetExt = new HashSet<String>();
    FileTypeEnablerAndProcessor fp;
    SupervisedClassifier sc = new SupervisedClassifier();
    UnsupervisedClassifier uc = new UnsupervisedClassifier();
    ClassifierEvaluator ce = new ClassifierEvaluator();
    Instances traindata, testdata;
    
    public void fileEntry () throws Exception{
    	
    	File folder = new File("H:\\NetBeansProjects\\BigDataClassification\\data");
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
        	sc.useNaiveBayesUpdateable(traindata);
        	ce.evaluatorClassifier(traindata, traindata, sc.useNaiveBayesUpdateable(traindata));
        	//ce.evaluatorClusterer(traindata, testdata);
        	
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
                                //sc.useNaiveBayesUpdateable(traindata);
                                //ce.evaluatorClassifier(traindata, traindata, sc.useNaiveBayesUpdateable(traindata));
    	                    }
    	                    
    	                    else if (!fileName.startsWith(".") && fileName.contains(".txt"))
                            {
    	                    	
    	                    	TextDirectoryLoader loader = new TextDirectoryLoader ();
    	                    	System.out.println("loader object created");

    	                    	System.out.println( "About to load text file " + fileName);
    	                    	System.out.println("Name of path " + fileEntry.getAbsolutePath());
    	                    	
    	                    	loader.setSource(folder);
    	                    	traindata = loader.getDataSet();
    	                    	//create a new arff dataset instance from the text loader
    	                    	//Instances data = loader.createDataset(fileEntry.getParent());

    	                		//System.out.println("directory located " + fileEntry.getPath() );
    	                		System.out.println(traindata.toSummaryString());
    	                		
    	                    } else if (!fileName.startsWith("."))
                            {
    	                        
    	                    	traindata = new Instances(new BufferedReader(new FileReader
    	                    			(fileEntry.getAbsolutePath())));
    	                    	System.out.println(traindata.toSummaryString());
                                //sc.useNaiveBayesUpdateable(traindata);
                                //ce.evaluatorClassifier(traindata, traindata, sc.useNaiveBayesUpdateable(traindata));
    	                    	//sc.useNaiveBayes(data);
    	                    	//sc.useClassifierWithFilter(data);
    	                    	//uc.useEMClusterer(data);
    	                    	//uc.useFarthestFirst(data);
    	                    }
                            /**
                            if (traindata.checkForStringAttributes() == false){
                            sc.useNaiveBayesUpdateable(traindata);
                            ce.evaluatorClassifier(traindata, traindata, sc.useNaiveBayesUpdateable(traindata));
                            }
                            else{
                            System.out.println("string attibute present");
                            }
                                    */
                            
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
          public static void useSupervisedML(){
        	  
          }
}
