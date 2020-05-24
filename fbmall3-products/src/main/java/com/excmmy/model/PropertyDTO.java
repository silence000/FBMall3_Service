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
@ApiModel(value="PropertyDTO", description="")
public class PropertyDTO implements Serializable {
    @ApiModelProperty(value = "属性名")
    private String descKey;

    @ApiModelProperty(value = "属性值")
    private String descValue;
}
