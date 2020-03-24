package cn.gson.oasys.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import cn.gson.oasys.model.dao.maildao.MailnumberDao;
import cn.gson.oasys.model.entity.mail.Mailnumber;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.util.StringUtil;

import cn.gson.oasys.common.formValid.BindingResultVOUtil;
import cn.gson.oasys.common.formValid.MapToList;
import cn.gson.oasys.common.formValid.ResultEnum;
import cn.gson.oasys.common.formValid.ResultVO;
import cn.gson.oasys.model.dao.informdao.InformRelationDao;
import cn.gson.oasys.model.dao.maildao.MailreciverDao;
import cn.gson.oasys.model.dao.processdao.NotepaperDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.PositionDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.mail.Mailreciver;
import cn.gson.oasys.model.entity.notice.NoticeUserRelation;
import cn.gson.oasys.model.entity.process.Notepaper;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.user.NotepaperService;

@Controller
@RequestMapping("/")
public class UserpanelController {
	@Autowired
	private UserDao udao;
	@Autowired
	private MailnumberDao mndao;
	@Autowired
	private DeptDao ddao;
	@Autowired
	private PositionDao pdao;
	@Autowired
	private InformRelationDao irdao;
	@Autowired
	private MailreciverDao mdao;
	@Autowired
	private NotepaperDao ndao;
	@Autowired
	private NotepaperService nservice;
	
	@Value("${img.rootpath}")
	private String rootpath;

	@Value("${attachment.roopath}")
	private String roopath;
	
