package com.jekyloco.base;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Field;
import java.sql.Timestamp;

@Data
public class BaseDTO {
    private String createBy;

    private String updatedBy;

    private Timestamp createTime;

    private Timestamp updateTime;

    @Override
    public String toString() {
        //类的格式化输出，只有append添加的变量才会在toString函数中打印
        ToStringBuilder builder = new ToStringBuilder(this);
        //getFields(): 获取某个类的所有公共的字段，包括父类中的字段
        //getDeclaredFields(): 获取某个类的声明的字段，但不包括父类中的申明字段
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field f :
                    fields) {
                f.setAccessible(true);
                builder.append(f.getName(), f.get(this)).append("\n");
            }
        } catch (Exception e) {
            builder.append("toString builder encounter an error");
        }
        return builder.toString();
    }

}
