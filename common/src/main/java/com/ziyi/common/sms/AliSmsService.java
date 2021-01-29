package com.ziyi.common.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.ziyi.common.Constants.SmsConstants;
import com.ziyi.common.base.exception.util.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云短信接口
 */
public class AliSmsService {
    private static final Logger logger = LoggerFactory.getLogger(AliSmsService.class);

    /**
     * @param signName     短信签名
     * @param templateCode 短信模板id
     * @param phone        手机号
     * @return
     */
    public static void send(String signName, String templateCode, String phone) throws BaseException {
        List<String> params = new ArrayList<>();
        try {
            Config config = new Config();
            // 您的AccessKey ID
            config.accessKeyId = SmsConstants.ACCESS_KEY_ID;
            // 您的AccessKey Secret
            config.accessKeySecret = SmsConstants.ACCESS_SECRET;
            // 访问的域名
            config.endpoint = SmsConstants.END_POINT;
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            //设置发送短信的手机号
            sendSmsRequest.phoneNumbers = phone;
            //设置签名名称
            if (StringUtils.isNotBlank(signName)) {
                sendSmsRequest.signName = signName;
            }
            //设置短信模板号
            sendSmsRequest.setTemplateCode(templateCode);

            sendSmsRequest.setTemplateParam("{\"code\":\"123456\"}");

            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            System.out.println(sendSmsResponse.body);
        } catch (Exception e) {
            logger.error("发送短信失败");
            throw new BaseException(e);
        }
    }

    /**
     * signName一直审核不通过，测试signName不存在，需要用真实signName测试一下
     * @param args
     * @throws BaseException
     */
    public static void main(String[] args) throws BaseException {
        send(SmsConstants.SINGN_NAME, SmsConstants.TEMPLATE_CODE_LOGIN, "15623049712");
    }

}
