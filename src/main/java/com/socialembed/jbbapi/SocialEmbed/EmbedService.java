package com.socialembed.jbbapi.SocialEmbed;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmbedService {

    private final static String FACEBOOKURL = "https://www.facebook.com/plugins/post/oembed.json/?url=";
    private final static String INSTAGRAMURL = "https://api.instagram.com/oembed?url=";
    private final static String TWITTERURL = "https://publish.twitter.com/oembed?url=";
    private final static String TIKTOKURL = "https://www.tiktok.com/oembed?url=";

    private static String getDomainName(String paramUrl) throws URISyntaxException {
        URI uri = new URI(paramUrl);
        String domain = uri.getHost();
        if (domain.startsWith("www.")) {
            domain = domain.substring(4);
        }
        if (domain.endsWith(".com")) {
            domain = domain.substring(0, domain.length()-4);
        }
        return domain;
    }

    public HttpEntity<Map<String, Object>> embedProcess(String paramUrl) {
        Map<String, Object> result = new HashMap<>();
        String domain = "";
        try {
            domain = getDomainName(paramUrl);
        } catch (URISyntaxException e) {
            result.put("result", "Fail");
            result.put("response", "잘못된 URL 입니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (domain.equals("instagram")) {
            return getInstagramHTML(paramUrl);
        } else if (domain.equals("facebook")) {
            return getFacebookHTML(paramUrl);
        } else if (domain.equals("twitter")) {
            return getTwitterHTML(paramUrl);
        } else if (domain.equals("tiktok")) {
            return getTiktokHTML(paramUrl);
        } else {
            result.put("result", "Fail");
            result.put("response", "현재 지원하지 않는 Social 이거나, 잘못된 URL 입니다. 다시 확인해주시기 바랍니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    private HttpEntity<Map<String, Object>> getFacebookHTML(String paramUrl) {
        Map<String, Object> embedResult = new HashMap<>();
        Map<String, Object> result = new HashMap<>();

        boolean isFacebookPost = Pattern.compile("https://www.facebook.com/.*?/(posts|photos|videos)/.*?").matcher(paramUrl).find();

        if (isFacebookPost == false) {
            result.put("result", "Fail");
            result.put("response", "지원하지 않는 형식의 Facebook URL 입니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        RestTemplate template = new RestTemplate();
        String embedResponse = template.getForObject(FACEBOOKURL + paramUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            embedResult = mapper.readValue(embedResponse, new TypeReference<Map<String, String>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            result.put("result", "Fail");
            result.put("response", "지원하지 않는 형식의 Facebook URL 입니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.put("result", "success");
        result.put("response", embedResult.get("html"));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private HttpEntity<Map<String, Object>> getInstagramHTML(String paramUrl) {
        Map<String, Object> embedResult = new HashMap<>();
        Map<String, Object> result = new HashMap<>();

        boolean isInstagramPost = Pattern.compile("(https://www.instagram.com/p/.*?)").matcher(paramUrl).find();
        if (isInstagramPost == false) {
            result.put("result", "Fail");
            result.put("response", "지원하지 않는 형식의 Instagram URL 입니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        RestTemplate template = new RestTemplate();
        embedResult = template.getForObject(INSTAGRAMURL + paramUrl, Map.class);

        result.put("result", "success");
        result.put("response", embedResult.get("html"));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private HttpEntity<Map<String, Object>> getTwitterHTML(String paramUrl) {
        Map<String, Object> embedResult = new HashMap<>();
        Map<String, Object> result = new HashMap<>();

        boolean isTwitterPost = Pattern.compile("(https://twitter.com/.*/status/.*?)").matcher(paramUrl).find();
        if (isTwitterPost == false) {
            result.put("result", "Fail");
            result.put("response", "지원하지 않는 형식의 Twitter URL 입니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        RestTemplate template = new RestTemplate();
        embedResult = template.getForObject(TWITTERURL + paramUrl, Map.class);

        result.put("result", "success");
        result.put("response", embedResult.get("html"));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private HttpEntity<Map<String, Object>> getTiktokHTML(String paramUrl) {
        Map<String, Object> embedResult = new HashMap<>();
        Map<String, Object> result = new HashMap<>();

        boolean isTiktokPost = Pattern.compile("(https://www.tiktok.com/.*/video/.*?)").matcher(paramUrl).find();
        if (isTiktokPost == false) {
            result.put("result", "Fail");
            result.put("response", "지원하지 않는 형식의 Instagram URL 입니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        RestTemplate template = new RestTemplate();
        embedResult = template.getForObject(TIKTOKURL + paramUrl, Map.class);

        result.put("result", "success");
        result.put("response", embedResult.get("html"));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}