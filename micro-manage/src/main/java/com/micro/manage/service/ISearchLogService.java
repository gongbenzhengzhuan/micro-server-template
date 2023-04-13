package com.micro.manage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.manage.dto.SearchLogDTO;
import com.micro.manage.entity.SearchLog;
import utils.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-24 18:07 星期五
 * @ClassName com.zyc.datasystem.manage.service.ISearchLogService
 * @Description:
 */
@SuppressWarnings("all")
public interface ISearchLogService extends IService<SearchLog> {

    public abstract Result pageList(SearchLogDTO searchLogDTO) throws Exception;

    public abstract void batchExport(HttpServletResponse httpServletResponse, List<Integer> searchLogIds) throws Exception;

    public abstract void storeSearchLog(SearchLogDTO searchLogDTO);

    public abstract void storeSearchLogList(List<SearchLogDTO> searchLogDTOs);
}
