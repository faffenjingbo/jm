package org.apache.jmeter.reporters;

import com.alibaba.fastjson.JSON;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.jmeter.util.JMeterUtils;

import java.util.Properties;

/**
 * @author: tong.wang
 * @since: 12/1/16 7:19 PM
 * @version: 1.0.0
 */
public class KafkaProducer {

    private static Producer producer = null;
    private static final Properties props = new Properties();

    private KafkaProducer() {
        props.put("serializer.class", JMeterUtils.getJMeterProperties().getProperty("serializer.class"));
        props.put("metadata.broker.list", JMeterUtils.getJMeterProperties().getProperty("metadata.broker.list"));

        producer = new Producer(new ProducerConfig(props));
    }

    static class Inner {
        static KafkaProducer kafkaProducer = new KafkaProducer();
    }

    public static KafkaProducer getInstance(){
        return Inner.kafkaProducer;
    }

    public void send(String msg) {
        try {
            producer.send(new KeyedMessage(KafkaTopics.YPT_KAFKA_TASK_DATA_TOPIC, msg));
        } catch (Exception e) {
            System.out.println("failed to send kafka msg " + e);
        }

    }

    public void send(String topic, String msg){
        try {
            producer.send(new KeyedMessage(topic, msg));
        } catch (Exception e) {
            System.out.println("failed to send kafka msg " + e);
        }
    }

    public static class KafkaTopics {
        public static String YPT_KAFKA_TASK_DATA_TOPIC = "YPT_KAFKA_TASK_DATA_TOPIC";
        public static String YPT_KAFKA_JMETER_ERROR_TOPIC = "YPT_KAFKA_JMETER_ERROR_TOPIC";
        public static String YPT_KAFKA_JEMTER_END_TOPIC = "YPT_KAFKA_JEMTER_END_TOPIC";
    }
}