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
	 * ���� ���� ɾ��
	 * ����ɾ����1-2-3
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")String ids){
		//����ɾ��
		if(ids.contains("-")){
			List<Integer> del_ids=new ArrayList<>();
			String[] str_ids=ids.split("-");
			//��װidd ����
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
	 * ���ֱ�ӷ���ajax=PUT��ʽ������ �������������ݣ�����Employee�����װ���� �������Ա��
	 * 
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	public Msg saveEmp2(Employee employee, HttpServletRequest request) {
		System.out.println("���£�" + employee);
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
			return Msg.fail().add("va_msg", "�û���3-16λ���ֺ���ĸ����ϻ���2��5λ����");
		}
		boolean b = employeeService.checkUser(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "�û���������");
		}
	}

	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(Employee employee) {
		employeeService.saveEmp(employee);
		return Msg.success();
	}

	// ��Ҫ����jsckson��
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// ����PageHelper��ҳ���
		// �ڲ�ѯ֮ǰֻ����ã�����ҳ�룬�Լ�ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		// startPage��������������ѯ����һ����ҳ��ѯ
		List<Employee> emps = employeeService.getAll();
		// ʹ��PageInfo��װ��ѯ��Ľ��,�󽻸�ҳ��,(������ʾ��ҳ��)
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}
}
