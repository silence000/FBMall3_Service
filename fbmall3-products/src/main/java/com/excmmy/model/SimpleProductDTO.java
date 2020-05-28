package com.excmmy.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleProductDTO implements Serializable {
    @ApiModelProperty(value = "商品Id")
    private Integer id;

    @ApiModelProperty(value = "小标题")
    private String subTitle;
}
