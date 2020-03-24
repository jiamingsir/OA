package cn.gson.oasys.controller.mail;


import cn.gson.oasys.common.formValid.BindingResultVOUtil;
import cn.gson.oasys.common.formValid.MapToList;
import cn.gson.oasys.common.formValid.ResultEnum;
import cn.gson.oasys.common.formValid.ResultVO;
import cn.gson.oasys.model.dao.maildao.InMailDao;
import cn.gson.oasys.model.dao.maildao.MailnumberDao;
import cn.gson.oasys.model.dao.maildao.MailreciverDao;
import cn.gson.oasys.model.dao.notedao.AttachmentDao;
import cn.gson.oasys.model.dao.roledao.RoleDao;
import cn.gson.oasys.model.dao.system.StatusDao;
import cn.gson.oasys.model.dao.system.TypeDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.PositionDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.mail.Inmaillist;
import cn.gson.oasys.model.entity.mail.Mailnumber;
import cn.gson.oasys.model.entity.mail.Mailreciver;
import cn.gson.oasys.model.entity.mail.Pagemail;
import cn.gson.oasys.model.entity.note.Attachment;
import cn.gson.oasys.model.entity.system.SystemStatusList;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Position;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.mail.MailServices;
import cn.gson.oasys.services.process.ProcessService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
public class MailTController {
	
	
	@Autowired
	private MailnumberDao mndao;
	
	@Autowired
	private StatusDao sdao;
	@Autowired
	private TypeDao tydao;
	@Autowired
	private UserDao udao;
	@Autowired
	private DeptDao ddao;
	@Autowired
	private RoleDao rdao;
	@Autowired
	private PositionDao pdao;
	@Autowired
	private InMailDao imdao;
	@Autowired
	private MailreciverDao mrdao;
	@Autowired
	private AttachmentDao AttDao;
	@Autowired
	private MailServices mservice;
	@Autowired
	private ProcessService proservice;
	
	/**
	 * 邮件管理主页
	 * @return
	 */
	@RequestMapping("mail")
	public  String index(HttpSession session,@SessionAttribute("userId") Long userId, Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		//Object errorObj = req.getAttribute("errors");
		if(!StringUtils.isEmpty(session.getAttribute("errors"))){
			model.addAttribute("errors", "邮件异常");
		}else {
			model.addAttribute("errors", "");
		}

		//查找用户
		User user=udao.findOne(userId);
		//查找未读邮件
		List<Mailreciver> noreadlist=mrdao.findByReadAndDelAndReciverId(false, false, user);
		//查找创建了但是却没有发送的邮件
		List<Inmaillist>  nopushlist=imdao.findByPushAndDelAndMailUserid(false,false, user);
		//查找发件条数
		List<Inmaillist> pushlist=imdao.findByPushAndDelAndMailUserid(true,false, user);
		//查找收件箱删除的邮件条数
		List<Mailreciver> rubbish=mrdao.findByDelAndReciverId(true,user);
		//分页及查找
		Page<Pagemail> pagelist=mservice.recive(page, size, user, null,"收件箱");
		List<Map<String, Object>> maillist=mservice.mail(pagelist);
		//model.addAttribute("errors", "邮件存在异常！！！");
		model.addAttribute("page", pagelist);
		model.addAttribute("maillist",maillist);
		model.addAttribute("url","mailtitle");
		model.addAttribute("noread", noreadlist.size());
		model.addAttribute("nopush", nopushlist.size());
		model.addAttribute("push", pushlist.size());
		model.addAttribute("rubbish", rubbish.size());
		model.addAttribute("mess", "收件箱");
		model.addAttribute("sort", "&title=收件箱");
		return "mail/mail";
	}
	
