package cn.gson.oasys.controller.user;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cn.gson.oasys.common.formValid.ReturnMessage;
import cn.gson.oasys.model.dao.user.DeptFatherDao;
import cn.gson.oasys.model.entity.user.DeptFather;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.PositionDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Position;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class DeptController {
	
	@Autowired
	DeptDao deptdao;
	@Autowired
	UserDao udao;
	@Autowired
	PositionDao pdao;
	@Autowired
	DeptFatherDao deptfatherdao;

	
	/**
	 * 第一次进入部门管理页面
	 * @return
	 */
	@RequestMapping("deptmanage")
	public String deptmanage(Model model,
							@RequestParam(value="page",defaultValue="0") int page,
							 @RequestParam(value="size",defaultValue="10") int size,
							 @RequestParam(value="baseKey",required=false)String basekey) {
		List<Sort.Order> orders=new ArrayList<>();
		orders.add(new Sort.Order(Sort.Direction.ASC, "deptName"));
		Sort sort=new Sort(orders);

		Pageable pa=new PageRequest(page, size,sort);

		List<Dept> depts;
		if(basekey == null){
			depts = (List<Dept>) deptdao.findAll(sort);
		}else{
			depts =  (List<Dept>) deptdao.findnamelike(basekey,pa);
		}
		model.addAttribute("url","/deptmanage");
		model.addAttribute("basekey", basekey);
		model.addAttribute("depts",depts);
		return "user/deptmanage";
	}




	@RequestMapping(value = "deptedit" ,method = RequestMethod.POST)
	public String adddept(@Valid Dept dept,@RequestParam("xg") String xg,@RequestParam("deptFatherid") Long deptFatherId,BindingResult br,Model model){
		System.out.println(br.hasErrors());
		System.out.println(br.getFieldError());
		if(!br.hasErrors()){
			System.out.println("没有错误");
			Dept adddept =new Dept();
			if (deptFatherId !=null && deptFatherId != 999){
				model.addAttribute("deptFatherId",deptFatherId);
				DeptFather df = deptdao.findByFatherId(deptFatherId);
				dept.setDeptFatherId(df);
				 adddept = deptdao.save(dept);
			}else if (deptFatherId !=null && deptFatherId == 999){
				DeptFather df = new DeptFather();
				df.setDeptFatherName(dept.getDeptName());
				DeptFather adddeptfather = deptfatherdao.save(df);
			}
			if("add".equals(xg)){
				System.out.println("新增拉");
				Position jinli = new Position();
				jinli.setDeptid(adddept.getDeptId());
				jinli.setName("经理");
				Position wenyuan = new Position();
				wenyuan.setDeptid(adddept.getDeptId());
				wenyuan.setName("文员");
				pdao.save(jinli);
				pdao.save(wenyuan);
			}
			if(adddept!=null){
				System.out.println("插入成功");
				model.addAttribute("success",1);
				return "/deptmanage";
			}
		}
		System.out.println("有错误");
		model.addAttribute("errormess","错误！~");
		return "user/deptedit";
	}
	
	@RequestMapping(value = "deptedit" ,method = RequestMethod.GET)
	public String changedept(@RequestParam(value = "dept",required=false) Long deptId, HttpServletRequest req, Model model){
		if(deptId!=null){
			Dept dept = deptdao.findOne(deptId);
			model.addAttribute("dept",dept);
			model.addAttribute("deptFatherName", deptdao.findOne(deptId).getDeptFatherId().getDeptFatherName());
		}
		List<DeptFather> deptfatherList = deptdao.findALLFatherName();
		DeptFather df=new DeptFather();
		long id = 999;
		df.setDeptFatherId(id);
		df.setDeptFatherName("#");
		deptfatherList.add(df);
		req.setAttribute("deptFatherList", deptfatherList);
		return "user/deptedit";
	}
	
	@RequestMapping("readdept")
	public String readdept(@RequestParam(value = "deptid") Long deptId,Model model){

		Dept dept = deptdao.findOne(deptId);
		User deptmanage = null;
		if(dept.getDeptmanager()!=null){
			deptmanage = udao.findOne(dept.getDeptmanager());
			model.addAttribute("deptmanage",deptmanage);
		}
		List<Dept> depts = (List<Dept>) deptdao.findAll();
		List<Position> positions = pdao.findByDeptidAndNameNotLike(deptId, "%离职");
		//List<Position> positions = pdao.findByDeptid(deptId);
		System.out.println(deptmanage);
		List<User> formaluser = new ArrayList<>();
		List<User> deptusers = udao.findByDept(dept);
		
		for (User deptuser : deptusers) {
			Position position = deptuser.getPosition();
			System.out.println(deptuser.getRealName()+":"+position.getName());
			formaluser.add(deptuser);
			/*if(!position.getName().endsWith("经理")){
				formaluser.add(deptuser);
			}*/
		}
		//部门领导
		Long ManagerId = deptdao.findDeptManager(dept.getDeptId());
		User deptManager = udao.findOneByUserId(ManagerId);

		System.out.println(deptusers);
		model.addAttribute("positions",positions);
		model.addAttribute("depts",depts);
		model.addAttribute("deptuser",formaluser);
		model.addAttribute("deptManager",deptManager);
		model.addAttribute("dept",dept);
		model.addAttribute("isread",1);
		
		return "user/deptread";
		
	}
	
	@RequestMapping("deptandpositionchange")
	public String deptandpositionchange(@RequestParam(value="positionid",defaultValue ="0") Long positionid,
			@RequestParam("changedeptid") Long changedeptid,
			@RequestParam("userid") Long userid,
			@RequestParam("deptid") Long deptid,
			Model model){
	    if (positionid!=0 && changedeptid!=0){
            User user = udao.findOne(userid);
            Dept changedept = deptdao.findOne(changedeptid);
            Position position = pdao.findOne(positionid);
            if (changedept.getDeptName().contains("离职")){
                user.setDept(changedept);
                user.setPosition(position);
                user.setIsLock(1);
            }else {
                user.setDept(changedept);
                user.setPosition(position);
            }
            udao.save(user);
        }else {
            model.addAttribute("error", "请选择正确的部门职位信息！");
            return "common/proce";
        }

        System.out.println(deptid);
		
		model.addAttribute("deptid",deptid);
		return "/readdept";
	}
	
	@RequestMapping("deletdept")
	public String deletdept(@RequestParam("deletedeptid") Long deletedeptid,Model model){
		Dept dept = deptdao.findOne(deletedeptid);
		List<User> userList = udao.findByDept(dept);
		if(userList.size() > 0) {
			String message = "";
			for(User user : userList){
				message += user.getRealName() + "，";
			}
			message = message.substring(0,message.length() -1);
			message += "。尚在部门，无法删除该部门";
			model.addAttribute("errormess",message);

		}else{
			List<Position> ps = pdao.findByDeptidAndNameNotLike(deletedeptid,"%离职");
			for (Position position : ps) {
				System.out.println(position);
				pdao.delete(position);
				model.addAttribute("success",1);
				deptdao.delete(dept);
			}
			if (ps.size() == 0){
				model.addAttribute("success",1);
				deptdao.delete(dept);
			}

		}
		return "/deptmanage";
		
	}
	
	@RequestMapping("deptmanagerchange")
	public String deptmanagerchange(@RequestParam(value="positionid",required=false) Long positionid,
			@RequestParam(value="changedeptid",required=false) Long changedeptid,
			@RequestParam(value="oldmanageid",required=false) Long oldmanageid,
			@RequestParam(value="newmanageid",required=false) Long newmanageid,
			@RequestParam(value="deptid") Long deptid,
			Model model){
		System.out.println("oldmanageid:"+oldmanageid);
		System.out.println("newmanageid:"+newmanageid);
		Dept deptnow = deptdao.findOne(deptid);
		if(oldmanageid!=null){
			/*User oldmanage = udao.findOne(oldmanageid);
			
			Position namage = oldmanage.getPosition();
			
			Dept changedept = deptdao.findOne(changedeptid);
			Position changeposition = pdao.findOne(positionid);
			
			oldmanage.setDept(changedept);
			oldmanage.setPosition(changeposition);
			udao.save(oldmanage);
			*/
			if(newmanageid!=null){
				User newmanage = udao.findOne(newmanageid);
				/*newmanage.setPosition(namage);*/
				deptnow.setDeptmanager(newmanageid);
				deptdao.save(deptnow);
				udao.save(newmanage);
				List<User> users = udao.findByDeptId(deptid);
				for(User user : users){
					user.setFatherId(newmanageid);
					udao.save(user);
				}

			}else{
				deptnow.setDeptmanager(null);
				deptdao.save(deptnow);
			}
			
		}else{
			User newmanage = udao.findOne(newmanageid);
			/*Position manage = pdao.findByDeptidAndNameLike(deptid, "%经理").get(0);
			newmanage.setPosition(manage);*/

			newmanage.setPosition(newmanage.getPosition());
			deptnow.setDeptmanager(newmanageid);
			deptdao.save(deptnow);
			udao.save(newmanage);
			List<User> users = udao.findByDeptId(deptid);
			for(User user : users){
				user.setFatherId(newmanageid);
				udao.save(user);
			}
		}
		model.addAttribute("deptid",deptid);
		return "/readdept";
	}

	@RequestMapping("checkUserAndPosition")
	@ResponseBody
	public ReturnMessage checkUserAndPosition(@RequestParam(value="positionid",required=false) Long deptid) {
		ReturnMessage rm = new ReturnMessage();
		Dept dept = deptdao.findOne(deptid);
		List<User> userList = udao.findByDept(dept);
		if(userList.size() > 0) {
			rm.setCode(0);
			rm.setMessage("该部门还有员工");
			rm.setData(userList);
		}else{
			rm.setCode(1);
		}
		return rm;

	}




}
