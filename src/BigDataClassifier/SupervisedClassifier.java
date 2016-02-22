 /*
 * supervisedClassifier algorithm
 */
package BigDataClassifier;

/**
 *
 * @author lamogha
 */
public class SupervisedClassifier {
    
      public void supervisedClassifier(String filePathName) {
        //call your supervised classifier here with the path to the file 
          this.importanceLevelSetting();
          //use training set to establish class function
          /**
           * call classifier to classify new instances (i.e. other records in the file)
           * Each column is classified into either of three categories i.e. high, medium, low
           * Where the first three columns with the weight of 1, 2, 3 are all in the 'high' class
           * Other columns are assigned weights by using a probability density estimation to derive
           * the probability of each column belonging to either
          */
          
    }
      
      /**
       * TODO read the column names and allow the user enter weights for attributes
       * Use the weights as a factor to pre-populate the training set
       * Take first three rows in structured file as the training data set.
      */
      public void importanceLevelSetting(){
          
      } 
      
}
