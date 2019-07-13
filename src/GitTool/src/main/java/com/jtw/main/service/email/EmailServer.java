package com.jtw.main.service.email;


import org.apache.commons.lang3.StringUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

public abstract class EmailServer
{
    protected final String sendUser;

    protected final String sendUserPassword;

    protected final InternetAddress[] targetInternetAddresses;

    protected final String attachFilePath;

    protected final Session session;

    public EmailServer(final Properties properties) throws AddressException {
        this.sendUser = properties.getProperty("sendUser");
        this.sendUserPassword = properties.getProperty("sendUserPassword");
        String targetInternetAddressesValue = properties.getProperty("targetInternetAddresses");
        if (StringUtils.isEmpty(targetInternetAddressesValue))
        {
            throw new IllegalArgumentException("no user recieve the email!");
        }
        String[] addresses = targetInternetAddressesValue.split(",");
        this.targetInternetAddresses = new InternetAddress[addresses.length];
        for (int i = 0; i < targetInternetAddresses.length; i++) {
            this.targetInternetAddresses[i] = new InternetAddress(addresses[i]);
        }
        this.attachFilePath = properties.getProperty("attachFilePath");
        this.session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("sendUserPassword"),
                        properties.getProperty("sendUserPassword"));
            }
        });
    }
    public abstract void send();
}
