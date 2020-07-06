package com.socialembed.jbbapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmbedController {

    @Autowired
    EmbedService embedService;

    @GetMapping
    public HttpEntity<Map<String, Object>> socialEmbed(String url) {
        return embedService.getSocialPost(url);
    }

}
