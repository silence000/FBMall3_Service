package com.excmmy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="ReviewDTO", description="")
public class ReviewDTO implements Serializable {
    @ApiModelProperty(value = "评论Id")
    private Integer id;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论用户")
    private String user;

    @ApiModelProperty(value = "评论时间")
    private String datetime;
}
