package com.weirwei.logger;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author weirwei 2021/1/21 18:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CanseeLog implements Serializable {

    private static final long serialVersionUID=1L;

    public final static int INFO = 10001;
    public final static int DEBUG = 10002;
    public final static int WARNING = 10003;
    public final static int ERROR = 10004;

    private Long logId;

    private Integer logType;

    private String logMsg;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logTime;

    private String projId;

    private String logClass;

    public CanseeLog(Integer logType, String logMsg, String projId, String logClass) {
        this.logType = logType;
        this.logMsg = logMsg;
        this.projId = projId;
        this.logClass = logClass;
        this.logTime = LocalDateTime.now();
    }
}
