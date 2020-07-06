package com.socialembed.jbbapi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmbedService {

    public static String getDomainName(String url) throws URISyntaxException{
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public HttpEntity<Map<String, Object>> getSocialPost(String url) {
        Map<String, Object> result = new HashMap<>();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
