package com.weirwei.logger;

/**
 * @author weirwei 2021/1/20 19:29
 */
public interface Cansee {

    void info(String... msgs);
    void debug(String... msgs);
    void err(String... msgs);
    void warn(String... msgs);

}
