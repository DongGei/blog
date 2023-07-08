package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.Link;
import com.donggei.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author dzz
 * @since 2022-05-13 20:56:47
 */
public interface LinkService extends IService<Link> {

    //在友链页面要查询出所有的审核通过的友链。
    ResponseResult getAllLink();

    PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}
