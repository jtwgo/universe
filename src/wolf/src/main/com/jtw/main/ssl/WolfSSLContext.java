package com.jtw.main.ssl;

import com.jtw.main.conf.PropertiesManager;
import com.jtw.main.utils.CommonUtils;
import com.jtw.main.utils.PasswordUtils;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Properties;

public class WolfSSLContext
{
    private int agetProt;
    private String protocol;
    private SSLContext sslContext;
    private String[] supportedCipherSuites;

    private static volatile boolean isInit = false;
    private WolfSSLContext()
    {
        Properties sslProerties = PropertiesManager.getInstance().getSslProperties();
        if(null == sslProerties || sslProerties.isEmpty())
        {
            throw new RuntimeException("properties is null");
        }
        checkProperties(sslProerties);
        protocol = sslProerties.getProperty("ssl.protocol");
        String serverPass = PasswordUtils.decryptByAES256(sslProerties.getProperty("ssl.server.ks.pass"));
        String trustPass = PasswordUtils.decryptByAES256(sslProerties.getProperty("ssl.server.trust.ks.pass"));
        try {

            sslContext = SSLContext.getInstance(protocol);
            //加载自己的keystore
            KeyStore selfKeystore = KeyStore.getInstance(sslProerties.getProperty("ssl.server.ks.type"));
            FileInputStream selfIn = new FileInputStream(new File(CommonUtils.getWebDir()+sslProerties.getProperty("ssl.server.ks.path")));
            selfKeystore.load(selfIn,serverPass.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(sslProerties.getProperty("ssl.server.ks.format"));
            keyManagerFactory.init(selfKeystore,serverPass.toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            //加载trustkeysotre.
            KeyStore trustsotre = KeyStore.getInstance(sslProerties.getProperty("ssl.server.trust.ks.type"));
            FileInputStream fis = new FileInputStream(new File(CommonUtils.getWebDir()+sslProerties.getProperty("ssl.server.trust.ks.path")));
            trustsotre.load(fis,trustPass.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(sslProerties.getProperty("ssl.server.trust.ks.format"));
            trustManagerFactory.init(trustsotre);
            TrustManager[] trustManagers =  trustManagerFactory.getTrustManagers();
            sslContext.init(keyManagers,trustManagers,null);
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
            isInit = false;
            e.printStackTrace();
        }
        isInit = true;

    }
    private static WolfSSLContext instance = new WolfSSLContext();
    public static WolfSSLContext getInstance()
    {
        return instance;
    }
    public SSLContext getSslContext()
    {
        return sslContext;
    }
    public boolean init() {

        return isInit;
    }

    private void checkProperties(Properties sslProerties)
    {
        //参数校验
    }

    public boolean reloadSSLContext()
    {
        isInit = false;
        return init();
    }
}
