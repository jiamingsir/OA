package cn.gson.oasys.Utils;

import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserUtils {

    public static User getUser(HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        UserDao uDao = (UserDao)SpringUtils.getBean(UserDao.class);


        List<User> userList = uDao.findByUserId(userId);
        User user = userList.get(0);

        return user;
    }

    public static User getLeader(HttpSession session){


        Long userId = (Long) session.getAttribute("userId");
        UserDao uDao = (UserDao)SpringUtils.getBean(UserDao.class);
        List<User> userList = uDao.findByUserId(userId);
        User user = userList.get(0);

        User leader = uDao.findByUserId((Long)user.getFatherId()).get(0);


        return leader;
    }



}
