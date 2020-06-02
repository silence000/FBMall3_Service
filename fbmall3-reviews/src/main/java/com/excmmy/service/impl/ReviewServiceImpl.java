package com.excmmy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.excmmy.bean.Review;
import com.excmmy.feign.UserServerFeign;
import com.excmmy.mapper.ReviewMapper;
import com.excmmy.model.ReviewDTO;
import com.excmmy.model.ReviewNumberDTO;
import com.excmmy.service.ReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pojo.MallConstant;
import pojo.ResponseJsonBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-24
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserServerFeign userServiceFeign;

    @Override
    public ResponseJsonBody getReviewsNumber(Integer id) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置分页与查询条件
        Page<Review> reviewPage = new Page<>(1, 1);
        QueryWrapper<Review> reviewQueryWrapper = new QueryWrapper<>();
        reviewQueryWrapper.eq("pid", id);
        // 执行查询
        reviewMapper.selectPage(reviewPage, reviewQueryWrapper);
        // 获取查询结果
        ReviewNumberDTO reviewNumberDTO = new ReviewNumberDTO(reviewPage.getTotal());
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(reviewNumberDTO);
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody getReviews(Integer id, Long current, Long size) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 设置分页与查询条件
        Page<Review> reviewPage = new Page<>(current, size);
        QueryWrapper<Review> reviewQueryWrapper = new QueryWrapper<>();
        reviewQueryWrapper.eq("pid", id);
        // 执行查询
        reviewMapper.selectPage(reviewPage, reviewQueryWrapper);
        // 获取查询结果
        List<Review> reviewList = reviewPage.getRecords();
        if (reviewList.size() == 0) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
            return responseJsonBody;
        }
        // 查询评论所属用户
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (Review review : reviewList) {
            ResponseJsonBody userServiceFeignRequest = userServiceFeign.getUsernameById(review.getUid());
            if (userServiceFeignRequest.getData() != null) {
                // 格式化Date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createDate = formatter.format(review.getCreateDate());
                reviewDTOList.add(new ReviewDTO(review.getId(), review.getContent(), userServiceFeignRequest.getData() + "", createDate));
            }
        }

        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        responseJsonBody.setData(reviewDTOList);
        return responseJsonBody;
    }

    @Override
    public ResponseJsonBody insertReview(Integer id, String review) {
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        Review reviewEntity = new Review();
        reviewEntity.setContent(review);
        reviewEntity.setPid(id);
        reviewEntity.setUid(new Integer(userDTO.getId()));
        reviewEntity.setCreateDate(new Date());
        int flag = reviewMapper.insert(reviewEntity);
        if (flag != 1) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE);
            responseJsonBody.setMsg(MallConstant.FAIL_DESC);
            return responseJsonBody;
        }
        responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
        responseJsonBody.setMsg(MallConstant.SUCCESS_DESC);
        return responseJsonBody;
    }
}
