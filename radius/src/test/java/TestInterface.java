import java.io.IOException;

import oracle.net.aso.p;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class TestInterface {
	
	@Test
	public void testInteface() throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod pm = new PostMethod("http://127.0.0.1:8080/radius/dinamicPwd/fetch");
		NameValuePair vp = new NameValuePair();
		vp.setName("username");
		vp.setValue("15021006856");
		pm.setQueryString(new NameValuePair[]{vp});
		int statusCode = client.executeMethod(pm);
	    System.out.println(statusCode);
		
	}

}
