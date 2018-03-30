package BigDataClassifier;

import java.awt.BorderLayout;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import static weka.core.Attribute.NOMINAL;
import static weka.core.Attribute.NUMERIC;
import static weka.core.Attribute.RELATIONAL;
import static weka.core.Attribute.STRING;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

public class ClassEvaluator {
    
    Instances trainDataset2;
    Instances testDataset2;
    int trainDatasetSize;
    int testDatasetSize;
    SupervisedClassifier sc = new SupervisedClassifier();
    UnsupervisedClassifier uc = new UnsupervisedClassifier();
    //ClusterEvaluator clusterEval = new ClusterEvaluator();
    private static FileTypeEnablerAndProcessor fp;

    //set folds
    int folds = 3;
    int classIndex = 0; 

    public ClassEvaluator() {
        fp = new FileTypeEnablerAndProcessor();
    }
    
	public void generateFolds(Instances trainDataset, int classIndexPassed) throws Exception{
            //randomize data
              Random rand = new Random(1);
             
              //create random dataset
              Instances randData = new Instances(trainDataset);
              classIndex = classIndexPassed;
              //int classIndex = trainDataset.numAttributes()-1;
              randData.randomize(rand);
                  //cross-validate with 3 folds
                for(int n=0; n<folds; n++)
                {
                    trainDataset = randData.trainCV(folds, n);
                    System.out.println("Train dataset size is = "+ trainDataset.size());
                    Instances testDataset = randData.testCV(folds, n);
                    System.out.println("Test dataset size is = "+ testDataset.size());
                    trainDataset2 = trainDataset;
                    testDataset2 = testDataset;
                    trainDataset2.setClassIndex(classIndex);
                    System.out.println("The number of class labels is:- " + trainDataset2.numClasses());    
                }
                 System.out.println("Calling the right Classifier ------ ");
                 this.callClassifier(trainDataset2,testDataset2,classIndex);
              
              
              
         }
        
        public int numOfNominalAtt(Instances trainDataset, Instances testDataset){
            int nom = 0;
            for (int i=0; i<trainDataset.numAttributes(); i++){
                //boolean nominal = trainDataset2.checkForAttributeType(i);
                if (trainDataset.attribute(i).isNominal()){
                    nom++;
                }
            }
            System.out.println(nom);
            return nom;
        }
        
        public int numOfNumericAtt(Instances trainDataset, Instances testDataset){
            int num = 0;
            for (int i=0; i<trainDataset.numAttributes(); i++){
                //boolean nominal = trainDataset2.checkForAttributeType(i);
                if (trainDataset.attribute(i).isNumeric()){
                    num++;
                }
            }
            System.out.println(num);
            return num;
        }
        
