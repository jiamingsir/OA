package cn.gson.oasys.controller.role;

import cn.gson.oasys.model.dao.roledao.RoleDao;
import cn.gson.oasys.model.dao.roledao.RoleProcessDao;
import cn.gson.oasys.model.dao.roledao.RoleTierDao;
import cn.gson.oasys.model.dao.roledao.RoleUserProcessDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.PositionDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.role.*;
import cn.gson.oasys.model.entity.system.SystemMenu;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Position;
import cn.gson.oasys.model.entity.user.User;
import com.github.pagehelper.util.StringUtil;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class RoleProcessController {


    @Autowired
    UserDao udao;
    @Autowired
    DeptDao ddao;
    @Autowired
    PositionDao pdao;
    @Autowired
    RoleDao rdao;
    @Autowired
    RoleUserProcessDao rupdao;
    @Autowired
    RoleProcessDao rpdao;
    @Autowired
    RoleTierDao roleTierDao;


    /**
     * 流程角色管理
     * @return
     */
    @RequestMapping("roleprocessmanage")
    public ModelAndView index(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pa=new PageRequest(page, size);
        ModelAndView mav=new ModelAndView("role/roleprocessmanage");
        Page<RoleProcess> pagerole=rpdao.findAll(pa);
        List<RoleProcess>  rolelist=pagerole.getContent();
        mav.addObject("page", pagerole);
        mav.addObject("rolelist", rolelist);
        mav.addObject("url", "roleser2");
        return mav;
    }



    /**
     * 条件查询
     */
    @RequestMapping("roleser2")
    public String roleser(HttpServletRequest req, Model model,
                          @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pa=new PageRequest(page, size);
        Page<RoleProcess> pagerole=null;
        List<RoleProcess>  rolelist=null;
        String val=null;


        if(!StringUtil.isEmpty(req.getParameter("val"))){
            val=req.getParameter("val").trim();
        }

        if(!StringUtil.isEmpty(val)){
            pagerole=rpdao.findbyrolename(val,pa);
            model.addAttribute("sort", "&val="+val);
        }else{
            pagerole=rpdao.findAll(pa);
        }
        rolelist=pagerole.getContent();
        model.addAttribute("page", pagerole);
        model.addAttribute("rolelist", rolelist);
        model.addAttribute("url", "roleser2");
        return "role/roleprocesstable";
    }

    /**
     * 进入新增角色
     * @return
     */
    @RequestMapping("addroleprocess")
    public String index3(HttpServletRequest req,Model model){
        String id=null;
        RoleProcess roleprocess=new RoleProcess();

        if(!StringUtil.isEmpty(req.getParameter("id"))){
            id=req.getParameter("id");
            Long lid=Long.parseLong(id);
            roleprocess=rpdao.findOne(lid);

        }
        model.addAttribute("role", roleprocess);
        return "role/addroleprocess";
    }

    /**
     * 新增，修改角色确定
     * @return
     */
    @RequestMapping("modifyroleprocess")
    public String index4(HttpServletRequest req, @Valid RoleProcess roleprocess, BindingResult br,Model model){
        String id=null;
        if(!StringUtil.isEmpty(req.getParameter("id"))){
            id=req.getParameter("id");
        }
        if(!StringUtil.isEmpty(id)){
            Long lid=Long.parseLong(id);
            RoleProcess roles=rpdao.findOne(lid);
            roles.setProRoleName(roleprocess.getProRoleName());
            rpdao.save(roles);

        }else{
            List<RoleProcess> roleProcesses = rpdao.findByrolename(roleprocess.getProRoleName());
            if(roleProcesses.size()>0){
                model.addAttribute("error","角色已存在");
                return "common/proce";
            }else {
                RoleProcess rolep = rpdao.save(roleprocess);
            }

        }
        return "redirect:/roleprocessmanage";
    }
    /**
     * 删除
     */
    @Transactional
    @RequestMapping("rpshan")
    public String index5(HttpServletRequest req,Model model,HttpSession session){
        //String userId = ((String) session.getAttribute("userId")).trim();
        String userId = String.valueOf(session.getAttribute("userId")).trim();
        Long userid = Long.parseLong(userId);
        User user=udao.findOne(userid);
        String id=null;
        if(!StringUtil.isEmpty(req.getParameter("id"))){
            id=req.getParameter("id");

        }
        Long lid=Long.parseLong(id);
        if(user.getSuperman().equals(true)){
            /*List<User> useist=udao.findrole(lid);
            if(useist.size()>0){
                model.addAttribute("error", "此角色下还有职员，不允许删除。");
                return "common/proce";
            }else{
                Role r=rdao.findOne(lid);
                Long roleid=r.getRoleId();
                List<Rolepowerlist> rolepowerlist1=rpdao.findroadids(lid);
                if (rolepowerlist1.size()>0){
                    model.addAttribute("error", "此角色下还有相关权限模块，不允许删除。");
                    return "common/proce";
                }else {
                    rpdao.delroadids(lid);
                    rdao.delete(r);
                }*/
            //根据角色id员工角色是否占用

            List<RoleUserProcess> ruplist = rupdao.findByRoleupId(lid);
            if(ruplist.size()>0){
                model.addAttribute("error", "此角色下还有职员，不允许删除。");
                return "common/proce";
            }else{
                RoleProcess r=rpdao.findOne(lid);
                rpdao.delete(r);
            }
        }else{
            model.addAttribute("error", "只有超级管理员才能操作。");
            return "common/proce";
        }
        return "roleprocessmanage";

    }




    /*
    * 员工流程角色
    *
    * */

    @RequestMapping("userrole")
    public String userrole(Model model, @RequestParam(value="page",defaultValue="0") int page,

                             @RequestParam(value="size",defaultValue="10") int size
    ) {
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"dept"));
        Pageable pa=new PageRequest(page, size,sort);
        Page<User> userspage = udao.findByIsLock(0, pa);
        List<User> users=userspage.getContent();
        model.addAttribute("isOnJob","0");
        model.addAttribute("users",users);
        model.addAttribute("page", userspage);
        model.addAttribute("url", "userroletable");
        return "role/userrole";
    }

    @RequestMapping("userroletable")
    public String userroletable(Model model,@RequestParam(value="page",defaultValue="0") int page,
                             @RequestParam(value="size",defaultValue="10") int size,
                             @RequestParam(value="isOnJob",defaultValue= "0" ) String isOnJob,
                             @RequestParam(value="usersearch",required=false) String usersearch
    ){
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"dept"));
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
        model.addAttribute("url", "userroletable");

        return "role/userroletable";
    }

    @RequestMapping(value="userroleedit",method = RequestMethod.GET)
    public String usereditroleget(@RequestParam(value = "userid",required=false) Long userid,Model model) {
        List<Position> positions = new ArrayList<>();
        if(userid!=null){
            User user = udao.findOne(userid);
            model.addAttribute("where","xg");
            model.addAttribute("user",user);
            positions = pdao.findByDeptidAndNameNotLike(user.getDept().getDeptId(),"%离职");
        }
        List<Dept> depts = (List<Dept>) ddao.findAll();

        User user =udao.findOne(userid);
        List<Role> roles = (List<Role>) rdao.findAll();

        List<RoleUserProcess> roleups = rupdao.findbyroleuserproname(user.getUserName());
        List<RoleProcess> roleps = rpdao.findAll();
        for (int i=0;i<roleps.size();i++){
            Long rolepsid = roleps.get(i).getId();
            roleps.get(i).setProRoleValue(0);
            for (int j=0;j<roleups.size();j++){
                Long roleupsid = roleups.get(j).getRoleProcess().getId();
                if(rolepsid == roleupsid){
                    roleps.get(i).setProRoleValue(1);
                }
            }

        }


        model.addAttribute("depts", depts);
        model.addAttribute("positions", positions);
        model.addAttribute("roles", roleps);
        return "role/edituserrole";
    }

    @RequestMapping(value="userroleedit",method = RequestMethod.POST)
    public String usereditrolepost(HttpServletRequest req,User user,
                                   @RequestParam("id") List<Long> roleid,
                                   Model model) throws PinyinException {

        //List<Long> roleid = null;
                req.getAttribute("id");
        String username = user.getUserName();
        List<RoleUserProcess> roleUserProcessList = rupdao.findbyroleuserproname(username);

        for (int i =0;i<roleUserProcessList.size();i++){
            rupdao.delete(roleUserProcessList.get(i));
        }
        for (int i=0;i<roleid.size();i++){
            Long roleproid = roleid.get(i);
            RoleUserProcess roleUserProcess =new RoleUserProcess();
            RoleProcess roleProcess =rpdao.findOne(roleproid);
            roleUserProcess.setRoleProcess(roleProcess);
            roleUserProcess.setUserName(user.getUserName());
            rupdao.save(roleUserProcess);
        }


        model.addAttribute("success",1);
        return "/userrole";
    }

    /*
    * 层级设定
    *
    * */
    @RequestMapping(value="editroletier",method = RequestMethod.GET)
    public String editroletierget(@RequestParam(value = "id",required=false) Long roleid,Model model) {
        List<Position> positions = new ArrayList<>();
        if(roleid!=null){
            RoleProcess roleProcess = rpdao.findOne(roleid);
            model.addAttribute("where","xg");
            model.addAttribute("roleProcess",roleProcess);

        }


        List<RoleTier> roleTiers = roleTierDao.findbyRoleFatherid(roleid);
        //List<RoleUserProcess> roleups = rpdao.findbyroleuserproname(user.getUserName());
        List<RoleProcess> roleps = rpdao.findAll();
        for (int i=0;i<roleps.size();i++){
            Long rolepsid = roleps.get(i).getId();
            roleps.get(i).setProRoleValue(0);
            for (int j=0;j<roleTiers.size();j++){
                Long roleupsid = roleTiers.get(j).getRoleproid();
                if(rolepsid == roleupsid){
                    roleps.get(i).setProRoleValue(1);
                }
            }

        }




        model.addAttribute("roles", roleps);
        return "role/editroletier";
    }

    @RequestMapping(value="editroletier",method = RequestMethod.POST)
    public String editroletierpost(HttpServletRequest req,RoleProcess roleProcess,
                                   @RequestParam("ids") List<Long> roleid,
                                   Model model) throws PinyinException {

        //List<Long> roleid = null;
        req.getAttribute("id");
        Long rpid = roleProcess.getId();
        //List<RoleUserProcess> roleUserProcessList = rupdao.findbyroleuserproname(username);
        List<RoleTier> roleTiers = roleTierDao.findbyRoleFatherid(rpid);

        for (int i =0;i<roleTiers.size();i++){
            roleTierDao.delete(roleTiers.get(i));
        }
        for (int i=0;i<roleid.size();i++){
            Long roleproid = roleid.get(i);
            RoleUserProcess roleUserProcess =new RoleUserProcess();
            RoleTier roleTier = new RoleTier();

            //RoleProcess rProcess =rpdao.findOne(roleproid);
            roleTier.setRoleproid(roleproid);
            roleTier.setRoleprofatherid(rpid);
            roleTier.setRemark("");
            roleTierDao.save(roleTier);
        }


        model.addAttribute("success",1);
        return "/roleprocessmanage";
    }
}
