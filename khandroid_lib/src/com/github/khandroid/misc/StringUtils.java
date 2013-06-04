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

package com.github.khandroid.misc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class StringUtils {
    public static String md5(String s) {

        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            KhandroidLog.wtf("Cannot get instance of MD5 digest");
            throw new RuntimeException(e);
        }

        m.update(s.getBytes());

        String hash = new BigInteger(1, m.digest()).toString(16);

        return hash;
    }


    public static String md5(long l) {
        return md5(Long.toString(l));
    }


    public static String md5(int i) {
        return md5(Integer.toString(i));
    }

}
