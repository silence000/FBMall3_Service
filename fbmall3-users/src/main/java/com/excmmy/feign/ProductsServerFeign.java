package com.excmmy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.ResponseJsonBody;

@FeignClient(name = "FBMALL3-PRODUCTS")
@Primary
public interface ProductsServerFeign {
    @GetMapping(value = "/products/get/product_details")
    ResponseJsonBody getProductDetails(@RequestParam(name = "id") Integer id);

    @GetMapping(value = "/products/get/product_one_image")
    ResponseJsonBody getOneProductImages(@RequestParam(name = "id") Integer id);
}
