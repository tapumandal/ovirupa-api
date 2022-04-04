package me.tapumandal.ovirupa.domain.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by TapuMandal on 8/6/2021.
 * For any query ask online.tapu@gmail.com
 */

@Service
public class RestService {

    private final RestTemplate restTemplate;

    @Value("${sms.api.key}")
    String apiKey;

    @Value("${sms.from}")
    String from;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON(String to, String message) {
        String url = "https://portalapi.biztech.com.bd/api/v1/channels/sms?apiKey=" +
                ""+apiKey+"&message="+message+"&recipient="+to+"&from="+from;
        return this.restTemplate.getForObject(url, String.class);
    }

}
