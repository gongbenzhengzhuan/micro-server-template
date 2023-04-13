package com.micro.manage.utils;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ListCopyUtil {
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target) {
        if(!CollectionUtils.isEmpty(sources)) {
            List<T> list = new ArrayList<>(sources.size());
            S from = sources.get(0);
            T to = target.get();
            BeanCopier copier = BeanCopier.create(from.getClass(), to.getClass(), false);
            for (S source : sources) {
                T t = target.get();
                copier.copy(source, t,null);
                list.add(t);
            }
            return list;
        }else{
            return null;
        }
    }
}
