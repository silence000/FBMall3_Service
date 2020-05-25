package com.excmmy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.ResponseJsonBody;

@FeignClient(name = "FBMALL3-USERS")
@Primary
public interface UserServerFeign {
    @GetMapping(value = "users/get/username")
    public ResponseJsonBody getUsernameById(@RequestParam(name = "id")Integer id);
}
