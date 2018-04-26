package cn.tian.dl4j.datavec.basic;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.writable.Writable;
import org.datavec.spark.transform.SparkTransformExecutor;
import org.datavec.spark.transform.misc.StringToWritablesFunction;
import org.datavec.spark.transform.misc.WritablesToStringFunction;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by mengtian on 2018/3/30
 */
public class BasicTransformExample {
    public static void main(String[] args) throws IOException {
        String filename = "iris.data";
        URL url = new URL("https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data");

        File irisText = new File(filename);
        if (!irisText.exists()) {
            FileUtils.copyURLToFile(url, irisText);
        }

        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("datavec example");

        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<String> stringData = context.textFile(filename);

        RecordReader reader = new CSVRecordReader();
        JavaRDD<List<Writable>> parsedInputData = stringData.filter(x -> !x.isEmpty())
                .map(new StringToWritablesFunction(reader));

        List<String> inputDataCollected = stringData.collect();
        System.out.println("original data-----");
        for (String s : inputDataCollected) {
            System.out.println("'" + s + "'");
        }

        JavaRDD<String> processedAsString = parsedInputData.map(new WritablesToStringFunction(","));
        List<String> inputDataParsed = processedAsString.collect();
        System.out.println("parsed data----");
        for (String s : inputDataParsed) {
            System.out.println("'" + s + "'");
        }

        Schema schema = new Schema.Builder()
                .addColumnsDouble("Sepal length", "Sepal width", "Petal lengyh", "Petal width")
                .addColumnCategorical("Species", "Iris-setosa", "Iris-versicolor", "Iris-virginica")
                .build();

        TransformProcess process = new TransformProcess.Builder(schema)
                .categoricalToInteger("Species")
                .build();

        JavaRDD<List<Writable>> processedData = SparkTransformExecutor.execute(parsedInputData, process);

        processedAsString = processedData.map(new WritablesToStringFunction(","));
        inputDataParsed = processedAsString.collect();
        System.out.println("parsed and filtered data---");
        for (String s : inputDataParsed) {
            System.out.println(s);
        }
    }
}
