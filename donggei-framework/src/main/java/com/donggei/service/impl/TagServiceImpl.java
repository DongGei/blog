package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.TagListDto;
import com.donggei.domain.entity.Tag;
import com.donggei.domain.vo.PageVo;
import com.donggei.domain.vo.TagVo;
import com.donggei.enums.AppHttpCodeEnum;
import com.donggei.exception.SystemException;
import com.donggei.mapper.TagMapper;
import com.donggei.service.TagService;
import com.donggei.utils.BeanCopyUtils;
import com.donggei.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 标签(Tag)表服务实现类
 *
 * @author dzz
 * @since 2022-07-30 21:15:58
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        Page<Tag> tagPage = new Page<>();
        tagPage.setCurrent(pageNum);
        tagPage.setSize(pageSize);

        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());


      //  Page<Tag> pageList = page(tagPage, wrapper);  pageList就是tagPage 是同一个大小
        page(tagPage, wrapper);
        //封装结果 返回

        PageVo pageVo = new PageVo(tagPage.getRecords(), tagPage.getTotal());
        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {

        if (!StringUtils.hasText(tagListDto.getRemark()) || !StringUtils.hasText(tagListDto.getName())){
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_NULL);
        }
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        if(!Objects.isNull(tag)) {
            save(tag);
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(-1,"保存失败");
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getId,id);
        boolean removeBoolean = remove(wrapper);
        if (removeBoolean){
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(-1,"删除失败");
    }

    @Override
    public ResponseResult<TagVo> modifyTag(TagListDto tagListDto ) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getId,tagListDto.getId());
        boolean update = update(tag, wrapper);
        if (update){
            return ResponseResult.okResult();
        }
        return  ResponseResult.errorResult(-1,"修改错误");
    }

    @Override
    public ResponseResult getAllTag() {
        List<Tag> tagList = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tagList, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}

