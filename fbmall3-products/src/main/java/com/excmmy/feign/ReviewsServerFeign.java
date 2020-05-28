package com.excmmy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.ResponseJsonBody;

@FeignClient(name = "FBMALL3-REVIEWS")
@Primary
public interface ReviewsServerFeign {
    @GetMapping(value = "reviews/get/reviews_number")
    ResponseJsonBody getReviewsNumber(@RequestParam(value = "id") Integer id);
}
