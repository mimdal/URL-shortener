package com.shortener.frontend.controller;

import com.shortener.frontend.dto.NewLink;
import com.shortener.frontend.dto.URLObject;
import com.shortener.frontend.exception.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
@Controller
public class FrontendController {

    @Value("${url.shortener.subdirectory}")
    private String subdirectory;

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;


    private ResponseEntity<String> serviceCall(HttpEntity<?> entity) {
        return restTemplate
                .postForEntity(getBackendRestURL(), entity, String.class);
    }

    private String getBackendRestURL() {
        String fullHostAddress = "http://localhost" + ":" + serverPort;
        return fullHostAddress + "/v1/shortURLs";
    }

    private HttpEntity<NewLink> prepareRequestEntityForRegister(NewLink requestDto) {
        return new HttpEntity<>(requestDto, new HttpHeaders());
    }

    @GetMapping("/short-link")
    public String friendForm(Model model) {
        model.addAttribute("newLink", new NewLink());
        return "registerURLForm";
    }

    @PostMapping("/short-link")
    public String userRegister(NewLink newLink, Model model) {
        if (newLink != null) {
            String fullHostAddress = "http://localhost" + ":" + serverPort + "/";
            HttpEntity<NewLink> entity = prepareRequestEntityForRegister(newLink);
            ResponseEntity<String> response = serviceCall(entity);
            URLObject urlObject = URLObject.builder()
                    .longURL(newLink.getLongURL())
                    .shortUrl(fullHostAddress + subdirectory + "/" + response.getBody())
                    .build();
            model.addAttribute("urlObject", urlObject);
            model.addAttribute("warning", "Link Registration Success." + response.getBody());

        } else {
            model.addAttribute("danger", "Something Going Bad");

        }
        return "result";
    }

    @GetMapping("/api/{identifier}")
    public void redirectToOriginalLink(@PathVariable String identifier, HttpServletResponse httpServletResponse) {
        try {
            ResponseEntity<String> response = serviceCallWithRequestParam(identifier);
            httpServletResponse.setHeader("Location", response.getBody());
            httpServletResponse.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
        } catch (Exception e) {
            if (((HttpClientErrorException.NotFound) e).getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new PageNotFoundException("The requested page not exist, " + identifier);
            }
        }
    }

    private ResponseEntity<String> serviceCallWithRequestParam(String identifier) {
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBackendRestURL())
                .queryParam("identifier", identifier);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

    }

}
