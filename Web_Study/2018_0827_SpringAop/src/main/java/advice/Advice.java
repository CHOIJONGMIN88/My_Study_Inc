package advice;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;

public class Advice {
	
	@Autowired
	HttpServletRequest request;
	
	public void before(JoinPoint jp){
		Signature s =  jp.getSignature();
		long start = System.currentTimeMillis();
		
		System.out.println("----before:" + s + " ���:" + s.toLongString());
		request.setAttribute("start", start);
	}
	
	public void after(JoinPoint jp){
		Signature s =  jp.getSignature();
		long start = (Long)request.getAttribute("start");
		long end = System.currentTimeMillis();
		System.out.println("----after:" + s + " ���:" +  s.toLongString() + "����ð�: " + (end-start));
	}
}
