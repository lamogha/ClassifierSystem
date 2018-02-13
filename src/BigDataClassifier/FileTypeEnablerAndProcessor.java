/*
 * Class to enable filetypes from input
and determine what classifier to use based on filetype
 */
package src.BigDataClassifier;
import java.io.*;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.TextDirectoryLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.converters.DatabaseConnection;
import weka.core.converters.JSONLoader;
import weka.core.converters.XRFFLoader;
import weka.experiment.InstanceQuery;
/**
 *
 * @author lamogha
 */
public class FileTypeEnablerAndProcessor {
    FileTypeEnablerAndProcessor fp;
    SupervisedClassifier sc = new SupervisedClassifier();
    UnsupervisedClassifier uc = new UnsupervisedClassifier();
    ClassEvaluator ce = new ClassEvaluator();
    Instances traindata, testdata;

    public void fileEntry () throws Exception{
    	
        //File folder =  file;
        File folder = new File ("H:\\NetBeansProjects\\BigDataClassification\\data\\data3\\soy-test.arff");
        System.out.println("file location opened");
    	fp  = new FileTypeEnablerAndProcessor();
        fp.processFolder(folder);
    }
    
    public void processFolder(File folder) throws Exception{
    	
    	if(!folder.isDirectory()){
    		//manipulate file here
    	        String fileName = folder.getName();
    	        System.out.println(fileName);
                //String extension = getFileExtension(fileName);
                testdata = new Instances(new BufferedReader(new FileReader
    			(folder)));
    	            if(!fileName.startsWith(".") && (fileName.contains(".csv")||fileName.contains(".xls")))
    	            {
    	                CSVLoader loader = new CSVLoader();
    	                loader.setSource(new File (folder.getAbsolutePath()));
    	                traindata = loader.getDataSet();
    	                System.out.println(traindata.toSummaryString());
                        this.chooseClassifier();
    	            }
    	                    
    	            else if (!fileName.startsWith(".") && fileName.contains(".txt")){
    	                    	
    	                    	TextDirectoryLoader loader = new TextDirectoryLoader ();
    	                    	System.out.println( "About to load text file " + fileName);
    	                    	System.out.println("Name of path " + folder.getAbsolutePath());
    	                    	loader.setSource(folder);
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
    	                		
    	            } 
                    else if (!fileName.startsWith(".") && fileName.contains(".json")){
                                JSONLoader loader = new JSONLoader();
    	                    	loader.setSource(new File (folder.getAbsolutePath()));
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
                    }
                    else if (!fileName.startsWith(".") && fileName.contains(".xrff")){
                                XRFFLoader loader = new XRFFLoader();
    	                    	loader.setSource(new File (folder.getAbsolutePath()));
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
                    }
                    else if (!fileName.startsWith(".") && fileName.contains(".arff")){
    	                    	traindata = new Instances(new BufferedReader(new FileReader
    	                    			(folder.getAbsolutePath())));
                                testdata = new Instances(new BufferedReader(new FileReader
                                                (folder)));
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
    	            }
                    else if (!fileName.startsWith(".") && fileName.contains(".mdf")){
                                DatabaseConnection loader = new DatabaseConnection();
                                loader.connectToDatabase();
    	                    	InstanceQuery query = new InstanceQuery();
                                query.setUsername("lamogha");
                                query.setPassword("l@mmyPHD");
                                query.setQuery("select * from customers");
                                // You can declare that your data set is sparse
                                // query.setSparseData(true);
                                Instances data = query.retrieveInstances();
                                System.out.println(data.toSummaryString());
                                this.chooseClassifier();
                    }
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
                                this.chooseClassifier();
    	                    }
    	                    
    	                    else if (!fileName.startsWith(".") && fileName.contains(".txt"))
                            {
    	                    	
    	                    	TextDirectoryLoader loader = new TextDirectoryLoader ();
    	                    	System.out.println( "About to load text file " + fileName);
    	                    	System.out.println("Name of path " + fileEntry.getAbsolutePath());
    	                    	loader.setSource(folder);
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
    	                		
    	                    } 
                            else if (!fileName.startsWith(".") && fileName.contains(".json")){
                                JSONLoader loader = new JSONLoader();
    	                    	loader.setSource(new File (fileEntry.getAbsolutePath()));
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
                            }
                            else if (!fileName.startsWith(".") && fileName.contains(".xrff")){
                                XRFFLoader loader = new XRFFLoader();
    	                    	loader.setSource(new File (fileEntry.getAbsolutePath()));
    	                    	traindata = loader.getDataSet();
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
                            }
                            else if (!fileName.startsWith("."))
                            {
    	                    	traindata = new Instances(new BufferedReader(new FileReader
    	                    			(fileEntry.getAbsolutePath())));
    	                    	System.out.println(traindata.toSummaryString());
                                this.chooseClassifier();
    	                    }
                            else if (!fileName.startsWith(".") && fileName.contains(".mdf")){
                                DatabaseConnection loader = new DatabaseConnection();
                                loader.connectToDatabase();
    	                    	InstanceQuery query = new InstanceQuery();
                                query.setUsername("lamogha");
                                query.setPassword("l@mmyPHD");
                                query.setQuery("select * from customers");
                                // You can declare that your data set is sparse
                                // query.setSparseData(true);
                                Instances data = query.retrieveInstances();
                                System.out.println(data.toSummaryString());
                                this.chooseClassifier();
                            }
                        
    	                }
    	             }
    		   //System.exit(0);
    	}
       
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
        
        public void chooseClassifier(){
            int classIndex = 0; //number of attributes must be 1 or greater
            /**We can use either a supervised or an un-supervised algorithm if a class attribute already
             * exists in the dataset (meaning some labelled instances exists),
             * depending on the size of the training set, the decision is taken.
             */
//            classIndex = traindata.numAttributes()-1;
//            traindata.setClassIndex(classIndex);
            if( classIndex == traindata.numAttributes()-1 || traindata.attribute("class") != null || traindata.attribute("Class")!= null
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
             * When there is no class attribute to show labelled instances exists
             * then use an un-supervised algorithm straight;
             * no need for the cross-validation folds.
            */
            else 
            {
                try {
                    System.out.println("class attribute not found");
                    classIndex = traindata.numAttributes()-1;
                    traindata.setClassIndex(classIndex);
                    System.out.println("Class to predict is = " + traindata.classAttribute() + "\n" );
                    uc.autoProbClass(traindata);
                    //uc.evaluatorClusterer(traindata, uc.useSimpleKMeans(traindata) );
                } catch (Exception ex) {
                    Logger.getLogger(FileTypeEnablerAndProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
    	    }
            
        }
}