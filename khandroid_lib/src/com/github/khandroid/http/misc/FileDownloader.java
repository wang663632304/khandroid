/*
 * Copyright (C) 2012-2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.khandroid.http.misc;

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
