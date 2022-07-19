package com.jekyloco.service.dto.small;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class JobSmallDto implements Serializable {
    private Long id;
    private String name;
}
