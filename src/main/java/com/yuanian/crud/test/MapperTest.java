package com.yuanian.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuanian.crud.bean.Department;
import com.yuanian.crud.bean.Employee;
import com.yuanian.crud.dao.DepartmentMapper;
import com.yuanian.crud.dao.EmployeeMapper;
/**
 * 
 * @author 27726
 * 可以使用spring的单元测试
 *1.导入SpringTest模块 
 *2.ContextConfiguration指定Spring配置文件的位置 自动创建容器
 *3.直接autowired要使用的组件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper dmapper;
	
	@Autowired
	EmployeeMapper emapper;
	
	@Autowired
	SqlSession sqlSession;

	@Test
	public void testMapper(){
		//1.创建spring容器 (原生)
//		ApplicationContext acContext=new ClassPathXmlApplicationContext("applicationContext.xml");
//		Department department=acContext.getBean(Department.class);
		
//		System.out.println(dmapper.selectByPrimaryKey(1));
//		dmapper.insertSelective(new Department(4,"开发部"));
//		dmapper.insertSelective(new Department(5,"测试部"));
		
		//emapper.insertSelective(new Employee(null,"小黑","男","16516",4));
		
		//批量生成对象
		EmployeeMapper mapper=sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 100; i++) {
			String uid =UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Employee(null,uid,"M",uid+"@yuanian",3));
		}
		System.out.println("批量加入完成！！");
	}
	

}
