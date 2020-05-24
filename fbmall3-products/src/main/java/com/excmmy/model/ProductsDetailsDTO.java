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
@ApiModel(value="ProductsDetailsDTO", description="")
public class ProductsDetailsDTO implements Serializable {
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

    @ApiModelProperty(value = "库存数量")
    private Integer stock;

    @ApiModelProperty(value = "销量")
    private Integer sales;
}
