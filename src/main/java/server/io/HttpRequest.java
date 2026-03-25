package server.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.StringManipulator;

public class HttpRequest 
{
	private String protocolVersion;
	private String method;
	private String path;
	private HashMap<String, String> headers;
	private HashMap<String, String> parameters;
	
	public HttpRequest(String method, String url)
	{
		this(method, url, null);
	}
	
	public HttpRequest(String method, String url, Map<String, String> headers)
	{
		this(Http.V1_0, method, url, headers);
	}
	
	public HttpRequest(String protocolVersion, String method, String url, Map<String, String> headers)
	{
		this.protocolVersion = protocolVersion;
		this.method = method;
		
		this.parameters = new HashMap<String, String>();
		if (url != null)
			parseUrl(url);
		
		this.headers = new HashMap<String, String>();
		if (headers != null)
		{
			for (String key : headers.keySet())
			{
				addHeader(key, headers.get(key));
			}	
		}
	}
	
	public void addHeader(String name, String value)
	{
		headers.put(name, value);
	}
	
	public String getPath()
	{
		return path;
	}
	
	public String getParameter(String name)
	{
		return parameters.get(name);
	}
	
	private void parseUrl(String url)
	{
		// creo la stringa che contiene il carattere ? escaped
		// per non farlo interpretare al regexp
		// (potevo anche passare a split direttamente la stringa "\\?")
		String[] urlInfos = StringManipulator.splitString(url, "?");
		
		String path = urlInfos[0];
		
		// estrapolo la query string solo se esiste!
		if (urlInfos.length > 1)
		{
			String queryString = urlInfos[1];
			
			this.path = path;
			
			String[] queryStringParams = StringManipulator.splitString(queryString, "&");
			String[] paramInfos;
			String name, value;
			for (int i = 0; i < queryStringParams.length; i++)
			{
				paramInfos = StringManipulator.splitString(queryStringParams[i], "=");
				name = paramInfos[0];
				value = paramInfos[1];
				
				addParameter(name, value);
			}	
		}
	}
	
	private void addParameter(String name, String value)
	{
		parameters.put(name, value);
	}
	
	public Set<String> getHeaders()
	{
		return headers.keySet();
	}
	
	public String getHeaderValue(String header)
	{
		return headers.get(header);
	}
	
	public String toString()
	{
		return String.format("%s; headers: %s; parameters: %s", method, headers, parameters);
	}
}
