package edu.muzhe.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by muzhe on 15-7-28.
 */
public class ConfigUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);


    private static Properties properties = null;

    private static final String propertyPath = "applicationContext.properties";

    static {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyPath);
        properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {

            logger.error("获取配置文件错误．");
        }

    }


    public static String getProperty(String key) {

        String property = properties.getProperty(key);
        return property;

    }


}
