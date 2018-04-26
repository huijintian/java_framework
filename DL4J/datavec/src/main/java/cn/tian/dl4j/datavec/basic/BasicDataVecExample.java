package cn.tian.dl4j.datavec.basic;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.condition.ConditionOp;
import org.datavec.api.transform.condition.column.CategoricalColumnCondition;
import org.datavec.api.transform.condition.column.DoubleColumnCondition;
import org.datavec.api.transform.filter.ConditionFilter;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.transform.transform.time.DeriveColumnsFromTimeTransform;
import org.datavec.api.writable.DoubleWritable;
import org.datavec.api.writable.Writable;
import org.datavec.spark.transform.SparkTransformExecutor;
import org.datavec.spark.transform.misc.StringToWritablesFunction;
import org.datavec.spark.transform.misc.WritablesToStringFunction;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mengtian on 2018/3/28
 */
public class BasicDataVecExample {
    public static void main(String[] args) throws IOException {
        Schema schema = new Schema.Builder()
                .addColumnString("DateTimeString")
                .addColumnsString("CustomerId", "MerchantId")
                .addColumnInteger("NumItemsInTransaction")
                .addColumnCategorical("MerchantCountryCode", Arrays.asList("USA", "CAN", "FR", "MX"))
                .addColumnDouble("TransactionAmountUSD", 0.0, null, false, false)
                .addColumnCategorical("FraudLabel", Arrays.asList("Fraud", "Legit"))
                .build();

        System.out.println("column nums:" + schema.numColumns());
        System.out.println("column names:" + schema.getColumnNames());
        System.out.println("column types:" + schema.getColumnTypes());


        TransformProcess process = new TransformProcess.Builder(schema)
                .removeColumns("CustomerId")
                .filter(new ConditionFilter(
                        new CategoricalColumnCondition(
                                "MerchantCountryCode",
                                ConditionOp.NotInSet,
                                new HashSet<>(Arrays.asList("USA", "CAN")))))
                .conditionalReplaceValueTransform(
                        "TransactionAmountUSD",
                        new DoubleWritable(0.0),
                        new DoubleColumnCondition("TransactionAmountUSD", ConditionOp.LessThan, 0.0)
                )
                .stringToTimeTransform("DateTimeString", "YYYY-MM-DD HH:mm:ss.SSS", DateTimeZone.UTC)
                .renameColumn("DateTimeString", "DateTime")
                .transform(
                        new DeriveColumnsFromTimeTransform.Builder("DateTime")
                                .addIntegerDerivedColumn("HourOfDay", DateTimeFieldType.hourOfDay()).build()
                )
                .removeColumns("DateTime")
                .build();

        Schema outputSchema = process.getFinalSchema();

        System.out.println("outputSchema column nums:" + outputSchema.numColumns());
        System.out.println("outputSchema column names:" + outputSchema.getColumnNames());
        System.out.println("outputSchema column types:" + outputSchema.getColumnTypes());


        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("datavec example");

        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        String path = new ClassPathResource("exampledata.csv").getFile().getAbsolutePath();
        JavaRDD<String> stringData = sparkContext.textFile(path);

        RecordReader reader = new CSVRecordReader();
        JavaRDD<List<Writable>> parsedInputData = stringData.map(new StringToWritablesFunction(reader));

        JavaRDD<List<Writable>> processedData = SparkTransformExecutor.execute(parsedInputData, process);

        JavaRDD<String> processedAsString = processedData.map(new WritablesToStringFunction(","));

        List<String> processedCollected = processedAsString.collect();
        List<String> inputDataCollected = stringData.collect();

        System.out.println("----origin data-----");
        for (String s : inputDataCollected) {
            System.out.println(s);
        }

        System.out.println("---process data----");
        for (String s : processedCollected) {
            System.out.println(s);
        }

    }
}
