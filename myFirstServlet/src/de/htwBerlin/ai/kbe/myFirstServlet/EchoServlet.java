package de.htwBerlin.ai.kbe.myFirstServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@WebServlet(
name = "EchoServlet",
urlPatterns = {"/echo", "/getHeaders"},
initParams = {
		@WebInitParam(name = "signature", 
		    value = "Thanks for using AI-KBE's Echo Servlet © 2017! ")
}
)
public class EchoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected String mySignature = null;

	public void init(ServletConfig servletConfig) throws ServletException{
	    this.mySignature = servletConfig.getInitParameter("signature");
    }
	  
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		switch (request.getServletPath()) {
		case "/echo":
			doGetEcho(request, response);
			return;
		case "/getHeaders":
			doGetHeaders(request, response);
			return;
		default:
			break;
		}
	}
	
	public void doGetHeaders(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String[]> parameter = request.getParameterMap();
		String responseStr = "";
		String param = "";
		if(parameter.containsKey("all")){
			Enumeration<String> allParam = request.getHeaderNames();
			while(allParam.hasMoreElements()){
				param = allParam.nextElement();
				responseStr = responseStr + param + "=[" + request.getHeader(param) +"]\n"; 
			}
		}
		else if(parameter.containsKey("header")){
			String[] headerKeys = parameter.get("header");
			for(String headerKey : headerKeys){
				responseStr = responseStr + headerKey + "=[" + request.getHeader(headerKey) +"]\n";
			}
		}
		
		try (PrintWriter out = response.getWriter()) {
			responseStr += "\n" + mySignature;
			out.println(responseStr);
		}
	}
	
	public void doGetEcho(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 // alle Parameter (keys) 
		   Enumeration<String> paramNames = request.getParameterNames();
			
		   String responseStr = "";
		   String param = "";	
		   while (paramNames.hasMoreElements()) {
			   param = paramNames.nextElement();
			   responseStr = responseStr + param + "=" + request.getParameter(param) +"\n";
		   }
				
			try (PrintWriter out = response.getWriter()) {
				responseStr += "\n" + mySignature;
				out.println(responseStr);
			}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		ServletInputStream inputStream = request.getInputStream();
        byte[] inBytes = IOUtils.toByteArray(inputStream);
		try (PrintWriter out = response.getWriter()) {
			out.println(new String(inBytes));
		}
	}
}

//@WebServlet(
//        name = "EchoServlet",
//        urlPatterns = "/echo",
//        initParams = {
//        		@WebInitParam(name = "signature", 
//        		    value = "Thanks for using AI-KBE's Echo Servlet © 2017! ")
//        }
//)
