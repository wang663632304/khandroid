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

import khandroid.ext.apache.http.cookie.Cookie;

public interface HttpFunctionalityWCookies extends HttpFunctionality {
    boolean cookieExists(Cookie cookie);
    boolean cookieExists(String name, String domain, String path);
    void setCookie(Cookie cookie);
    String getCookieValue(String name, String domain, String path);
    String getCookieValue(String name);
    Cookie getCookie(String name, String domain, String path);
    Cookie getCookie(String name);
}
