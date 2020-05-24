package com.excmmy.service;

import com.excmmy.bean.Review;
import com.baomidou.mybatisplus.extension.service.IService;
import pojo.ResponseJsonBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-24
 */
public interface ReviewService extends IService<Review> {
    ResponseJsonBody getReviewsNumber(Integer id);

    ResponseJsonBody getReviews(Integer id, Long size, Long current);
}
