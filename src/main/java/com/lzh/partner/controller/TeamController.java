package com.lzh.partner.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzh.partner.common.BaseResponse;
import com.lzh.partner.common.DeleteRequest;
import com.lzh.partner.common.ErrorCode;
import com.lzh.partner.common.ResultUtils;
import com.lzh.partner.exception.BusinessException;
import com.lzh.partner.model.domain.Team;
import com.lzh.partner.model.domain.User;
import com.lzh.partner.model.domain.UserTeam;
import com.lzh.partner.model.dto.TeamQuery;
import com.lzh.partner.model.request.TeamAddRequest;
import com.lzh.partner.model.request.TeamJoinRequest;
import com.lzh.partner.model.request.TeamQuitRequest;
import com.lzh.partner.model.request.TeamUpdateRequest;
import com.lzh.partner.model.vo.TeamUserVO;
import com.lzh.partner.service.TeamService;
import com.lzh.partner.service.UserService;
import com.lzh.partner.service.UserTeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname TeamController
 * @Description 队伍功能接口
 * @Version 1.0.0
 * @Date 2023/10/11 23:59
 * @Created by lzh
 */
@RestController
@RequestMapping("/team")
@Slf4j
@CrossOrigin(origins = {"http://localhost:5173"})
public class TeamController {

    @Resource
    private TeamService teamService;

    @Resource
    private UserService userService;

    @Resource
    private UserTeamService userTeamService;

    @PostMapping("/add")
    public BaseResponse<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request){
        if (teamAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team = new Team();
        BeanUtils.copyProperties(teamAddRequest,team);
        User loginUser = userService.getLoginUser(request);
        long teamId = teamService.addTeam(team, loginUser);
        return ResultUtils.success(teamId);
    }

    @GetMapping("/list")
    public BaseResponse<List<TeamUserVO>> listTeams(TeamQuery teamQuery,HttpServletRequest request){
        if (teamQuery == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean isAdmin = userService.isAdmin(request);
        List<TeamUserVO> listTeams = teamService.listTeams(teamQuery,isAdmin);
        return ResultUtils.success(listTeams);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest,HttpServletRequest request){
        if (teamUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.updateTeam(teamUpdateRequest,loginUser);
        if (!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/get")
    public BaseResponse<Team> getTeamById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team = teamService.getById(id);
        if (team == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(team);
    }


    @PostMapping("/join")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest, HttpServletRequest request){
        if (teamJoinRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest,loginUser);
        return ResultUtils.success(result);
    }

    @PostMapping("/quit")
    public BaseResponse<Boolean> quitTeam(@RequestBody TeamQuitRequest teamQuitRequest, HttpServletRequest request) {
        if (teamQuitRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.quitTeam(teamQuitRequest, loginUser);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeam(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.deleteTeam(id, loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/list/my/create")
    public BaseResponse<List<TeamUserVO>> listMyCreateTeams(TeamQuery teamQuery,HttpServletRequest request){
        if (teamQuery == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        teamQuery.setUserId(loginUser.getId());
        List<TeamUserVO> listTeams = teamService.listTeams(teamQuery,true);
        return ResultUtils.success(listTeams);
    }

    @GetMapping("/list/my/join")
    public BaseResponse<List<TeamUserVO>> listMyJoinTeams(TeamQuery teamQuery,HttpServletRequest request){
        if (teamQuery == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        LambdaQueryWrapper<UserTeam> userTeamLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userTeamLambdaQueryWrapper.eq(UserTeam::getUserId,loginUser.getId());
        List<UserTeam> userTeams = userTeamService.list(userTeamLambdaQueryWrapper);
        Map<Long, List<UserTeam>> listMap = userTeams.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));
        ArrayList<Long> idList = new ArrayList<>(listMap.keySet());
        teamQuery.setIdList(idList);
        List<TeamUserVO> listTeams = teamService.listTeams(teamQuery,true);
        return ResultUtils.success(listTeams);
    }

}
