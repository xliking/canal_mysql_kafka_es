package com.easy.framework.bean.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * </p>
 *
 * @author muchi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "id 集合请求参数")
public class IdListDTO {

    @Schema(title = "id集合")
    private List<String> idList;
}