package com.ljx.controller;
import com.ljx.domain.Permission;
import com.ljx.domain.Role;
import com.ljx.domain.UserInfo;
import com.ljx.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService service;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleList",service.findAll());
        mv.setViewName("role-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Role role) throws Exception {
        service.save(role);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(Integer id) throws Exception {
        Role role = service.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }

    @RequestMapping("/deleteRole.do")
    public String deleteRole(Integer id) throws Exception {
        service.deleteRoleById(id);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(Integer id) throws Exception {
        ModelAndView mv = new ModelAndView();
        //根据id查询用户
        Role role = service.findById(id);
        //查询用户可以添加的角色
        List<Permission> otherPermissions = service.findOtherPermissions(id);

        mv.addObject("role",role);
        mv.addObject("permissionList",otherPermissions);
        mv.setViewName("role-permission-add");
        return mv;
    }

    @RequestMapping("/addPermissionToRole.do")
    public ModelAndView addPermissionToRole(Integer roleId,@RequestParam(name = "ids",required = true) Integer[] perIds) throws Exception {
        service.addPermissionToRole(roleId,perIds);
        ModelAndView mv = findById(roleId);
        return mv;
    }
}
