package com.ljx.controller;

import com.github.pagehelper.PageInfo;
import com.ljx.domain.Role;
import com.ljx.domain.UserInfo;
import com.ljx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> all = userService.findAll();
        PageInfo<UserInfo> pageInfo = new PageInfo<>(all);
        mv.addObject("userList",all);
        mv.setViewName("user-list");
        return mv;
    }

    @RequestMapping("/save.do")
    @RolesAllowed("ADMIN")
    public String save(UserInfo user) throws Exception {
        userService.save(user);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(Integer id) throws Exception {
        UserInfo byId = userService.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",byId);
        mv.setViewName("user-show");
        return mv;
    }

    //查询用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(Integer id) throws Exception {
        ModelAndView mv = new ModelAndView();
        //根据id查询用户
        UserInfo userInfo = userService.findById(id);
        //查询用户可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(id);

        mv.addObject("user",userInfo);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }

    @RequestMapping("/addRoleToUser.do")
    @RolesAllowed("ADMIN")
    public ModelAndView addRoleToUser(Integer userId,@RequestParam(name = "ids",required = true) Integer[] roleIds) throws Exception {
        userService.addRoleToUser(userId,roleIds);
        ModelAndView mv = findById(userId);
        return mv;
    }
}
