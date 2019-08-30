package com.yuanian.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanian.crud.bean.Department;
import com.yuanian.crud.bean.Msg;
import com.yuanian.crud.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	//返回所有部门信息
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts(){
		List<Department> li=departmentService.getDepts();
		return Msg.success().add("depts", li);
	}
}
