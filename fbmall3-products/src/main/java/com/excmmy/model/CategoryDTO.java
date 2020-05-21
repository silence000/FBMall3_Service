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
@ApiModel(value="CategoryDTO对象", description="")
public class CategoryDTO implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "分类内容")
    private String name;
}
