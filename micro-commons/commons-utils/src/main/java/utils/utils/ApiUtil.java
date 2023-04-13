package utils.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.vo.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author fangxin
 * @Date 2023/2/9
 */
public class ApiUtil {

    public static Object getApiData(Result result) {
        if (result.getCode().equals(200) || result.getCode().equals(0)) {
            if (result.getData() != null) {
                return result.getData();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getJsonString(JSONObject jsonObject, String string) {
        return Optional.ofNullable(jsonObject).flatMap(a -> Optional.ofNullable(a.getString(string))).orElse("");
    }

    public static JSONArray getJsonArray(JSONObject jsonObject, String string) {
        return Optional.ofNullable(jsonObject).flatMap(a -> Optional.ofNullable(a.getJSONArray(string))).orElse(new JSONArray());
    }

    public static List<String> getStringList(JSONObject jsonObject, String string) {
        JSONArray jsonArray = Optional.ofNullable(jsonObject).flatMap(a -> Optional.ofNullable(a.getJSONArray(string))).orElse(null);
        return Optional.ofNullable(jsonArray).flatMap(a -> Optional.ofNullable(JSONObject.parseArray(jsonArray.toJSONString(), String.class))).orElse(new ArrayList<>());
    }

    public static JSONObject getJsonObject(JSONObject jsonObject, String string) {
        return Optional.ofNullable(jsonObject).flatMap(a -> Optional.ofNullable(a.getJSONObject(string))).orElse(null);
    }
}
