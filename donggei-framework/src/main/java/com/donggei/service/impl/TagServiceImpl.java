package com.donggei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.domain.entity.Tag;
import com.donggei.mapper.TagMapper;
import com.donggei.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-07-30 21:15:58
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

