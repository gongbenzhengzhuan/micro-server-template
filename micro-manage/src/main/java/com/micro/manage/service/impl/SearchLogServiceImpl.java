package com.micro.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.manage.dto.SearchLogDTO;
import com.micro.manage.entity.SearchLog;
import com.micro.manage.mapper.SearchLogMapper;
import com.micro.manage.service.ISearchLogService;
import com.micro.manage.utils.poi.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import utils.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-25 9:51 星期六
 * @ClassName com.zyc.datasystem.manage.service.impl.SearchLogServiceImpl
 * @Description: 搜索日志实现类
 */
@SuppressWarnings("all")
@Service("searchLogService")
public class SearchLogServiceImpl extends ServiceImpl<SearchLogMapper, SearchLog> implements ISearchLogService {

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 13:03:09
     * @Description 搜索日志分页查询统计列表
     * @Param[0] com.zyc.datasystem.manage.dto.SearchLogDTO searchLogDTO
     * @Return com.zyc.commons.utils.vo.Result
     */
    @Override
    public Result pageList(SearchLogDTO searchLogDTO) throws Exception {
        Result result = new Result();
        // 获取页面传入参数
        Date startTime = searchLogDTO.getStartTime();
        Date endTime = searchLogDTO.getEndTime();
        String application = searchLogDTO.getApplication();
        String searchCategory = searchLogDTO.getSearchCategory();
        String department = searchLogDTO.getDepartment();
        String userName = searchLogDTO.getUserName();
        String searchKeywords = searchLogDTO.getSearchKeywords();
        long pageIndex = searchLogDTO.getPageIndex();
        long pageSize = searchLogDTO.getPageSize();

        QueryWrapper<SearchLog> searchLogQueryWrapper = new QueryWrapper<>();
        // 操作时间：开始时间~结束时间范围
        searchLogQueryWrapper.between(startTime != null && endTime != null, "operation_time", startTime, endTime)
                // 发起应用
                .eq(StringUtils.isNotEmpty(application), "application", application)
                // 搜索分类
                .eq(StringUtils.isNotEmpty(searchCategory), "search_category", searchCategory)
                // 所属组织
                .eq(StringUtils.isNotEmpty(department), "department", department)
                // 搜索人
                .eq(StringUtils.isNotEmpty(userName), "user_name", userName)
                // 前端内容搜索，内容搜索只能按照搜索人和搜索关键词进行模糊查询，前端只传入 searchKeywords
                .like(StringUtils.isNotEmpty(searchKeywords), "user_name", searchKeywords)
                .like(StringUtils.isNotEmpty(searchKeywords), "search_keywords", searchKeywords)
                // 未删除
                .eq("deleted", 0)
                // 操作时间倒排序
                .orderByDesc("operation_time");
        IPage<SearchLog> searchLogPage = this.page(new Page(pageIndex, pageSize), searchLogQueryWrapper);
        long total = searchLogPage.getTotal();
        List<SearchLog> searchLogRecords = searchLogPage.getRecords();
        if (null == searchLogRecords || 0 > searchLogRecords.size()) {
            return result.failure("未查询到有效数据！");
        }
        List<SearchLogDTO> searchLogDTOs = new ArrayList<>();
        searchLogRecords.forEach(searchLogRecord -> {
            SearchLogDTO searchLogDTOTemp = new SearchLogDTO();
            BeanCopier.create(SearchLog.class, SearchLogDTO.class, false)
                    .copy(searchLogRecord, searchLogDTOTemp, null);
            searchLogDTOs.add(searchLogDTOTemp);
        });
        return result.success(searchLogDTOs).total(Integer.parseInt(String.valueOf(total)));
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 13:03:32
     * @Description 搜索日志批量导出
     * @Param[0] javax.servlet.http.HttpServletResponse httpServletResponse
     * @Param[1] java.util.List<java.lang.Integer> searchLogIds
     * @Return void
     */
    @Override
    public void batchExport(HttpServletResponse httpServletResponse, List<Integer> searchLogIds) throws Exception {
        Result result = new Result();
        QueryWrapper<SearchLog> searchLogQueryWrapper = new QueryWrapper<>();
        searchLogQueryWrapper.in("id", searchLogIds);
        List<SearchLog> searchLogs = this.list(searchLogQueryWrapper);
        for (int i = 0; i < searchLogs.size(); i++) {
            SearchLog searchLog = searchLogs.get(i);
            searchLog.setId(i + 1);
        }
        ExcelUtil excelUtil = new ExcelUtil(SearchLog.class);
        excelUtil.exportExcel(httpServletResponse, searchLogs, "搜索日志统计列表");
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 13:03:07
     * @Description 搜索日志存储
     * @Param[0] com.zyc.datasystem.manage.dto.SearchLogDTO searchLogDTO
     * @Return void
     */
    @Override
    public void storeSearchLog(SearchLogDTO searchLogDTO) {
        SearchLog searchLog = new SearchLog();
        BeanCopier.create(SearchLogDTO.class, SearchLog.class, false)
                .copy(searchLogDTO, searchLog, null);
        this.save(searchLog);
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 13:03:07
     * @Description 搜索日志存储（批量）
     * @Param[0] java.util.List<com.zyc.datasystem.manage.dto.SearchLogDTO> searchLogDTOs
     * @Return void
     */
    @Override
    public void storeSearchLogList(List<SearchLogDTO> searchLogDTOs) {
        List<SearchLog> searchLogs = new ArrayList<SearchLog>();
        searchLogDTOs.forEach(searchLogDTO -> {
            SearchLog searchLog = new SearchLog();
            BeanCopier.create(SearchLogDTO.class, SearchLog.class, false)
                    .copy(searchLogDTO, searchLog, null);
            searchLogs.add(searchLog);
        });
        this.saveBatch(searchLogs, searchLogs.size());
    }
}
