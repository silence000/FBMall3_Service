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
@ApiModel(value="CategoryProductDTO", description="")
public class CategoryProductDTO implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "促销价格")
    private Float promotePrice;

    @ApiModelProperty(value = "图片URL")
    private Integer imgUrl;

    @ApiModelProperty(value = "销量")
    private Integer sales;

    @ApiModelProperty(value = "评论数量")
    private Long reviewNumber;
}
