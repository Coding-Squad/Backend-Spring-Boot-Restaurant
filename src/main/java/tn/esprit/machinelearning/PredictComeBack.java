package tn.esprit.machinelearning;

import java.io.File;
import java.util.List;
import java.util.Vector;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.InstanceQuery;

public class PredictComeBack {
	
public Double Test(Long id) throws Exception{
		
		DataSource source = new DataSource("src/main/resources/predict/comeback/resto1.arff");
		Instances trainDataset = source.getDataSet();	
		//set class index to the last attribute
		trainDataset.setClassIndex(trainDataset.numAttributes()-1);

		//build model
		J48 tree = new J48();
		tree.buildClassifier(trainDataset);
		//output model
		System.out.println(tree);
		
		// test query
		
		 InstanceQuery query = new InstanceQuery();
	        query.setUsername("root");
	        query.setPassword("root");
	        query.setQuery("SELECT COUNT(id),(SUM(rating))/(COUNT(id)),come_back FROM restaurantdb.user_order u where user_id="+id+" AND order_date between DATE_ADD(NOW(),INTERVAL -30 minute) and NOW();");
	 
	        Instances data = query.retrieveInstances();
	      //create attribute
			List values = new Vector(); /* FastVector is now deprecated. Users can use any java.util.List */
		      values.add("No");              /* implementation now */
		      values.add("Yes");
		      data.deleteAttributeAt(2);
		     data.insertAttributeAt(new Attribute("come_back", values), data.numAttributes());
		     
	        System.out.println(data);
	        
	        ArffSaver saver = new ArffSaver();
	        saver.setInstances(data);
	        saver.setFile(new File("src/main/java/resto2.arff"));
	        saver.writeBatch();
	        		
		// fin test query
	        
	      //load new dataset
			DataSource source1 = new DataSource("src/main/java/resto2.arff");
			Instances testDataset = source1.getDataSet();	
			//set class index to the last attribute
			testDataset.setClassIndex(testDataset.numAttributes()-1);
	        
	      //loop through the new dataset and make predictions
			System.out.println("===================");
			System.out.println("Actual Class, J48 Predicted");
			
				//get class double value for current instance
				double actualValue = testDataset.instance(0).classValue();

				//get Instance object of current instance
				Instance newInst = testDataset.instance(0);
				//call classifyInstance, which returns a double value for the class
				double predJ48 = tree.classifyInstance(newInst);
	
			return predJ48;

		
	}

}