	/**
	 * 删除邮件@SessionAttribute("userId") Long userId
	 */
	@RequestMapping("alldelete")
	public String delete(HttpServletRequest req,@SessionAttribute("userId") Long userId, Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		//查找用户
		User user=udao.findOne(userId);
		String title=req.getParameter("title");
		Page<Pagemail> pagelist=null;
		Page<Inmaillist> pagemail=null;
		List<Map<String, Object>> maillist=null;
		//得到删除id
		String ids=req.getParameter("ids");
		if(("收件箱").equals(title)){
			
			StringTokenizer st = new StringTokenizer(ids, ",");
			while (st.hasMoreElements()) {
				//找到该用户联系邮件的中间记录
				Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(user,Long.parseLong(st.nextToken()));
				if(!Objects.isNull(mailr)){
					//把删除的字段改为1
					mailr.setDel(true);
					mrdao.save(mailr);
				}else{
					return "redirect:/notlimit";
					}
			}
			//分页及查找
			pagelist=mservice.recive(page, size, user, null,title);
			maillist=mservice.mail(pagelist);
		}else if(("发件箱").equals(title)){
			StringTokenizer st = new StringTokenizer(ids, ",");
			while (st.hasMoreElements()) {
				//找到该邮件
				Inmaillist  inmail=imdao.findByMailUseridAndMailId(user,Long.parseLong(st.nextToken()));
				if(!Objects.isNull(inmail)){
					//把删除的字段改为1
					inmail.setDel(true);
					imdao.save(inmail);
				}else{
					return "redirect:/notlimit";
				}
			}
			pagemail=mservice.inmail(page, size, user, null,title);
			maillist=mservice.maillist(pagemail);
		}else if(("草稿箱").equals(title)){
			StringTokenizer st = new StringTokenizer(ids, ",");
			while (st.hasMoreElements()) {
				//找到该邮件
				Inmaillist  inmail=imdao.findByMailUseridAndMailId(user,Long.parseLong(st.nextToken()));
				if(!Objects.isNull(inmail)){
					imdao.delete(inmail);
				}else{
					return "redirect:/notlimit";
				}
			}
			pagemail=mservice.inmail(page, size, user, null,title);
			maillist=mservice.maillist(pagemail);
		}else{
			//垃圾箱
			StringTokenizer st = new StringTokenizer(ids, ",");
			while (st.hasMoreElements()) {
				Long mailid=Long.parseLong(st.nextToken());
				//查看中间表关于这条邮件的del字段
				List<Boolean> dellist=mrdao.findbyMailId(mailid);
				
				//判断中间表中关于这条邮件是否还有del字段为false的
				if(dellist.contains(false)){
					Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(user,mailid);
					if(!Objects.isNull(mailr)){
						mrdao.delete(mailr);
					}else{
						return "redirect:/notlimit";
					}
				}else{
					Inmaillist imail= imdao.findOne(mailid);
					//判断这条邮件的del字段是为true
					if(imail.getDel().equals(true)){
						List<Mailreciver> mreciver=mrdao.findByMailId(mailid);
						//循环删除关于这条邮件的所有中间表信息
						for (Mailreciver mailreciver : mreciver) {
							mrdao.delete(mailreciver);
						}
						imdao.delete(imail);
					}else{
						//这条邮件的del字段为false，则删除中间表信息
						Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(user,mailid);
						if(!Objects.isNull(mailr)){
							mrdao.delete(mailr);
						}else{
							return "redirect:/notlimit";
						}
					}
				}
			}
			pagelist=mservice.recive(page, size, user, null,title);
			maillist=mservice.mail(pagelist);
		}
		
		if(!Objects.isNull(pagelist)){
			model.addAttribute("page", pagelist);
		}else{
			model.addAttribute("page", pagemail);
		}
		model.addAttribute("maillist",maillist);
		model.addAttribute("url","mailtitle");
		model.addAttribute("mess", title);
		return "mail/mailbody";
		
	}
	/**
	 * 批量查看
	 */
	@RequestMapping("watch")
	public String watch(@SessionAttribute("userId") Long userId, Model model,HttpServletRequest req,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
			User user=udao.findOne(userId);
			String title=req.getParameter("title");
			String ids=req.getParameter("ids");
			Page<Pagemail> pagelist=null;
			List<Map<String, Object>> maillist=null;
		
			if(("收件箱").equals(title)){
				StringTokenizer st = new StringTokenizer(ids, ",");
				while (st.hasMoreElements()) {
					//找到该用户联系邮件的中间记录
					Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(user,Long.parseLong(st.nextToken()));
					if(mailr.getRead().equals(false)){
				
						mailr.setRead(true);
					}else{
					
						mailr.setRead(false);
					}
					
					mrdao.save(mailr);
				}
				//分页及查找
				pagelist=mservice.recive(page, size, user, null,title);
				
			}else{
				//垃圾箱
				StringTokenizer st = new StringTokenizer(ids, ",");
				while (st.hasMoreElements()) {
					//找到该用户联系邮件的中间记录
					Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(user,Long.parseLong(st.nextToken()));
					if(mailr.getRead().equals(false)){
						mailr.setRead(true);
					}else{
						mailr.setRead(false);
					}
					mrdao.save(mailr);
				}
				//分页及查找
				pagelist=mservice.recive(page, size, user, null,title);
			}
			maillist=mservice.mail(pagelist);
				
				model.addAttribute("page", pagelist);
				model.addAttribute("maillist",maillist);
				model.addAttribute("url","mailtitle");
				model.addAttribute("mess",title);
		    return "mail/mailbody";
	}
	/**
	 * 批量标星
	 */
	@RequestMapping("star")
	public String star(@SessionAttribute("userId") Long userId, Model model,HttpServletRequest req,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
			User user=udao.findOne(userId);
			String title=req.getParameter("title");
			String ids=req.getParameter("ids");
			Page<Pagemail> pagelist=null;
			Page<Inmaillist> pagemail=null;
			List<Map<String, Object>> maillist=null;
		
			if(("收件箱").equals(title)){
				StringTokenizer st = new StringTokenizer(ids, ",");
				while (st.hasMoreElements()) {
				
					//找到该用户联系邮件的中间记录
					Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(user,Long.parseLong(st.nextToken()));
					if(mailr.getStar().equals(false)){
						mailr.setStar(true);
					}else{
						mailr.setStar(false);
					}
					mrdao.save(mailr);
				}
				//分页及查找
				pagelist=mservice.recive(page, size, user, null,title);
				maillist=mservice.mail(pagelist);
			}else if(("发件箱").equals(title)){
				StringTokenizer st = new StringTokenizer(ids, ",");
				while (st.hasMoreElements()) {
					//找到该邮件
					Inmaillist  inmail=imdao.findByMailUseridAndMailId(user,Long.parseLong(st.nextToken()));
					if(inmail.getStar().equals(false)){
						inmail.setStar(true);
					}else{
						inmail.setStar(false);
					}
					imdao.save(inmail);
				}
				pagemail=mservice.inmail(page, size, user, null,title);
				maillist=mservice.maillist(pagemail);
			}else if(("草稿箱").equals(title)){
				StringTokenizer st = new StringTokenizer(ids, ",");
				while (st.hasMoreElements()) {
					//找到该邮件
					Inmaillist  inmail=imdao.findByMailUseridAndMailId(user,Long.parseLong(st.nextToken()));
					if(inmail.getStar().equals(false)){
						inmail.setStar(true);
					}else{
						inmail.setStar(false);
					}
					imdao.save(inmail);
				}
				pagemail=mservice.inmail(page, size, user, null,title);
				maillist=mservice.maillist(pagemail);
			}else{
				//垃圾箱
				StringTokenizer st = new StringTokenizer(ids, ",");
				while (st.hasMoreElements()) {
					//找到该用户联系邮件的中间记录
					Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(user,Long.parseLong(st.nextToken()));
					if(mailr.getStar().equals(false)){
						mailr.setStar(true);
					}else{
						mailr.setStar(false);
					}
					mrdao.save(mailr);
				}
				//分页及查找
				pagelist=mservice.recive(page, size, user, null,title);
				maillist=mservice.mail(pagelist);
			}
				
			if(!Objects.isNull(pagelist)){
				model.addAttribute("page", pagelist);
			}else{
				model.addAttribute("page", pagemail);
			}
				model.addAttribute("maillist",maillist);
				model.addAttribute("url","mailtitle");
				model.addAttribute("mess",title);
		    return "mail/mailbody";
	}
	/**
	 *邮箱条件查找
	 */
	@RequestMapping("mailtitle")
	public String serch(@SessionAttribute("userId") Long userId, Model model,HttpServletRequest req,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		User user=udao.findOne(userId);
		String title=req.getParameter("title");
		String val=null;
		Page<Pagemail> pagelist=null;
		Page<Inmaillist> pagemail=null;
		List<Map<String, Object>> maillist=null;
		
		if(!StringUtil.isEmpty(req.getParameter("val"))){
			val=req.getParameter("val");
		}
		if(("收件箱").equals(title)){
			pagelist=mservice.recive(page, size, user, val,title);
			maillist=mservice.mail(pagelist);
			
		}else if(("发件箱").equals(title)){
			
			pagemail=mservice.inmail(page, size, user, val,title);
			maillist=mservice.maillist(pagemail);
		}else if(("草稿箱").equals(title)){
			
			pagemail=mservice.inmail(page, size, user, val,title);
			maillist=mservice.maillist(pagemail);
		}else{
			//垃圾箱
			pagelist=mservice.recive(page, size, user, val,title);
			maillist=mservice.mail(pagelist);
		}
		
		if(!Objects.isNull(pagelist)){
			model.addAttribute("page", pagelist);
		}else{
			model.addAttribute("page", pagemail);
		}
		if(val!=null){
			model.addAttribute("sort", "&title="+title+"&val="+val);
		}
		else{
			model.addAttribute("sort", "&title="+title);
		}
		model.addAttribute("maillist",maillist);
		model.addAttribute("url","mailtitle");
		model.addAttribute("mess",title);
		return "mail/mailbody";
	}
	
