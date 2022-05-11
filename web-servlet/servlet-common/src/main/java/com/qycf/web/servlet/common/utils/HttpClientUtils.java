/*
 * Copyright (C) 2020 Baidu, Inc. All Rights Reserved.
 */

package com.qycf.web.servlet.common.utils;


import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * User: qycf
 * Date: 2020/4/17
 * Description: http操作工具类
 */
public final class HttpClientUtils {

    public static final RequestConfig DEFAULT_REQ_CONFIG = RequestConfig.custom()
//            .setSocketTimeout(3000)
//            .setConnectTimeout(3000)
//            .setConnectionRequestTimeout(3000)
            .build();

    private static final PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

    static {
        manager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());
        manager.setMaxTotal(600); // 全并发
        manager.setDefaultMaxPerRoute(200); // 单个主机/域名的并发上限
    }

    public static Set<BasicHeader> CONNECTION_CLOSE_HEADER = ImmutableSet.of(new BasicHeader("Connection", "close"));

    private HttpClientUtils() {

    }

    /**
     * 把BasicNameValuePair连接为k1=v1&k2=v2&k3=v3的格式
     *
     * @param kvs 参数
     * @return string k1=v1&k2=v2&k3=v3
     */
    public static String joinParams(final Collection<BasicNameValuePair> kvs) {
        return Joiner.on("&").join(kvs);
    }

    /**
     * 构建一个url，会把第二个参数的BasicNameValuePair连接为k1=v1&k2=v2&k3=v3的格式
     * 如果baseUri中存在‘?’符号，则使用‘&’连接baseUri和参数，否则使用‘?’
     *
     * @param baseUri url主体
     * @param kvs     参数
     * @return url  string
     */
    public static String buildUrl(final String baseUri, final Collection<BasicNameValuePair> kvs) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(baseUri));

        if (CollectionUtils.isEmpty(kvs)) {
            return baseUri;
        }

        StringBuilder result = new StringBuilder();
        result.append(baseUri);
        if (baseUri.indexOf("?") > 0) {
            result.append('&');
        } else {
            result.append('?');
        }

        return Joiner.on("&").appendTo(result, kvs).toString();
    }

    /**
     * 使用content创建一个UTF-8格式的StringEntity
     *
     * @param content String content
     * @return UTF8 StringEntity instance
     */
    public static StringEntity newJsonStringEntity(String content) {
        return new StringEntity(content, ContentType.create("application/json", Charsets.UTF_8));
    }

    /**
     * 使用者应尽量调用一次，重复使用 CloseableHttpClient 实例
     *
     * @return CloseableHttpClient instance
     */
    public static CloseableHttpClient newHttpClient() {

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(DEFAULT_REQ_CONFIG)
                .setDefaultHeaders(CONNECTION_CLOSE_HEADER)
                .setConnectionManager(manager)
                .build();
    }

    public static CloseableHttpClient newHttpClient(SSLConnectionSocketFactory sslsf) {

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(DEFAULT_REQ_CONFIG)
                .setDefaultHeaders(CONNECTION_CLOSE_HEADER)
                .setConnectionManager(manager)
                .setSSLSocketFactory(sslsf)
                .build();

    }

    public static String executePost(CloseableHttpClient httpClient, HttpPost post) throws IOException {
        return executeHTTP(httpClient, post);
    }

    public static String executeGet(CloseableHttpClient httpClient, HttpGet httpGet) throws IOException {
        return executeHTTP(httpClient, httpGet);
    }

    public static String executeDelete(CloseableHttpClient httpClient, HttpDelete httpDelete) throws IOException {
        return executeHTTP(httpClient, httpDelete);
    }

    public static String executePut(CloseableHttpClient httpClient, HttpPut httpPut) throws IOException {
        return executeHTTP(httpClient, httpPut);
    }

    private static String executeHTTP(CloseableHttpClient httpClient, HttpUriRequest request) throws IOException {
        String content = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            content = EntityUtils.toString(response.getEntity());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return content;
    }
}
