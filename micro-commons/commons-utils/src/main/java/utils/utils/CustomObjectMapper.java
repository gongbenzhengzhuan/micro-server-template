package utils.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


import java.math.BigInteger;


/**
 * 自定义ObjectMapper
 *
 * @author lizhongpeng 2019/07/15 下午 12:13
 */
public class CustomObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = -5045180143682932306L;

    public CustomObjectMapper() {
        super();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        registerModule(simpleModule);
    }

}
