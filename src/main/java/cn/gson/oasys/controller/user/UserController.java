package cn.gson.oasys.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.gson.oasys.common.formValid.BindingResultVOUtil;
import cn.gson.oasys.common.formValid.MapToList;
import cn.gson.oasys.common.formValid.ResultEnum;
import cn.gson.oasys.common.formValid.ResultVO;
import cn.gson.oasys.model.dao.informdao.InformRelationDao;
import cn.gson.oasys.model.dao.maildao.MailreciverDao;
import cn.gson.oasys.model.dao.processdao.NotepaperDao;
import cn.gson.oasys.model.entity.mail.Mailreciver;
import cn.gson.oasys.model.entity.notice.NoticeUserRelation;
import cn.gson.oasys.model.entity.process.Notepaper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.util.StringUtil;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import cn.gson.oasys.model.dao.roledao.RoleDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.PositionDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.role.Role;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Position;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	UserDao udao;
	@Autowired
	DeptDao ddao;
	@Autowired
	PositionDao pdao;
	@Autowired
	RoleDao rdao;
	@Autowired
	private InformRelationDao irdao;
	@Autowired
	private MailreciverDao mdao;
	@Autowired
	private NotepaperDao ndao;
	@RequestMapping("userlogmanage")
	public String userlogmanage() {
		return "user/userlogmanage";
	}
	
	@RequestMapping("usermanage")
	public String usermanage(Model model,@RequestParam(value="page",defaultValue="0") int page,

							 @RequestParam(value="size",defaultValue="10") int size
							 ) {
		Sort sort=new Sort(new Order(Direction.ASC,"dept"));
		Pageable pa=new PageRequest(page, size,sort);
		Page<User> userspage = udao.findByIsLock(0, pa);
		List<User> users=userspage.getContent();
		model.addAttribute("isOnJob","0");
		model.addAttribute("users",users);
		model.addAttribute("page", userspage);
		model.addAttribute("url", "usermanagepaging");
		return "user/usermanage";
	}
	
	@RequestMapping("usermanagepaging")
	public String userPaging(Model model,@RequestParam(value="page",defaultValue="0") int page,
							 @RequestParam(value="size",defaultValue="10") int size,
							 @RequestParam(value="isOnJob",defaultValue= "0" ) String isOnJob,
							 @RequestParam(value="usersearch",required=false) String usersearch
			){
		Sort sort=new Sort(new Order(Direction.ASC,"dept"));
		Pageable pa=new PageRequest(page, size,sort);
		Page<User> userspage = null;
		int isJob = Integer.parseInt(isOnJob);
		if(StringUtil.isEmpty(usersearch)){
			userspage =  udao.findByIsLock(isJob, pa);
		}else{
			userspage = udao.findByIsLockAndSearch(isJob , usersearch ,pa);
		}
		List<User> users=userspage.getContent();
		model.addAttribute("usersearch",usersearch);
		model.addAttribute("isOnJob",isOnJob);
		model.addAttribute("users",users);
		model.addAttribute("page", userspage);
		model.addAttribute("url", "usermanagepaging");
		
		return "user/usermanagepaging";
	}
	
	
	@RequestMapping(value="useredit",method = RequestMethod.GET)
	public String usereditget(@RequestParam(value = "userid",required=false) Long userid,Model model) {
		List<Position> positions = new ArrayList<>();
		if(userid!=null){
			User user = udao.findOne(userid);
			model.addAttribute("where","xg");
			model.addAttribute("user",user);
			positions = pdao.findByDeptidAndNameNotLike(user.getDept().getDeptId(),"%离职");
		}
		List<Dept> depts = (List<Dept>) ddao.findAll();


		List<Role> roles = (List<Role>) rdao.findAll();
		
		model.addAttribute("depts", depts);
		model.addAttribute("positions", positions);
		model.addAttribute("roles", roles);
		return "user/edituser";
	}
	
	@RequestMapping(value="useredit",method = RequestMethod.POST)
	public String usereditpost(User user,
			@RequestParam("deptid") Long deptid,
			@RequestParam("positionid") Long positionid,
			@RequestParam("roleid") Long roleid,
			@RequestParam(value = "isbackpassword",required=false) boolean isbackpassword,
			Model model) throws PinyinException {
		System.out.println(user);
		System.out.println(deptid);
		System.out.println(positionid);
		System.out.println(roleid);
		Dept dept = ddao.findOne(deptid);
		Position position = pdao.findOne(positionid);
		Role role = rdao.findOne(roleid);
		if(user.getUserId()==null){
			/*String pinyin=PinyinHelper.convertToPinyinString(user.getUserName(), "", PinyinFormat.WITHOUT_TONE);
			user.setPinyin(pinyin);*/
			user.setPassword("123456");
			user.setDept(dept);
			user.setRole(role);
			user.setPosition(position);
			user.setFatherId(dept.getDeptmanager());
			udao.save(user);
		}else{
			User user2 = udao.findOne(user.getUserId());
			user2.setUserTel(user.getUserTel());
			user2.setRealName(user.getRealName());
			user2.setEamil(user.getEamil());
			user2.setAddress(user.getAddress());
			user2.setUserEdu(user.getUserEdu());
			user2.setSchool(user.getSchool());
			user2.setIdCard(user.getIdCard());
			user2.setSex(user.getSex());
			user2.setBank(user.getBank());
			user2.setCardId(user.getCardId());
			user2.setThemeSkin(user.getThemeSkin());
			user2.setSalary(user.getSalary());
			user2.setFatherId(dept.getDeptmanager());
			user2.setFenji(user.getFenji());
			user2.setZhixian(user.getZhixian());
			user2.setPinyin(user.getPinyin());
			if(isbackpassword){
				user2.setPassword("123456");
			}
			user2.setDept(dept);
			user2.setRole(role);
			user2.setPosition(position);
			user2.setIsLock(user.getIsLock());

			if(user.getIsLock() == 1){
				Dept d = ddao.findByDeptName("离职人员");
				user2.setDept(d);
				List<Position> p = pdao.findByPosition("已离职",22L);
				user2.setPosition(p.get(0));
				user2.setIsLock(1);
				Role r = rdao.findOne(62L);
				user2.setRole(r);
			}
			udao.save(user2);
		}
		
		model.addAttribute("success",1);
		return "/usermanage";
	}
	
	
	@RequestMapping("deleteuser")
	public String deleteuser(@RequestParam("userid") Long userid,Model model){
		User user = udao.findOne(userid);
		
		user.setIsLock(1);
		
		udao.save(user);
		
		model.addAttribute("success",1);
		return "/usermanage";
		
	}
	
	@RequestMapping("useronlyname")
    public @ResponseBody boolean useronlyname(@RequestParam("username") String username){
		System.out.println(username);
		User user = udao.findByUserName(username);
		System.out.println(user);
		if(user==null){
			return true;
		}
		return false;
    }
	
	@RequestMapping("selectdept")
	public @ResponseBody List<Position> selectdept(@RequestParam("selectdeptid") Long deptid){
		
		//return pdao.findByDeptidAndNameNotLike(deptid, "%离职");
		return pdao.findByDeptid(deptid);
	}

	

}
