package com.ziyi.common.email;

import com.ziyi.common.base.exception.util.BaseException;
import com.ziyi.config.EmailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.prefs.BackingStoreException;

/**
 * 邮件发送服务
 */
@Component
public class SendEmail {

    private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);

    @Resource
    private EmailConfig emailConfig;

    /**
     *
     * @param fromEmail 发送邮件方
     * @param toEmail 邮件接收方
     * @param subject 主题
     * @param text 内容
     */
    public void sendSimpleEmail(String fromEmail, String toEmail, String subject, String text) throws BaseException {
        try {
            // 构造Email消息
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(text);
            emailConfig.javaMailSender().send(message);
        } catch (Exception e) {
            logger.error("发送邮件失败，异常原因：{}", e.getMessage(), e);
            throw new BaseException(e);
        }
    }

    /**
     * 发送邮件，附件的方式
     * @param fromEmail 发送邮件方
     * @param toEmail 邮件接收方
     * @param subject 主题
     * @param text 内容
     * @throws MessagingException
     */
    public void mimeEmail(String fromEmail, String toEmail, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = emailConfig.javaMailSender().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = initMimeMessage(fromEmail, toEmail, subject, mimeMessage);
        mimeMessageHelper.setText(text);
        //添加附件,第一个参数表示添加到 Email 中附件的名称，第二个参数是图片资源
        //后面使用读文件的方式
        mimeMessageHelper.addAttachment("boot.png", new ClassPathResource("public/images/boot.png"));
        emailConfig.javaMailSender().send(mimeMessage);
    }

    /**
     * 富文本形式发送邮件
     * @param fromEmail 发送邮件方
     * @param toEmail 邮件接收方
     * @param subject 主题
     * @param html 富文本内容
     * @throws MessagingException
     */
    public void htmlEmail(String fromEmail, String toEmail, String subject, String html) throws MessagingException {
        MimeMessage mimeMessage = emailConfig.javaMailSender().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = initMimeMessage(fromEmail, toEmail, subject, mimeMessage);
        html = "<html><body><h4>Hello,SpringBoot</h4><img src='cid:boot' /></body></html>";
        mimeMessageHelper.setText(html, true);
        // 设置内嵌元素 cid，第一个参数表示内联图片的标识符，第二个参数标识资源引用
        mimeMessageHelper.addInline("boot", new ClassPathResource("public/images/boot.png"));
        emailConfig.javaMailSender().send(mimeMessage);
    }

    /**
     * 初始化mimeMessageHelper对象，使用mimeMessageHelper对象进行数据封装
     * @param fromEmail 发送邮件方
     * @param toEmail 邮件接收方
     * @param subject 主题
     * @param mimeMessage
     * @return
     * @throws MessagingException
     */
    public MimeMessageHelper initMimeMessage(String fromEmail, String toEmail, String subject, MimeMessage mimeMessage) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subject);
        return mimeMessageHelper;
    }

}
