package edu.muzhe.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOError;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * todo 其中这边还有的工作是对于ＨｔｔｐＣｌｉｅｎｔ中基本的连接信息进行处理．
 * 需要借鉴一下ＭＰＩ中的ｈｔｔｐＣｌｉｅｎｔ中的相对应的代码．
 * Created by muzhe on 15-7-30.
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 主要完成的功能是生成一个httpClient,对于当前的httpClient进行一个简单的设置．
     * @return
     */
    private HttpClient  init()
    {

        CloseableHttpClient httpClient = HttpClients.createDefault();



        return httpClient;

    }

    /**
     * 关闭当前的httpClient
     * @param httpClient
     */
    private void closeHttpClient(CloseableHttpClient httpClient)
    {
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("关闭httpClient的连接出现错误．");
        }
    }

    public static String post(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String body = null;

        try {

            HttpPost post = createPostFrom(url, params);
            body = invoke(httpClient, post);
            httpClient.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return body;

    }

    public static String get(String url) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String body = null;

        HttpGet httpGet = new HttpGet(url);

        body = invoke(httpClient, httpGet);
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return body;

    }

    /**
     * 用来执行一个httpPost的请求．
     * @param httpClient
     * @param post
     * @return
     */

    private static String invoke(CloseableHttpClient httpClient, HttpUriRequest post) {


        HttpResponse httpResponse = sendRequest(httpClient, post);

        String body = parseResponse(httpResponse);


        return body;
    }


    /**
     * 执行当前的请求
     * @param httpClient
     * @param httpUriRequest
     * @return
     */
    private static HttpResponse sendRequest(HttpClient httpClient, HttpUriRequest httpUriRequest) {

        HttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpUriRequest);
        } catch (IOException e) {
            logger.error("在执行http请求的时候发生了异常．",e);
        }

        return httpResponse;
    }

    /**
     * @param url,以及相关的参数转化为一个httpPost
     * @param params  这是在处理中相关的参数
     * @return
     */
    private static HttpPost createPostFrom(String url, Map<String, String> params) {

        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

        Set<String> keySets = params.keySet();
        for (String key :keySets)
        {
            nameValuePairList.add(new BasicNameValuePair(key,params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList , HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            logger.error(" 生成HTTPPOST发生了错误． ");
        }

        return httpPost;
    }


    /**
     * 将当前的返回的一个结果解析为一个Ｓｔｒｉｎｇ的字符串
     * @param response
     * @return
     */
    private static  String parseResponse(HttpResponse  response)
    {

        HttpEntity entity = response.getEntity();
        String charset = ContentType.get(entity).getCharset().displayName();

        String body = null;

        try {
            body = EntityUtils.toString(entity);
        } catch (IOException e) {
            logger.error("解析返回的报文体的时候出现了问题");
        }

        return body;

    }

}
