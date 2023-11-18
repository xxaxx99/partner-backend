package com.lzh.partner.controller;

import com.lzh.partner.common.BaseResponse;
import com.lzh.partner.common.ErrorCode;
import com.lzh.partner.common.ResultUtils;
import com.lzh.partner.exception.BusinessException;
import com.lzh.partner.model.domain.User;
import com.lzh.partner.model.request.UserLoginRequest;
import com.lzh.partner.model.request.UserRegisterRequest;
import com.lzh.partner.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.lzh.partner.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @Classname UserController
 * @Description 用户接口
 * @Version 1.0.0
 * @Date 2023/10/4 15:21
 * @Created by lzh
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }


    /**
     * @description 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }


    /**
     * 获取当前登录的用户的信息
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 通过标签进行搜索
     * @param tagNameList
     * @return
     */
    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUserByTags(@RequestParam(required = false) List<String> tagNameList){
        if (CollectionUtils.isEmpty(tagNameList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> userList = userService.searchUsersByTags(tagNameList);
        return ResultUtils.success(userList);
    }

    /**
     * 更新用户信息
     * @param user
     * @param request
     * @return
     */
    @PutMapping("/update")
    public BaseResponse<Integer> updateUser(@RequestBody User user,HttpServletRequest request){
        //1. 校验参数是否为空
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2. 校验权限
        User loginUser = userService.getLoginUser(request);
        //3. 触发更新
        int result = userService.updateUser(user, loginUser);
        return ResultUtils.success(result);
    }

    @GetMapping("/recommend")
    public BaseResponse<Object> recommendUsers(long pageSize, long pageNum, HttpServletRequest request){
        //查出当前用户
        User loginUser = userService.getLoginUser(request);
        //设计redisKey保证Key的唯一
        String redisKey = String.format("partner:user:recommend:%s",loginUser.getId());
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        //如果缓存存在直接返回
        List<User> userList = (List<User>) valueOperations.get(redisKey);
        if (userList != null){
            return ResultUtils.success(userList);
        }
        //没有缓存走数据库
        userList =  userService.getRandomRecords(pageSize,pageNum);
        List<User> finalUserList = userList.stream()
                .filter(user -> user.getId() != loginUser.getId()).collect(Collectors.toList());
        //第一次查数据库写入缓存
        try {
            valueOperations.set(redisKey,finalUserList,30000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("redis set key error", e);
        }
        return ResultUtils.success(finalUserList);
    }

    /**
     * 获取最匹配的用户
     *
     * @param num
     * @param request
     * @return
     */
    @GetMapping("/match")
    public BaseResponse<List<User>> matchUsers(long num, HttpServletRequest request) {
        if (num <= 0 || num > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.matchUsers(num, user));
    }


}
