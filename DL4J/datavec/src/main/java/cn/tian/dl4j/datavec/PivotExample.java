package cn.tian.dl4j.datavec;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.transform.ReduceOp;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.reduce.Reducer;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.util.ClassPathResource;
import org.datavec.api.writable.Writable;
import org.datavec.spark.transform.SparkTransformExecutor;
import org.datavec.spark.transform.misc.StringToWritablesFunction;
import org.datavec.spark.transform.misc.WritablesToStringFunction;
import org.joda.time.DateTimeZone;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mengtian on 2018/4/11
 */
public class PivotExample {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        Schema inputSchema = new Schema.Builder()
                .addColumnString("person")
                .addColumnCategorical("country_visited", Arrays.asList("USA", "Japan", "China", "India"))
                .addColumnString("entry_time")
                .build();

        TransformProcess process = new TransformProcess.Builder(inputSchema)
                .stringToTimeTransform("entry_time", "YYYY/MM/DD", DateTimeZone.UTC)
                .categoricalToOneHot("country_visited")
                .reduce(new Reducer.Builder(ReduceOp.Sum)
                        .keyColumns("person")
                        .maxColumn("entry_time")
                        .build())
                .renameColumn("max(entry_time)", "most_recent_entry")
                .build();

        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[*]");
        sparkConf.setAppName("datavec example");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        String path = new ClassPathResource("PivotExampleData.csv").getFile().getAbsolutePath();
        JavaRDD<String> stringData = sparkContext.textFile(path);

        RecordReader reader = new CSVRecordReader();
        JavaRDD<List<Writable>> parsedInputData = stringData.map(new StringToWritablesFunction(reader));

        JavaRDD<List<Writable>> processedData = SparkTransformExecutor.execute(parsedInputData, process);

        JavaRDD<String> processedAsString = processedData.map(new WritablesToStringFunction(","));

        List<String> processedCollected = processedAsString.collect();
        List<String> inputDataCollected = stringData.collect();

        sparkContext.stop();
        Thread.sleep(2000);

        System.out.println("----original data schema----");
        System.out.println(inputSchema);

        System.out.println("----final data schema-----");
        Schema finalDataSchema = process.getFinalSchema();
        System.out.println(finalDataSchema);

        System.out.println("----original data----");
        for (String s : inputDataCollected) {
            System.out.println(s);
        }

        System.out.println("----processed data----");
        for (String s : processedCollected) {
            System.out.println(s);
        }
    }
}
