/*
 * Class to enable filetypes from input
and determine what classifier to use based on filetype
 */
package src.BigDataClassifier;
import BigDataClassifier.DirectoryChooser;
import java.io.*;
import java.util.ArrayList;
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
    Instances traindata;
    Instances testdata = null;
    File folder, folder2;
    int classIndex; //number of attributes must be 1 or greater
//    private static DirectoryChooser chooseDirectory =  new DirectoryChooser();
    
    public FileTypeEnablerAndProcessor(){
        
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public FileTypeEnablerAndProcessor(String filename) throws FileNotFoundException, IOException {
//        File folder2= file;
//        folder2 = new File ("H:\\NetBeansProjects\\BigDataClassification\\data\\data3\\soy-test.arff");
        folder2 = new File (filename);
        if(!filename.isEmpty()){
           testdata = new Instances(new BufferedReader(new FileReader
                            (folder2.getAbsolutePath()))); 
        }
        
    }

    public void fileEntry (String filename) throws Exception{
    	
        //File folder =  file;
        folder = new File (filename);
        System.out.println("file location opened");
    	//fp  = new FileTypeEnablerAndProcessor();
        this.processFolder(folder);
    }
    
    public int getClassIndex(){
        return this.classIndex;
    }
    
    public void setClassIndex(int index){
        classIndex = index;
    }
    
    public void processFolder(File folder) throws Exception{
    	
    	if(!folder.isDirectory()){
    		//manipulate file here
    	        String fileName = folder.getName();
    	        System.out.println(fileName);
                //String extension = getFileExtension(fileName);
                traindata = new Instances(new BufferedReader(new FileReader
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
//                                testdata = new Instances(new BufferedReader(new FileReader
//                                                (folder)));
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
        
        public void chooseClassifier() throws Exception{
            /**We can use either a supervised or an un-supervised algorithm if a class attribute already
             * exists in the dataset (meaning some labelled instances exists),
             * depending on the size of the training set, the decision is taken.
             */
//            classIndex = traindata.numAttributes()-1;
              classIndex = this.getClassIndex();
              System.out.println("------SELECTED INDEX IS--------------" + classIndex);
              traindata.setClassIndex(classIndex);
            if(classIndex >= 0)
            {
    	        System.out.println("class attribute found...." );
                
                //Go ahead to generate folds, then call classifier
                    if(testdata == null){
                        System.out.println("NO test data");
                        ce.generateFolds(traindata);
                    }
                    //Or call classifier directly if supplied a test set 
                    else if (classIndex >= 0 && traindata.size()>= testdata.size()){
                       System.out.println("Test Data Exists");
                       System.out.println("Initial training set is larger than the test set...." + traindata.size());
                       System.out.println(testdata.toSummaryString());
                       ce.callClassifier(traindata, testdata, classIndex);
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
                    System.out.println("class attribute not found, using a clusterer");
                    //uc.useFarthestFirst(traindata);
                    //uc.useEMClusterer(traindata);
                    uc.useEMClusterer(traindata);
                } catch (Exception ex) {
                    Logger.getLogger(FileTypeEnablerAndProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
    	    }  
        }
        
        public ArrayList showSummary(File file) throws FileNotFoundException, IOException{
           ArrayList attributeList = new ArrayList(); 
           Instances traindata = new Instances(new BufferedReader(new FileReader
    	                    			(file.getAbsolutePath())));
            System.out.println(traindata.toSummaryString());
            for(int i=0; i<traindata.numAttributes();i++){
                attributeList.add(traindata.attribute(i).name());
            }
//              System.out.println(attributeList);
        return attributeList;
        }
        
}