package com.easy.framework.bean.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * </p>
 *
 * @author muchi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "单Key入参")
public class KeyDTO {

    @NotBlank(message = "key不能为空")
    @Schema(title = "key")
    private String key;
}