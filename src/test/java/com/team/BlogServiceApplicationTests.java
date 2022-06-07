package com.team;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@SpringBootTest
class BlogServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String a = "=?gbk?B?YmVzdGJvZHktaWNvbi5wbmc=?=";
        Base64.Decoder decoder = Base64.getDecoder();
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        System.out.println("args = " +  URLEncoder.encode(a,"GBK"));
        System.out.println("args = " +  URLDecoder.decode(a, StandardCharsets.UTF_8));

        System.out.println("args = " + Arrays.toString(urlDecoder.decode(a)));

    }
}
