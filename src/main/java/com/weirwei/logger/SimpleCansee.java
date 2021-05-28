package com.weirwei.logger;

import com.alibaba.fastjson.JSON;
import com.weirwei.service.KafkaProducer;
import com.weirwei.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author weirwei 2021/1/20 19:36
 */
@Component
@Slf4j
public class SimpleCansee implements Cansee {


    @Resource
    HttpServletRequest req;
    @Resource
    HttpServletResponse rsp;

    @Resource
    private KafkaProducer kafkaProducer;
    @Value("${log.project.id}")
    private String projId;

    @Override
    public void info(String... msgs) {
        String info = messageMaker(CanseeLog.INFO, msgs);
        kafkaProducer.send(info);
        log.info(info);
    }

    @Override
    public void debug(String... msgs) {
        String debug = messageMaker(CanseeLog.DEBUG, msgs);
        log.debug(debug);
    }

    @Override
    public void err(String... msgs) {
        String err = messageMaker(CanseeLog.ERROR, msgs);
        kafkaProducer.send(err);
        log.error(err);
    }

    @Override
    public void warn(String... msgs) {
        String warn = messageMaker(CanseeLog.WARNING, msgs);
        kafkaProducer.send(warn);
        log.warn(warn);
    }

    private String messageMaker(int code, String... msgs) {
        StringBuilder message = new StringBuilder();
        for (String msg : msgs) {
            message.append(msg);
        }

        // 获得调用类的类名
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[3].getClassName();
        // 获得请求id
        String reqId = (String) req.getAttribute("reqId");
        String logId = IdUtil.getLogId();
        CanseeLog canseeLog = new CanseeLog(logId, reqId, code, message.toString(), projId, className);
        return JSON.toJSONString(canseeLog);
    }
}