	/**
	 * 账号管理
	 */
	@RequestMapping("accountmanage")
	public String account(@SessionAttribute("userId") Long userId, Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		// 通过邮箱建立用户id找用户对象
		User tu = udao.findOne(userId);
		
		Page<Mailnumber> pagelist=mservice.index(page, size, tu, null,model);
		//List<Map<String, Object>> list=mservice.up(pagelist);

		//只操作自己
		List<Mailnumber> mailnumberList = mndao.findByUserIds(tu);
		
		//model.addAttribute("account", list);
		model.addAttribute("account", mailnumberList);
		model.addAttribute("page", pagelist);
		model.addAttribute("url", "mailpaixu");
		return "mail/mailmanage";
	}
	/**
	 * 账号各种排序
	 * 和查询
	 */
	@RequestMapping("mailpaixu")
	public String paixu(HttpServletRequest request, @SessionAttribute("userId") Long userId, Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		// 通过发布人id找用户
		User tu = udao.findOne(userId);
		//得到传过来的值
		String val =null;
		if(!StringUtil.isEmpty(request.getParameter("val"))){
			
		 val = request.getParameter("val");
		}
		Page<Mailnumber> pagelist=mservice.index(page, size, tu, val,model);
		List<Map<String, Object>> list=mservice.up(pagelist);
		model.addAttribute("account", list);
		model.addAttribute("page", pagelist);
		model.addAttribute("url", "mailpaixu");
		
		return "mail/mailtable";
	}

