package com.ljx.service.impl;

import com.ljx.service.*;
import com.ljx.dao.*;
import com.ljx.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private ISysLogDao sysLogDao;


    @Override
    public List<SysLog> findAll() throws Exception {
        return sysLogDao.findAll();
    }

    @Override
    public void save(SysLog sysLog) throws Exception {
        System.out.println(sysLog.getVisitTimeStr());
        sysLogDao.save(sysLog);
    }
}
