package www.aiyi.com.myapplicatuon.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by dings on 2016-12-06.
 */

public class RecomantParam implements Serializable {
    private String date;
    private String author_name;
    private String thumbnail_pic_s;
    private String title;
    private String type;
    private String realtype;
    private String uniquekey;
    private Integer pno; // 页码
    private Integer psize; //分页大小


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRealtype() {
        return realtype;
    }

    public void setRealtype(String realtype) {
        this.realtype = realtype;
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public Integer getPno() {
        return pno;
    }

    public void setPno(Integer pno) {
        this.pno = pno;
    }

    public Integer getPsize() {
        return psize;
    }

    public void setPsize(Integer psize) {
        this.psize = psize;
    }

    public RecomantParam copy() {
        return JSON.parseObject(JSON.toJSONString(this), RecomantParam.class);
    }

}
