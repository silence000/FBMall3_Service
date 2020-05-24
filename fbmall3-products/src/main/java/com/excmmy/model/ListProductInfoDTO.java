package com.excmmy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="ListProductInfoDTO", description="")
public class ListProductInfoDTO implements Serializable {
    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "分类内容")
    private String name;

    @ApiModelProperty(value = "商品概述")
    private List<HomeProductDTO> content;
}
