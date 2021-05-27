package com.weirwei.service;

import com.weirwei.logger.Cansee;
import com.weirwei.logger.CanseeLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

/**
 * @author weirwei 2021/1/4 10:44
 */
@Service
public class KafkaProducer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${log.df-kafkaconsumer.topic}")
    private String topic;

    public void send(String message) {

        LOG.info("topic=" + topic + ",message=" + message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(success -> LOG.info("KafkaMessageProducer 发送消息成功！"),
                fail -> LOG.error("KafkaMessageProducer 发送消息失败！"));
    }
}
