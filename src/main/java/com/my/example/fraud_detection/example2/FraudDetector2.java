
package com.my.example.fraud_detection.example2;

import com.my.example.fraud_detection.entity.Alert;
import com.my.example.fraud_detection.entity.Transaction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Skeleton code for implementing a fraud detector.
 */
public class FraudDetector2 extends KeyedProcessFunction<Long, Transaction, Alert> {

	private static final long serialVersionUID = 1L;


	private static final Logger LOG = LoggerFactory.getLogger(FraudDetector2.class);


	private static final double SMALL_AMOUNT = 1.00;
	private static final double LARGE_AMOUNT = 500.00;
	private static final long ONE_MINUTE = 60 * 1000;

	//上一次交易是小数据，true
	private transient ValueState<Boolean> flagState;

	//上一次交易是小数据，true
	private transient ValueState<Integer> flagIndex;
	private transient ValueState<Long> timeState;

	 int  index =0;

	int index2 =0;

	@Override
	public void open(Configuration parameters) throws Exception {
		index = 0;
		ValueStateDescriptor<Boolean> valueStateDescriptor = new ValueStateDescriptor<Boolean>("flag", Types.BOOLEAN);

		flagState = getRuntimeContext().getState(valueStateDescriptor);

		ValueStateDescriptor<Integer> valueStateDescriptorIndex = new ValueStateDescriptor<Integer>("index", Types.INT);

		flagIndex = getRuntimeContext().getState(valueStateDescriptorIndex);

		ValueStateDescriptor<Long> valueStateDescriptorTimerState = new ValueStateDescriptor<Long>("timer-state", Types.LONG);

		timeState = getRuntimeContext().getState(valueStateDescriptorTimerState);

		LOG.debug("[open 执行的次数] :{}  :{}",++index2);
	}

	@Override
	public void processElement(
			Transaction transaction,
			Context context,
			Collector<Alert> collector) throws Exception {

		if(flagIndex.value() == null){
			flagIndex.update( 1);
		}else {
			flagIndex.update(flagIndex.value() + 1);
		}



		LOG.debug("[每次日志2] :index:{}, flagIndex:{}, transaction:{}",++index ,flagIndex.value(),transaction);

		//上一次交易是小数据，true
		Boolean lastTransactionWasSmall = flagState.value();

		if (lastTransactionWasSmall != null) {
			if (transaction.getAmount() > LARGE_AMOUNT) {
				// Output an alert downstream
				Alert alert = new Alert();
				alert.setId(transaction.getAccountId());
				alert.setTimestamp(transaction.getTimestamp());
				alert.setAmount(transaction.getAmount());
				alert.setTransactionId(transaction.getId());

				collector.collect(alert);
			}
			cleanUp(context);
		}

		//当前交易是小数据
		if (transaction.getAmount() < SMALL_AMOUNT) {
			flagState.update(true);

			//long timer = context.timerService().currentProcessingTime() + ONE_MINUTE;
			long timer = context.timerService().currentProcessingTime() + 1000 ;
			LOG.info("注册时间：{} ,格式化:{} ,transaction:{}",timer,new Date(timer),transaction);

			context.timerService().registerProcessingTimeTimer(timer);
			timeState.update(timer);
		}

	/*	Alert alert = new Alert();
		alert.setId(transaction.getAccountId());
		alert.setAmount(transaction.getAmount());
		alert.setTimestamp(transaction.getTimestamp());

		collector.collect(alert);*/
	}

	@Override
	public void onTimer(long timestamp, OnTimerContext ctx, Collector<Alert> out) throws Exception {
		LOG.info("[时钟] 一分钟后执行");
		timeState.clear();
		flagState.clear();
	}

	private void cleanUp(Context ctx) throws Exception {
		LOG.info("[时钟 cleanUp] ");
		Long timer = timeState.value();
		ctx.timerService().deleteProcessingTimeTimer(timer);
		timeState.clear();
		flagState.clear();

	}
}
