package com.github.khandroid.http;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.IOUtils;


import khandroid.ext.apache.http.HttpEntity;
import khandroid.ext.apache.http.HttpResponse;
import khandroid.ext.apache.http.StatusLine;
import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.HttpClient;
import khandroid.ext.apache.http.client.methods.HttpGet;


public class FileDownloader {
	public static byte[] download(HttpClient httpClient, URI source) throws ClientProtocolException, IOException {
		byte[] ret;
		
		HttpGet req = new HttpGet(source);
		HttpResponse response = httpClient.execute(req);
		StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
        	HttpEntity entity = response.getEntity();
        	
        	ByteArrayOutputStream output = new ByteArrayOutputStream();
        	
        	entity.writeTo(output);
        	output.close();
        	
        	ret = output.toByteArray();
        } else {
            throw new IOException("Download failed, HTTP response code " + statusCode + " - " + statusLine.getReasonPhrase());        	
        }
		
		return ret;
	}
	
	
	public static boolean download(HttpClient httpClient, URI source, File destination) throws ClientProtocolException, IOException {
		boolean ret = false;
		
		byte[] content = download(httpClient, source);
		ByteArrayInputStream input = new ByteArrayInputStream(content);
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(destination));
		
		IOUtils.copy(input, output);
		input.close();
		output.close();
		
		return ret;
	}
}
