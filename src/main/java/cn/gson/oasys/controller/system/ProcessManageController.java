package cn.gson.oasys.controller.system;

import cn.gson.oasys.common.formValid.BindingResultVOUtil;
import cn.gson.oasys.common.formValid.MapToList;
import cn.gson.oasys.common.formValid.ResultEnum;
import cn.gson.oasys.common.formValid.ResultVO;
import cn.gson.oasys.model.dao.IndexDao;
import cn.gson.oasys.model.dao.roledao.RoleDao;
import cn.gson.oasys.model.dao.roledao.RolepowerlistDao;
import cn.gson.oasys.model.dao.system.ProcessManageDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.atdrecord.Vacation;
import cn.gson.oasys.model.entity.attendce.Attends;
import cn.gson.oasys.model.entity.role.Role;
import cn.gson.oasys.model.entity.role.Rolepowerlist;
import cn.gson.oasys.model.entity.system.ProcessManage;
import cn.gson.oasys.model.entity.system.SystemMenu;
import cn.gson.oasys.model.entity.system.SystemStatusList;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Position;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.role.RoleService;
import cn.gson.oasys.services.system.MenuSysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProcessManageController {

	@Autowired
	private ProcessManageDao pDao;

	@RequestMapping("processManageList")
	public String ProcessManageList(Model model,
									@RequestParam(value="page",defaultValue="0") int page,
									@RequestParam(value="size",defaultValue="10") int size) {
		Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"processnum"));
		Pageable pa=new PageRequest(page, size,sort);
		Page<ProcessManage> processManagePage = pDao.findAll(pa);
		List<ProcessManage> processManageList=processManagePage.getContent();
		model.addAttribute("processManageList",processManageList);


		return "systemcontrol/processManageList";
	}

	@RequestMapping(value = "processListEdit" , method = RequestMethod.GET)
	public String processListEdit(@RequestParam(value = "id",required=false) Integer id,Model model) {
		List<Position> positions = new ArrayList<>();
		ProcessManage pm = new ProcessManage();
		if(id!=null){
			Long ID = Long.valueOf(id);
			 pm = pDao.findOne(ID);
		}
		model.addAttribute("ProcessManage",pm);
		return "systemcontrol/processManageEdit";
	}

	@RequestMapping(value = "processListEdit" , method = RequestMethod.POST)
	public String processListEdit2(ProcessManage processManage , Model model){

		pDao.save(processManage);

		model.addAttribute("success", 1);
		return "processManageList";
	}


	@RequestMapping(value = "deleteProcessList" )
	public String deleteProcessList(@RequestParam("id") Integer id,Model model){
		ProcessManage pm = pDao.findById(id);
		pDao.delete(pm);
		model.addAttribute("success",1);
		return "processManageList";

	}



}
