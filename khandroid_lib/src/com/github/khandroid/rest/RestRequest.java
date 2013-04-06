package com.github.khandroid.rest;

import khandroid.ext.apache.http.client.methods.HttpUriRequest;


abstract public class RestRequest {
	abstract public HttpUriRequest createHttpRequest();
}
