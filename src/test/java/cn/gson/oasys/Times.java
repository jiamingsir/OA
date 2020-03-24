package cn.gson.oasys;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Times {
    @Test
    public void isInDate(){
        Map map = new HashMap();
        //开始时间
        Date beginDate = new Date();
        Date begin =null;
        //结束时间
        Date endDate = new Date();
        Date end = null;
        //未打卡时间
        SimpleDateFormat formatday = new SimpleDateFormat("dd");
        SimpleDateFormat formath = new SimpleDateFormat("H");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");

        List<String> lDate = new ArrayList<String>();
        lDate.add("2019-12-05 14:21:44");
        lDate.add("2019-12-06 14:21:44");
        lDate.add("2019-12-07 11:21:44");
        Date aa =null;
        try {
             aa = sdf.parse(lDate.get(0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String a = sdfymd.format(aa);
        Date first = null;
        Date last =null;
        Date firsta = null;
        Date lasta =null;
        Date centa =null;
        String monhms=" 09:00:00";
        String evehms=" 17:00:00";
        for (int i = 0;i<lDate.size();i++){
            try {

            // String day =format.format(first);


                //first = format.parse(lDate.get(0));
                // last = format.parse(lDate.get(lDate.size()-1));
                String ymd = "";
                if (i==0 ) {
                    firsta = sdf.parse(lDate.get(i));
                    ymd = sdfymd.format(firsta);
                    String day = formatday.format(firsta);
                    String house = formath.format(firsta);

                    if (Integer.parseInt(house) <=12) {

                        String monymdhms = ymd + monhms;
                        String eveymdhms = ymd + evehms;
                        map.put(day + 1, monymdhms);
                        map.put(day + 2, eveymdhms);
                    } else {
                        String eveymdhms = ymd + evehms;
                        map.put(day + 1, eveymdhms);
                    }
                }else if(i==lDate.size()-1){
                    lasta = sdf.parse(lDate.get(i));
                    ymd = sdfymd.format(lasta);
                    String day = formatday.format(lasta);
                    String house = formath.format(lasta);
                    if (Integer.parseInt(house) > 12) {
                        String monymdhms = ymd + monhms;
                        String eveymdhms = ymd + evehms;
                        map.put(day + 1, monymdhms);
                        map.put(day + 2, eveymdhms);
                    } else {
                        String monymdhms = ymd + monhms;
                        map.put(day + 1, monymdhms);
                    }
                } else {
                    centa = sdf.parse(lDate.get(i));
                    ymd = sdfymd.format(centa);
                    String day = formatday.format(centa);
                    String house = formath.format(centa);
                    String monymdhms = ymd + monhms;
                    String eveymdhms = ymd + evehms;
                    map.put(day + 1, monymdhms);
                    map.put(day + 2, eveymdhms);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for(Object m:map.values()){
            System.out.println("xunhuan:"+m);
        }


    }
@Test
    //public List<String> findDates(Date dBegin, Date dEnd){
public void findDates(){
    Date dBegin =null;
    Date dEnd =null;
        List<String> lDate = new ArrayList<String>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    try {
        dBegin = sd.parse("2019-12-05 14:21:44");
        dEnd = sd.parse("2019-12-07 12:21:44");
    } catch (ParseException e) {
        e.printStackTrace();
    }


    lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
// 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            if (dEnd.after(calBegin.getTime())){

            }
            lDate.add(sd.format(calBegin.getTime()));
        }
    System.out.println("时间list："+lDate);
        //return lDate;
    }
}
