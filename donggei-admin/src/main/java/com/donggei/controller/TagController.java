package com.donggei.controller;


import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.TagListDto;
import com.donggei.domain.entity.Tag;
import com.donggei.domain.vo.PageVo;
import com.donggei.domain.vo.TagVo;
import com.donggei.service.TagService;
import com.donggei.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: TagController
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/30
 **/
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    TagService tagService;
    @PostMapping("")
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }
    @GetMapping("/{id}")
    public ResponseResult<TagVo> getTag(@PathVariable("id") Long id){
        Tag byId = tagService.getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(byId, TagVo.class);
        if (byId!=null){
          return   ResponseResult.okResult(tagVo);
        }else {
            return    ResponseResult.errorResult(-1,"查询错误");
        }

    }

    @PutMapping("")
    public ResponseResult<TagVo> modifyTag(@RequestBody TagListDto tagListDto){
        return tagService.modifyTag(tagListDto);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.getAllTag();
    }
}
