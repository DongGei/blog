package com.donggei.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.Category;
import com.donggei.domain.vo.CategoryVo;
import com.donggei.domain.vo.ExcelCategoryVo;
import com.donggei.domain.vo.PageVo;
import com.donggei.enums.AppHttpCodeEnum;
import com.donggei.service.CategoryService;
import com.donggei.service.TagService;
import com.donggei.utils.BeanCopyUtils;
import com.donggei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @className: CategoryController
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/8/31
 **/
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;



    @PutMapping
    public ResponseResult edit(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable(value = "id")Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }
    @PostMapping
    public ResponseResult add(@RequestBody Category category){
        categoryService.save(category);
        return ResponseResult.okResult();
    }
    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public ResponseResult list(Category category, Integer pageNum, Integer pageSize) {
        PageVo pageVo = categoryService.selectCategoryPage(category,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.getCategoryList();
    }
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
     public void export(HttpServletResponse response){
        try {
            //设置下载文件请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> list = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(list, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果异常响应json数据

            // 重置response
            response.reset();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }



    }


}
