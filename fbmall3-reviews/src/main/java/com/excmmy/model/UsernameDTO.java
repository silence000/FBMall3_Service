package com.excmmy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UsernameDTO", description="")
public class UsernameDTO {
    @ApiModelProperty(value = "用户名")
    private String username;
}
