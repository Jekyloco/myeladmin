package com.jekyloco.service.dto.small;

import lombok.Data;

import java.io.Serializable;


//实现Serializable接口的类可序列化
@Data
public class DeptSmallDto implements Serializable {
    private Long id;

    private String name;
}
