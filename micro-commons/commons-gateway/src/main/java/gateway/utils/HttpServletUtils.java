package gateway.utils;

import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public class HttpServletUtils {

    //获取对应cookie值 并校验是否存在
    public static String getCookieAndAssert(String key, Cookie[] cookies){
        return getCookieAndAssert(key,null, cookies);
    }
    //获取对应cookie值
    public static String getCookieAndAssert(String key, String msg, Cookie[] cookies){
        String cookie = getCookie(key,cookies);
        if(msg==null){
            Assert.hasText(cookie,"从cookie中未获取到值key:"+key);
        }else{
            Assert.hasText(cookie,msg);
        }
        return cookie;
    }
    //获取对应cookie值 并校验是否存在
    public static String getCookie(HttpServletRequest request, String key) {
        return getCookie(key,request.getCookies());
    }
    //获取对应cookie值
    public static String getCookie(String key, Cookie[] cookies) {
        if(cookies==null){cookies=new Cookie[0];}
        return Stream.of(cookies).filter(c -> key.equalsIgnoreCase(c.getName())).map(Cookie::getValue).findFirst().orElse("");
    }



}
