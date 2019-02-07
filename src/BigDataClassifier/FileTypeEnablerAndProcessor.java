/*
 * Class to enable filetypes from input
and determine what classifier to use based on filetype
 */
package BigDataClassifier;

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
import weka.classifiers.meta.AutoWEKAClassifier;

/**
 *
 * @author lamogha
 */
public class FileTypeEnablerAndProcessor {

    private static FileTypeEnablerAndProcessor fp;
    private static SupervisedClassifier sc = new SupervisedClassifier();
    private static UnsupervisedClassifier uc = new UnsupervisedClassifier();
    private static ClassEvaluator ce = new ClassEvaluator();
    Instances traindata;
    Instances testdata;
    File folder, folder2;
    int classIndex = -1; //number of attributes must be 1 or greater
    String ext = "";
//    private static DirectoryChooser chooseDirectory =  new DirectoryChooser();

    public FileTypeEnablerAndProcessor() {

    }

    /**
     *
     * @param testFile
     * @param trainFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void testFileEntry(File testFile, File trainFile) throws Exception {
//        File folder2= file;
//        folder2 = new File ("H:\\NetBeansProjects\\BigDataClassification\\data\\data3\\soy-test.arff");
        folder2 = testFile;
        folder = trainFile;
        testdata = new Instances(new BufferedReader(new FileReader(folder2.getAbsolutePath())));
        this.processFolder(folder);
//        if(folder.isDirectory()){
//            this.processFolder(folder);
//        }
//        else{
//            traindata = new Instances(new BufferedReader(new FileReader
//    	                    			(folder.getAbsolutePath())));
//            //this.chooseClassifier();
//            this.processFolder(folder);
//        }
//        
    }

    public void fileEntry(File trainFile) throws Exception {

        folder = trainFile;
        //folder = new File (filename);
        //System.out.println("file location opened");
        //fp  = new FileTypeEnablerAndProcessor();
        this.processFolder(folder);
    }

    //Method to get the Class Index
    public int getClassIndex() {
        return this.classIndex;
    }

    //Method for setting the classIndex 
    public void setClassIndex(int index) {
        classIndex = index;
    }

    //if the file is not a directory, then process the file
    public void processFolder(File folder) throws Exception {

        if (!folder.isDirectory()) {
            //manipulate file here
            String fileName = folder.getName();
            //System.out.println(fileName);
            String extension = getFileExtension(fileName);
            //traindata = new Instances(new BufferedReader(new FileReader(folder)));
            if (!fileName.startsWith(".")) {

                //if((fileName.contains(".csv")||fileName.contains(".xls")))
                if (extension.equalsIgnoreCase("csv") || extension.equalsIgnoreCase("xls")) {
                    System.out.println("Opening CSV Loader");
                    CSVLoader loader = new CSVLoader();
                    loader.setFile(folder.getAbsoluteFile());
                    loader.setSource(new File(folder.getAbsolutePath()));
                    traindata = loader.getDataSet();
                    System.out.println(traindata.toSummaryString());
                    //assumes that if it is a csv file, the last attribute is its class attribute
                    this.setClassIndex(traindata.numAttributes() - 1);
                    this.chooseClassifier();
                } else if (extension.equalsIgnoreCase("txt")) {
                    this.setFileExtension("txt");
                    TextDirectoryLoader loader = new TextDirectoryLoader();
                    System.out.println("About to load text file " + fileName);
                    System.out.println("Name of path " + folder.getAbsolutePath());
                    loader.setSource(folder);
                    //loader.setSource(new File (folder.getAbsolutePath()));
                    traindata = loader.getDataSet();
                    testdata = traindata;
                    System.out.println(loader.getStructure());
                    //assumes that if it is a txt file, the last attribute is its class attribute
                    this.setClassIndex(traindata.numAttributes() - 1);
                    System.out.println(traindata.toSummaryString());
                    this.chooseClassifier();

                } else if (extension.equalsIgnoreCase("json")) {
                    JSONLoader loader = new JSONLoader();
                    loader.setSource(new File(folder.getAbsolutePath()));
                    traindata = loader.getDataSet();
                    //System.out.println(traindata.toSummaryString());
                    this.chooseClassifier();
                } else if (extension.equalsIgnoreCase("xrff")) {
                    XRFFLoader loader = new XRFFLoader();
                    loader.setSource(new File(folder.getAbsolutePath()));
                    traindata = loader.getDataSet();
                    //System.out.println(traindata.toSummaryString());
                    this.chooseClassifier();
                } else if (extension.equalsIgnoreCase("arff")) {
                    traindata = new Instances(new BufferedReader(new FileReader(folder.getAbsolutePath())));
//                                testdata = new Instances(new BufferedReader(new FileReader
//                                                (folder)));
                    //System.out.println(traindata.toSummaryString());
                    this.chooseClassifier();
                } else if (extension.equalsIgnoreCase(".mdf")) {
                    DatabaseConnection loader = new DatabaseConnection();
                    loader.connectToDatabase();
                    InstanceQuery query = new InstanceQuery();
                    query.setUsername("lamogha");
                    query.setPassword("l@mmyPHD");
                    query.setQuery("select * from customers");
                    // You can declare that your data set is sparse
                    // query.setSparseData(true);
                    traindata = query.retrieveInstances();
                    //System.out.println(data.toSummaryString());
                    this.chooseClassifier();
                }
            }

        } else {
            //Once it is a file directory, it loops through each one and process it
            for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    this.processFolder(fileEntry);
                } else {
                    //manipulate file here
                    String fileName = fileEntry.getName();
                    System.out.println(fileName);
                    String extension = getFileExtension(fileName);
                    if (!fileName.startsWith(".")) {
                        if ((extension.equalsIgnoreCase("csv") || extension.equalsIgnoreCase("xls"))) {
                            System.out.println("Opening CSV Loader");
                            CSVLoader loader = new CSVLoader();
                            loader.setFile(fileEntry.getAbsoluteFile());
                            loader.setSource(new File(fileEntry.getAbsolutePath()));
                            traindata = loader.getDataSet();
                            System.out.println(traindata.toSummaryString());
                            //assumes that if it is a csv file, the last attribute is its class attribute
                            this.setClassIndex(traindata.numAttributes() - 1);
                            this.chooseClassifier();
                        } else if (extension.equalsIgnoreCase("txt")) {
                            this.setFileExtension("txt");
                            TextDirectoryLoader loader = new TextDirectoryLoader();
                            System.out.println("About to load text file " + fileName);
                            System.out.println("Name of path " + fileEntry.getAbsolutePath());
                            loader.setSource(fileEntry);
                            traindata = loader.getDataSet();
                            testdata = loader.getDataSet();
                            this.setClassIndex(traindata.numAttributes() - 1);
                            System.out.println(traindata.toSummaryString());
                            this.chooseClassifier();

                        } else if (extension.equalsIgnoreCase("json")) {
                            JSONLoader loader = new JSONLoader();
                            loader.setSource(new File(fileEntry.getAbsolutePath()));
                            traindata = loader.getDataSet();
                            this.setClassIndex(traindata.numAttributes() - 1);
                            //System.out.println(traindata.toSummaryString());
                            this.chooseClassifier();
                        } else if (extension.equalsIgnoreCase("xrff")) {
                            XRFFLoader loader = new XRFFLoader();
                            loader.setSource(new File(fileEntry.getAbsolutePath()));
                            traindata = loader.getDataSet();
                            this.setClassIndex(traindata.numAttributes() - 1);
                            //System.out.println(traindata.toSummaryString());
                            this.chooseClassifier();
                        } else if (extension.equalsIgnoreCase("arff")) {
                            traindata = new Instances(new BufferedReader(new FileReader(fileEntry.getAbsolutePath())));
                            System.out.println("\n" + "======================================");
                            System.out.println(traindata.toSummaryString());
                            this.setClassIndex(traindata.numAttributes() - 1);
                            this.chooseClassifier();
                        } else if (extension.equalsIgnoreCase("mdf")) {
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
                            this.setClassIndex(data.numAttributes() - 1);
                            this.chooseClassifier();
                        }
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

    public String getFileExtension(String fileName) {
        String ext = "";

        int indexOfDot = fileName.indexOf(".");

        if (indexOfDot != -1) {
            ext = fileName.substring(indexOfDot + 1);
        }

        return ext;
    }

    public void setFileExtension(String fileName) {
        ext = fileName;
    }

    public void chooseClassifier() throws Exception {
        /**
         * We can use either a supervised or an un-supervised algorithm if a
         * class attribute already exists in the dataset (meaning some labelled
         * instances exists), depending on the size of the training set, the
         * decision is taken.
         */
//            classIndex = traindata.numAttributes()-1;
        classIndex = this.getClassIndex();
        //System.out.println("------USED class INDEX IS--------------" + classIndex);
        traindata.setClassIndex(classIndex);
        if (classIndex >= 0) {
            System.out.println("class attribute found....");

            //Go ahead to generate folds, then call classifier
            if (testdata == null) {
                System.out.println("NO test data" + "\n" + "--------------------------------");
                ce.generateFolds(traindata, this.getClassIndex());
            } //Or call classifier directly if supplied a test set 
            else if (folder2.exists() && classIndex >= 0 && traindata.size() >= testdata.size()) {
                System.out.println("Initial training set is larger than the test set...." + traindata.size());
                //System.out.println(testdata.toSummaryString());
                ce.callClassifier(traindata, testdata, this.getClassIndex());
            } else if (folder2.exists() && classIndex >= 0 && testdata.size() >= traindata.size()) {
                System.out.println(String.format("<b> Initial training set is smaller than the test set.... </b>" + traindata.size(), 0));
                //System.out.println(testdata.toSummaryString());
                ce.callClassifier(traindata, testdata, this.getClassIndex());
            }

        } /**
         * When there is no class attribute to show labelled instances exists
         * then use an un-supervised algorithm straight; no need for the
         * cross-validation folds.
         */
        else {
            try {
                System.out.println("NOT A LABELLED DATASET, HENCE USING AN UNSUPERVISED ALGORITHM");
                System.out.println("==========");
                if (this.getClassIndex() == -1 && traindata.numAttributes() > 10 && traindata.numInstances() > 50) {
                    uc.useEMClusterer(traindata);
                    System.out.println("Attribute Statistics:= " + traindata.attributeStats(traindata.numAttributes() - 1));
                } else {
                    uc.autoProbClass(traindata);
                }
                //uc.useFarthestFirst(traindata);
                //this.setClassIndex();
            } catch (Exception ex) {
                Logger.getLogger(FileTypeEnablerAndProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList showSummary(File file) throws FileNotFoundException, IOException {
        ArrayList attributeList = new ArrayList();
        Instances traindata = new Instances(new BufferedReader(new FileReader(file.getAbsolutePath())));
        System.out.println(traindata.toSummaryString());
        for (int i = 0; i < traindata.numAttributes(); i++) {
            attributeList.add(traindata.attribute(i).name());
        }
//              System.out.println(attributeList);
        return attributeList;
    }

    public String showPredictions() throws Exception {
        String predictions = ce.getPredictions();
        ce.setPredictions(predictions);
        return predictions;
    }

}
