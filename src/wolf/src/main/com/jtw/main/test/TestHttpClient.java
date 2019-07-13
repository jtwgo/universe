package com.jtw.main.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtw.main.utils.CommonUtils;
import com.jtw.main.utils.PasswordUtils;
import org.apache.commons.io.LineIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestHttpClient {
    private static HttpPost request = new HttpPost();
    private static HttpClient client = new DefaultHttpClient();
    private static String sslPropertiesPath = "/home/mrjiang/IdeaProjects/wolf/java_se/src/ssl.properties";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) { ;

        boolean ret = registerKeystore(client,sslPropertiesPath);
        if(ret)
        {
            URI uri = createURI();
            request.getParams().setIntParameter("http.socket.timeout",3000);
            request.setURI(URI.create(uri+"/"+"test"));
            Map<String,Object> param = new HashMap<>();
            param.put("host","127.0.0.1");
            try {
                StringEntity entity = new StringEntity(objectMapper.writeValueAsString(param),"utf-8");
                request.setHeader("content-type","application/json");
                request.setEntity(entity);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            try {
                HttpResponse response = client.execute(request);
                if(response.getStatusLine().getStatusCode()!=200)
                {
                    System.out.println("result:"+response.getStatusLine());
                }
                else
                {
                    HttpEntity entity = response.getEntity();
                    String result = "";
                    BufferedReader reader = null;
                    reader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
                    LineIterator iterator = new LineIterator(reader);
                    if(iterator.hasNext())
                    {
                        result = iterator.next();
                        Map<String,Object> map= objectMapper.readValue(result,Map.class);
                        System.out.println("resultCode:200 \n"+"msg:"+map);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            System.out.println("注册证书失败");
            return;
        }
    }

    private static URI createURI() {
        URIBuilder uriBuilder = new URIBuilder();
        URI uri=null;
        uriBuilder.setScheme("https").setHost("localhost").setPort(6443);
        try {
             uri = uriBuilder.build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return uri;
    }

    private static boolean registerKeystore(HttpClient client,String sslPropertiesPath) {
        FileInputStream fis = null;
        Properties sslProperties = CommonUtils.loadProperties(sslPropertiesPath);
        try {
            fis = new FileInputStream(new File(sslProperties.getProperty("ssl.client.trust.ks.path")));
            KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
            String password = PasswordUtils.decryptByAES256(sslProperties.getProperty("ssl.client.trust.ks.pass"));
            truststore.load(fis, password.toCharArray());

            SSLSocketFactory sslSocketFactory = new SSLSocketFactory("TLSv1.2",null,null,truststore,null,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme scheme = new Scheme("https",6443,sslSocketFactory);
            client.getConnectionManager().getSchemeRegistry().register(scheme);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void releaseHttpClient()
    {

    }
}
