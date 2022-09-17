package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.TagListDto;
import com.donggei.domain.entity.Tag;
import com.donggei.domain.vo.PageVo;
import com.donggei.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-07-30 21:15:56
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long id);

    ResponseResult<TagVo> modifyTag(TagListDto tagListDto);

    ResponseResult getAllTag();
}

