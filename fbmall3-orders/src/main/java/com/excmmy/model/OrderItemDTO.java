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
@ApiModel(value="OrderItemDTO", description="")
public class OrderItemDTO implements Serializable {
    @ApiModelProperty(value = "商品ID")
    private Integer id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "小标题")
    private String subTitle;

    @ApiModelProperty(value = "原始价格")
    private Float originalPrice;

    @ApiModelProperty(value = "促销价格")
    private Float promotePrice;

    @ApiModelProperty(value = "购买数量")
    private Integer number;

    @ApiModelProperty(value = "图片URL")
    private String imgUrl;
}