	/**
	 * 新增账号
	 * 修改账号
	 */
	@RequestMapping("addaccount")
	public String add(@SessionAttribute("userId") Long userId, Model model,HttpServletRequest req){
		// 通过用户id找用户
		User tu = udao.findOne(userId);
		
		Mailnumber mailn=null;
		if(StringUtil.isEmpty(req.getParameter("id"))){


			List<SystemTypeList> typelist=tydao.findByTypeModel("aoa_mailnumber");
			List<SystemStatusList> statuslist=sdao.findByStatusModel("aoa_mailnumber");//aoa_mailnumber
			model.addAttribute("typelist", typelist);
			model.addAttribute("statuslist", statuslist);
			
			if(!StringUtil.isEmpty((String) req.getAttribute("errormess"))){
				mailn=(Mailnumber) req.getAttribute("mail");
				 req.setAttribute("errormess",req.getAttribute("errormess"));
				 model.addAttribute("mails", mailn);
				 model.addAttribute("type", tydao.findname(mailn.getMailType()));
				 model.addAttribute("status", sdao.findname(mailn.getStatus()));
				 
			}else if(!StringUtil.isEmpty((String) req.getAttribute("success"))){

				mailn=(Mailnumber) req.getAttribute("mail");
				req.setAttribute("success","fds");
				model.addAttribute("mails", mailn);
				model.addAttribute("type", tydao.findname(mailn.getMailType()));
				model.addAttribute("status", sdao.findname(mailn.getStatus()));

			}
		}else{
			
			Long id=Long.parseLong(req.getParameter("id"));
			Mailnumber mailnum=mndao.findOne(id);
			model.addAttribute("type", tydao.findname(mailnum.getMailType()));
			model.addAttribute("status", sdao.findname(mailnum.getStatus()));
			model.addAttribute("mails", mailnum);

		}
		model.addAttribute("realname", tu.getRealName());
		return "mail/addaccounts";
	}
	/**
	 * 存邮箱账号
	 */
	@RequestMapping("saveaccount")
	public String save(HttpServletRequest request,@Valid Mailnumber mail,BindingResult br, @SessionAttribute("userId") Long userId,Model model){
		User tu=udao.findOne(userId);
		request.setAttribute("mail", mail);
		ResultVO res = BindingResultVOUtil.hasErrors(br);
		if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
			List<Object> list = new MapToList<>().mapToList(res.getData());
			request.setAttribute("errormess", list.get(0).toString());
			
			System.out.println("list错误的实体类信息：" + mail);
			System.out.println("list错误详情:" + list);
			System.out.println("list错误第一条:" + list.get(0));
			System.out.println("啊啊啊错误的信息——：" + list.get(0).toString());
			
		}else{

			if(Objects.isNull(mail.getMailNumberId())){
				List<Mailnumber> mailnumberList = mndao.findByUserIds(tu);
				if (mailnumberList.size()>0){
					model.addAttribute("error", "您的邮箱信息已存在！");
					return "common/proce";
				}else {
					mail.setMailUserId(tu);
					mail.setMailCreateTime(new Date());
					mndao.save(mail);
				}
			}else{
				Mailnumber mails=mndao.findOne(mail.getMailNumberId());
				//mails.setMailType(mail.getMailType());
				//mails.setStatus(mail.getStatus());
				//mails.setMailDes(mail.getMailDes());
				//mails.setMailAccount(mail.getMailAccount());
				//mails.setPassword(mail.getPassword());
				mails.setMailUserName(mail.getMailUserName());
				mails.setMailDes(mail.getMailDes());
				mndao.save(mails);
			}
			request.setAttribute("success", "执行成功！");
			
		}
		
