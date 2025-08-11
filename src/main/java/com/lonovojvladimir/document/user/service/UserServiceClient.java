package com.lonovojvladimir.document.user.service;

import com.lonovojvladimir.document.application.client.FeignClientConfig;
import com.lonovojvladimir.document.user.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", url = "${user-service.host}:${user-service.port}", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping("/api/user/userByToken")
    UserEntity getUserByToken();

}