	@RequestMapping("userpanel")
	public String index(@SessionAttribute("userId") Long userId,Model model,HttpServletRequest req,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		
		Pageable pa=new PageRequest(page, size);
		User user=null;
		if(!StringUtil.isEmpty((String) req.getAttribute("errormess"))){
			 user=(User) req.getAttribute("users");
			 req.setAttribute("errormess",req.getAttribute("errormess"));
		}
		else if(!StringUtil.isEmpty((String) req.getAttribute("success"))){
			user=(User) req.getAttribute("users"); 
			req.setAttribute("success","fds");
		}
		else{
			//找到这个用户
			user=udao.findOne(userId);
		}
		
		//找到部门名称
		String deptname=ddao.findname(user.getDept().getDeptId());
		
		//找到职位名称
		String positionname=pdao.findById(user.getPosition().getId());
		
		//找未读通知消息
		List<NoticeUserRelation> noticelist=irdao.findByReadAndUserId(false, user);
		
		//找未读邮件
		List<Mailreciver> maillist=mdao.findByReadAndDelAndReciverId(false,false, user);
		
		//找便签
		Page<Notepaper> list=ndao.findByUserIdOrderByCreateTimeDesc(user,pa);
		
		List<Notepaper> notepaperlist=list.getContent();
		
		model.addAttribute("user", user);
		model.addAttribute("deptname", deptname);
		model.addAttribute("positionname", positionname);
		model.addAttribute("noticelist", noticelist.size());
		model.addAttribute("maillist", maillist.size());
		model.addAttribute("notepaperlist", notepaperlist);
		model.addAttribute("page", list);
		model.addAttribute("url", "panel");
		
	
		return "user/userpanel";
	}
	/**
	 * 上下页
	 */
	@RequestMapping("panel")
	public String index(@SessionAttribute("userId") Long userId,Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		Pageable pa=new PageRequest(page, size);
		User user=udao.findOne(userId);
		//找便签
		Page<Notepaper> list=ndao.findByUserIdOrderByCreateTimeDesc(user,pa);
		List<Notepaper> notepaperlist=list.getContent();
		model.addAttribute("notepaperlist", notepaperlist);
		model.addAttribute("page", list);
		model.addAttribute("url", "panel");
		return "user/panel";
	}
	/**
	 * 存便签
	 */
	@RequestMapping("writep")
	public String savepaper(Notepaper npaper,@SessionAttribute("userId") Long userId,@RequestParam(value="concent",required=false)String concent){
		User user=udao.findOne(userId);
		npaper.setCreateTime(new Date());
		npaper.setUserId(user);
		System.out.println("内容"+npaper.getConcent());
		if(npaper.getTitle()==null||npaper.getTitle()=="")
			npaper.setTitle("无标题");
		if(npaper.getConcent()==null||npaper.getConcent()=="")
			npaper.setConcent(concent);
		ndao.save(npaper);
		
		return "redirect:/userpanel";
	}
	/**
	 * 删除便签
	 */
	@RequestMapping("notepaper")
	public String deletepaper(HttpServletRequest request,@SessionAttribute("userId") Long userId){
		User user=udao.findOne(userId);
		String paperid=request.getParameter("id");
		Long lpid = Long.parseLong(paperid);
		Notepaper note=ndao.findOne(lpid);
		if(user.getUserId().equals(note.getUserId().getUserId())){
			nservice.delete(lpid);
		}else{
			System.out.println("权限不匹配，不能删除");
			return "redirect:/notlimit";
		}
		return "redirect:/userpanel";
		
	}
	/**
	 * 修改用户
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("saveuser")
	public String saveemp(@RequestParam("filePath")MultipartFile filePath,HttpServletRequest request,@Valid User user,
			BindingResult br,@SessionAttribute("userId") Long userId) throws IllegalStateException, IOException{
		String imgpath=nservice.upload(filePath);
		User users=udao.findOne(userId);
		
		//重新set用户
		users.setRealName(user.getRealName());
		users.setUserTel(user.getUserTel());
		users.setEamil(user.getEamil());
		users.setAddress(user.getAddress());
		users.setUserEdu(user.getUserEdu());
		users.setSchool(user.getSchool());
		users.setIdCard(user.getIdCard());
		users.setBank(user.getBank());
		users.setSex(user.getSex());
		users.setThemeSkin(user.getThemeSkin());
		users.setBirth(user.getBirth());
		users.setPcmacpwd(user.getPcmacpwd());
		users.setEmailpwd(user.getEmailpwd());
		if(!StringUtil.isEmpty(user.getUserSign())){
			users.setUserSign(user.getUserSign());
		}
		if(!StringUtil.isEmpty(user.getPassword())){
			users.setPassword(user.getPassword());
		}
		if(!StringUtil.isEmpty(imgpath)){
			users.setImgPath(imgpath);
			
		}
		
		request.setAttribute("users", users);
		
		ResultVO res = BindingResultVOUtil.hasErrors(br);
		if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
			List<Object> list = new MapToList<>().mapToList(res.getData());
			request.setAttribute("errormess", list.get(0).toString());
			
			System.out.println("list错误的实体类信息：" + user);
			System.out.println("list错误详情:" + list);
			System.out.println("list错误第一条:" + list.get(0));
			System.out.println("啊啊啊错误的信息——：" + list.get(0).toString());
			
		}else{
			udao.save(users);
			/*
			* 集成邮箱
			* */
			List<Mailnumber> mailnumberList = mndao.findByUserIds(users);
			Mailnumber mailnumber = new Mailnumber();
			if (mailnumberList.size()>0){
				mailnumber = mailnumberList.get(0);
				mailnumber.setMailAccount(users.getEamil());
				mailnumber.setPassword(users.getEmailpwd());
				//if (mailnumber.getMailUserName().isEmpty()){
					mailnumber.setMailUserName(users.getUserSign());
				//}

				mailnumber.setMailCreateTime(new Date());
			}else {
				mailnumber.setMailAccount(users.getEamil());
				if (users.getEmailpwd().equals("")||users.getEmailpwd().isEmpty()){
					mailnumber.setPassword("123456");
				}else {
					mailnumber.setPassword(users.getEmailpwd());
				}
				mailnumber.setMailUserName(users.getUserSign());
				mailnumber.setMailUserId(users);
				mailnumber.setMailCreateTime(new Date());
			}

			mndao.save(mailnumber);

