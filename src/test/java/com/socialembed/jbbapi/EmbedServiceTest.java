package com.socialembed.jbbapi;


import java.net.URI;
import java.net.URISyntaxException;

public class EmbedServiceTest {

    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("https://www.tiktok.com/@scout2015/video/6718335390845095173");
        String domain = uri.getHost();
        if (domain.startsWith("www.")) {
            domain = domain.substring(4);
        }
        if (domain.endsWith(".com")) {
            domain = domain.substring(0, domain.length()-4);
        }
        System.out.println(domain);
    }

}