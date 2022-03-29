package com.ljx.controller;

import com.ljx.domain.Permission;
import com.ljx.domain.Role;
import com.ljx.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    IPermissionService service;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("permissionList",service.findAll());
        mv.setViewName("permission-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Permission permission) throws Exception {
        service.save(permission);
        return "redirect:findAll.do";
    }

    @RequestMapping("/deletePermission.do")
    public String deleteRole(Integer id) throws Exception {
        service.deleteById(id);
        return "redirect:findAll.do";
    }
    @RequestMapping("/findById.do")
    public ModelAndView findById(Integer id) throws Exception {
        Permission permission = service.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("permission",permission);
        mv.setViewName("permission-show");
        return mv;
    }
}
