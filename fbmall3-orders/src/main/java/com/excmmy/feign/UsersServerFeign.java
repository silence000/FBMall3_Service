package com.excmmy.feign;

import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.ResponseJsonBody;

@FeignClient(name = "FBMALL3-USERS")
@Primary
public interface UsersServerFeign {
    @GetMapping(value = "/users/get/cart/product/num")
    ResponseJsonBody getProductNum(@RequestParam(name = "uid") Integer uid, @RequestParam(name = "pid") Integer pid);

    @PostMapping(value = "/users/feign/delete/cart")
    ResponseJsonBody deleteProductInCartFeign(@RequestParam(name = "uid") Integer uid, @RequestParam(name = "pid") Integer pid);
}
