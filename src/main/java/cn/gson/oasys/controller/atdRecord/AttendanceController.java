package cn.gson.oasys.controller.atdRecord;

import cn.gson.oasys.Utils.DBUtils;
import cn.gson.oasys.Utils.DBUtils2;
import cn.gson.oasys.Utils.DateUtils;
import cn.gson.oasys.model.dao.atdrecorddao.AtdrecordDao;
import cn.gson.oasys.model.dao.atdrecorddao.AttendanceDao;
import cn.gson.oasys.model.dao.atdrecorddao.VacationDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import cn.gson.oasys.model.entity.atdrecord.Attendance;
import cn.gson.oasys.model.entity.atdrecord.Vacation;
import cn.gson.oasys.model.entity.tools.Monthday;
import cn.gson.oasys.model.entity.user.User;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;

@Controller
@RequestMapping("/")
public class AttendanceController {


    @Autowired
    UserDao uDao;

    @Autowired
    AtdrecordDao atdDao;

    @Autowired
    AttendanceDao attDao;

    @Autowired
    VacationDao vDao;


    @RequestMapping("attendanceList")
    public String attendanceList(HttpServletRequest request, Model model, HttpSession session,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "baseKey", required = false) String baseKey) throws ParseException {
        //得到AttendanceList 显示
        List<Attendance> list =  attDao.findAll();
        List<Monthday> monthday = new ArrayList<Monthday>();
        String year = "";
        String month = "";
        //创建表格第二，三行
        if(list.size() != 0) {
           String beginDate = list.get(0).getBegindate();
           String endDate = list.get(0).getEnddate();
           monthday = converttolist(beginDate,endDate);
           year = monthday.get(0).getYaer();
            month = monthday.get(0).getMonth();
        }
        //创建表格第一行行合并数量

        int colspan = 3+monthday.size()*2+8;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String startmonth = sdf.format(date.getTime()-2592000000L);
        String endmonth = sdf.format(date);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        String choosemonth = sdf2.format(date);

        model.addAttribute("choosemonth",choosemonth);
        model.addAttribute("startmonth",startmonth);
        model.addAttribute("endmonth",endmonth);

        model.addAttribute("attendanceList",list);
        model.addAttribute("monthday",monthday);
        model.addAttribute("colspan",colspan);
        model.addAttribute("year",year);
        model.addAttribute("month",month);



        return "atdRecord/attendanceList";

    }




    //同步考勤！
    @RequestMapping("synchronizeAttendance")
    public String synchronizeAttendance(HttpServletRequest request, Model model, HttpSession session,
                                @RequestParam(value = "chooseDate", defaultValue = "0") String chooseDate,
                                @RequestParam(value = "baseKey", required = false) String baseKey) {
        Connection conn = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps = null;
        try {


            conn = DBUtils2.getConn();
            Statement stat = conn.createStatement();

            //得到月开始结束日期
            String endTime = "";
            String beginTime = "";

            Calendar c = Calendar.getInstance();
            String[] yearmonth = chooseDate.split("-");
            c.set(Integer.parseInt(yearmonth[0]), Integer.parseInt(yearmonth[1]), 0); //输入类型为int类型
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            endTime = chooseDate + "-" + dayOfMonth;
            beginTime = chooseDate + "-01";


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            //清空原有表
            String deletesql = "delete from aoa_attendance";
            ps = conn.prepareStatement(deletesql);
            ps.executeUpdate();


            //更新新表
            Map<String, List<Atdrecord>> atdMap = new HashMap<String, List<Atdrecord>>();
            List<String> cardnoList = atdDao.findCardnoByRecdate(beginTime, endTime);
            //将打卡信息放进Map
            for (int i = 0; i < cardnoList.size(); i++) {

                String cardno = cardnoList.get(i);
                List<Atdrecord> list = atdDao.findByRecdateAndCardnoGroupbyDay(beginTime, endTime, cardno);

                atdMap.put(list.get(0).getCardno(), list);
            }


            int id = 1;
            //遍历Map，将数据存到数据库
            for (Map.Entry<String, List<Atdrecord>> entry : atdMap.entrySet()) {
                int latetime = 0;//迟到时间
                int latetimes = 0;//迟到次数
                int nowork = 0;//旷工
                StringBuilder sb = new StringBuilder("insert into aoa_attendance(id,cardno,name,dept," +
                        "day1am,day1pm,day2am,day2pm,day3am,day3pm,day4am,day4pm,day5am,day5pm,day6am,day6pm,day7am,day7pm,day8am,day8pm,day9am,day9pm,day10am,day10pm," +
                        "day11am,day11pm,day12am,day12pm,day13am,day13pm,day14am,day14pm,day15am,day15pm,day16am,day16pm,day17am,day17pm,day18am,day18pm,day19am,day19pm,day20am,day20pm," +
                        "day21am,day21pm,day22am,day22pm,day23am,day23pm,day24am,day24pm,day25am,day25pm,day26am,day26pm,day27am,day27pm,day28am,day28pm,day29am,day29pm,day30am,day30pm," +
                        "day31am,day31pm,latetimes,latetime,aplogy,absenteeism,trouble,sick,year,special,begindate,enddate) values ( ");
                /* StringBuilder sb = new StringBuilder("insert into aoa_attendance");*/
                //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());

                Attendance attendance = new Attendance();
                List<Atdrecord> atdList = entry.getValue();
                //remark临时字段。记录这一条数据是否已使用
               /* for(Atdrecord a : atdList){
                    a.setRemark("未使用");
                }*/
                Integer[] intArr = new Integer[62];

                attendance.setName(entry.getKey());
                attendance.setDept("哈哈哈哈哈哈啊");
                /*  sb.append(id++ +",");*/
                //id，name，dept


                sb.append(id++ +",");
                String cardno = entry.getKey().trim();
                String username = "";
                String dept = "";
                User u = uDao.findycardId(cardno);
                if(u != null){
                    //如果没有补过卡，只有一个卡号
                    dept = u.getDept().getDeptName();
                    username = u.getRealName();
                }else{
                    dept = "-";
                    username = "请核对卡号：" + cardno;
                }
                sb.append("'" + cardno + "',");
                sb.append("'" + username + "',");
                sb.append("'" + dept + "',");

                for (int i = 1; i <= 31; i++) {
                    String stateam = "";
                    String statepm = "";
                    String day = i <= 9?"0" + i : ""+i;
                    String recdate = chooseDate + "-" + day;

                    for (int j = 0; j < atdList.size(); j++) {
                        //首先判断是否为上班，如果不上班则添加 -
                        if (isWeekend(sdf.parse(recdate),"09:00") == 1) {
                            stateam = "-";
                        }
                        if (isWeekend(sdf.parse(recdate),"17:00") == 1) {
                            statepm = "-";
                        }


                        String entryDate = atdList.get(j).getRecdate();
                        //循环的时间和list的时间相同时，放进对象中。
                        String morning = atdList.get(j).getMorning();
                        String evening = atdList.get(j).getEvening();

                        Integer type = atdList.get(j).getFlag();

                        if (entryDate.equals(recdate)) {
                            //判断是否是上班日
                            //if (isWeekend(sdf.parse(recdate)) == 1) {
                                //如果morning在0:00 - 12:00之间，则进行后续判断
                                if (compareTime(morning, "00:00", "12:00") &&  !"-".equals(stateam)) {
                                    //判断是否迟到
                                    if (compareLate(morning, "09:00", "12:00")) {
                                        stateam = "√";
                                        //else为迟到
                                    } else {
                                        stateam = morning;
                                        if(atdDao.findNumByFlagAndRecdateAndRectime(recdate,morning,cardno,0) == 0) {
                                            latetimes++;
                                            latetime = latetime + (int)DateUtils.HHmm(recdate+" 09:00",recdate + " " +morning);

                                        }
                                    }
                                }if (compareTime(evening, "12:00", "23:59") && !"-".equals(statepm)) {
                                    //判断是否早退
                                    if (compareLate(evening, "17:00", "23:59")) {
                                        statepm = "√";
                                        //else为迟到
                                    } else {
                                        statepm = evening;
                                    }
                                }

                                    // }
                                //}

                            }

                        if (i > dayOfMonth) {
                            stateam = "-1";
                            statepm = "-1";
                        }
                    }
                    sb.append("'" + stateam + "','" + statepm + "',");
                }



                //插入请假事由等
                sb.append( + latetimes + "," + latetime + ","+nowork+",0,0,0,0,0,");
                //插入日期开始结束时间
                sb.append("'" + beginTime + "','" + endTime+"')");

                String sql = sb.toString();
                ps2 = conn.prepareStatement(sql);
                ps2.executeUpdate();
               // System.out.println("插入成功！");

              /*  attDao.aaa(sql);*/











            }

            String recdate;
            String rectime;
            String ning;
            String show="";

               //在数据库中查找补卡，请假等。
            List<String> cardIdList = atdDao.findCardnoByRecdate(beginTime,endTime);
            for(String cardno : cardIdList){
                double buka = 0;
                double shijia = 0;
                double nianjia = 0;
                double tebijia = 0;
                double zongcaibanqingjia = 0;

                List<Atdrecord> bukaqingjia = atdDao.findAtdrecordByFlagIsNotZeroTen(beginTime,endTime,cardno.trim());
                for(Atdrecord atd : bukaqingjia){
                    String morning = atd.getMorning();
                    String evening = atd.getEvening();
                    int flag = atd.getFlag();
                    recdate = atd.getRecdate();
                    rectime = atd.getRectime();

                    Date rec = sdf.parse(recdate);
                    ning = "";
                    int day = Integer.parseInt(recdate.split("-")[2]);
                    if(compareTime(morning, "00:00", "12:00")) {
                        ning = "AM";
                        if (isWeekend(rec , "09:00") == 1) {
                            show = "-";
                        }else {
                            if (flag == 1) {
                                show = "补卡";
                                buka += 0.5;
                            } else if (flag == 2) {
                                show = "事假";
                                shijia += 0.5;
                            } else if (flag == 3) {
                                show = "年假";
                                nianjia += 0.5;
                            } else if (flag == 4) {
                                show = "特别假";
                                tebijia += 0.5;
                            } else if (flag == 5) {
                                show = "总裁办请假";
                                zongcaibanqingjia += 0.5;
                            }

                        }
                        String sql2 = "update aoa_attendance set day" + day + ning + " = '"+ show + "' where cardno = '" + atd.getCardno() + "'";
                        ps2 = conn.prepareStatement(sql2);
                        ps2.executeUpdate();
                    }
                    if(compareTime(evening, "12:00", "23:59")) {
                        ning="PM";
                        if (isWeekend(rec , "17:00") == 1) {
                            show = "-";
                        }else {
                            if (flag == 1) {
                                show = "补卡";
                                buka += 0.5;
                            } else if (flag == 2) {
                                show = "事假";
                                shijia += 0.5;
                            } else if (flag == 3) {
                                show = "年假";
                                nianjia += 0.5;
                            } else if (flag == 4) {
                                show = "特别假";
                                tebijia += 0.5;
                            } /*else if (flag == 5) {
                                show = "总裁办请假";
                                zongcaibanqingjia += 0.5;
                            }*/

                        }
                        String sql2 = "update aoa_attendance set day" + day + ning + " = '"+ show + "' where cardno = '" + atd.getCardno() + "'";
                        ps2 = conn.prepareStatement(sql2);
                        ps2.executeUpdate();
                    }








                }
                String sql3 = "update aoa_attendance set  trouble = " + shijia + " , year = " + nianjia +" , special = " + tebijia + " where cardno = '" + cardno + "'";

                ps2 = conn.prepareStatement(sql3);
                ps2.executeUpdate();


                List<User> users = uDao.findByIsLock2(0);
                List<Attendance> attList = attDao.findAll();
                String day29 = 29 > dayOfMonth ? "-1" : "";
                String day30 = 30 > dayOfMonth ? "-1" : "";
                String day31 = 31 > dayOfMonth ? "-1" : "";
                for(User u : users){
                    boolean flag = false;
                    String realName = u.getRealName();
                    for(Attendance att : attList){
                        if(att.getName().equals(u.getRealName())){
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        sql3 ="insert into aoa_attendance(id,cardno,name,dept,day29am,day29pm,day30am,day30pm,day31am,day31pm , begindate , enddate)" +
                                "values ( " + id++ +",null,'"+ u.getRealName() + "','" + u.getDept().getDeptName() + "','" + day29 + "','" + day29 +"','" +day30 +"','"  +day30 +"','" + day31 +"','" + day31 +"','"  +beginTime +"','" + endTime + "')";
                        ps2 = conn.prepareStatement(sql3);
                        ps2.executeUpdate();
                    }
                }




            }


            ps2.close();
            ps.close();

            model.addAttribute("message","同步成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtils2.closeConn(conn);
        }

        return "forward:/attendanceList";
    }
    //导出Excel表格
    @RequestMapping("exportAttendance")
    public  byte[] ExportAttendance(HttpServletRequest request, HttpServletResponse response)  {
        FileOutputStream out = null;
        try {
            List<Attendance> list = attDao.findAll();
            List<Monthday> monthday = new ArrayList<Monthday>();
            String year = "";
            String month = "";
            //创建表格第二，三行
            if (list.size() != 0) {
                String beginDate = list.get(0).getBegindate();
                String endDate = list.get(0).getEnddate();
                monthday = converttolist(beginDate, endDate);
                year = monthday.get(0).getYaer();
                month = monthday.get(0).getMonth();


                //创建表格第一行行合并数量

                int colspan = 3 + monthday.size() * 2 + 8;


                HSSFWorkbook workbook = new HSSFWorkbook();     //创建excel表
                HSSFSheet sheet = workbook.createSheet();       //创建sheet表
                //创建第一行
                HSSFRow row = sheet.createRow(0);
                row.createCell(0).setCellValue("上海新云传媒股份有限公司" + year + "年" + month + "月员工日常考勤汇总");
                CellRangeAddress region = new CellRangeAddress(0, 0, 0, colspan);       //合并单元格
                sheet.addMergedRegion(region);
                //创建第二行
                HSSFRow row2 = sheet.createRow(1);
                row2.createCell(0).setCellValue("序号");
                CellRangeAddress region2 = new CellRangeAddress(1, 2, 0, 0);       //合并单元格
                sheet.addMergedRegion(region2);
                row2.createCell(1).setCellValue("姓名");
                CellRangeAddress region3 = new CellRangeAddress(1, 2, 1, 1);       //合并单元格
                sheet.addMergedRegion(region3);
                row2.createCell(2).setCellValue("部门");
                CellRangeAddress region4 = new CellRangeAddress(1, 2, 2, 2);       //合并单元格
                sheet.addMergedRegion(region4);
                for(int i = 0 ; i < monthday.size() ; i++){
                    row2.createCell((i*2)+3).setCellValue(monthday.get(i).getDay());
                    CellRangeAddress region5 = new CellRangeAddress(1, 1,(i*2)+3 , (i*2)+4);       //合并单元格
                    sheet.addMergedRegion(region5);
                }
                row2.createCell(monthday.size()*2+3).setCellValue("合计");
                CellRangeAddress region5 = new CellRangeAddress(1, 1, monthday.size()*2+3, monthday.size()*2+10);       //合并单元格
                sheet.addMergedRegion(region5);
                //创建第三行
                HSSFRow row3 = sheet.createRow(2);
                for(int i = 0 ; i < monthday.size() ; i++) {
                    row3.createCell((i*2)+3).setCellValue("AM");
                    row3.createCell((i*2)+4).setCellValue("PM");
                }
                row3.createCell(monthday.size()*2 + 3).setCellValue("迟到次数");
                row3.createCell(monthday.size()*2 + 4).setCellValue("迟到（分）");
                row3.createCell(monthday.size()*2 + 5).setCellValue("致歉信");
                row3.createCell(monthday.size()*2 + 6).setCellValue("旷工");
                row3.createCell(monthday.size()*2 + 7).setCellValue("事假");
                row3.createCell(monthday.size()*2 + 8).setCellValue("病假");
                row3.createCell(monthday.size()*2 + 9).setCellValue("年假");
                row3.createCell(monthday.size()*2 + 10).setCellValue("特别假");
                //创建其他具体考勤数据
                for(int i = 0 ; i < list.size() ; i++){
                    HSSFRow rows4 = sheet.createRow(i+3);
                    List<Object> valueList  = bianLi(list.get(i));

                    int k = 0;
                    //-2删除最后两行
                    for(int j = 0 ; j < valueList.size() ; j++){
                        String value = valueList.get(j) == null ? "":valueList.get(j).toString();
                        if(!value.equals("-1")){
                            rows4.createCell(k++).setCellValue(value);

                        }
                    }
                }
                //改变样式
                HSSFRow rowCellStyle1 = sheet.getRow(0);


                //导出下载Excel
                response.setHeader("Content-Disposition", "attachment;Filename=上海新云传媒股份有限公司" + year + "年" + month + "月月考勤.xls");
                OutputStream outputStream = response.getOutputStream();
                workbook.write(outputStream);
                outputStream.close();
                return workbook.getBytes();


            }else{
                return null;
            }

        }catch (Exception e){
            return null;
        }


    }











    //将开始结束时间转换成List
    public List<Monthday> converttolist(String begin, String end) throws ParseException {
        List<Monthday> monthday = new ArrayList<Monthday>();
        List dates = new ArrayList<>();
        String begins[] = begin.split("-");
        String ends[] = end.split("-");
        Calendar start = Calendar.getInstance();
        Calendar endT = Calendar.getInstance();
        int bmonth = Integer.parseInt(begins[1]) - 1;//0-11代表1-12月
        int emonth = Integer.parseInt(ends[1]) - 1;
        start.set(Integer.parseInt(begins[0]), bmonth, Integer.parseInt(begins[2]));//开始日期转为日历
        endT.set(Integer.parseInt(ends[0]), emonth, Integer.parseInt(ends[2]));//结束日期转为日历
        Long startTIme = start.getTimeInMillis();
        Long endTime = endT.getTimeInMillis();
        Long oneDay = 1000 * 60 * 60 * 24l;//一天的时间转化为ms

        Long time = startTIme;
        int i = 0;
        while (time <= endTime) {
            Monthday md = new Monthday();
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            md.setYaer(df.format(d).split("-")[0]);
            md.setMonth(df.format(d).split("-")[1]);
            md.setDay(df.format(d).split("-")[2]);



            md.setrDate(d);
            //md.setState(isWeekend(d));
            md.setState(isWeekend(d ,"09:00"));
            dates.add(i, df.format(d));
            i++;
            time += oneDay;
            monthday.add(md);
        }
        return monthday;

    }


//比较是否迟到
    private Boolean compareLate(String rectime , String starttime ,String endtime) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:ss");
        Long rectime1 = sdf.parse(rectime).getTime();
        Long starttime1 = sdf.parse(starttime).getTime();
        Long endtime1 = sdf.parse(endtime).getTime();
        Long ning = sdf.parse("12:00").getTime();
        Boolean flag = true;
        //早迟到
        if(rectime1 > starttime1 && rectime1 < ning){
            return false;
        }
        //晚早退
        if(rectime1 >= ning && rectime1 < endtime1){
            return false;
        }



        return flag;
    }
    private boolean compareTime(String morning ,String starttime , String endtime) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:ss");
        Long rectime1 = sdf.parse(morning).getTime();
        Long starttime1 = sdf.parse(starttime).getTime();
        Long endtime1 = sdf.parse(endtime).getTime();


        return (rectime1 >= starttime1 && rectime1 < endtime1);
    }


    //判断是否为节假日
    private int isWeekend(Date date ,String time) throws ParseException {

        boolean isWeekend = false;
        boolean isVacation = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断周末
        isWeekend = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

        //判断调休
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date) + " " + time;
        List<Vacation> vacationList = vDao.findByDate(dateString);
        if(vacationList.size() > 0){
            Vacation vacation = vacationList.get(0);
            //类型为1则为假期
            if(vacation.getType().equals("1")){
                isWeekend = false;
            }
            //类型为0则为补班（上班）
            if(vacation.getType().equals("0")){
                isWeekend = true;
            }

        }





   /*     //判断调休
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s =sdf.format(date);
        Date d =  sdf.parse(s);
        List<Vacation> vacationList = vDao.findByTimes(startTime,endTime);
        for(Vacation v :vacationList){
           String type =  v.getType();
           Date begin = sdf.parse(sdf.format(v.getBegindate()));
           Date end = v.getEnddate();
           if(d == begin){
               if(type.equals("1")) {
                   isVacation = true;
               }else{
                    isVacation = false;
               }
           }
        }
*/

        /*return isWeekend && isVacation ? 1: 0;*/
        return isWeekend ?1:0;
    }

    //判断是否为节假日
    private int isWeekend2(Date date , String time) throws ParseException {

        boolean isWeekend = false;
        boolean isVacation = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断周末
        isWeekend = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

        //判断调休
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        List<Vacation> vacationList = vDao.findByDate(dateString+time);
        if(vacationList.size() > 0){
            Vacation vacation = vacationList.get(0);
            //类型为1则为假期
            if(vacation.getType().equals("1")){
                isWeekend = false;
            }
            //类型为0则为补班（上班）
            if(vacation.getType().equals("0")){
                isWeekend = true;
            }

        }





   /*     //判断调休
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s =sdf.format(date);
        Date d =  sdf.parse(s);
        List<Vacation> vacationList = vDao.findByTimes(startTime,endTime);
        for(Vacation v :vacationList){
           String type =  v.getType();
           Date begin = sdf.parse(sdf.format(v.getBegindate()));
           Date end = v.getEnddate();
           if(d == begin){
               if(type.equals("1")) {
                   isVacation = true;
               }else{
                    isVacation = false;
               }
           }
        }
*/

        /*return isWeekend && isVacation ? 1: 0;*/
        return isWeekend ?1:0;
    }



    //Java反射得到对象的所有属性

    private List<Object> bianLi(Object obj) {
        List<Object> list = new ArrayList<Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o;
                try {
                    o = fields[i].get(obj);
                    //System.err.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                    list.add(o);


                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);

            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();

            }
        }
        return list;
    }






}
