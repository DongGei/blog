package com.donggei.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDTO {

    private Long id;
    private String description;

    private String name;

    private String Status;
}