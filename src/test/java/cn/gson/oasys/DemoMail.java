package cn.gson.oasys;


import microsoft.exchange.webservices.data.EWSConstants;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Properties;

public class DemoMail {

    // 发送邮件的账号 @flairmedia.com.cn
    public static String ownEmailAccount = "app@flairmedia.com.cn";
    // 发送邮件的密码------》授权码
    public static String ownEmailPassword = "xinyun1234";
    // 218.80.193.154
    public static String myEmailSMTPHost = "mail.flairmedia.com.cn";
    //smtp.flairmedia.com.cn
    // 发送邮件对方的邮箱 @flairmedia.com.cn
    public static String receiveMailAccount = "zhangjiaming@flairmedia.com.cn";

    @Test
    public void outlooks() throws Exception {
        ExchangeService service = new ExchangerServiceSSLVerify(ExchangeVersion.Exchange2010_SP2);
        String userName = "";  //用户名
        String password = ""; //密码
        String domain = ""; //域
        String url = ""; //邮件服务器地址
        //ExchangeCredentials credentials = new WebCredentials(userName, password, domain);
        ExchangeCredentials credentials = new WebCredentials(ownEmailAccount, ownEmailPassword, "com.cn");
        service.setCredentials(credentials);
        service.setUrl(new URI("https://" + myEmailSMTPHost+ "/ews/Exchange.asmx"));//"https://" +  "邮箱服务器地址" + "/EWS/Exchange.asmx"
        //"https://mail.flairmedia.com.cn/ews/Exchange.asmx
        EmailMessage msg = new EmailMessage(service);
        msg.setSubject("标题");
        msg.setBody(MessageBody.getMessageBodyFromText("测试内容"));
        msg.getToRecipients().add(receiveMailAccount); //接受人 "xxx@xx.com.cn"
        //msg.getCcRecipients().add("xxxx@xx.com.cn"); //抄送
        msg.send();  //发送现有电子邮件

    }

    public class ExchangerServiceSSLVerify extends ExchangeService {

        public ExchangerServiceSSLVerify(ExchangeVersion requestedServerVersion) {
            super(requestedServerVersion);
        }

        @Override
        protected Registry<ConnectionSocketFactory> createConnectionSocketFactoryRegistry() {
            try {
                SSLContext sslContext = createSSL();
                return RegistryBuilder.<ConnectionSocketFactory>create()
                        .register(EWSConstants.HTTP_SCHEME, PlainConnectionSocketFactory.INSTANCE)
                        .register(EWSConstants.HTTPS_SCHEME, new SSLConnectionSocketFactory(sslContext))
                        .build();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(
                        "Could not initialize ConnectionSocketFactory instances for HttpClientConnectionManager", e
                );
            }
        }

        private SSLContext createSSL() throws NoSuchAlgorithmException, KeyManagementException {

            //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
            //Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            return ctx;
        }
    }





    @Test
    public void save1(){
        Properties prop = new Properties();
        // 设置邮件传输采用的协议smtp
        prop.setProperty("mail.transport.protocol", "smtp");
        // 设置发送人邮件服务器的smtp地址
        // 这里以网易的邮箱smtp服务器地址为例
        prop.setProperty("mail.smtp.host", myEmailSMTPHost);
        // 设置验证机制
        prop.setProperty("mail.smtp.auth", "true");

        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        // 需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        // QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)

        /*final String smtpPort = "995";
        prop.setProperty("mail.smtp.port", smtpPort);
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.socketFactory.port", smtpPort);
        prop.setProperty("java.net.preferIPv4Stack", "true");*/

        // 创建对象回话跟服务器交互
        Session session = Session.getInstance(prop);
        // 会话采用debug模式
        session.setDebug(true);
        // 创建邮件对象
        Message message = null;
        try {
            message = createSimpleMail(session);


        Transport trans = session.getTransport();
        // 链接邮件服务器
            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            //
            //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
            //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
            //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            //           (1) 邮箱没有开启 SMTP 服务;
            //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
            //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
            //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            //
            //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        trans.connect(ownEmailAccount, ownEmailPassword);
        // 发送信息  发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        trans.sendMessage(message, message.getAllRecipients());

        // 关闭链接
        trans.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: createSimpleMail
     * @Description: 创建邮件对象
     * @author: chengpeng
     * @param @param session
     * @param @return
     * @param @throws Exception    设定文件
     * @return Message    返回类型
     * @throws
     */
    public static Message createSimpleMail(Session session) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // 设置发送邮件地址,param1 代表发送地址 param2 代表发送的名称(任意的) param3 代表名称编码方式
        message.setFrom(new InternetAddress("app@flairmedia.com.cn", "张三", "utf-8"));
        // 代表收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailAccount, "李四", "utf-8"));
        // To: 增加收件人（可选）
        /*message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com", "USER_DD", "UTF-8"));
        // Cc: 抄送（可选）
        message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("ee@receive.com", "USER_EE", "UTF-8"));
        // Bcc: 密送（可选）
        message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", "UTF-8"));*/
        // 设置邮件主题
        message.setSubject("测试转发邮件");
        // 设置邮件内容
        message.setContent("早安,世界   你最近好吗！ 时间是一个伟大的单位，他消灭了痛苦，带走了悲伤，洗劫了磨难，也成就了人类！ ", "text/html;charset=utf-8");
        // 设置发送时间
        message.setSentDate(new Date());
        // 保存上面的编辑内容
        message.saveChanges();
        // 将上面创建的对象写入本地
        OutputStream out = new FileOutputStream("MyEmail.eml");
        message.writeTo(out);
        out.flush();
        out.close();
        return message;

    }

}
