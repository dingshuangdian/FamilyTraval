package www.aiyi.com.myapplicatuon.Utils;

import com.google.gson.Gson;

import java.util.List;

import www.aiyi.com.myapplicatuon.http.RecourseJson;
import www.aiyi.com.myapplicatuon.model.NewsParam;

/**
 * Created by Mr.DSD on 2016-12-08.
 */

public class JsonUtils {
    public static List<NewsParam> getNewsFromJson(String json) {
        Gson gson = new Gson();
        RecourseJson rj = gson.fromJson(json, RecourseJson.class);
        return rj.result.data;
    }
}
