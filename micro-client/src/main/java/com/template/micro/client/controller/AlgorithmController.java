package com.template.micro.client.controller;

import com.template.micro.client.dto.TextDTO;
import com.template.micro.client.utils.EditDistanceUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.vo.Result;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-05-06
 */
@Api(tags = {"[算法接口], tags = [算法接口], description = [算法接口]"})
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {
    //增删改查
    @Autowired
    private EditDistanceUtils editDistanceUtils;

    @ApiOperation(value = "编辑距离-相似度")
    @PostMapping(value = "/editDistance")
    public Result<String> editDistance(@RequestBody TextDTO textDTO) {
        return new Result<String>().success(editDistanceUtils.similarity(textDTO.getContent_first(),textDTO.getContent_second()));
    }

}

