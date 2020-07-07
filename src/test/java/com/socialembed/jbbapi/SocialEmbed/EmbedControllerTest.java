package com.socialembed.jbbapi.SocialEmbed;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmbedControllerTest {

    @Autowired
    private TestRestTemplate template;

    private final String testUrl = "https://www.facebook.com/20531316728/posts/10154009990506729/";

    @Test
    public void embedProcessTest() {
        ResponseEntity<Map> response = template.getForEntity("/api/socialembed?url=" + testUrl, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("result")).isEqualTo("success");
    }

}