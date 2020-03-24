package cn.gson.oasys.controller.user;

import cn.gson.oasys.model.dao.atdrecorddao.VacationDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.HolidaysetDao;
import cn.gson.oasys.model.dao.user.PositionDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.atdrecord.Vacation;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Holidayset;
import cn.gson.oasys.model.entity.user.Position;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.process.ProcessService;
import org.apache.ibatis.annotations.Param;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class HolidaysetController {

    /*@Autowired
    VacationDao vDao;*/
    @Autowired
    HolidaysetDao holidaysetDao;
    @Autowired
    UserDao userDao;
    @Autowired
    DeptDao deptDao;
    @Autowired
    PositionDao positionDao;
    @Autowired
    private ProcessService proservice;



    @RequestMapping(value="holidaysetList")
    public String holidaysetList(@RequestParam(value="page",defaultValue="0") int page,
                                 @RequestParam(value="baseKey",required=false) String baseKey,Model model, HttpSession session){

        Pageable pa = new PageRequest(page, 10);


        Page<Holidayset> depts;
        if(baseKey == null){
            depts =  holidaysetDao.findAll(pa);
        }else{
            depts =   holidaysetDao.findnamelike(baseKey,pa);
        }

        session.removeAttribute("returnUrl");
        session.setAttribute("returnUrl", "holidaysettable");
        model.addAttribute("holidaysetList",depts.getContent());
        model.addAttribute("page", depts);//holidaysetList);
        model.addAttribute("url", "holidaysettable");
        return "user/holidaysetmanage";
    }

    @RequestMapping(value="holidaysettable")
    public String holidaysettable(@RequestParam(value="page",defaultValue="0") int page,
                                  @RequestParam(value="baseKey",required=false) String baseKey,Model model, HttpSession session){

        Pageable pa = new PageRequest(page, 10);
        List<Sort.Order> orders=new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "deptName"));
        Sort sort=new Sort(orders);

        //Pageable pa=new PageRequest(page, size,sort);

        Page<Holidayset> depts;
        if(baseKey == null){
            depts =  holidaysetDao.findAll(pa);
        }else{
            depts =   holidaysetDao.findnamelike(baseKey,pa);
        }
        session.removeAttribute("returnUrl");
        session.setAttribute("returnUrl", "holidaysettable");
        model.addAttribute("holidaysetList",depts.getContent());
        model.addAttribute("page", depts);
        model.addAttribute("url", "holidaysettable");
        return "user/holidaysettable";
    }

    @RequestMapping(value="holidaysetedit",method = RequestMethod.GET)
    public String holidaysetedit(@Param("id") String id, Model model, HttpServletRequest request,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size,HttpSession session) {
        //List<User> userList = userDao.findAll();
        Pageable pa=new PageRequest(page, size);

        //查看用户并分页
        Page<User> pageuser=userDao.findAll(pa);
        List<User> userlist=pageuser.getContent();
        model.addAttribute("emplist", userlist);
        // 查询部门表
        Iterable<Dept> deptlist = deptDao.findAll();
        // 查职位表
        Iterable<Position> poslist = positionDao.findAll();
        //List<Dept> deptList = deptDao.findALLInName();
        model.addAttribute("deptlist", deptlist);
        //List<Position> positionList = positionDao.findByAll();
        model.addAttribute("poslist", poslist);
        model.addAttribute("page", pageuser);
        model.addAttribute("url", "names");


        //proservice.index6(model, userId, page, size);

        if (id == null) {
            model.addAttribute("insert", true);
        } else{
            model.addAttribute("insert", false);
            Integer idd = Integer.parseInt(id);
            Holidayset holidayset = holidaysetDao.findById(idd);
            model.addAttribute("holidayset", holidayset);

        }

        return "user/holidaysetedit";
    }


    @RequestMapping(value="holidaysetedit",method = RequestMethod.POST)
    public String holidaysetedit(@Valid Holidayset holidayset, BindingResult br,
                                 @RequestParam(value = "username",defaultValue = "")String username, Model model) throws Exception {
        List<User> userList = userDao.findByUserNameZ(username);
        holidayset.setUserid(userList.get(0).getUserId());
        if (holidayset.getAyudays()!=null && holidayset.getAcomdays()!=null && holidayset.getByudays()!=null && holidayset.getBcomdays()!=null){
            if(holidayset.getId()==0){
                List<Holidayset> holidaysetList = holidaysetDao.findByUserid(holidayset.getUserid());
                if (holidaysetList.size()>0){
                    model.addAttribute("error", "已存在此员工假期天数记录！");
                    return "common/proce";
                }else {
                    holidaysetDao.save(holidayset);
                }
            }else {
                holidaysetDao.save(holidayset);
            }
        }else {
            model.addAttribute("error", "请输入正确的天数！");
            return "common/proce";
        }


        model.addAttribute("success",1);
        return "/holidaysetList";
    }



    @RequestMapping("deleteholidayset")
    public String deleteuser(@RequestParam("id") Integer id,Model model){
        Holidayset v = holidaysetDao.findById(id);
        holidaysetDao.delete(v);
        model.addAttribute("success",1);
        return "holidaysetList";

    }




}
