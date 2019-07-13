package com.jtw.main.service.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class SMTPEmail extends EmailServer
{

    public SMTPEmail(Properties properties) throws AddressException
    {
        super(properties);
    }

    @Override
    public void send()
    {
        //2.创建邮件对象，写邮件
        try
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendUser));//设置发件人
            message.setRecipients(Message.RecipientType.TO,targetInternetAddresses);
            message.setSubject("git 分支统计信息");//设置邮件的主题
            message.setContent("<h1>git 分支信息统计</h1><p>此邮件由系统自动发出</p>", "text/html;charset=utf-8");
            //添加附件
            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.attachFile(new File(attachFilePath));
            mimeMultipart.addBodyPart(mimeBodyPart);
            message.setContent(mimeMultipart);
            //3.发送邮件
            Transport.send(message);
            System.out.println("发送成功！");
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
