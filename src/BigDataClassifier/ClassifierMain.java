/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.DatabaseLoader;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *Starts the processing of the dataset to be classified or clustered
 * @author lamogha
 */
public class ClassifierMain {

	public static void main(String args[]) throws Exception {
	/**
		Class.forName ("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded...");
        Connection conn = DriverManager.getConnection ("jdbc:mysql://adegokeobasa.me:3306/classic_models","lamogha","l@mmyPHD");
        System.out.println("connection established...");
        
        DatabaseLoader loader = new DatabaseLoader();
        loader.connectToDatabase();
    	loader.setSource("jdbc:mysql://adegokeobasa.me:3306/classic_models/customers", "lamogha", "l@mmyPHD" );
    	Instances traindata = loader.getDataSet();
    	System.out.println(traindata.toSummaryString());
    	 
    	ArffSaver saver = new ArffSaver();
    	 saver.setInstances(traindata);
    	 saver.setFile(new File("/workspace/data/classic_models.arff"));
    	 saver.writeBatch();
    	 
    	 conn.close();
    	 
        */
	FileTypeEnablerAndProcessor f = new FileTypeEnablerAndProcessor ();
        f.fileEntry();
    }
}
