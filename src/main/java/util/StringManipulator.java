package util;

import java.util.regex.Pattern;

public class StringManipulator 
{
	public static String[] splitString(String s, String delimiter)
	{
		String quotedString = quoteRegExpString(delimiter);
		return s.split(quotedString);
	}
	
	private static String quoteRegExpString(String regExp)
	{
		return Pattern.quote(regExp);
	}
}
