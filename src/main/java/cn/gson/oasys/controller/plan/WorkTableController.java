package cn.gson.oasys.controller.plan;

import cn.gson.oasys.common.formValid.BindingResultVOUtil;
import cn.gson.oasys.common.formValid.MapToList;
import cn.gson.oasys.common.formValid.ResultEnum;
import cn.gson.oasys.common.formValid.ResultVO;
import cn.gson.oasys.model.dao.plandao.WorkTableDao;
import cn.gson.oasys.model.dao.system.TypeDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.plan.AoaWorkLable;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.plan.LableService;
import cn.gson.oasys.services.system.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WorkTableController {

@Autowired
    private WorkTableDao workTableDao;
    @Autowired
    private LableService lableService;
    @Autowired
            private DeptDao deptDao;
    @Autowired
            private UserDao userDao;

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TypeService typeService;

    /**
     * 进入类型管理表格界面
     *
     * @param req
     * @return
     */
    @RequestMapping("testsyslable")
    public String testsyslable(HttpServletRequest req, @SessionAttribute("userId") Long userId) {
        User user = userDao.getOne(userId);
        Long deptid =user.getDept().getDeptId();
        //List<AoaWorkLable> lableList1 = workTableDao.findAll();
        List<AoaWorkLable> lableList = workTableDao.findByDeptId(deptid);



        req.setAttribute("lableList", lableList);
        return "plan/lablemanage";
    }

    /**
     * 查找类型表格
     *
     * @param req
     * @return
     */
    @RequestMapping("labletable")
    public String lableTable(HttpServletRequest req) {
        if(!StringUtils.isEmpty(req.getParameter("name"))){
            String name="%"+req.getParameter("name")+"%";
            //req.setAttribute("lableList",workTableDao.findByLableNameLikeOrLableModelLike(name, name));
            req.setAttribute("lableList",workTableDao.findByDeptNameLike(name));
        }
        else{
            Iterable<AoaWorkLable> lableList = workTableDao.findAll();
            req.setAttribute("lableList", lableList);
        }
        return "plan/labletable";
    }

    /**
     * 类型编辑界面；
     *
     * @param req
     * @return
     */
    @RequestMapping("lableedit")
    public String lableEdit(HttpServletRequest req, @SessionAttribute("userId") Long userId) {
        User user = userDao.getOne(userId);
        String deptName =user.getDept().getDeptName();
        if (!StringUtils.isEmpty(req.getParameter("lableid"))) {
            Long lableid = Long.parseLong(req.getParameter("lableid"));
            AoaWorkLable lableObj = workTableDao.findOne(lableid);
            req.setAttribute("lableObj", lableObj);
            HttpSession session = req.getSession();
            session.setAttribute("lableid", lableid);

        }
        Iterable<Dept> parentList=deptDao.findAll();
        req.setAttribute("deptName", deptName);
        req.setAttribute("parentList", parentList);
        return "plan/lableedit";
    }

    /**
     * 系统管理表单验证
     *
     * @param req
     * @param menu
     * @param br
     * 后台校验表单数据，不通过则回填数据，显示错误信息；通过则直接执行业务，例如新增、编辑等；
     * @return
     */
    @RequestMapping("lablecheck")
    public String testMess(HttpServletRequest req, @RequestParam("deptName") String deptName, @Valid AoaWorkLable menu, BindingResult br) {
        HttpSession session = req.getSession();
        Long menuId = null;
        Dept dept = deptDao.findByDeptName(deptName);
        menu.setDeptId(dept);
        req.setAttribute("menuObj", menu);

        // 这里返回ResultVO对象，如果校验通过，ResultEnum.SUCCESS.getCode()返回的值为200；否则就是没有通过；
        ResultVO res = BindingResultVOUtil.hasErrors(br);
        // 校验失败
        if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
            List<Object> list = new MapToList<>().mapToList(res.getData());
            req.setAttribute("errormess", list.get(0).toString());
            // 代码调试阶段，下面是错误的相关信息；
            System.out.println("list错误的实体类信息：" + menu);
            System.out.println("list错误详情:" + list);
            System.out.println("list错误第一条:" + list.get(0));
            System.out.println("啊啊啊错误的信息——：" + list.get(0).toString());
            // 下面的info信息是打印出详细的信息
            log.info("getData:{}", res.getData());
            log.info("getCode:{}", res.getCode());
            log.info("getMsg:{}", res.getMsg());
        }
        // 校验通过，下面写自己的逻辑业务
        else {
            // 判断是否从编辑界面进来的，前面有"session.setAttribute("getId",getId);",在这里获取，并remove掉；
            if (!StringUtils.isEmpty(session.getAttribute("lableid"))) {
                System.out.println(session.getAttribute("lableid"));
                menuId = (Long) session.getAttribute("lableid"); // 获取进入编辑界面的menuID值
                menu.setId(menuId);
                log.info("getId:{}", session.getAttribute("lableid"));
                session.removeAttribute("lableid");

            }
            // 执行业务代码
            lableService.save(menu);
            System.out.println("此操作是正确的");
            req.setAttribute("success", "后台验证成功");
        }
        System.out.println("是否进入最后的实体类信息：" + menu);
        return "/testsyslable";
    }

    /**
     * 执行删除方法
     */
    @RequestMapping("deletelable")
    public String deleteThis(HttpServletRequest req){
        Long lableId=Long.parseLong(req.getParameter("id"));
        lableService.deleteLable(lableId);
        return "forward:/testsyslable";
    }

}