        public void callClassifier(Instances trainData, Instances testData, int classIndex){
            trainData.setClassIndex(classIndex);
            if((trainData.size() >= testData.size()) && trainData.numClasses()!= 0)
            {
                if(this.numOfNumericAtt(trainData, testData) > (trainData.numAttributes()/2))
                {
                    try {
                     this.evaluatorClassifier(trainData, testData, sc.useRandomForest(trainData, classIndex));
                     System.out.println("Random Forest used" + "\n"
                             + "-------------------------------------------------------");
                    } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
//                
//                else if (trainData.checkForAttributeType(NUMERIC) 
//                        && this.numOfNumericAtt(trainData, testData) == (trainData.numAttributes()))
//                {
//                    try {
//                     this.evaluatorClassifier(trainData, testData, sc.useLinearRegression(trainData, classIndex));
//                     System.out.println("Linear Regression used"+ "\n"
//                             + "-------------------------------------------------------");
//                    } catch (Exception ex) {
//                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    
//                }
                
                else if (trainData.checkForAttributeType(RELATIONAL))
                {
                    try {
                     this.evaluatorClassifier(trainData, testData, sc.useRandomForest(trainData, classIndex));
                     System.out.println("Random Forest used"+ "\n"
                             + "-------------------------------------------------------");
                    } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }
                
                else if (trainData.checkForAttributeType(NOMINAL))
                {
                   try {
                     this.evaluatorClassifier(trainData, testData, sc.useNaiveBayes(trainData, classIndex));
                     System.out.println("Naive Bayes used"+ "\n"
                             + "-------------------------------------------------------");
                 } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                 } 
                   
                }
                 
                else
                {
                    try {
                     this.evaluatorClassifier(trainData, testData, sc.useRandomForest(trainData, classIndex));
                     System.out.println("Random Forest used"+ "\n"
                             + "-------------------------------------------------------");
                    } catch (Exception ex) {
                     Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
            else
            {
                try {
                    //use unsupervised classifier
                    uc.autoProbClass(trainData);
                    //uc.evaluatorClusterer(trainDataset2, (SimpleKMeans) uc.useEMClusterer(trainDataset2));
                } catch (Exception ex) {
                    Logger.getLogger(ClassEvaluator.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
	/** 
        public int getTrainDataSize(){
          return trainDatasetSize;
      }
      
        public int getTestDataSize(){
          return testDatasetSize;
      }
      * */
        Evaluation eval;
        public void evaluatorClassifier(Instances trainDataset, Instances testDataset, Classifier cs) throws Exception
        {
            testDataset.setClassIndex(classIndex);
            eval = new Evaluation (trainDataset);
            eval.evaluateModel(cs, testDataset);
            System.out.println(eval.toSummaryString("Evaluation results:\n", false));
              
            if (testDataset.classAttribute().isNominal()){
                System.out.println(eval.areaUnderROC(NOMINAL));
                this.plotROC();
            }
            else if (testDataset.classAttribute().isNumeric()) {
                //this.numericToNominal(testDataset, cs);
                System.out.println("DONE");
                //System.out.println(eval.areaUnderROC(NUMERIC));
            }
 	      //System.out.println(eval.toMatrixString("Confusion Matrix for this"));
        }
        
        public void numericToNominal(Instances dataset, Classifier cs)throws Exception{
                NumericToNominal convert= new NumericToNominal();
                String[] options= new String[2];
                options[0]="-R";
                options[1]="first-last";  //range of variables to make nominal
                
                convert.setOptions(options);
                convert.setInputFormat(dataset);

                Instances newData=Filter.useFilter(dataset, convert);

                System.out.println("Before");
                for(int i=0; i<dataset.numAttributes(); i=i+1) {
                    System.out.println("Nominal? "+dataset.attribute(i).isNominal());
                }

                System.out.println("After");
                for(int i=0; i<dataset.numAttributes(); i=i+1) {
                    System.out.println("Nominal? "+newData.attribute(i).isNominal());
                }
              System.out.println(newData.toSummaryString());  
              newData.setClassIndex(newData.numAttributes()-1);
              this.generateFolds(newData,classIndex);
   	      eval = new Evaluation (newData);
              eval.evaluateModel(cs, newData);
 	      System.out.println(eval.toSummaryString("Evaluation results:\n", false));
        }
        
        public void plotROC()throws Exception
        {
            // generate curve
            ThresholdCurve tc = new ThresholdCurve();
            int classIndex = 0;
            Instances result = tc.getCurve(eval.predictions(), classIndex);
//            ArffSaver saver = new ArffSaver();
//            saver.setInstances(result);
//            saver.setFile(new File("H:\\NetBeansProjects\\BigDataClassification\\data\\result.arff"));
//            saver.writeBatch();
            //f = System.currentTimeMillis();
            // plot curve
            ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
            vmc.setROCString("(AUC for " + trainDataset2.relationName() +" dataset = "+ Utils.doubleToString(ThresholdCurve.getROCArea(result), 4) + ")");
            vmc.setName(result.relationName());
            PlotData2D tempd = new PlotData2D(result);
            tempd.setPlotName(result.relationName());
            tempd.addInstanceNumberAttribute();
            // specify which points are connected
            boolean[] cp = new boolean[result.numInstances()];
            for (int n = 1; n < cp.length; n++) cp[n] = true;
            tempd.setConnectPoints(cp);
            // add plot
            vmc.addPlot(tempd);
            // display curve
            String plotName = vmc.getName();
            final javax.swing.JFrame jf = new javax.swing.JFrame("ROC Curve: " + plotName);
            jf.setSize(500, 400);
            jf.getContentPane().setLayout(new BorderLayout());
            jf.getContentPane().add(vmc, BorderLayout.CENTER);
            jf.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    jf.dispose();
                }
            });
            jf.setVisible(true);
        }
      
}