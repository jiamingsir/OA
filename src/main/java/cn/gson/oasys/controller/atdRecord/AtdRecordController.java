package cn.gson.oasys.controller.atdRecord;

import cn.gson.oasys.model.dao.atdrecorddao.AtdrecordDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import cn.gson.oasys.model.entity.schedule.ScheduleList;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
@RequestMapping("/")
public class AtdRecordController {

    String month_;


    @Autowired
    UserDao uDao;

    @Autowired
    AtdrecordDao atdDao;

    @RequestMapping("atdRecordList")
    public String AtdRecordList(HttpServletRequest request, Model model, HttpSession session,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "baseKey", required = false) String baseKey) {

        String month = request.getParameter("month");
        getAtdRecordData(request,model,session,page,month);






        return "atdRecord/atdrecordList";
    }

    @RequestMapping(value="atdRecordListtable",method= RequestMethod.GET)
    public String atdRecordListtable(HttpServletRequest request, Model model, HttpSession session,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "baseKey", required = false) String baseKey) {

        String month = request.getParameter("month");
        getAtdRecordData(request,model,session,page,month);


        return "atdrecord/atdRecordListtbale";
    }

    @RequestMapping(value = "atdRecordCalendar",method = RequestMethod.GET)
    public String atdRecordCalendar(@SessionAttribute("userId") Long userid, HttpServletResponse response) throws IOException {



        return "atdrecord/atdRecordCalendar";
    }

    @RequestMapping("atdCalendarload")
    public @ResponseBody List<Atdrecord> mycalendarload(@SessionAttribute("userId") Long userid, HttpServletResponse response) throws IOException, ParseException {

        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);
        List<Atdrecord> atdList = null;
        if(user.getCardId() != null) {
            String cardIdStr = uDao.findCardIdByUserId(user.getUserId());
            String[] cardIdArr = cardIdStr.split(",");
            List<String> cardIds = new ArrayList<String>();
            //List<String> 转 List<Integer>
            for (String str : cardIdArr) {
                cardIds.add(str);
            }

             atdList = atdDao.findByCardid(cardIds);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            //Rectime 为显示数值，补卡，请假等修改Rectime属性
            for(Atdrecord a : atdList){
                //falg = 1 为 补卡，Rectime >= 12:00算下午  Rectime < 12:00 算上午
                Long rectime1 = sdf.parse(a.getRectime()).getTime() +86400000L;        //+86,400,000放时区干扰
                Long ning = sdf.parse("12:00").getTime() + 86400000L;

                if(a.getFlag() == 1 && rectime1 < ning){
                    a.setRectime("补卡申请(上班)");
                }
                if(a.getFlag() == 1 &&  rectime1 >= ning){
                    a.setRectime("补卡申请(下班)");
                }
                if(a.getFlag() == 2 && rectime1 < ning){
                    a.setRectime("事假申请(上午)");
                }
                if(a.getFlag() == 2 &&  rectime1 >= ning){
                    a.setRectime("事假申请(下午)");
                }
                if(a.getFlag() == 3 && rectime1 < ning){
                    a.setRectime("年假申请(上午)");
                }
                if(a.getFlag() == 3 &&  rectime1 >= ning){
                    a.setRectime("年假申请(下午)");
                }
                if(a.getFlag() == 10 && rectime1 < ning){
                    a.setRectime(a.getRectime()+"(线上打卡)");
                }
                if(a.getFlag() == 10 &&  rectime1 >= ning){
                    a.setRectime(a.getRectime()+"(线上打卡)");
                }



            }



        }
        return atdList;
    }














    private void getAtdRecordData(HttpServletRequest request,
                                  Model model,
                                  HttpSession session,
                                  int page,
                                  String month){

        String endDate = "";
        String beginDate = "";
        Calendar c = Calendar.getInstance();



        if(month == null) {
            if(month_ != null) {
                month = month_;
            }else {
                month = "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1);
            }
            endDate = month +"-"+ c.get(Calendar.DAY_OF_MONTH);

        }else{
            String[] yearmonth = month.split("-");
             c.set(Integer.parseInt(yearmonth[0]), Integer.parseInt(yearmonth[1]), 0); //输入类型为int类型
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
             endDate = month + "-" + dayOfMonth;
            month_=month;

        }
        beginDate = month + "-01";

        Page<Atdrecord> atdPage = null;
        Pageable pa=new PageRequest(page, 20);
        List<Atdrecord> atdList = null;


        Long userId = (Long) session.getAttribute("userId");
        List<User> userList = uDao.findByUserId(userId);
        User user = userList.get(0);
        if(user.getCardId() != null){
            String cardIdStr = uDao.findCardIdByUserId(user.getUserId());
            String[] cardIdArr = cardIdStr.split(",");
            List<String> cardIds = new ArrayList<String>();
            //List<String> 转 List<Integer>
            for(String str : cardIdArr) {
                cardIds.add(str);
            }




            atdPage =  atdDao.findByCardno(cardIds,beginDate,endDate,pa);
            //atdList = atdDao.findByCardid(cardIds,beginDate,endDate);
             atdList =atdPage.getContent();
        }
        request.setAttribute("url", "atdRecordListtable");
        model.addAttribute("page",atdPage);

        //设置名称
        for(Atdrecord a : atdList){
            String cardno = a.getCardno();
             a.setCardno(user.getRealName());
        }
        model.addAttribute("atdList",atdList);

    }




}
