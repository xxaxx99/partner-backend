package com.lzh.partner.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzh.partner.model.domain.User;
import com.lzh.partner.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Classname PreCacheJob
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/10/10 11:54
 * @Created by lzh
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    // 重点用户
    private final List<Long> mainUserList = Collections.singletonList(1L);

    //重点用户缓存预热
    @Scheduled(cron = "0 0 0 * * *")
    public void doCacheRecommendUser(){

        RLock lock = redissonClient.getLock("partner:precachejob:docache:lock");

        try {
            if (lock.tryLock(0,-1,TimeUnit.MILLISECONDS)){
                System.out.println("lock: " + Thread.currentThread().getName());
                for (Long userId : mainUserList) {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1, 20), queryWrapper);
                    String redisKey = String .format("partner:user:recommend:%s",userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    try {
                        valueOperations.set(redisKey,userPage,30000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.info("redis set key error",e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.info("doCacheRecommendUser error",e);
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
                System.out.println("unlock: " + Thread.currentThread().getName());
            }
        }

    }



}
