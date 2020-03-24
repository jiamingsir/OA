package cn.gson.oasys.controller.file;

import cn.gson.oasys.model.dao.filedao.PcmaclogDao;
import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import cn.gson.oasys.model.entity.file.Pcmaclog;
import cn.gson.oasys.model.entity.schedule.ScheduleList;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class PcmacLogController {

    @Autowired
    private PcmaclogDao pDao;

    @RequestMapping("pclog")
    public String pclog(@RequestParam(value="page",defaultValue = "0")int page,
                        HttpSession session, Model model,
                        @RequestParam(value="baseKey",required=false)String basekey,
                        @RequestParam(value="time",required=false)String time){

        getPcmacLog(page,20, session, model, basekey, time);



        return "file/pcmaclog";
    }

    @RequestMapping("pclogtable")
    public String pclogtable(@RequestParam(value="page",defaultValue = "0")int page,
                        HttpSession session, Model model,
                        @RequestParam(value="baseKey",required=false)String basekey,
                        @RequestParam(value="time",required=false)String time){

        getPcmacLog(page, 20,  session, model, basekey, time);



        return "file/pcmaclogtable";
    }


    private void getPcmacLog(int page, int size , HttpSession session, Model model, String basekey, String time) {

        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"downdate"));
        Pageable pa=new PageRequest(page, size,sort);
        Page<Pcmaclog> logPage = pDao.findAll(pa);

        model.addAttribute("logList", logPage.getContent());
        model.addAttribute("page",logPage);
        model.addAttribute("baseKey","baseKey");
        model.addAttribute("url", "pclogtable");


    }


}
