package com.donggei.service.impl;

import com.donggei.domain.entity.Article;
import com.donggei.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @className: ThreadService
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/9/28
 **/
@Component
public class ThreadService {

    //使用这个线程池去执行下面的任务,不会影响主线程
    @Async("taskExecutor")
    public void updateCount(ArticleMapper articleMapper, Article article){
        try {
            Thread.sleep(500); //更新操作
            System.out.println("更新完成了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
