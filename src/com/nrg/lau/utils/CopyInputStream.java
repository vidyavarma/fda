package com.nrg.lau.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class CopyInputStream {
	
	private InputStream is;
	private ByteArrayOutputStream copy = new ByteArrayOutputStream();
	private static Logger log = Logger.getLogger(CopyInputStream.class);

	public CopyInputStream(InputStream in)
	{
		is = in;
		
		try
		{
			copy();
		}
		catch(IOException ex)
		{
			log.error(ex);
		}
	}

	private int copy() throws IOException
	{
		int read = 0;
		int chunk = 0;
		byte[] data = new byte[256];
		
		while(-1 != (chunk = is.read(data)))
		{
			read += data.length;
			copy.write(data, 0, chunk);
		}
		
		return read;
	}
	
	public InputStream getCopy()
	{
		return (InputStream)new ByteArrayInputStream(copy.toByteArray());
	}

}
