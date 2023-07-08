package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.AddCategoryDTO;
import com.donggei.domain.dto.UpdateCategoryDTO;
import com.donggei.domain.entity.Category;
import com.donggei.domain.vo.CategoryVo;
import com.donggei.domain.vo.PageVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author dzz
 * @since 2022-05-12 10:23:21
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    ResponseResult getAllCategory();
    ResponseResult addCategory(AddCategoryDTO addCategoryDTO);

    ResponseResult deleteCategoryById(List<Long> idList);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(UpdateCategoryDTO updateCategoryDTO);

    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);

    List<CategoryVo> listAllCategory();
}
