package com.jtw.main.ssl;


import com.jtw.main.utils.CommonUtils;
import com.jtw.main.utils.PasswordUtils;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SSLClient
{
    private SSLSocket SslClient;
    private final String sslPropertiesPah = "/home/mrjiang/IdeaProjects/wolf/java_se/src/ssl.properties";
    public SSLSocket getSslClient() {
        Properties sslProerties = CommonUtils.loadProperties(sslPropertiesPah);
        if(null == sslProerties)
        {
            throw new RuntimeException("properties is null");
        }
        final String protocol = sslProerties.getProperty("ssl.protocol");
        final int port = Integer.parseInt(sslProerties.getProperty("ssl.port"));
        final String serverIp = sslProerties.getProperty("ssl.serverIp");
        final String clientstorePath = sslProerties.getProperty("ssl.client.ks.path");
        final String clientPass = PasswordUtils.decryptByAES256(sslProerties.getProperty("ssl.client.ks.pass"));
        final String clientstoreType = sslProerties.getProperty("ssl.client.ks.type");
        final String clientstoreFormat = sslProerties.getProperty("ssl.client.ks.format");

        final String trustsotrePath = sslProerties.getProperty("ssl.client.trust.ks.path");
        final String truststoreType = sslProerties.getProperty("ssl.client.trust.ks.type");
        final String trustPass = PasswordUtils.decryptByAES256(sslProerties.getProperty("ssl.client.trust.ks.pass"));
        final String truststoreFormat = sslProerties.getProperty("ssl.client.trust.ks.format");
        FileInputStream trustFis = null;
        FileInputStream selfIn = null;
        try {
            SSLContext sslContext = SSLContext.getInstance(protocol);
            //加载trustkeysotre
            KeyStore trustsotre = KeyStore.getInstance(truststoreType);
            trustFis = new FileInputStream(new File(trustsotrePath));
            trustsotre.load(trustFis,trustPass.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(truststoreFormat);
            trustManagerFactory.init(trustsotre);
            TrustManager[] trustManagers =  trustManagerFactory.getTrustManagers();
            //加载自己的keystore
            KeyStore selfKeystore = KeyStore.getInstance(clientstoreType);
            selfIn = new FileInputStream(new File(clientstorePath));
            selfKeystore.load(selfIn,clientPass.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(clientstoreFormat);
            keyManagerFactory.init(selfKeystore,clientPass.toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            sslContext.init(keyManagers,trustManagers,null);
            SslClient = (SSLSocket) sslContext.getSocketFactory().createSocket(serverIp,28443);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        finally {
            CommonUtils.close(trustFis);
            CommonUtils.close(selfIn);
        }
        return SslClient;
    }

    public static void main(String[] args) {
        SSLSocket sslClient = new SSLClient().getSslClient();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(sslClient.getOutputStream());
            Map<String,String> map = new HashMap<>();
            map.put("a","1");
            map.put("b","2");

            oos.writeObject(map);
            oos.flush();
            ois = new ObjectInputStream(sslClient.getInputStream());
            System.out.println(ois.readObject());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            CommonUtils.close(oos);
            CommonUtils.close(ois);
            CommonUtils.close(sslClient);

        }
    }
}
