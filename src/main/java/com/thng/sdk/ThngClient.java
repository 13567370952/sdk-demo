// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.thng.sdk;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.thng.sdk.utils.EncoderUtils;
import com.thng.sdk.utils.HttpClientUtils;
import com.thng.sdk.utils.JsonUtils;
import com.thng.sdk.vo.ThngInVo;

import com.thng.sdk.utils.PropertiesUtils;

public class ThngClient {
    public static final String appId =  PropertiesUtils.getProperty("api_id");
    public static final String secret =  PropertiesUtils.getProperty("api_secret");
    public static final String apiUrl = PropertiesUtils.getProperty("api_url");

    public static  <T> T exec(ThngInVo inVo, Class<T> type) {
        return JsonUtils.fromJSON(execute(inVo), type);
    }

    public static  String execute(ThngInVo vo) {
        String str_base = EncoderUtils.strToBase64(vo.getData());
        String at = new Date().getTime()+"";
        String b = secret + at + str_base;
        String token = EncoderUtils.strToMd5(b);
        String url = apiUrl + vo.getUri();
        Map<String, String> header = new HashMap<String, String>();
        header.put("appid", appId);
        header.put("at", at);
        header.put("token", token);
        String res = null;
      if("POST".equals(vo.getMethod())) {
            res = HttpClientUtils.postByHttp(url, header,str_base.getBytes(StandardCharsets.UTF_8));
        }
        return res;
    }

    public static  String execute(String url, String data) {
        String str_base = EncoderUtils.strToBase64(data);
        String at = new Date().getTime()+"";
        String b = secret + at + str_base;
        String token = EncoderUtils.strToMd5(b);
        Map<String, String> header = new HashMap<String, String>();
        header.put("appid", appId);
        header.put("at", at);
        header.put("token", token);
        return HttpClientUtils.postByHttp(apiUrl + url, header,str_base.getBytes(StandardCharsets.UTF_8));

    }
}
