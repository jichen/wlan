import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.p;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cmct.common.util.Passwordmd5;
import com.cmct.radius.dao.RadCheckDao;
import com.cmct.radius.dao.RadUserGroupDao;
import com.cmct.radius.dao.UserInfoDao;
import com.cmct.radius.service.RadUserService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class TestSMS {
	@Autowired
	private RadUserService radUserService;
	@Test
	public void testInteface() throws HttpException, IOException {
		String username="13918736720";
		String validtime="30";
		String onTimePassword;
		try {
			onTimePassword = radUserService.generateOnTimePassword(username, null);

			System.out.println("onTimePassword="+onTimePassword);
			String md5Pwd = Passwordmd5.MD5("wlantest");
			

			HttpClient client = new HttpClient();
			//url
			PostMethod pm = new PostMethod("http://192.168.54.13:8088/sms/remote/MtSms");
			String content="账号："+username+",获取动态短信密码为："+onTimePassword+",该密码有效期为："+validtime+"分钟。";
			//密码有效期
			NameValuePair[] vp = {
				new NameValuePair("uid","wlantest"),
				new NameValuePair("pwd",md5Pwd),
				new NameValuePair("time",""),
				new NameValuePair("encode","utf-8"),
				new NameValuePair("mobile",username),
				new NameValuePair("content",content)
			};
			pm.addParameters(vp);
			//获取返回值
			int Status=client.executeMethod(pm);
			System.out.println("Status_TEST=="+Status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}

}
