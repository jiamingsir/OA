package cn.gson.oasys.controller.atdRecord;

import cn.gson.oasys.model.dao.atdrecorddao.VacationDao;
import cn.gson.oasys.model.entity.atdrecord.Vacation;
import cn.gson.oasys.model.entity.attendce.Attends;
import cn.gson.oasys.model.entity.discuss.Discuss;
import cn.gson.oasys.model.entity.role.Role;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Position;
import cn.gson.oasys.model.entity.user.User;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/")
public class VacationController {

    @Autowired
    VacationDao vDao;


    @RequestMapping(value="vacationList")
    public String vacationList(@RequestParam(value="page",defaultValue="0") int page, Model model, HttpSession session){

        Pageable pa = new PageRequest(page, 10);

        Page<Vacation> vacationList = vDao.findAll(pa);

        session.removeAttribute("returnUrl");
        session.setAttribute("returnUrl", "vacationtable");
        model.addAttribute("vacationList",vacationList.getContent());
        model.addAttribute("page", vacationList);
        model.addAttribute("url","vacationtable");
        return "atdrecord/vacationmanage";
    }

    @RequestMapping(value="vacationtable")
    public String vacationtable(@RequestParam(value="page",defaultValue="0") int page, Model model, HttpSession session){

        Pageable pa = new PageRequest(page, 10);

        Page<Vacation> vacationList = vDao.findAll(pa);

        session.removeAttribute("returnUrl");
        session.setAttribute("returnUrl", "vacationtable");
        model.addAttribute("vacationList",vacationList.getContent());
        model.addAttribute("page", vacationList);
        model.addAttribute("url","vacationtable");
        return "atdrecord/vacationtable";
    }

    @RequestMapping(value="vacationedit",method = RequestMethod.GET)
    public String vacationedit(@Param("id") String id, Model model, HttpServletRequest request, HttpSession session) {


        if (id == null) {

        } else{
            Integer idd = Integer.parseInt(id);
            Vacation vacation = vDao.findById(idd);
            model.addAttribute("vacation", vacation);
        }

        return "atdRecord/vacationedit";
    }


    @RequestMapping(value="vacationedit",method = RequestMethod.POST)
    public String vacationedit(Vacation vacation,Model model) throws Exception {
        vDao.save(vacation);
       /* if(vacation.getId()==null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Date begindate = sdf.parse(vacation.getBegindate());

            //vacation.setBegindate();
            vDao.save(vacation);
        }else{
            Vacation vacation2 = vDao.findOne(Long.valueOf(vacation.getId()));
            vacation2.setBegindate(vacation.getBegindate());
            vacation2.setDescript(vacation.getDescript());
            vacation2.setEnddate(vacation.getEnddate());
            vacation2.setName(vacation.getName());
            vacation2.setType(vacation.getType());
            vDao.save(vacation2);
        }*/
        model.addAttribute("success",1);
        return "/vacationList";
    }



    @RequestMapping("deletevacation")
    public String deleteuser(@RequestParam("id") Integer id,Model model){
        Vacation v = vDao.findById(id);
        vDao.delete(v);
        model.addAttribute("success",1);
        return "vacationList";

    }




}
