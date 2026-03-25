package server.io;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse 
{
	public static final int STATUS_CODE_OK = 200;
	public static final int STATUS_CODE_BAD_REQUEST = 400;
	public static final int STATUS_CODE_NOT_FOUND = 404;
	
	public static final int CONTENT_TYPE_JSON = 0;
	public static final int CONTENT_TYPE_PLAINTEXT = 1;
	
	private String protocolVersion;
	private int statusCode;
	private String statusMessage;
	private HashMap<String, String> headers;
	private String body;
	
	private HttpResponse(String protocolVersion, int statusCode)
	{
		this.protocolVersion = protocolVersion;
		this.statusCode = statusCode;
		setStatusMessage();
		
		this.headers = new HashMap<String, String>();
	}
	
	private HttpResponse(String protocolVersion, int statusCode, String contentType)
	{
		this(protocolVersion, statusCode);
		
		headers.put("Content-Type", contentType);
	}
	
	private HttpResponse(String protocolVersion, int statusCode, String contentType, String body)
	{
		this(protocolVersion, statusCode, contentType);

		this.body = body;
	}
	
	public static HttpResponse createHttpResponse(int statusCode)
	{
		HttpResponse response = new HttpResponse(Http.V1_0, statusCode);
		
		setResponseDefaultHeaders(response);
		
		return response;
	}
	
	public static HttpResponse createHttpResponse(int statusCode, int contentType)
	{	
		HttpResponse response = new HttpResponse(Http.V1_0, statusCode, getContentTypeHeaderValue(contentType));
		
		setResponseDefaultHeaders(response);
		
		return response;
	}
	
	public static HttpResponse createHttpResponse(int statusCode, int contentType, String body)
	{
		HttpResponse response = new HttpResponse(Http.V1_0, statusCode, getContentTypeHeaderValue(contentType), body);
		
		setResponseDefaultHeaders(response);
		
		return response;
	}
	
	private static String getContentTypeHeaderValue(int contentType)
	{
		if (contentType == CONTENT_TYPE_PLAINTEXT)
			return "text/plain";
		else
			return "application/json";
	}
	
	private static void setResponseDefaultHeaders(HttpResponse response)
	{
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
		String date = sdf.format(now);
		response.addHeader("Date", date);
		
		int contentLength;
		if (response.body != null)
			contentLength = response.body.length();
		else
			contentLength = 0;
		
		response.addHeader("Content-Length", Integer.toString(contentLength));
	}
	
	private void addHeader(String name, String value)
	{
		headers.put(name, value);
	}
	
	public int getStatusCode()
	{
		return statusCode;
	}
	
	public String getStatusMessage()
	{
		return statusMessage;
	}
	
	public Set<String> getHeaders()
	{
		return headers.keySet();
	}
	
	public String getHeaderValue(String header)
	{
		return headers.get(header);
	}
	
	public String getBody()
	{
		return body;
	}
	
	public String getProtocolVersion()
	{
		return protocolVersion;
	}
	
	private void setStatusMessage()
	{
		switch (statusCode)
		{
			case STATUS_CODE_OK:
				statusMessage = "OK";
				break;
			case STATUS_CODE_BAD_REQUEST:
				statusMessage = "BAD REQUEST";
				break;
			case STATUS_CODE_NOT_FOUND:
				statusMessage = "NOT FOUND";
				break;
		}
	}
	
	public String toString()
	{
		return String.format("{protocolVersion: %s; statusCode: %d; statusMessage: %s; headers: %s; body: %s}", protocolVersion, statusCode, statusMessage, headers, body);
	}
}
