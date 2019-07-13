package com.jtw.main.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Map;

public class RestClient
{

    private static final int HTTP_REQUSET_TIMEOUT = 3000;

    private static final int HTTP_RESPONSE_TIMEOUT = 3000;

    private static final int HTTP_CONNECT_TIMEOUT = 5000;

    private static final String UTF_8 = "UTF-8";

    private static final String CONTENT_TYPE = "content-type";

    private static final String APPLICATION_JSON = "application/json;utf-8";

    private HttpClient client;

    public RestClient(String cerPath)
    {
        KeyStore trustore = regKeystore(cerPath);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(HTTP_REQUSET_TIMEOUT)
                .setSocketTimeout(HTTP_RESPONSE_TIMEOUT).setConnectTimeout(HTTP_CONNECT_TIMEOUT).build();
        try
        {
            SSLContext sslContext = SSLContexts.custom().
                    loadTrustMaterial(trustore,new TrustSelfSignedStrategy()).build();
            SSLConnectionSocketFactory sslsf =
                    new SSLConnectionSocketFactory(sslContext,new String[]{"TLSv1.2"},null,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(requestConfig).build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

    }

    private KeyStore regKeystore(String cerPath)
    {
        FileInputStream fis = null;
        KeyStore truststore = null;
        try
        {
            truststore = KeyStore.getInstance(KeyStore.getDefaultType());
            fis = new FileInputStream(new File(cerPath));
            truststore.load(null,null);

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate certificate = certificateFactory.generateCertificate(fis);

            truststore.setCertificateEntry("my_ca_rooat",certificate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            CommonUtils.close(fis);
        }
        return truststore;
    }

    /**
     *发送post请求
     * @param url 访问的地址
     * @param headers 请求头消息
     * @param body 请求body,json格式
     * @return HttpResponse
     */
    public HttpResponse doPost(String url, Map<String,String> headers,String body)
    {
        HttpPost request = new HttpPost(url);
        request.setHeader(CONTENT_TYPE,APPLICATION_JSON);
        setHeaders(request,headers);
        request.setEntity(new StringEntity(body,UTF_8));
        HttpResponse response = executeHttpRequst(request);
        return response;
    }

    /**
     * 发送get请求
     * @param url 请求url
     * @param headers 请求头信息
     * @return HttpResponse
     */
    public HttpResponse doGet(String url,Map<String,String> headers)
    {
        HttpGet request = new HttpGet(url);
        setHeaders(request,headers);
        HttpResponse response = executeHttpRequst(request);
        return response;
    }

    private HttpResponse executeHttpRequst(HttpRequestBase request)
    {
        HttpResponse response = null;
        CloseableHttpResponse closeResponse = null;
        try {
            closeResponse = (CloseableHttpResponse) client.execute(request);
            response = new BasicHttpResponse(closeResponse.getStatusLine());
            response.setEntity(new StringEntity(EntityUtils.
                    toString(closeResponse.getEntity())));
            Header[] allHeaders = closeResponse.getAllHeaders();
            if(allHeaders != null)
            {
                for (Header header:allHeaders)
                {
                    response.setHeader(header.getName(),header.getValue());
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

        } finally
        {
            CommonUtils.close(closeResponse);
            releaseConnection(request);

        }
        return response;
    }

    private void setHeaders(HttpRequestBase request, Map<String, String> headers)
    {
        if (headers != null && !headers.isEmpty() )
        {
            for (Map.Entry<String,String> header : headers.entrySet())
            {
                request.setHeader(header.getKey(),header.getValue());
            }
        }

    }

    private void releaseConnection(HttpRequestBase request)
    {
        request.releaseConnection();
    }

}
