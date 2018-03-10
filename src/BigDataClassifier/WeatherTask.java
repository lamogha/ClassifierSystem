/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.RemoveUseless;

/**
 *
 * @author u1457710
 */
public class WeatherTask {
    
    public static void main(String args[]) throws Exception {
        
        File task = new File ("H:\\NetBeansProjects\\BigDataClassification\\data\\task.arff");
        File taskTest = new File ("H:\\NetBeansProjects\\BigDataClassification\\data\\taskTest.arff");
        //Create a csv loader
//        CSVLoader csvLoader = new CSVLoader();
//        //set the source of the loader
//    	csvLoader.setSource(new File (task.getAbsolutePath()));
//        //get the train dataset
//        //csvLoader.setNumericAttributes( csvLoader.getNumericAttributes());         
//    	Instances trainDataset = csvLoader.getDataSet();
//    	System.out.println(trainDataset.toSummaryString()); 
        
        Instances data = new Instances(new BufferedReader(new FileReader
    	                    			(task.getAbsolutePath())));
        //System.out.println(data.toSummaryString());
        //filter out useless attributes
//        String[] options = new String[]{"-R", "first-last"};
        RemoveUseless remove = new RemoveUseless();
//        remove.setOptions(options);
        remove.setInputFormat(data);
        
        //use filter to load new train dataset
        Instances traindata = Filter.useFilter(data, remove);
        //System.out.println(traindata.toSummaryString());
        //class index of the train set.
        traindata.setClassIndex(0);
        //attribute selection
        AttributeSelection selection = new AttributeSelection();
        CfsSubsetEval eval = new CfsSubsetEval();
        GreedyStepwise search = new GreedyStepwise();
        search.setSearchBackwards(true);
        selection.setEvaluator(eval);
        selection.setSearch(search);
        selection.SelectAttributes(traindata);
        
        
        System.out.println("With Removed Attributes \n "+traindata.toSummaryString());
        
        //build model
        Logistic classifier = new Logistic();
        classifier.buildClassifier(traindata);
        System.out.println(classifier);
        
        //load new test dataset without useless attribute.
        Instances data2 = new Instances(new BufferedReader(new FileReader
    	                    			(taskTest.getAbsolutePath())));
        remove.setInputFormat(data2);
        Instances testdata = Filter.useFilter(data2, remove);
        testdata.setClassIndex(0);
        System.out.println(testdata.toSummaryString());
        
        //loop through new data set to make predictions
        System.out.println("========================");
        System.out.println("Actual, Predicted");
        for(int i = 0; i<testdata.numInstances();i++){
            //get classdouble value for current instance
            double actualValue = testdata.instance(i).classValue();
            
            //get instance object of current instance
            Instance newInst = testdata.instance(i);
            //call classifyInstance, which returns a double value for the class
            double predicted = classifier.classifyInstance(newInst);
            System.out.println(actualValue + ", " + predicted);
        }
      
    }
    
}
