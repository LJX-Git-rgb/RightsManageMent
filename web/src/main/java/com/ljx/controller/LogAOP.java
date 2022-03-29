package com.ljx.controller;

import com.ljx.domain.SysLog;
import com.ljx.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAOP {

    @Autowired
    //web.xml中配置了RequestContextLister就可以注入
    private HttpServletRequest request;

    @Autowired
    private ISysLogService service;

    //访问的时间,类,方法
    private Date startTime;
    private Class excutionClass;
    private Method excutionMethod;

    @Pointcut("execution(* com.ljx.controller.*.*(..))")
    private void pt1(){}

    @Before("pt1()")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        //开始访问的时间
        startTime = new Date();
        //访问的类
        excutionClass = jp.getTarget().getClass();
        //访问的方法名及参数
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();

        if (args != null && args.length != 0) {
            System.out.println("args.length: "+ args.length);
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {

                System.out.println("i: " + i + "  " + args[i]);

                classArgs[i] = args[i].getClass();
            }
            excutionMethod = excutionClass.getMethod(methodName, classArgs);
        }else{
            //获取访问的方法(无参数)
            excutionMethod = excutionClass.getMethod(methodName);
        }
    }

    @AfterReturning("pt1()")
    public void doafter(JoinPoint jp) throws Exception {
        //最终的用户操作的时间
        long time = new Date().getTime() - startTime.getTime();
        //获取URL(/user/findAll.do的形式)
        String url = "";
        if (excutionClass != null && excutionMethod != null && excutionClass != LogAOP.class) {
            //获取类注解
            RequestMapping classMapping = (RequestMapping) excutionClass.getAnnotation(RequestMapping.class);
            if (classMapping != null) {
                String[] classValue = classMapping.value();
                //方法注解
                RequestMapping methodMapping = excutionMethod.getAnnotation(RequestMapping.class);
                if (methodMapping != null) {
                    String[] methodValue = methodMapping.value();
                    url = classValue[0] + methodValue[0];
                }
            }
        }
        //获取IP,Servlet的request
        //在Spring中可以通过RequestContextListener来获取request或session对象。
        //ip地址的获取我们可以通过request.getRemoteAddr()方法获取到。
        String ip = request.getRemoteAddr();

        //获取操作者,SecurityContextHolder获取
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();

        SysLog sysLog = new SysLog();
        sysLog.setExecutionTime(time); //执行时长
        sysLog.setIp(ip);
        sysLog.setMethod("[类名] " + excutionClass.getName() + "[方法名] " + excutionMethod.getName());
        sysLog.setUrl(url);
        sysLog.setUsername(username);
        sysLog.setVisitTime(startTime);
        //调用Service完成操作
        service.save(sysLog);
    }
}
