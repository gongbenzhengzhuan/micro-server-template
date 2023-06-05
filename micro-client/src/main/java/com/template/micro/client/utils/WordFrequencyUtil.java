package com.template.micro.client.utils;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author duanzili
 * @function 根据单词出现频率，计算topn工具类
 */
@Component
public class WordFrequencyUtil {

    public  List<String> getWordFrequency(List<String> words, Integer num) {
        List<String> result = new ArrayList<>();

        //测试数据
//        words.add("a");
//        words.add("a");
//        words.add("b");

//        System.out.println(words);
//排序
        HashMap<String, Integer> map_Data = new HashMap<>();
        for (String w : words) {
            if (map_Data.containsKey(w)) {
                map_Data.put(w, map_Data.get(w) + 1);
            } else {
                map_Data.put(w, 1);
            }
        }

        List<Map.Entry<String, Integer>> list_Data = new ArrayList<Map.Entry<String, Integer>>(map_Data.entrySet());
//        System.out.println(list_Data);
        Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if ((o2.getValue() - o1.getValue()) > 0)
                    return 1;
                else if ((o2.getValue() - o1.getValue()) == 0)
                    return 0;
                else
                    return -1;
            }
        });

        if (list_Data.size() < num) {
            num = list_Data.size();
        }
       //返回topn
        for (int i = 0; i < num; i++) {
//            System.out.println(list_Data.get(i).getKey() + ": " + list_Data.get(i).getValue());
            result.add(list_Data.get(i).getKey());
        }
        return result;
    }
}
