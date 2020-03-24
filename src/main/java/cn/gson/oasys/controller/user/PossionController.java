package cn.gson.oasys.controller.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.PositionDao;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Position;

@Controller
@RequestMapping("/")
public class PossionController {
	
	@Autowired
	PositionDao pdao;
	@Autowired
	DeptDao ddao;
	@Autowired
	UserDao udao;
	
	@RequestMapping("positionmanage")
	public String positionmanage(Model model,
								  @RequestParam(value="baseKey",required=false)String basekey,
								  @RequestParam(value="deptid",required=false)Long deptid) {
		List<Position> positions;
		if(StringUtils.isEmpty(basekey) && StringUtils.isEmpty(deptid)) {
			/*positions = (List<Position>) pdao.findAll();*/
			positions = pdao.findByNameNotLike("%离职%");
		}else if(!StringUtils.isEmpty(basekey)){
			positions = pdao.findByPosition(basekey,deptid);
		}else{
			positions = pdao.findByDeptidAndNameNotLike(deptid,"%离职%");
		}
		List<Dept> deptNames = new ArrayList<>();
		for (Position p: positions) {
			Long id = p.getDeptid();
			Dept dept = ddao.findByDeptId(id);
			p.setDept(dept);
		}
		//根据deptName排序
/*		Collections.sort(positions, new Comparator<Position>() {
			@Override
			public int compare(Position o1, Position o2) {
				return o1.getDept().getDeptName().compareTo(o1.getDept().getDeptName());
			}
		});*/
		List<Order> orders=new ArrayList<>();
		orders.add(new Order(Direction.ASC, "handle"));
		orders.add(new Order(Direction.DESC, "sharetime"));
		Sort sort=new Sort(orders);
		deptNames = (List<Dept>) ddao.findAll();

		model.addAttribute("id",deptid == null?0:deptid);
		model.addAttribute("deptNames",deptNames);
		model.addAttribute("positions",positions);
		model.addAttribute("basekey",basekey);
		
		return "user/positionmanage";
	}
	
	@RequestMapping(value = "positionedit" ,method = RequestMethod.GET)
	public String positioneditget(@RequestParam(value = "positionid",required=false) Long positionid,Model model){
		if(positionid!=null){
			
			Position position = pdao.findOne(positionid);
			System.out.println(position);
			Dept dept = ddao.findOne(position.getDeptid());
			model.addAttribute("positiondept",dept);
			model.addAttribute("position",position);
		}
		List<Dept> depts = (List<Dept>) ddao.findAll();
		model.addAttribute("depts", depts);
		return "user/positionedit";
	}
	
	@RequestMapping(value = "positionedit" ,method = RequestMethod.POST)
	public String positioneditpost(Position position,Model model){
		System.out.println(position);
		
		Position psition2 = pdao.save(position);
		
		if(psition2!=null){
			model.addAttribute("success",1);
			return "/positionmanage";
		}
		
		model.addAttribute("errormess","数据插入失败");
		return "user/positionedit";
	}
	
	
	@RequestMapping("removeposition")
	public String removeposition(@RequestParam("positionid") Long positionid,Model model) {
		Position position = pdao.findOne(positionid);
		List<User> userList = udao.findByPosition(position);
		if (userList.size() > 0) {
			String message = "";
			for (User user : userList) {
				message += user.getRealName() + "，";
			}
			message = message.substring(0, message.length() - 1);
			message += "。尚在此职位，无法删除该职位";
			model.addAttribute("errormess", message);

		} else {

			pdao.delete(positionid);
			model.addAttribute("success", 1);

		}
		return "/positionmanage";
	}

	
	
}
