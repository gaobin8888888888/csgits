package com.sts.csgits.entity;

/**
 * 异步任务
 * 1、添加学校信息时创建相对应的数据表
 * @author ：gb
 * @date ：Created in 2020/3/10 20:39
 */
public enum AsyncTask {

    ADD_SCHOOL_CREATE_TABLE("1");

    private final String no;

    AsyncTask(String no) {
        this.no = no;
    }

    public String getNo(){
        return no;
    }

    public static AsyncTask getAsyncTask(String no){
        for (AsyncTask value : AsyncTask.values()) {
            if(value.getNo().equals(no)){
                return value;
            }
        }
        return null;
    }
}
