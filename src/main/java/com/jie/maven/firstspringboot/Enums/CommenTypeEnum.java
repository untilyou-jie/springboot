package com.jie.maven.firstspringboot.Enums;

import com.jie.maven.firstspringboot.model.Comment;

public enum  CommenTypeEnum {
    QUESTION(1),
    COMMENT(2);
    ;

    private Integer type;

    public Integer getType() {
        return type;
    }

    CommenTypeEnum(Integer type) {
        this.type = type;
    }
    public static boolean isExist(Comment comment){
        for( CommenTypeEnum commenTypeEnum :CommenTypeEnum.values()){
            if(commenTypeEnum.getType() ==comment.getType()){
               return  true;
            }

        }
        return  false;
    }
}
