package com.template.micro.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.template.micro.client.entity.SystemLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统日志表 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-04-13
 */
@Repository
public interface SystemLogMapper extends BaseMapper<SystemLog> {
    public List<SystemLog> getSystemLog(List<Integer> ids);
}
