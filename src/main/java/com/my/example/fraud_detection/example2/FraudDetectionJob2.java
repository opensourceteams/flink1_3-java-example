
package com.my.example.fraud_detection.example2;

import com.my.example.fraud_detection.entity.Alert;
import com.my.example.fraud_detection.entity.Transaction;
import com.my.example.fraud_detection.sink.AlertSink;
import com.my.example.fraud_detection.source.TransactionSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Skeleton code for the datastream walkthrough
 */
public class FraudDetectionJob2 {
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		//env.setParallelism(2);
		DataStream<Transaction> transactions = env
			.addSource(new TransactionSource())
			.name("transactions");



		DataStream<Alert> alerts = transactions
			.keyBy(Transaction::getAccountId)
			.process(new FraudDetector2())
			.name("process信息");

		alerts
			.addSink(new AlertSink())
			.name("sink信息");

		env.execute("欺诈检测 2");
		//System.out.println(env.getExecutionPlan());
	}
}
