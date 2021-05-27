package com.weirwei.logger;

import com.alibaba.fastjson.JSON;
import com.weirwei.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author weirwei 2021/1/20 19:36
 */
@Component
@Slf4j
public class SimpleCansee implements Cansee {


    @Resource
    private KafkaProducer kafkaProducer;
    @Value("${log.project.id}")
    private String projId;

    @Override
    public void info(String... msgs) {
        String info = messageMaker(msgs);
        log.info(info);
    }

    @Override
    public void debug(String... msgs) {
        String debug = messageMaker(msgs);
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();
        CanseeLog canseeLog = new CanseeLog(CanseeLog.DEBUG, debug, projId, className);
        String jsonString = JSON.toJSONString(canseeLog);
        kafkaProducer.send(jsonString);
        log.debug(jsonString);
    }

    @Override
    public void err(String... msgs) {
        String err = messageMaker(msgs);
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();
        CanseeLog canseeLog = new CanseeLog(CanseeLog.ERROR, err, projId, className);
        String jsonString = JSON.toJSONString(canseeLog);
        kafkaProducer.send(jsonString);
        log.error(jsonString);
    }

    @Override
    public void warn(String... msgs) {
        String warn = messageMaker(msgs);
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();
        CanseeLog canseeLog = new CanseeLog(CanseeLog.WARNING, warn, projId, className);
        String jsonString = JSON.toJSONString(canseeLog);
        kafkaProducer.send(jsonString);
        log.warn(jsonString);
    }

    private String messageMaker(String... msgs) {
        StringBuilder message = new StringBuilder();
        for (String msg : msgs) {
            message.append(msg);
        }
        return message.toString();
    }
}
