package com.ljx.dao;

import com.ljx.domain.Member;
import org.apache.ibatis.annotations.Select;

public interface IMemberDao {

    @Select("select * from members where id=#{id}")
    public Member findById(String id) throws Exception;
}
