package com.qycf.web.servlet.common.utils;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.solidfire.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @Author qycf
 * @Description
 * @Date 2020/6/9
 **/
@Slf4j
@Component
public class HttpUtils {

    private static final CloseableHttpClient httpClient = HttpClientUtils.newHttpClient();

    private static Gson gson = new Gson();


    public static String getCall(String url) {
        if (Strings.isNullOrEmpty(url)) {
            return "";
        }

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        httpGet.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

        String response = null;
        try {
            response = HttpClientUtils.executeGet(httpClient, httpGet);
        } catch (IOException e) {
            log.error("----------error--------");
            log.error("callback result is error,message {}", Throwables.getRootCause(e).getMessage());
        }
        return response;
    }

    public static String postCall(String url, Object inputParams) {
        if (Strings.isNullOrEmpty(url)) {
            return "";
        }

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

        try {
            httpPost.setEntity(new StringEntity(gson.toJson(inputParams), Consts.UTF_8));
        } catch (Exception ex) {
            log.error("----------error--------");
            log.error("callback object2Json false, message is {}", Throwables.getRootCause(ex).getMessage());
        }
        String response = null;
        try {

            response = HttpClientUtils.executePost(httpClient, httpPost);
        } catch (Exception e) {
            log.error("----------error--------");
            log.error("callback result is error,message {}", Throwables.getRootCause(e).getMessage());
        }
        return response;
    }

    public static String postCallFormUrlencoded(String url, List<NameValuePair> params) {
        if (Strings.isNullOrEmpty(url)) {
            return "";
        }

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString());

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        } catch (Exception ex) {
            log.error("----------error--------");
            log.error("callback object2Json false, message is {}", Throwables.getRootCause(ex).getMessage());
        }
        String response = null;
        try {

            response = HttpClientUtils.executePost(httpClient, httpPost);
        } catch (Exception e) {
            log.error("----------error--------");
            log.error("callback result is error,message {}", Throwables.getRootCause(e).getMessage());
        }
        return response;
    }

    public static String postCallFormData(String url, byte[] inputParams) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString());

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
            builder.addBinaryBody("image", inputParams, ContentType.MULTIPART_FORM_DATA, "file");
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
        } catch (Exception ex) {
            log.error("----------error--------");
            log.error("callback object2Json false, message is {}", Throwables.getRootCause(ex).getMessage());
        }
        String response = null;
        try {
            response = HttpClientUtils.executePost(httpClient, httpPost);
        } catch (Exception e) {
            log.error("----------error--------");
            log.error("callback result is error,message {}", Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
