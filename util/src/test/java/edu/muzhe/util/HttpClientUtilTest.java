package edu.muzhe.util;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * Created by muzhe on 15-7-30.
 */
public class HttpClientUtilTest {


    @Test
    public void testHttpClientUtil()
    {

        Map<String ,String > params  = Maps.newHashMap();

        params.put("name" , "zhangsan");
        params.put("password" , "lisi");
        String url ="http://mvnrepository.com/artifact/junit/junit/4.12";

        String s = HttpClientUtil.post(url , params);

        System.out.println(s);

    }


}
