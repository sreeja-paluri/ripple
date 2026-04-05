package com.ripple.friend_service.client;

import com.ripple.friend_service.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url:http://localhost:8080}")
    private String userServiceUrl;

    public List<UserDTO> getUsersByIds(List<Long> ids) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(userServiceUrl + "/api/users/batch")
                    .queryParam("ids", ids)
                    .toUriString();

            var response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<List<UserDTO>>>() {}
            );

            if (response.getBody() != null && response.getBody().isSuccess()) {
                return response.getBody().getData();
            }
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("User service unavailable. Could not fetch users for ids: {}", ids);
            return Collections.emptyList();
        }
    }
}