			request.setAttribute("success", "执行成功！");
		}
		return "forward:/userpanel";
		
	}

	@RequestMapping("changepwd")
	public String changepwd(@SessionAttribute("userId") Long userId,Model model,HttpServletRequest req,
						@RequestParam(value = "page", defaultValue = "0") int page,
						@RequestParam(value = "size", defaultValue = "10") int size){

		Pageable pa=new PageRequest(page, size);
		User user=null;
		if(!StringUtil.isEmpty((String) req.getAttribute("errormess"))){
			user=(User) req.getAttribute("users");
			req.setAttribute("errormess",req.getAttribute("errormess"));
		}
		else if(!StringUtil.isEmpty((String) req.getAttribute("success"))){
			user=(User) req.getAttribute("users");
			req.setAttribute("success","fds");
		}
		else{
			//找到这个用户
			user=udao.findOne(userId);
		}

		//找到部门名称
		String deptname=ddao.findname(user.getDept().getDeptId());

		//找到职位名称
		String positionname=pdao.findById(user.getPosition().getId());

		//找未读通知消息
		List<NoticeUserRelation> noticelist=irdao.findByReadAndUserId(false, user);

		//找未读邮件
		List<Mailreciver> maillist=mdao.findByReadAndDelAndReciverId(false,false, user);

		//找便签
		Page<Notepaper> list=ndao.findByUserIdOrderByCreateTimeDesc(user,pa);

		List<Notepaper> notepaperlist=list.getContent();

		model.addAttribute("user", user);
		model.addAttribute("deptname", deptname);
		model.addAttribute("positionname", positionname);
		model.addAttribute("noticelist", noticelist.size());
		model.addAttribute("maillist", maillist.size());
		model.addAttribute("notepaperlist", notepaperlist);
		model.addAttribute("page", list);
		model.addAttribute("url", "panel");


		return "user/changepwd";
	}



	@RequestMapping("changepassword")
	public String changepassword(HttpServletRequest request,@Valid User user,BindingResult br,
						   @SessionAttribute("userId") Long userId) {

		User users=udao.findOne(userId);
		try{
			users.setPassword(user.getPassword());
			udao.save(users);

			ResultVO res = BindingResultVOUtil.hasErrors(br);
			if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
				List<Object> list = new MapToList<>().mapToList(res.getData());
				request.setAttribute("errormess", list.get(0).toString());
			}else{
				udao.save(users);
				request.setAttribute("success", "执行成功！");
			}

		}catch (Exception e){
			request.setAttribute("errormess", "系统异常，请联系管理员");
		}
		request.setAttribute("users", users);
		return "forward:/changepwd";
	}


	@RequestMapping("image/**")
	public void image(Model model, HttpServletResponse response, @SessionAttribute("userId") Long userId, HttpServletRequest request)
			throws IOException {

		String startpath = new String(URLDecoder.decode(request.getRequestURI(), "utf-8"));

		String path = startpath.replace("/image", "");

		File f = new File(rootpath, path);

		ServletOutputStream sos = response.getOutputStream();
		FileInputStream input = new FileInputStream(f.getPath());
		byte[] data = new byte[(int) f.length()];
		IOUtils.readFully(input, data);
		// 将文件流输出到浏览器
		IOUtils.write(data, sos);
		input.close();
		sos.close();
	}

	@RequestMapping("image2/**")
	public void image2(Model model, HttpServletResponse response, @SessionAttribute("userId") Long userId, HttpServletRequest request)
			throws IOException {

		String startpath = new String(URLDecoder.decode(request.getRequestURI(), "utf-8"));

		String path = startpath.replace("/image2", "");

		File f = new File(roopath, path);

		ServletOutputStream sos = response.getOutputStream();
		FileInputStream input = new FileInputStream(f.getPath());
		byte[] data = new byte[(int) f.length()];
		IOUtils.readFully(input, data);
		// 将文件流输出到浏览器
		IOUtils.write(data, sos);
		input.close();
		sos.close();
	}




}
