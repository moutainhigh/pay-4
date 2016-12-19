/**
 * 
 */
package tes;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.pay.cardbin.model.Response;
import com.pay.cardbin.util.HttpUtils;
import com.pay.util.HttpClientUtil;

/**
 * @author PengJiangbo
 *
 */
public class T {
	
	private static String vccApiPayUrl = "http://zhangj-gw.ipaylinks.com:8081/webgate/traApiPay.htm" ;
	private static String vccApiPayUrlLocal = "http://localhost:8090/webgate/traApiPay.htm" ;
	
	private static String vccApiPayReUrl = "https://api.ipaylinks.com/webgate/traApiPay.htm" ;

	public static void main(String[] args) {
		try {
			String postXml = HttpClientUtil.getPostXml(vccApiPayUrlLocal, "borrowingMarked=0&cardExpirationMonth=02&cardExpirationYear=16&cardHolderFirstName=名&cardHolderLastName=姓&cardHolderNumber=4625960016561853&charset=1&currencyCode=EUR&customerIP=172.168.1.123&goodsDesc=2&goodsName=2342&noticeUrl=mps.ipaylinks.com&orderAmount=1234&orderId=1211464092810342197&partnerId=10000003671&payMode=10&securityCode=161&signType=2&siteId=www.pay.com&submitTime=20160524202659&tradeType=6000&version=1.0&pkey=30819f300d06092a864886f70d010101050003818d003081890281810095e635fbad48d58ca176bc366807896eeecf3815c7632aff6592f87044b7d83e79ed668b1643f5789a54e6ec9339cafa037c3fa68fe02fc4b3819513c0a451e74305938700e166426e30698ea7c3865f0cf7b8c242b192d070a95e3bffc72cc27f36117c22876da48da981504bbc2875d26ec9e23d391e7d43cc237e8ec8da5f0203010001") ;
			System.out.println(postXml);
			/*System.out.println("============================");
			System.out.println(HttpClientUtil.getPostXml(vccApiPayReUrl, ""));
			TestXml2Bean xml2bean = XMLUtil.xml2bean(postXml, TestXml2Bean.class) ;
			System.out.println("========================bean=======================");
			System.out.println(xml2bean);
			System.out.println("========================bean=======================");*/
			Response response = HttpUtils.sendPostReq(vccApiPayUrlLocal, "borrowingMarked=0&cardExpirationMonth=02&cardExpirationYear=16&cardHolderFirstName=名&cardHolderLastName=姓&cardHolderNumber=4625960016561853&charset=1&currencyCode=EUR&customerIP=172.168.1.123&goodsDesc=2&goodsName=2342&noticeUrl=mps.ipaylinks.com&orderAmount=1234&orderId=1211464092810342197&partnerId=10000003671&payMode=10&securityCode=161&signType=2&siteId=www.pay.com&submitTime=20160524202659&tradeType=6000&version=1.0&pkey=30819f300d06092a864886f70d010101050003818d003081890281810095e635fbad48d58ca176bc366807896eeecf3815c7632aff6592f87044b7d83e79ed668b1643f5789a54e6ec9339cafa037c3fa68fe02fc4b3819513c0a451e74305938700e166426e30698ea7c3865f0cf7b8c242b192d070a95e3bffc72cc27f36117c22876da48da981504bbc2875d26ec9e23d391e7d43cc237e8ec8da5f0203010001") ;
			System.out.println("...............response ...............");
			System.out.println(response);
			System.out.println("...............response ...............");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
