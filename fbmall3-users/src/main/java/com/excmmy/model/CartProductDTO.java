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
@ApiModel(value="CartProductDTO", description="")
public class CartProductDTO implements Serializable {
    @ApiModelProperty(value = "商品Id")
    private Integer id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "促销价格")
    private Float promotePrice;

    @ApiModelProperty(value = "原始价格")
    private Float originalPrice;

    @ApiModelProperty(value = "图片URL")
    private Integer imgUrl;

    @ApiModelProperty(value = "商品的购买数量")
    private Integer number;

    @ApiModelProperty(value = "购物商品总价(由前台填写)")
    private Float total;
}
