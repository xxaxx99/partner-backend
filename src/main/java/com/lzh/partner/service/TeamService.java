package com.lzh.partner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.partner.model.domain.Team;
import com.lzh.partner.model.domain.User;
import com.lzh.partner.model.dto.TeamQuery;
import com.lzh.partner.model.request.TeamJoinRequest;
import com.lzh.partner.model.request.TeamQuitRequest;
import com.lzh.partner.model.request.TeamUpdateRequest;
import com.lzh.partner.model.vo.TeamUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 86132
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2023-10-11 23:55:17
*/
public interface TeamService extends IService<Team> {

    /**
     * 增加队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     * @param teamQuery
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery,Boolean isAdmin);

    /**
     * 用户更新队伍
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest,User loginUser);

    /**
     * 用户加入队伍
     * @param teamJoinRequest
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest,User loginUser);

    /**
     * 用户退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 队长退出队伍(删除)
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);

    /**
     * 判断用户是否已经加入队伍
     * @param teamList 查出来的所有的标签的列表
     * @param request
     * @return
     */
    List<TeamUserVO> inTeam(List<TeamUserVO> teamList, HttpServletRequest request);

    /**
     * 获取私密队伍
     * @param teamQuery
     * @param teamList
     * @param userId
     * @return
     */
    List<TeamUserVO> getSecretTeam(TeamQuery teamQuery, List<TeamUserVO> teamList, Long userId);

    /**
     * 获取所有队伍集合
     * @param teamQuery
     * @param request
     * @return
     */
    List<TeamUserVO> getAllTeam(TeamQuery teamQuery, HttpServletRequest request,Long userId);
}
