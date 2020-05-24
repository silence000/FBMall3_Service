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
@ApiModel(value="HomeProductDTO", description="")
public class HomeProductDTO implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "促销价格")
    private Float promotePrice;

    @ApiModelProperty(value = "图片URL")
    private Integer imgUrl;
}
