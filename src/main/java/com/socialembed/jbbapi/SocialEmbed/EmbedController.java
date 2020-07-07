package com.socialembed.jbbapi.SocialEmbed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmbedController {

    @Autowired
    EmbedService embedService;

    @GetMapping("/api/socialembed")
    public HttpEntity<Map<String, Object>> socialEmbed(
                                        @RequestParam(value = "url") String url) {
        return embedService.embedProcess(url);
    }

}
