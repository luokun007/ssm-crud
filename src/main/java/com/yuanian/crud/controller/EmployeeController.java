package com.yuanian.crud.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanian.crud.bean.Employee;
import com.yuanian.crud.bean.Msg;
import com.yuanian.crud.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	
	/**
	 * 单个 批量 删除
	 * 批量删除：1-2-3
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")String ids){
		//批量删除
		if(ids.contains("-")){
			List<Integer> del_ids=new ArrayList<>();
			String[] str_ids=ids.split("-");
			//组装idd 集合
			for (String string:str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		}else{
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}

		return Msg.success();
	}
	
	/**
	 * 如果直接发送ajax=PUT形式的请求 请求体中有数据，但是Employee对象封装不上 保存更新员工
	 * 
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	public Msg saveEmp2(Employee employee, HttpServletRequest request) {
		System.out.println("更新：" + employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}

	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer id) {

		Employee employee = employeeService.getEmp(id);

		return Msg.success().add("emp", employee);
	}

	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuse(@RequestParam("empName") String empName) {
		String regx = "(^[a-zA-Z0-9_-]{3,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名3-16位数字和字母的组合或者2到5位中文");
		}
		boolean b = employeeService.checkUser(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}

	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(Employee employee) {
		employeeService.saveEmp(employee);
		return Msg.success();
	}

	// 需要下载jsckson包
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// 引入PageHelper分页插件
		// 在查询之前只需调用，传入页码，以及每页的大小
		PageHelper.startPage(pn, 5);
		// startPage后面紧跟的这个查询就是一个分页查询
		List<Employee> emps = employeeService.getAll();
		// 使用PageInfo包装查询后的结果,后交给页面,(连续显示的页数)
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}
}
