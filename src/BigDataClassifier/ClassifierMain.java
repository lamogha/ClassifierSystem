/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigDataClassifier;
/**
 *Starts the processing of the dataset to be classified or clustered
 * @author lamogha
 */
public class ClassifierMain {

	public static void main(String args[]) throws Exception {
    	//DataSource source = new DataSource("/workspace/data/Samsung-Galaxy-S3 Mini 2.csv");
    	//Instances dataset = source.getDataSet();
    	//Instances dataset = new Instances(new BufferedReader(new FileReader("/workspace/data/ReutersCorn-test.arff")));
    	 
    	 //System.out.println(dataset.toSummaryString());
    	 
    	 //ArffSaver saver = new ArffSaver();
    	 //saver.setInstances(dataset);
    	 //saver.setFile(new File("/workspace/data/ReutersCorn-test-new.arff"));
    	 //saver.writeBatch();
        FileTypeEnablerAndProcessor f = new FileTypeEnablerAndProcessor ();
        f.fileEntry();
    }
}
