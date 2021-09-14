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
package com.thng.sdk.test;

import com.alibaba.fastjson.JSONObject;
import com.thng.sdk.ThngClient;
import com.thng.sdk.utils.JsonUtils;

import com.thng.sdk.vo.TetsVo;
import com.thng.sdk.vo.ThngInVo;
import com.thng.sdk.vo.ThngOutVo;
import org.codehaus.jackson.map.util.JSONPObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ThngBaseClientTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testApi()  {
        String out = ThngClient.execute("/app/data/city_tower_sum_number", "{\"uid\": 1}");
        System.out.println(out);
        System.out.println(JsonUtils.fromJSON(out));
    }

}
