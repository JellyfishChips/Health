package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className MemberServiceImpl
 * @description
 * @return
 * @date 2019/7/1 21:05
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public List<Integer> findByCountOfMonths(List<String> months) {
        List<Integer> list = new ArrayList<>();
        months.forEach(month -> {
            list.add(memberDao.findMemberCountByMonth(month + ".31"));
        });
        return list;
    }
}
