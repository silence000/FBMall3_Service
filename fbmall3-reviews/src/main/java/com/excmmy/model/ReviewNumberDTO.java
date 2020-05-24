package com.excmmy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="ProductimageDTO", description="")
public class ReviewNumberDTO implements Serializable {
    @ApiModelProperty(value = "该商品的评论数量")
    private Long reviewNumber;
}
