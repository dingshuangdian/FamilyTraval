package www.aiyi.com.myapplicatuon.http;

import java.util.List;

import www.aiyi.com.myapplicatuon.model.NewsParam;

/**
 * Created by Mr.DSD on 2016-12-08.
 */

public class RecourseJson {
    public Result result;

    public static class Result {
        public List<NewsParam> data;
    }
}
