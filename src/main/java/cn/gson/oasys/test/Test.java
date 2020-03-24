package cn.gson.oasys.test;

import cn.gson.oasys.Utils.DateUtils;
import cn.gson.oasys.activiti.entity.Buka;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {


    @org.junit.jupiter.api.Test
    public void test() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM

        Date beginDate = simpleDateFormat.parse("2019-09-01 11:12:21");
        Date endDate = simpleDateFormat.parse("2019-09-02 00:00:00");

        int a = Integer.parseInt(DateUtils.getTwoDay(DateUtils.formatDate(endDate,null),DateUtils.formatDate(beginDate,null)));

        System.err.println(a);

    }




}
