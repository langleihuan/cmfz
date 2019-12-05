package com.baizhi.service;

import com.baizhi.dao.CourseDao;
import com.baizhi.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public void addCourse(Course course) {
        courseDao.insertSelective(course);
    }

    @Override
    public void delete(String id) {
        courseDao.deleteByPrimaryKey(id);
    }
}