		return "forward:/addaccount";
	}
	/**
	 * 删除账号
	 */
	@RequestMapping("dele")
	public String edit(HttpServletRequest request,@SessionAttribute("userId") Long userId){
		//得到账号id
		Long accountid=Long.parseLong(request.getParameter("id"));
		Mailnumber mail=mndao.findOne(accountid);
		if(mail.getMailUserId().getUserId().equals(userId)){
			mservice.dele(accountid);
		}else{
			return "redirect:/notlimit";
		}
		System.out.println("ffffffff");
		return "redirect:/accountmanage";
	}
	
	/**
	 * 写信
	 */
	@RequestMapping("wmail")
	public  String index2(Model model, @SessionAttribute("userId") Long userId,HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		
		User mu=udao.findOne(userId);
		//得到编辑过来的id
		String id=null;
		if(!StringUtil.isEmpty(request.getParameter("id"))){
			id=request.getParameter("id");
		}
		//回复那边过来的
		String huifu=null;
		
		if(!StringUtil.isEmpty(id)){
			Long lid=Long.parseLong(id);
			//找到这条邮件
			Inmaillist	mail=imdao.findOne(lid);
			if(!StringUtil.isEmpty(request.getParameter("huifu"))){
				huifu=request.getParameter("huifu");
				model.addAttribute("title",huifu+mail.getMailTitle());
				model.addAttribute("content",mail.getContent());
				
			}else{
				model.addAttribute("title",mail.getMailTitle());
				model.addAttribute("content", mail.getContent());
			}
			model.addAttribute("status", sdao.findOne(mail.getMailStatusid()));
			model.addAttribute("type", tydao.findOne(mail.getMailType()));
			model.addAttribute("id", "回复");
			
		}else{
		
		List<SystemTypeList> typelist=tydao.findByTypeModel("邮件");
		List<SystemStatusList>  statuslist=sdao.findByStatusModel("邮件");
		model.addAttribute("typelist", typelist);
		model.addAttribute("statuslist", statuslist);
		model.addAttribute("id", "新发");
		
		}
		//查看该用户所创建的有效邮箱账号
		List<Mailnumber> mailnum=mndao.findByStatusAndMailUserId(1L, mu);
		proservice.user(page, size, model);
		model.addAttribute("mailnum", mailnum);
		//model.addAttribute("url", "namesa");
		return "mail/wirtemail";
	}
	/**
	 * 发送邮件
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("pushmail")
	public String push(@RequestParam("file")MultipartFile[] files,HttpServletRequest request,HttpSession session,
					   @Valid Inmaillist mail,BindingResult br,
					   @SessionAttribute("userId") Long userId,Model model) throws IllegalStateException, IOException{
		User tu=udao.findOne(userId);
		if (true){

				// 要验证的字符串
				String str = tu.getEamil(); //"service@xsoftlab.net";
		// 邮箱验证规则
				String regEx = "[a-zA-Z_]{0,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		// 编译正则表达式
				Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(str);
		// 字符串是否与正则表达式相匹配
				boolean rs = matcher.matches();
				if (rs){

				}else {
					model.addAttribute("error", "请输入正确的邮箱！");
					return "common/proce";
				}
		}

		String rece = mail.getInReceiver();
		String[] recearr = rece.split(";");
		Set<String> receSet = new HashSet<>();
		for (int i = 0; i < recearr.length; i++) {
			if (StringUtil.isNotEmpty(recearr[i])){
				receSet.add(recearr[i]);
			}
		}
		String newrece1 = "";
		String newrece2 = "";
		for (String strrece:receSet) {
			newrece1 = newrece1 + strrece + ";";
		}
		newrece2 = newrece1.substring(0,newrece1.length()-1);
		mail.setInReceiver(newrece2);

		String name=null;
		//Attachment attaid=null;//原单附件
		List<Long> attaids=new ArrayList<>();
		Mailnumber number=null;
		StringTokenizer st =null;
		ResultVO res = BindingResultVOUtil.hasErrors(br);
		if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
			List<Object> list = new MapToList<>().mapToList(res.getData());
			request.setAttribute("errormess", list.get(0).toString());
		}else{
			if(!StringUtil.isEmpty(request.getParameter("fasong"))){
				name=request.getParameter("fasong");
			}
			
			
			if(!StringUtil.isEmpty(name)){
				for (int i = 0; i < files.length; i++) {
					Attachment attaid=null;
					MultipartFile file = files[i];
					if(!StringUtil.isEmpty(file.getOriginalFilename())){
						attaid=mservice.upload(file, tu);
						attaid.setModel("mail");
						AttDao.save(attaid);
						//存id
						attaids.add(attaid.getAttachmentId());
					}
				}

				//发送成功
				mail.setPush(true);
				
			}else{
				//存草稿
				mail.setInReceiver(null);
			}
			String attaidstr ="";
			if (attaids.size()>0){
				for (int i = 0; i < attaids.size(); i++) {
					Long atid = attaids.get(i);
					String atidstr = atid.toString();
					attaidstr = attaidstr + atidstr +",";
				}
			}
			if (StringUtil.isNotEmpty(attaidstr)){
				mail.setMailFiles(attaidstr);
			}

			//mail.setMailFileid(attaid);//原单附件
			mail.setMailCreateTime(new Date());
			mail.setMailUserid(tu);
			if(!mail.getInmail().equals(0)){
				//number=mndao.findOne(mail.getInmail());
				long numberid =mndao.findByUserIds(tu).get(0).getMailNumberId();
				number=mndao.findOne(numberid);
				mail.setMailNumberid(number);
			}
			//存邮件
			Inmaillist imail=imdao.save(mail);
			Map map =new HashMap();

			Mailnumber mailnumber1 = mndao.findByid(tu).get(0);
			// 发送邮件的账号
		 String ownEmailAccount = mailnumber1.getMailAccount();
		// 发送邮件的密码------》授权码
		 String ownEmailPassword = mailnumber1.getPassword();
		// 发送邮件的smtp 服务器 地址
		 String myEmailSMTPHost = "smtp.qq.com";
		// 发送邮件对方的邮箱
		 String receiveMailAccount = "";
			map.put("ownEmailAccount",ownEmailAccount);
			map.put("ownEmailPassword",ownEmailPassword);
			map.put("myEmailSMTPHost",myEmailSMTPHost);
boolean Flag = true;
			if(!StringUtil.isEmpty(name)){
				if(mservice.isContainChinese(mail.getInReceiver())){
					// 分割任务接收人
					StringTokenizer st2 = new StringTokenizer(mail.getInReceiver(), ";");
					while (st2.hasMoreElements()) {
						String aa =st2.nextToken();
						User reciver = udao.findid(aa);
						Mailreciver mreciver=new Mailreciver();
						mreciver.setMailId(imail);
						mreciver.setReciverId(reciver);
						mrdao.save(mreciver);
						//查出用户外部邮箱
						Mailnumber mailnumber = mndao.findByid(reciver).get(0);
						receiveMailAccount = mailnumber.getMailAccount();
						map.put("receiveMailAccount",receiveMailAccount);
						//pushmaillong(ownEmailAccount,ownEmailPassword,myEmailSMTPHost,receiveMailAccount);
						User user1 = udao.findByUserNameZ(aa).get(0);
						String accout = mndao.findByid(user1).get(0).getMailAccount();
						//if(!StringUtil.isEmpty(file.getOriginalFilename())){
						if(!StringUtil.isEmpty(mail.getMailFiles())){
							/*mservice.pushmail(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
									mail.getContent(), attaid.getAttachmentPath(), attaid.getAttachmentName());*/
							Flag =mservice.pushmailexchange(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
									mail.getContent(),mail.getMailFiles(), mail.getMailFiles());

						}else{
							/*mservice.pushmail(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
									mail.getContent(), null, null);*/
							Flag = mservice.pushmailexchange(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
									mail.getContent(),null, null);
						}

					}
				}else{
					if(mail.getInReceiver().contains(";")){
						st = new StringTokenizer(mail.getInReceiver(), ";");
					}else if (mail.getInReceiver().contains("；")){
						st = new StringTokenizer(mail.getInReceiver(), "；");
					}else {
						st = new StringTokenizer(mail.getInReceiver(), " ");
					}
					/*while (st.hasMoreElements()) {
						String aa =st.nextToken();
						User reciver = udao.findid(aa);
						Mailreciver mreciver=new Mailreciver();
						mreciver.setMailId(imail);
						mreciver.setReciverId(reciver);
						mrdao.save(mreciver);
						//查出用户外部邮箱
						Mailnumber mailnumber = mndao.findByid(reciver).get(0);
						receiveMailAccount = mailnumber.getMailAccount();
						map.put("receiveMailAccount",receiveMailAccount);
						pushmaillong(ownEmailAccount,ownEmailPassword,myEmailSMTPHost,receiveMailAccount);

					}*/
					String aa =st.nextToken();
					User reciver = udao.findid(aa);
					Mailreciver mreciver=new Mailreciver();
					mreciver.setMailId(imail);
					mreciver.setReciverId(reciver);
					mrdao.save(mreciver);
						while (st.hasMoreElements()) {
							String accout = mndao.findByid(udao.findByUserNameZ(st.nextToken()).get(0)).get(0).getMailAccount();
							//if(!StringUtil.isEmpty(file.getOriginalFilename())){
							if(!StringUtil.isEmpty(mail.getMailFiles())){
								/*mservice.pushmail(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
										mail.getContent(), attaid.getAttachmentPath(), attaid.getAttachmentName());*/
								Flag = mservice.pushmailexchange(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
										mail.getContent(),mail.getMailFiles(), mail.getMailFiles());
							}else{
								/*mservice.pushmail(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
										mail.getContent(), null, null);*/
								Flag = mservice.pushmailexchange(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
										mail.getContent(),null, null);
							}
						}
				}
				if (Flag == false){
					session.setAttribute("errors","errors");
				}
			}
		}
		
		return "redirect:/mail";
	}
	/*// 发送邮件的账号
	public static String ownEmailAccount = "*****@qq.com";
	// 发送邮件的密码------》授权码
	public static String ownEmailPassword = "zpacmijozkfebcgd";
	// 发送邮件的smtp 服务器 地址
	public static String myEmailSMTPHost = "smtp.qq.com";
	// 发送邮件对方的邮箱
	public static String receiveMailAccount = "*****@qq.com";*/


		/**
		 * @Title: createSimpleMail
		 * @Description: 创建邮件对象
		 * @author: chengpeng
		 * @param @param session
		 * @param @return
		 * @param @throws Exception    设定文件
		 * @return Message    返回类型
		 * @throws
		 */
		public static Message createSimpleMail(Session session,String receiveMailAccount) throws Exception {
			MimeMessage message = new MimeMessage(session);
			// 设置发送邮件地址,param1 代表发送地址 param2 代表发送的名称(任意的) param3 代表名称编码方式
			message.setFrom(new InternetAddress("574181350@qq.com", "张大大", "utf-8"));
			// 代表收件人
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailAccount, "李小四", "utf-8"));
			// To: 增加收件人（可选）
        /*message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com", "USER_DD", "UTF-8"));
        // Cc: 抄送（可选）
        message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("ee@receive.com", "USER_EE", "UTF-8"));
        // Bcc: 密送（可选）
        message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", "UTF-8"));*/
			// 设置邮件主题
			message.setSubject("测试推邮件");
			// 设置邮件内容
			message.setContent("早安,世界   你最近好吗！ 时间是一个伟大的单位，他消灭了痛苦，带走了悲伤，洗劫了磨难，也成就了人类！ ", "text/html;charset=utf-8");
			// 设置发送时间
			message.setSentDate(new Date());
			// 保存上面的编辑内容
			message.saveChanges();
			// 将上面创建的对象写入本地
			OutputStream out = new FileOutputStream("MyEmail.eml");
			message.writeTo(out);
			out.flush();
			out.close();
			return message;

		}


		/**
         * 用户姓名查找
         */
	@RequestMapping("names")
	public String serch(Model model,HttpServletRequest req, @SessionAttribute("userId") Long userId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		Pageable pa=new PageRequest(page, size);
		String name=null;
		String qufen=null;
		Page<User> pageuser=null;
		List<User> userlist=null;
		
		if(!StringUtil.isEmpty(req.getParameter("title"))){
			name=req.getParameter("title").trim();
		}
		if(!StringUtil.isEmpty(req.getParameter("qufen"))){
			qufen=req.getParameter("qufen").trim();
			
			System.out.println("111");
			if(StringUtil.isEmpty(name)){
				// 查询部门下面的员工
				pageuser = udao.findByFatherId(userId,pa);
			}else{
				// 查询名字模糊查询员工
				pageuser = udao.findbyFatherId(name,userId,pa);
			}
			
		}else{
			System.out.println("222");
			if(StringUtil.isEmpty(name)){
				//查看用户并分页
				//pageuser=udao.findAll(pa);
				pageuser=udao.findByNotIsLock(pa);

			}else{
				pageuser=udao.findbyUserNameLike(name, pa);
			}
		}
		userlist=pageuser.getContent();
		// 查询部门表
		Iterable<Dept> deptlist = ddao.findAll();
		// 查职位表
		Iterable<Position> poslist = pdao.findAll();
		model.addAttribute("emplist", userlist);
		model.addAttribute("page", pageuser);
		model.addAttribute("deptlist", deptlist);
		model.addAttribute("poslist", poslist);
		model.addAttribute("url", "names");
		
		return "common/recivers";
		
	}


	/**
	 * 用户姓名查找
	 */
	@RequestMapping("namesa")
	public String sercha(Model model,HttpServletRequest req, @SessionAttribute("userId") Long userId,
						@RequestParam(value = "page", defaultValue = "0") int page,
						@RequestParam(value = "size", defaultValue = "10") int size){
		Pageable pa=new PageRequest(page, size);
		String name=null;
		String qufen=null;
		Page<User> pageuser=null;
		List<User> userlist=null;

		if(!StringUtil.isEmpty(req.getParameter("title"))){
			name=req.getParameter("title").trim();
		}
		if(!StringUtil.isEmpty(req.getParameter("qufen"))){
			qufen=req.getParameter("qufen").trim();

			System.out.println("111");
			if(StringUtil.isEmpty(name)){
				// 查询部门下面的员工
				pageuser = udao.findByFatherId(userId,pa);
			}else{
				// 查询名字模糊查询员工
				pageuser = udao.findbyFatherId(name,userId,pa);
			}

		}else{
			System.out.println("222");
			if(StringUtil.isEmpty(name)){
				//查看用户并分页
				//pageuser=udao.findAll(pa);
				pageuser=udao.findByNotIsLock(pa);
			}else{
				pageuser=udao.findbyUserNameLike(name, pa);
			}
		}
		userlist=pageuser.getContent();
		// 查询部门表
		Iterable<Dept> deptlist = ddao.findAll();
		// 查职位表
		Iterable<Position> poslist = pdao.findAll();
		model.addAttribute("emplist", userlist);
		model.addAttribute("page", pageuser);
		model.addAttribute("deptlist", deptlist);
		model.addAttribute("poslist", poslist);
		model.addAttribute("url", "namesa");

		return "common/reciversf";

	}


	/**
	 * 用户姓名查找
	 */
	@RequestMapping("namesab")
	public String serchs(Model model,HttpServletRequest req, @SessionAttribute("userId") Long userId,
						@RequestParam(value = "page", defaultValue = "0") int page,
						@RequestParam(value = "size", defaultValue = "15") int size){
		Pageable pa=new PageRequest(page, size);
		String name=null;
		String qufen=null;
		Page<User> pageuser=null;
		List<User> userlist=null;

		if(!StringUtil.isEmpty(req.getParameter("title"))){
			name=req.getParameter("title").trim();
		}
		if(!StringUtil.isEmpty(req.getParameter("qufen"))){
			qufen=req.getParameter("qufen").trim();

			System.out.println("111");
			if(StringUtil.isEmpty(name)){
				// 查询部门下面的员工
				pageuser = udao.findByFatherId(userId,pa);
			}else{
				// 查询名字模糊查询员工
				pageuser = udao.findbyFatherId(name,userId,pa);
			}

		}else{
			System.out.println("222");
			if(StringUtil.isEmpty(name)){
				//查看用户并分页
				//pageuser=udao.findAll(pa);
				pageuser=udao.findByNotIsLock(pa);
			}else{
				pageuser=udao.findbyUserNameLike(name, pa);
			}
		}
		userlist=pageuser.getContent();
		// 查询部门表
		Iterable<Dept> deptlist = ddao.findAll();
		// 查职位表
		Iterable<Position> poslist = pdao.findAll();
		model.addAttribute("emplist", userlist);
		model.addAttribute("page", pageuser);
		model.addAttribute("deptlist", deptlist);
		model.addAttribute("poslist", poslist);
		model.addAttribute("url", "names");

		return "common/recivers";

	}
	
	/**
	 * 最近邮件
	 */
	@RequestMapping("amail")
	public  String index3(HttpServletRequest req,@SessionAttribute("userId") Long userId,Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "15") int size) {
		Pageable pa=new PageRequest(page, size);
		User mu=udao.findOne(userId);
		String mess=req.getParameter("title");
		
		Page<Pagemail> pagelist=null;
		Page<Inmaillist> pagemail=null;
		List<Map<String, Object>> maillist=null;
		if(("收件箱").equals(mess)){
			//分页及查找
			pagelist=mservice.recive(page, size, mu, null,mess);
			maillist=mservice.mail(pagelist);
		}else if(("发件箱").equals(mess)){
			pagemail=mservice.inmail(page, size, mu, null,mess);
			maillist=mservice.maillist(pagemail);
		}else if(("草稿箱").equals(mess)){
			pagemail=mservice.inmail(page, size,mu, null,mess);
			maillist=mservice.maillist(pagemail);
		}else{
			//垃圾箱
			//分页及查找
			pagelist=mservice.recive(page, size, mu, null,mess);
			maillist=mservice.mail(pagelist);
			
		}
		
		if(!Objects.isNull(pagelist)){
			model.addAttribute("page", pagelist);
		}else{
			model.addAttribute("page", pagemail);
			
		}
		model.addAttribute("sort", "&title="+mess);
		model.addAttribute("maillist",maillist);
		model.addAttribute("url","mailtitle");
		model.addAttribute("mess",mess);
		return "mail/allmail";
	}
	
	/**
	 * 查看邮件
	 */
	@RequestMapping("smail")
	public  String index4(HttpServletRequest req,@SessionAttribute("userId") Long userId,Model model) {
		User mu=udao.findOne(userId);
		//邮件id
		Long id=Long.parseLong(req.getParameter("id"));
		//title
		String title=req.getParameter("title");
		//找到中间表信息
		if(("收件箱").equals(title)||("垃圾箱").equals(title)){
			Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(mu,id);
			mailr.setRead(true);
			mrdao.save(mailr);
		}
		
		//找到该邮件信息
		Inmaillist mail=imdao.findOne(id);
		String filetype=null;
		if(!Objects.isNull(mail.getMailFileid())){
			String filepath= mail.getMailFileid().getAttachmentPath();
			System.out.println(filepath);
				if(mail.getMailFileid().getAttachmentType().startsWith("image")){
					
					filetype="img";
				}else{
					filetype="appli";
					
				}
		model.addAttribute("filepath", filepath);
		model.addAttribute("filetype", filetype);
		}
		if(StringUtil.isNotEmpty(mail.getMailFiles())){
			List<Attachment> filepath = new ArrayList<>();
			String[] idstr = mail.getMailFiles().split(",");
			for (int i = 0; i < idstr.length; i++) {
				if (StringUtil.isNotEmpty(idstr[i])){
					Long attrid = Long.parseLong(idstr[i]);
					Attachment attachment = AttDao.findOne(attrid);
					filepath.add(attachment);
				}
			}
			model.addAttribute("fileatta", filepath);
		}

		
		User pushuser=udao.findOne(mail.getMailUserid().getUserId());
		model.addAttribute("pushname", pushuser.getRealName());
		model.addAttribute("pushdept", pushuser.getDept().getDeptName());
		model.addAttribute("mail", mail);
		model.addAttribute("mess", title);
		model.addAttribute("file", mail.getMailFileid());
		
		return "mail/seemail";
	}

	/**
	 * 
	 */
	@RequestMapping("refresh")
	public String refresh(HttpServletRequest req,@SessionAttribute("userId") Long userId,Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		//查找用户
		User user=udao.findOne(userId);
		String title=req.getParameter("title");
		Page<Pagemail> pagelist=null;
		List<Map<String, Object>> maillist=null;
		//得到恢复删除id
		String ids=req.getParameter("ids");

		StringTokenizer st = new StringTokenizer(ids, ",");
		while (st.hasMoreElements()) {
			//找到该用户联系邮件的中间记录
			Mailreciver	mailr=mrdao.findbyReciverIdAndmailId(user,Long.parseLong(st.nextToken()));
			if(!Objects.isNull(mailr)){
				mailr.setDel(false);
				mrdao.save(mailr);
			}else{
				return "redirect:/notlimit";
				}
		}
		//分页及查找
		pagelist=mservice.recive(page, size, user, null,title);
		maillist=mservice.mail(pagelist);
		
		model.addAttribute("page", pagelist);
		model.addAttribute("maillist",maillist);
		model.addAttribute("url","mailtitle");
		model.addAttribute("mess", title);
		
		return "mail/mailbody";
		
	}
}
