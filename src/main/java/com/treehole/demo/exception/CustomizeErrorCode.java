package com.treehole.demo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"您找的问题不存在了，要不换一个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复");

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public Integer getCode(){
        return code;
    }

    CustomizeErrorCode(Integer code, String message){
        this.message = message;
        this.code = code;
    }
}
