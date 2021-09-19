package com.my.example.fraud_detection.sink;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import com.my.example.fraud_detection.entity.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PublicEvolving
public class AlertSink implements SinkFunction<Alert> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(AlertSink.class);

    public AlertSink() {
    }

    public void invoke(Alert value, SinkFunction.Context context) {
        LOG.info(value.toString());
    }
}
