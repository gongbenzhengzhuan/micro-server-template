package log.service;

import java.util.List;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-31 15:57 星期五
 * @ClassName com.zyc.commons.log.service.IService
 * @Description: 定制化自己的业务接口
 */
@SuppressWarnings("all")
public interface IService<T> {

    public void init();

    public void save(List<T> data);
}
