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


package com.github.khandroid.functionality;

import java.io.IOException;

import khandroid.ext.apache.http.HttpResponse;
import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.ResponseHandler;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;


public interface HttpFunctionality {
    String execute(HttpUriRequest httpRequest) throws ClientProtocolException, IOException;
    <T> T execute(HttpUriRequest httpRequest, ResponseHandler<T> responseHandler) throws ClientProtocolException, IOException;
    HttpResponse executeForHttpResponse(HttpUriRequest httpRequest) throws ClientProtocolException, IOException;
    void shutDown();
}
