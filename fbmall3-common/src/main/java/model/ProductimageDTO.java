package model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="ProductimageDTO", description="")
public class ProductimageDTO implements Serializable {
    @ApiModelProperty(value = "图片ID")
    private Integer id;

    @ApiModelProperty(value = "图片名称")
    private Integer link;
}
