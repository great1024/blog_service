package com.team.mail;

import javax.mail.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class Email {
    public static void main(String[] args) throws MessagingException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pop3Server = "imap.exmail.qq.com";
        String protocol = "pop3";
        String username = "";
        String password = "";

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.smtp.host", pop3Server);
        //获取连接
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        //获取Store对象
        Store store = session.getStore(protocol);
        store.connect(pop3Server, username, password);
        //通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
        Folder folder = store.getFolder("INBOX");//获得用户的邮件帐户
        folder.open(Folder.READ_ONLY);
        System.out.println("count：" + folder.getMessageCount());
        Message[] messages = folder.getMessages();
        for (Message message : messages) {


            Date sentDate = message.getSentDate();
            String parse = simpleDateFormat.format(sentDate);
            System.out.println("sent = " + parse);
            Address[] from = message.getFrom();
            System.out.println("from = " + from[0].toString());
            System.out.println("content = " + message.getContent().toString());
        }

    }
}
