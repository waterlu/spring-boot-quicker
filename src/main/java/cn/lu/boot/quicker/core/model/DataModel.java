package cn.lu.boot.quicker.core.model;

/**
 * Created by lutiehua on 2017/9/26.
 */
public abstract class DataModel {

    /**
     * 作者
     */
    protected String author;

    /**
     * 日期
     */
    protected String date;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
