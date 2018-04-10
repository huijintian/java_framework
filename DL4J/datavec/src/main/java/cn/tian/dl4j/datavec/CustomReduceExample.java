package cn.tian.dl4j.datavec;

import org.datavec.api.transform.ReduceOp;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.metadata.ColumnMetaData;
import org.datavec.api.transform.metadata.StringMetaData;
import org.datavec.api.transform.ops.AggregableMultiOp;
import org.datavec.api.transform.ops.IAggregableReduceOp;
import org.datavec.api.transform.reduce.AggregableColumnReduction;
import org.datavec.api.transform.reduce.Reducer;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.writable.UnsafeWritableInjector;
import org.datavec.api.writable.Writable;
import org.joda.time.DateTimeZone;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by mengtian on 2018/4/3
 */
public class CustomReduceExample {

    private static class CustomReduceTakeSecond implements AggregableColumnReduction {
        @Override
        public IAggregableReduceOp<Writable, List<Writable>> reduceOp() {
            return new AggregableMultiOp<>(Collections.<IAggregableReduceOp<Writable, Writable>>
                    singletonList(new AggregableSecond<Writable>()));
        }

        @Override
        public List<String> getColumnsOutputName(String columnInputName) {
            return Collections.singletonList("myCustomResuce(" + columnInputName + ")");
        }

        @Override
        public List<ColumnMetaData> getColumnOutputMetaData(List<String> newColumnName, ColumnMetaData columnInputMeta) {
            ColumnMetaData thisColumnMeta = new StringMetaData(newColumnName.get(0));
            return Collections.singletonList(thisColumnMeta);
        }


        public static class AggregableSecond<T> implements IAggregableReduceOp<T, Writable> {

            private T firstMet = null;
            private T elem = null;

            protected T getFirstMet() {
                return firstMet;
            }

            protected T getElem() {
                return elem;
            }

            @Override
            public <W extends IAggregableReduceOp<T, Writable>> void combine(W accu) {
                if (accu instanceof AggregableSecond && elem == null) {
                    if (firstMet == null) {
                        AggregableSecond<T> accumulator = (AggregableSecond) accu;
                        T otherFirst = accumulator.getFirstMet();
                        T otherElement = accumulator.getElem();
                        if (otherFirst != null) firstMet = otherFirst;
                        if (otherElement != null) elem = otherElement;
                    } else {
                        AggregableSecond<T> accumulator = (AggregableSecond) accu;
                        T otherFirst = accumulator.getFirstMet();
                        if (otherFirst != null) elem = otherFirst;
                    }
                }
            }

            @Override
            public void accept(T t) {
                if (firstMet == null) firstMet = t;
                else {
                    if (elem == null) elem = t;
                }
            }

            @Override
            public Writable get() {
                return UnsafeWritableInjector.inject(elem);
            }
        }

        @Override
        public Schema transform(Schema inputSchema) {
            return null;
        }

        @Override
        public void setInputSchema(Schema inputSchema) {

        }

        @Override
        public Schema getInputSchema() {
            return null;
        }

        @Override
        public String outputColumnName() {
            return null;
        }

        @Override
        public String[] outputColumnNames() {
            return new String[0];
        }

        @Override
        public String[] columnNames() {
            return new String[0];
        }

        @Override
        public String columnName() {
            return null;
        }
    }

    public static void main(String[] args) {
        Schema schema = new Schema.Builder()
                .addColumnString("DateTimeString")
                .addColumnsString("CustomerId", "MerchantId")
                .addColumnInteger("NumItemsInTransaction")
                .addColumnCategorical("MerchantCountryCode", Arrays.asList("USA", "CAN", "FR", "MX"))
                .addColumnDouble("TransactionAmountUSD", 0.0, null, false, false)
                .addColumnCategorical("FraudLabel", Arrays.asList("Fraud", "Legit"))
                .build();

        TransformProcess process = new TransformProcess.Builder(schema)
                .removeColumns("CustomerId", "MerchantId", "MerchantCountryCode", "FraudLabel")
                .stringToTimeTransform("DateTimeString", "YYYY-MM-DD HH:mm:ss.SSS", DateTimeZone.UTC)
                .renameColumn("DateTimeString", "DateTime")
                .reduce(new Reducer.Builder(ReduceOp.TakeFirst)
                        .keyColumns("DateTime", "DateTineString")
                        .maxColumn("NumItemsInTransaction")
                        .customReduction("", new CustomReduceTakeSecond())
                        .build()
                ).build();

        Schema outputSchema = process.getFinalSchema();
        System.out.println(outputSchema);
    }
}
