package cn.tian.dl4j.datavec.analysis;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.transform.analysis.DataAnalysis;
import org.datavec.api.transform.analysis.columns.DoubleAnalysis;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.transform.ui.HtmlAnalysis;
import org.datavec.api.util.ClassPathResource;
import org.datavec.api.writable.Writable;
import org.datavec.spark.transform.AnalyzeSpark;
import org.datavec.spark.transform.misc.StringToWritablesFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by mengtian on 2018/4/13
 */
public class IrisAnalysis {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema.Builder()
                .addColumnsDouble("Sepal length", "Sepal width", "Petal length", "petal width")
                .addColumnInteger("Species")
                .build();

        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("iris datavec example");

        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        String directory = new ClassPathResource("IrisData/iris.txt").getFile().getAbsolutePath();
        JavaRDD<String> stringData = sparkContext.textFile(directory);

        RecordReader reader = new CSVRecordReader();
        JavaRDD<List<Writable>> parsedInputData = stringData.map(new StringToWritablesFunction(reader));

        int maxHistogramBuckets = 10;
        DataAnalysis dataAnalysis = AnalyzeSpark.analyze(schema, parsedInputData, maxHistogramBuckets);

        System.out.println(dataAnalysis);

        DoubleAnalysis doubleAnalysis = (DoubleAnalysis) dataAnalysis.getColumnAnalysis("Sepal length");
        double minValue = doubleAnalysis.getMin();
        double maxValue = doubleAnalysis.getMax();
        double mean = doubleAnalysis.getMean();

        System.out.printf("min:%s, max:%s, mean:%s%n", minValue, maxValue, mean);

        HtmlAnalysis.createHtmlAnalysisFile(dataAnalysis, new File("DataVecIrisAnalysis.html"));

    }
}
