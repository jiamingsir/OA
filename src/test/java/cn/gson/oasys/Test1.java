package cn.gson.oasys;

import java.util.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import cn.gson.oasys.mappers.NoticeMapper;
import cn.gson.oasys.model.dao.attendcedao.AttendceService;
import cn.gson.oasys.model.dao.processdao.NotepaperDao;
import cn.gson.oasys.model.dao.user.UserDao;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {
	@Autowired
	private NotepaperDao notepaperDao;
	
	@Autowired
	private NoticeMapper nm;
	
	@Autowired
	AttendceService attendceService;
	@Autowired
	UserDao uDao;

	@Test
	public void test(){
		PageHelper .startPage(0, 10);
		List<Map<String, Object>> list=nm.findMyNotice(1L);
		PageInfo<Map<String, Object>> info=new PageInfo<Map<String, Object>>(list);
		System.out.println(info);
	}
	
	@Test
	public void test2(){
		String str="罗祥";
		try {
			System.out.println(PinyinHelper.convertToPinyinString(str, "",PinyinFormat.WITH_TONE_MARK));
			System.out.println(PinyinHelper.convertToPinyinString(str, "",PinyinFormat.WITH_TONE_NUMBER));
			System.out.println(PinyinHelper.convertToPinyinString(str, "",PinyinFormat.WITHOUT_TONE));
			System.out.println(PinyinHelper.getShortPinyin(str));
		} catch (PinyinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void getDifferent2() {
		List<String> atdOperdateList = new ArrayList<String>();
		atdOperdateList.add("2019-11-29 09:44:03");
		atdOperdateList.add("2019-11-28 09:44:03");
		atdOperdateList.add("2019-11-27 09:44:03");
		atdOperdateList.add("2019-11-26 09:44:03");


		List<String> atdOperdate2List = new ArrayList<String>();
		atdOperdate2List.add("2019-11-29 09:44:03");
		atdOperdate2List.add("2019-11-28 09:44:03");

		Collection realA = new ArrayList<String>(atdOperdateList);
		Collection realB = new ArrayList<String>(atdOperdate2List);

		//全集 ：打卡机考勤数据库
		//交集 ：OA烤考勤数据库
// 求交集
		realA.retainAll(realB);
		System.out.println("交集结果：" + realA);
		Set result = new HashSet();
// 求全集
		result.addAll(atdOperdateList);
		result.addAll(atdOperdate2List);
		System.out.println("全集结果：" + result);
// 求差集：结果
		Collection aa = new ArrayList(realA);
		Collection bb = new ArrayList(result);
		bb.removeAll(aa);
		System.out.println("最终结果：" + bb);


	}


	@Test
	private void WoffMap(){
		Map<String,String> aae = new HashMap<>();
		aae.put("unie046","1");
		aae.put("unie4e1","2");
		aae.put("unie911","3");
		aae.put("unie88f","4");
		aae.put("unif2c0","5");
		aae.put("unif886","6");
		aae.put("unie953","7");
		aae.put("unif613","8");
		aae.put("unif4c2","9");
		aae.put("unie4b3","0");
		aae.put("unieacb","店");
		aae.put("unif39c","中");
		aae.put("unif225","美");
		aae.put("unif11a","家");
		aae.put("unie9b9","馆");
		aae.put("unif3ec","小");
		aae.put("unie563","车");
		aae.put("unif3f6","大");
		aae.put("unif70b","市");
		aae.put("unie3fc","公");
		aae.put("unif585","酒");
		aae.put("unie7af","行");
		aae.put("uniecbe","国");
		aae.put("unie3cd","品");
		aae.put("unie447","发");
		aae.put("uniebfd","电");
		aae.put("unie20e","金");
		aae.put("unie514","心");
		aae.put("unie2c2","业");
		aae.put("unie055","商");
		aae.put("unif6ef","司");
		aae.put("unif5ef","超");
		aae.put("unie45c","生");
		aae.put("unif784","装");
		aae.put("unif4a7","园");
		aae.put("unied90","场");
		aae.put("unif4d0","食");
		aae.put("unie266","有");
		aae.put("unif557","新");
		aae.put("unie058","限");
		aae.put("unie2ee","天");
		aae.put("unif0e4","面");
		aae.put("uniec1c","工");
		aae.put("unif1b1","服");
		aae.put("unif61b","海");
		aae.put("unie545","华");
		aae.put("uniece7","水");
		aae.put("unif5fd","房");
		aae.put("unie853","饰");
		aae.put("unie518","城");
		aae.put("unie31e","乐");
		aae.put("uniede4","汽");
		aae.put("unif8c0","香");
		aae.put("uniee31","部");
		aae.put("unif515","利");
		aae.put("unif04e","子");
		aae.put("unie1a3","老");
		aae.put("unieb11","艺");
		aae.put("unie281","花");
		aae.put("unie5d3","专");
		aae.put("uniec9b","东");
		aae.put("unif6bc","肉");
		aae.put("unif2cf","菜");
		aae.put("uniebef","学");
		aae.put("unif426","福");
		aae.put("uniebb0","饭");
		aae.put("unief5b","人");
		aae.put("unie5d4","百");
		aae.put("unie383","餐");
		aae.put("unie1d7","茶");
		aae.put("unif7a0","务");
		aae.put("unie4a6","通");
		aae.put("unied32","味");
		aae.put("unif746","所");
		aae.put("unie0ca","山");
		aae.put("unif088","区");
		aae.put("unie0da","门");
		aae.put("uniec00","药");
		aae.put("unie079","银");
		aae.put("unie957","农");
		aae.put("uniedbf","龙");
		aae.put("unie0cf","停");
		aae.put("uniec69","尚");
		aae.put("unie257","安");
		aae.put("unif5a2","广");
		aae.put("unie224","一");
		aae.put("unif2cc","鑫");
		aae.put("unie96c","容");
		aae.put("unif051","动");
		aae.put("unif44b","南");
		aae.put("unie136","具");
		aae.put("unif277","源");
		aae.put("unie4ed","兴");
		aae.put("unie0c2","鲜");
		aae.put("unie66d","记");
		aae.put("unif5a1","时");
		aae.put("unif8a8","机");
		aae.put("unieb5c","烤");
		aae.put("unie321","文");
		aae.put("unief57","康");
		aae.put("unie1e7","信");
		aae.put("unie469","果");
		aae.put("unie870","阳");
		aae.put("unied4e","理");
		aae.put("unif68f","锅");
		aae.put("uniebe2","宝");
		aae.put("unie27f","达");
		aae.put("uniec5a","地");
		aae.put("unif3b9","儿");
		aae.put("unif541","衣");
		aae.put("unie502","特");
		aae.put("unif834","产");
		aae.put("unief82","西");
		aae.put("unie513","批");
		aae.put("unie9e4","坊");
		aae.put("unif360","州");
		aae.put("unief7d","牛");
		aae.put("unif57d","佳");
		aae.put("uniea1e","化");
		aae.put("unie0e4","五");
		aae.put("unif24a","米");
		aae.put("uniee26","修");
		aae.put("unie7d0","爱");
		aae.put("unif565","北");
		aae.put("unie7aa","养");
		aae.put("uniecaf","卖");
		aae.put("unie3ac","建");
		aae.put("unif802","材");
		aae.put("unie29d","三");
		aae.put("unieb57","会");
		aae.put("uniec62","鸡");
		aae.put("unie6dd","室");
		aae.put("unif5ed","红");
		aae.put("unie273","站");
		aae.put("unif76e","德");
		aae.put("unif48f","王");
		aae.put("unie214","光");
		aae.put("uniebdd","名");
		aae.put("uniefa7","丽");
		aae.put("unif110","油");
		aae.put("unif80d","院");
		aae.put("unie420","堂");
		aae.put("uniecd4","烧");
		aae.put("unif46c","江");
		aae.put("unieb41","社");
		aae.put("uniea9e","合");
		aae.put("unie919","星");
		aae.put("unif290","货");
		aae.put("uniec85","型");
		aae.put("unief0f","村");
		aae.put("unif2e1","自");
		aae.put("uniedc8","科");
		aae.put("unie412","快");
		aae.put("unied9a","便");
		aae.put("unif5ad","日");
		aae.put("unie735","民");
		aae.put("unieb81","营");
		aae.put("unieb5e","和");
		aae.put("unie054","活");
		aae.put("unied31","童");
		aae.put("unie1f2","明");
		aae.put("unied8a","器");
		aae.put("unieb94","烟");
		aae.put("unie3fb","育");
		aae.put("unie2b4","宾");
		aae.put("unif803","精");
		aae.put("uniedff","屋");
		aae.put("unie30c","经");
		aae.put("unif70d","居");
		aae.put("unif724","庄");
		aae.put("unie94f","石");
		aae.put("unie95e","顺");
		aae.put("unif87f","林");
		aae.put("unif1f5","尔");
		aae.put("unif5d4","县");
		aae.put("unif6d9","手");
		aae.put("uniea40","厅");
		aae.put("unif076","销");
		aae.put("unie3c0","用");
		aae.put("unief58","好");
		aae.put("unied09","客");
		aae.put("unie234","火");
		aae.put("unif3fa","雅");
		aae.put("unif202","盛");
		aae.put("unieace","体");
		aae.put("uniec14","旅");
		aae.put("unif074","之");
		aae.put("uniea1f","鞋");
		aae.put("unif18d","辣");
		aae.put("unif2a7","作");
		aae.put("unif2f3","粉");
		aae.put("unif6f5","包");
		aae.put("unif2eb","楼");
		aae.put("unie6d3","校");
		aae.put("unif749","鱼");
		aae.put("unif169","平");
		aae.put("unif18c","彩");
		aae.put("unieeec","上");
		aae.put("unie603","吧");
		aae.put("unie74f","保");
		aae.put("unif4ea","永");
		aae.put("unie507","万");
		aae.put("unied5c","物");
		aae.put("unie2e8","教");
		aae.put("unie344","吃");
		aae.put("unif871","设");
		aae.put("unie14d","医");
		aae.put("unif8bc","正");
		aae.put("unie4db","造");
		aae.put("unif8a4","丰");
		aae.put("unif771","健");
		aae.put("unie199","点");
		aae.put("unie18b","汤");
		aae.put("unie873","网");
		aae.put("unif409","庆");
		aae.put("unie1aa","技");
		aae.put("unif387","斯");
		aae.put("unif6ce","洗");
		aae.put("unied8c","料");
		aae.put("unie146","配");
		aae.put("uniea20","汇");
		aae.put("unie5b9","木");
		aae.put("unie7ef","缘");
		aae.put("unie620","加");
		aae.put("unie573","麻");
		aae.put("unief22","联");
		aae.put("unif87b","卫");
		aae.put("unif3bc","川");
		aae.put("unie760","泰");
		aae.put("unie312","色");
		aae.put("unif02b","世");
		aae.put("unieb5f","方");
		aae.put("unif6fe","寓");
		aae.put("unif769","风");
		aae.put("unieb78","幼");
		aae.put("unieb5a","羊");
		aae.put("unif0c9","烫");
		aae.put("unie497","来");
		aae.put("unif369","高");
		aae.put("unif7bd","厂");
		aae.put("unie2a3","兰");
		aae.put("unied1a","阿");
		aae.put("unif02d","贝");
		aae.put("unie73f","皮");
		aae.put("unif5aa","全");
		aae.put("unief11","女");
		aae.put("unif8e3","拉");
		aae.put("unie943","成");
		aae.put("unie06f","云");
		aae.put("unif2a9","维");
		aae.put("unie638","贸");
		aae.put("unie1ff","道");
		aae.put("unif0b8","术");
		aae.put("unif740","运");
		aae.put("unie8ae","都");
		aae.put("uniec30","口");
		aae.put("unieaac","博");
		aae.put("unif520","河");
		aae.put("unie7cf","瑞");
		aae.put("unif3eb","宏");
		aae.put("unie341","京");
		aae.put("unif4e2","际");
		aae.put("unif2dc","路");
		aae.put("unieae6","祥");
		aae.put("unie6ec","青");
		aae.put("unie41d","镇");
		aae.put("unie3c7","厨");
		aae.put("unif2c7","培");
		aae.put("unie543","力");
		aae.put("unie3d9","惠");
		aae.put("unie7b5","连");
		aae.put("unied38","马");
		aae.put("unie2e9","鸿");
		aae.put("unie852","钢");
		aae.put("unie5de","训");
		aae.put("unif1e5","影");
		aae.put("uniea96","甲");
		aae.put("uniea82","助");
		aae.put("unie763","窗");
		aae.put("unie3e5","布");
		aae.put("uniecb8","富");
		aae.put("unie51b","牌");
		aae.put("unif093","头");
		aae.put("unie569","四");
		aae.put("unie61f","多");
		aae.put("unief6d","妆");
		aae.put("unif13d","吉");
		aae.put("unie72b","苑");
		aae.put("unif5f1","沙");
		aae.put("unieea6","恒");
		aae.put("unied22","隆");
		aae.put("unie5c1","春");
		aae.put("unif2b7","干");
		aae.put("uniea81","饼");
		aae.put("unied87","氏");
		aae.put("unie062","里");
		aae.put("unie1ee","二");
		aae.put("unie081","管");
		aae.put("unif370","诚");
		aae.put("uniedc7","制");
		aae.put("unif1e9","售");
		aae.put("unif745","嘉");
		aae.put("unie0ac","长");
		aae.put("unif6c4","轩");
		aae.put("unief3a","杂");
		aae.put("unie77c","副");
		aae.put("unie5d1","清");
		aae.put("uniec52","计");
		aae.put("unie089","黄");
		aae.put("unie80d","讯");
		aae.put("unif46d","太");
		aae.put("unie0a6","鸭");
		aae.put("unie781","号");
		aae.put("unif4c3","街");
		aae.put("unie24b","交");
		aae.put("uniee71","与");
		aae.put("unie8c9","叉");
		aae.put("unif53d","附");
		aae.put("unif3a4","近");
		aae.put("unieb0e","层");
		aae.put("uniebe8","旁");
		aae.put("unie9ed","对");
		aae.put("unie9c4","巷");
		aae.put("unie967","栋");
		aae.put("unie4c3","环");
		aae.put("unif7d5","省");
		aae.put("unif836","桥");
		aae.put("unif601","湖");
		aae.put("uniecaa","段");
		aae.put("unie36b","乡");
		aae.put("uniebd3","厦");
		aae.put("unieb20","府");
		aae.put("unie42f","铺");
		aae.put("unif733","内");
		aae.put("unif463","侧");
		aae.put("unied11","元");
		aae.put("unie40e","购");
		aae.put("unieba2","前");
		aae.put("unif174","幢");
		aae.put("unie419","滨");
		aae.put("uniec50","处");
		aae.put("unie5ac","向");
		aae.put("unie185","座");
		aae.put("unif662","下");
		aae.put("unie7a4","鼎");
		aae.put("unif6e6","凤");
		aae.put("unie5aa","港");
		aae.put("unie82f","开");
		aae.put("unie050","关");
		aae.put("uniea75","景");
		aae.put("unie418","泉");
		aae.put("uniefdc","塘");
		aae.put("unie6e9","放");
		aae.put("uniec9a","昌");
		aae.put("unieb9d","线");
		aae.put("unif022","湾");
		aae.put("unif208","政");
		aae.put("unie1c4","步");
		aae.put("unif716","宁");
		aae.put("unie456","解");
		aae.put("unif69a","白");
		aae.put("unieec9","田");
		aae.put("unie75d","町");
		aae.put("unie643","溪");
		aae.put("unif378","十");
		aae.put("unif6a5","八");
		aae.put("unie94d","古");
		aae.put("unie55d","双");
		aae.put("unieaf7","胜");
		aae.put("unie05b","本");
		aae.put("unie961","单");
		aae.put("unie7fe","同");
		aae.put("unieab6","九");
		aae.put("unie31f","迎");
		aae.put("unif855","第");
		aae.put("unie11a","台");
		aae.put("unie741","玉");
		aae.put("unie3b0","锦");
		aae.put("unie417","底");
		aae.put("unif83d","后");
		aae.put("uniee6c","七");
		aae.put("unie90e","斜");
		aae.put("unif8dd","期");
		aae.put("uniefa5","武");
		aae.put("unief7b","岭");
		aae.put("uniebf3","松");
		aae.put("unied3a","角");
		aae.put("unif103","纪");
		aae.put("unif840","朝");
		aae.put("uniee30","峰");
		aae.put("unif6cd","六");
		aae.put("unif2b4","振");
		aae.put("unif7df","珠");
		aae.put("unief80","局");
		aae.put("unie91b","岗");
		aae.put("unif1b6","洲");
		aae.put("uniead7","横");
		aae.put("unie8fa","边");
		aae.put("unif3ca","济");
		aae.put("uniefe1","井");
		aae.put("unif8b1","办");
		aae.put("unif705","汉");
		aae.put("unie65f","代");
		aae.put("unie275","临");
		aae.put("unie0ee","弄");
		aae.put("unie558","团");
		aae.put("unie2f8","外");
		aae.put("unie99e","塔");
		aae.put("unie7c1","杨");
		aae.put("unie5f6","铁");
		aae.put("unie600","浦");
		aae.put("unie287","字");
		aae.put("unie04c","年");
		aae.put("unie8bb","岛");
		aae.put("unie6de","陵");
		aae.put("unif850","原");
		aae.put("unie10a","梅");
		aae.put("uniebdf","进");
		aae.put("unif05c","荣");
		aae.put("unie922","友");
		aae.put("unif744","虹");
		aae.put("unie078","央");
		aae.put("unie1e8","桂");
		aae.put("uniec28","沿");
		aae.put("unie2dc","事");
		aae.put("uniedc5","津");
		aae.put("uniee3f","凯");
		aae.put("unie5c8","莲");
		aae.put("unie75c","丁");
		aae.put("unie2af","秀");
		aae.put("unif393","柳");
		aae.put("uniee65","集");
		aae.put("unif28e","紫");
		aae.put("unie599","旗");
		aae.put("unif121","张");
		aae.put("uniec25","谷");
		aae.put("unie093","的");
		aae.put("unif075","是");
		aae.put("unief04","不");
		aae.put("unie5fe","了");
		aae.put("unif1b0","很");
		aae.put("unie16c","还");
		aae.put("uniec65","个");
		aae.put("uniec9c","也");
		aae.put("unie2bf","这");
		aae.put("uniebcc","我");
		aae.put("unie6e6","就");
		aae.put("unief96","在");
		aae.put("unie828","以");
		aae.put("unie49a","可");
		aae.put("unie9be","到");
		aae.put("unie3ab","错");
		aae.put("unied69","没");
		aae.put("unie578","去");
		aae.put("unif025","过");
		aae.put("uniee64","感");
		aae.put("unif63e","次");
		aae.put("unif141","要");
		aae.put("unie150","比");
		aae.put("unie3ee","觉");
		aae.put("unie10e","看");
		aae.put("unie745","得");
		aae.put("unie553","说");
		aae.put("unif2aa","常");
		aae.put("unif610","真");
		aae.put("unif253","们");
		aae.put("unif52d","但");
		aae.put("unie22d","最");
		aae.put("unif429","喜");
		aae.put("unif6e8","哈");
		aae.put("uniec2e","么");
		aae.put("unif1fa","别");
		aae.put("uniee32","位");
		aae.put("uniecd9","能");
		aae.put("unif1f4","较");
		aae.put("unie0a3","境");
		aae.put("uniee34","非");
		aae.put("unif3b7","为");
		aae.put("uniee79","欢");
		aae.put("unif6c3","然");
		aae.put("unie8df","他");
		aae.put("unie528","挺");
		aae.put("unie982","着");
		aae.put("unif2e2","价");
		aae.put("unie06b","那");
		aae.put("unif2ba","意");
		aae.put("unif550","种");
		aae.put("uniedb4","想");
		aae.put("unif06c","出");
		aae.put("unieae4","员");
		aae.put("unif7c2","两");
		aae.put("unie796","推");
		aae.put("unieb67","做");
		aae.put("unif6e1","排");
		aae.put("unie41e","实");
		aae.put("unie6ff","分");
		aae.put("unie7f1","间");
		aae.put("unif5ea","甜");
		aae.put("unie6ce","度");
		aae.put("uniec86","起");
		aae.put("unif1f3","满");
		aae.put("unie718","给");
		aae.put("unie125","热");
		aae.put("unie876","完");
		aae.put("unie68d","格");
		aae.put("unif170","荐");
		aae.put("unif28d","喝");
		aae.put("unieeae","等");
		aae.put("unieb52","其");
		aae.put("unif621","再");
		aae.put("uniedfa","几");
		aae.put("unif41a","只");
		aae.put("uniece6","现");
		aae.put("unif3c3","朋");
		aae.put("unie78a","候");
		aae.put("unie0e1","样");
		aae.put("unieafd","直");
		aae.put("unie4f5","而");
		aae.put("unieb60","买");
		aae.put("unie940","于");
		aae.put("unif218","般");
		aae.put("uniecf0","豆");
		aae.put("unie00a","量");
		aae.put("unie0b6","选");
		aae.put("unieff8","奶");
		aae.put("unie4e0","打");
		aae.put("unied35","每");
		aae.put("unif3b4","评");
		aae.put("unif4cb","少");
		aae.put("unif03a","算");
		aae.put("unif21f","又");
		aae.put("unie8eb","因");
		aae.put("unie980","情");
		aae.put("uniee92","找");
		aae.put("unie71e","些");
		aae.put("unieae7","份");
		aae.put("unie7e6","置");
		aae.put("unie00f","适");
		aae.put("unie36f","什");
		aae.put("uniebc3","蛋");
		aae.put("unie9e7","师");
		aae.put("unif13a","气");
		aae.put("unie3f2","你");
		aae.put("unif337","姐");
		aae.put("unie130","棒");
		aae.put("unie2ce","试");
		aae.put("unif0e5","总");
		aae.put("unie3b8","定");
		aae.put("uniedfc","啊");
		aae.put("uniea0f","足");
		aae.put("unie808","级");
		aae.put("unif43e","整");
		aae.put("unif867","带");
		aae.put("unif13e","虾");
		aae.put("unif3db","如");
		aae.put("unie0af","态");
		aae.put("unie28e","且");
		aae.put("uniec33","尝");
		aae.put("unie494","主");
		aae.put("uniefc9","话");
		aae.put("unif5e4","强");
		aae.put("unif34d","当");
		aae.put("unief4f","更");
		aae.put("unie6f9","板");
		aae.put("unie596","知");
		aae.put("unif525","己");
		aae.put("unie6d6","无");
		aae.put("unie4e6","酸");
		aae.put("unif5e9","让");
		aae.put("unif70f","入");
		aae.put("unif13f","啦");
		aae.put("unie9fc","式");
		aae.put("unie8f6","笑");
		aae.put("unie2ae","赞");
		aae.put("unif67f","片");
		aae.put("uniebe9","酱");
		aae.put("unie278","差");
		aae.put("unie675","像");
		aae.put("unif71b","提");
		aae.put("unie6a2","队");
		aae.put("unie634","走");
		aae.put("unie7de","嫩");
		aae.put("unie8d8","才");
		aae.put("uniee73","刚");
		aae.put("uniee03","午");
		aae.put("unieec6","接");
		aae.put("unif2d3","重");
		aae.put("unif1f6","串");
		aae.put("unie45d","回");
		aae.put("unie2e6","晚");
		aae.put("unie284","微");
		aae.put("unie268","周");
		aae.put("unieafb","值");
		aae.put("uniecf6","费");
		aae.put("unif773","性");
		aae.put("unif0da","桌");
		aae.put("unie649","拍");
		aae.put("unie5e2","跟");
		aae.put("uniec0e","块");
		aae.put("unif1ab","调");
		aae.put("uniea56","糕");



		Map<String,String> aaa97 = new HashMap<>();

		aaa97.put("unie757","1");
		aaa97.put("unief2d","2");
		aaa97.put("unif3a0","3");
		aaa97.put("unif53b","4");
		aaa97.put("unie596","5");
		aaa97.put("unif167","6");
		aaa97.put("unieac3","7");
		aaa97.put("unie91d","8");
		aaa97.put("unif73b","9");
		aaa97.put("unie37d","0");
		aaa97.put("uniea82","店");
		aaa97.put("unif11a","中");
		aaa97.put("unie6f5","美");
		aaa97.put("unie756","家");
		aaa97.put("unie7a5","馆");
		aaa97.put("unif44f","小");
		aaa97.put("unied0f","车");
		aaa97.put("unif180","大");
		aaa97.put("unif100","市");
		aaa97.put("unie09e","公");
		aaa97.put("unie21f","酒");
		aaa97.put("unif16a","行");
		aaa97.put("unie58c","国");
		aaa97.put("unie798","品");
		aaa97.put("unied73","发");
		aaa97.put("unif60e","电");
		aaa97.put("unie217","金");
		aaa97.put("unif364","心");
		aaa97.put("unif293","业");
		aaa97.put("unie90c","商");
		aaa97.put("unie44d","司");
		aaa97.put("unie710","超");
		aaa97.put("unif8b2","生");
		aaa97.put("unif7dc","装");
		aaa97.put("unif013","园");
		aaa97.put("unie942","场");
		aaa97.put("unie48e","食");
		aaa97.put("unieea5","有");
		aaa97.put("unie301","新");
		aaa97.put("uniee19","限");
		aaa97.put("unif2d7","天");
		aaa97.put("unie637","面");
		aaa97.put("unif621","工");
		aaa97.put("unie3e2","服");
		aaa97.put("unif430","海");
		aaa97.put("uniec19","华");
		aaa97.put("unif5e8","水");
		aaa97.put("unie5c1","房");
		aaa97.put("unif36d","饰");
		aaa97.put("unie163","城");
		aaa97.put("unif7b7","乐");
		aaa97.put("unie3f5","汽");
		aaa97.put("uniebc3","香");
		aaa97.put("unif4ea","部");
		aaa97.put("unif2a9","利");
		aaa97.put("unif2bb","子");
		aaa97.put("unie9f3","老");
		aaa97.put("unie05b","艺");
		aaa97.put("unie5ad","花");
		aaa97.put("unie6f4","专");
		aaa97.put("unie941","东");
		aaa97.put("unif5a1","肉");
		aaa97.put("unie82d","菜");
		aaa97.put("unif371","学");
		aaa97.put("unif09d","福");
		aaa97.put("unie6b9","饭");
		aaa97.put("unied5c","人");
		aaa97.put("unif2f0","百");
		aaa97.put("unie408","餐");
		aaa97.put("unif040","茶");
		aaa97.put("unie5fb","务");
		aaa97.put("unif582","通");
		aaa97.put("unief02","味");
		aaa97.put("unif73c","所");
		aaa97.put("unie8a6","山");
		aaa97.put("unif0eb","区");
		aaa97.put("unif7ed","门");
		aaa97.put("unie5b1","药");
		aaa97.put("unie0d9","银");
		aaa97.put("unif030","农");
		aaa97.put("unied6d","龙");
		aaa97.put("unie421","停");
		aaa97.put("unif1d0","尚");
		aaa97.put("uniecca","安");
		aaa97.put("unif433","广");
		aaa97.put("uniec42","鑫");
		aaa97.put("unie4c1","一");
		aaa97.put("unif143","容");
		aaa97.put("unif600","动");
		aaa97.put("unif6a6","南");
		aaa97.put("uniee00","具");
		aaa97.put("unif2a3","源");
		aaa97.put("unie45e","兴");
		aaa97.put("unie3ab","鲜");
		aaa97.put("unieb87","记");
		aaa97.put("unieac0","时");
		aaa97.put("unie777","机");
		aaa97.put("unie6c1","烤");
		aaa97.put("unif853","文");
		aaa97.put("unie191","康");
		aaa97.put("unie800","信");
		aaa97.put("unif230","果");
		aaa97.put("unieb3a","阳");
		aaa97.put("unif05c","理");
		aaa97.put("unif2b1","锅");
		aaa97.put("unif03f","宝");
		aaa97.put("unieb13","达");
		aaa97.put("uniebe2","地");
		aaa97.put("unieab3","儿");
		aaa97.put("unif301","衣");
		aaa97.put("unif6b3","特");
		aaa97.put("unie7c1","产");
		aaa97.put("unie676","西");
		aaa97.put("unif460","批");
		aaa97.put("unie424","坊");
		aaa97.put("unif5af","州");
		aaa97.put("unie597","牛");
		aaa97.put("unif5df","佳");
		aaa97.put("unif1bd","化");
		aaa97.put("uniec2a","五");
		aaa97.put("unif4fe","米");
		aaa97.put("unie8bb","修");
		aaa97.put("uniefd2","爱");
		aaa97.put("unif014","北");
		aaa97.put("unie36e","养");
		aaa97.put("unif5a7","卖");
		aaa97.put("unie489","建");
		aaa97.put("unif044","材");
		aaa97.put("unif67b","三");
		aaa97.put("unieee6","会");
		aaa97.put("unif6ab","鸡");
		aaa97.put("unied1c","室");
		aaa97.put("unie21e","红");
		aaa97.put("unie4d1","站");
		aaa97.put("unif3bd","德");
		aaa97.put("uniefed","王");
		aaa97.put("unie14d","光");
		aaa97.put("unie78a","名");
		aaa97.put("unif255","丽");
		aaa97.put("unif128","油");
		aaa97.put("unif1a3","院");
		aaa97.put("unieaa8","堂");
		aaa97.put("unie9dd","烧");
		aaa97.put("unie426","江");
		aaa97.put("unie5b2","社");
		aaa97.put("unie563","合");
		aaa97.put("unie09c","星");
		aaa97.put("unif224","货");
		aaa97.put("unif4d3","型");
		aaa97.put("unieadd","村");
		aaa97.put("unieb33","自");
		aaa97.put("unie672","科");
		aaa97.put("unieb35","快");
		aaa97.put("unif671","便");
		aaa97.put("uniecd0","日");
		aaa97.put("unif842","民");
		aaa97.put("unie583","营");
		aaa97.put("unied7e","和");
		aaa97.put("unieae4","活");
		aaa97.put("unie12e","童");
		aaa97.put("unieb4e","明");
		aaa97.put("unie7ae","器");
		aaa97.put("unie793","烟");
		aaa97.put("unie8f2","育");
		aaa97.put("uniec36","宾");
		aaa97.put("unif4ca","精");
		aaa97.put("uniee24","屋");
		aaa97.put("unie990","经");
		aaa97.put("unie283","居");
		aaa97.put("unie613","庄");
		aaa97.put("unif6e5","石");
		aaa97.put("unif8fd","顺");
		aaa97.put("unif8c1","林");
		aaa97.put("unie43a","尔");
		aaa97.put("unif656","县");
		aaa97.put("unif3b5","手");
		aaa97.put("unif09a","厅");
		aaa97.put("unie0bb","销");
		aaa97.put("unif29d","用");
		aaa97.put("unif054","好");
		aaa97.put("unif173","客");
		aaa97.put("unif233","火");
		aaa97.put("unied4f","雅");
		aaa97.put("unie1b0","盛");
		aaa97.put("unif5a0","体");
		aaa97.put("unie85f","旅");
		aaa97.put("unie166","之");
		aaa97.put("unie8d4","鞋");
		aaa97.put("unif640","辣");
		aaa97.put("unif3fb","作");
		aaa97.put("unieebb","粉");
		aaa97.put("unief10","包");
		aaa97.put("unie3ff","楼");
		aaa97.put("unie2c0","校");
		aaa97.put("unie257","鱼");
		aaa97.put("unif50e","平");
		aaa97.put("unie10d","彩");
		aaa97.put("unif0e6","上");
		aaa97.put("unif0e4","吧");
		aaa97.put("unie15a","保");
		aaa97.put("uniee8f","永");
		aaa97.put("unie6eb","万");
		aaa97.put("unieb11","物");
		aaa97.put("unif52f","教");
		aaa97.put("uniedcd","吃");
		aaa97.put("unie49c","设");
		aaa97.put("unie262","医");
		aaa97.put("unieeed","正");
		aaa97.put("unif8f5","造");
		aaa97.put("unie618","丰");
		aaa97.put("uniebd9","健");
		aaa97.put("unie03e","点");
		aaa97.put("uniee18","汤");
		aaa97.put("unif2cf","网");
		aaa97.put("unif0fa","庆");
		aaa97.put("uniee79","技");
		aaa97.put("unie9ac","斯");
		aaa97.put("unif267","洗");
		aaa97.put("uniea9b","料");
		aaa97.put("unif019","配");
		aaa97.put("uniec59","汇");
		aaa97.put("unif391","木");
		aaa97.put("unif56a","缘");
		aaa97.put("unie9f7","加");
		aaa97.put("unief95","麻");
		aaa97.put("unie7f4","联");
		aaa97.put("unie84e","卫");
		aaa97.put("unif473","川");
		aaa97.put("unief30","泰");
		aaa97.put("unie673","色");
		aaa97.put("unif146","世");
		aaa97.put("unif33d","方");
		aaa97.put("unif10c","寓");
		aaa97.put("unieab4","风");
		aaa97.put("unie526","幼");
		aaa97.put("unied1f","羊");
		aaa97.put("unieffe","烫");
		aaa97.put("unieb0b","来");
		aaa97.put("unie5d1","高");
		aaa97.put("unif4ed","厂");
		aaa97.put("unif19a","兰");
		aaa97.put("unif2c3","阿");
		aaa97.put("unie5bb","贝");
		aaa97.put("unif8b3","皮");
		aaa97.put("unied08","全");
		aaa97.put("unie78b","女");
		aaa97.put("unie594","拉");
		aaa97.put("unieed1","成");
		aaa97.put("unied2c","云");
		aaa97.put("unif17d","维");
		aaa97.put("unif6c1","贸");
		aaa97.put("uniea7b","道");
		aaa97.put("unied3c","术");
		aaa97.put("unie2fc","运");
		aaa97.put("unied9e","都");
		aaa97.put("unif7ec","口");
		aaa97.put("unie996","博");
		aaa97.put("unif2a4","河");
		aaa97.put("unie8ec","瑞");
		aaa97.put("unie80b","宏");
		aaa97.put("unie95d","京");
		aaa97.put("unif6c3","际");
		aaa97.put("unie349","路");
		aaa97.put("unif85b","祥");
		aaa97.put("unif18b","青");
		aaa97.put("unied4d","镇");
		aaa97.put("uniebeb","厨");
		aaa97.put("unif7b1","培");
		aaa97.put("unif1e8","力");
		aaa97.put("uniebbe","惠");
		aaa97.put("unif229","连");
		aaa97.put("unie664","马");
		aaa97.put("unif07a","鸿");
		aaa97.put("unie453","钢");
		aaa97.put("unie1e9","训");
		aaa97.put("unie6dd","影");
		aaa97.put("unied51","甲");
		aaa97.put("unif147","助");
		aaa97.put("unif73d","窗");
		aaa97.put("unieb28","布");
		aaa97.put("unie6cb","富");
		aaa97.put("unie0cb","牌");
		aaa97.put("unie286","头");
		aaa97.put("unif8b1","四");
		aaa97.put("unie26f","多");
		aaa97.put("unif306","妆");
		aaa97.put("uniea43","吉");
		aaa97.put("unie331","苑");
		aaa97.put("unie40b","沙");
		aaa97.put("unie098","恒");
		aaa97.put("unif7f6","隆");
		aaa97.put("unif4b5","春");
		aaa97.put("unif3ca","干");
		aaa97.put("unif65f","饼");
		aaa97.put("unie6bc","氏");
		aaa97.put("unie986","里");
		aaa97.put("unif7a6","二");
		aaa97.put("unie72f","管");
		aaa97.put("unif252","诚");
		aaa97.put("unif719","制");
		aaa97.put("unie297","售");
		aaa97.put("uniee5a","嘉");
		aaa97.put("unif175","长");
		aaa97.put("unif7ae","轩");
		aaa97.put("uniee25","杂");
		aaa97.put("uniee75","副");
		aaa97.put("unie2cf","清");
		aaa97.put("unie2b1","计");
		aaa97.put("unie294","黄");
		aaa97.put("unif854","讯");
		aaa97.put("unie33d","太");
		aaa97.put("unieee4","鸭");
		aaa97.put("unif532","号");
		aaa97.put("unif0a5","街");
		aaa97.put("unie5e3","交");
		aaa97.put("unif1c0","与");
		aaa97.put("unif120","叉");
		aaa97.put("uniefa2","附");
		aaa97.put("unieaf1","近");
		aaa97.put("uniec5e","层");
		aaa97.put("unie430","旁");
		aaa97.put("unif214","对");
		aaa97.put("unie5f9","巷");
		aaa97.put("unie045","栋");
		aaa97.put("unie096","环");
		aaa97.put("unie015","省");
		aaa97.put("unie2e7","桥");
		aaa97.put("unie23b","湖");
		aaa97.put("unif0b0","段");
		aaa97.put("unif138","乡");
		aaa97.put("unie6b1","厦");
		aaa97.put("uniec9e","府");
		aaa97.put("unif815","铺");
		aaa97.put("unie2c3","内");
		aaa97.put("unif1e1","侧");
		aaa97.put("unif7ad","元");
		aaa97.put("unif1d6","购");
		aaa97.put("uniec1f","前");
		aaa97.put("unif24f","幢");
		aaa97.put("unied9c","滨");
		aaa97.put("unie18b","处");
		aaa97.put("unie5dc","向");
		aaa97.put("unie4c7","座");
		aaa97.put("unie79c","下");
		aaa97.put("unif527","鼎");
		aaa97.put("unie3e4","凤");
		aaa97.put("unie044","港");
		aaa97.put("unie876","开");
		aaa97.put("unif681","关");
		aaa97.put("unied87","景");
		aaa97.put("uniee41","泉");
		aaa97.put("unie1f5","塘");
		aaa97.put("unie79b","放");
		aaa97.put("unie6ad","昌");
		aaa97.put("unie033","线");
		aaa97.put("unie617","湾");
		aaa97.put("uniee59","政");
		aaa97.put("unif495","步");
		aaa97.put("unie8f1","宁");
		aaa97.put("unieb2c","解");
		aaa97.put("unie99c","白");
		aaa97.put("unif48d","田");
		aaa97.put("unif659","町");
		aaa97.put("unie7c5","溪");
		aaa97.put("unif3aa","十");
		aaa97.put("unie76e","八");
		aaa97.put("unie59f","古");
		aaa97.put("unif432","双");
		aaa97.put("unif628","胜");
		aaa97.put("unif8f2","本");
		aaa97.put("unif87b","单");
		aaa97.put("unieb58","同");
		aaa97.put("unif663","九");
		aaa97.put("unif57c","迎");
		aaa97.put("unie9b4","第");
		aaa97.put("unie825","台");
		aaa97.put("unieaaa","玉");
		aaa97.put("unie1fb","锦");
		aaa97.put("unie89c","底");
		aaa97.put("unie0d2","后");
		aaa97.put("uniebfe","七");
		aaa97.put("unie099","斜");
		aaa97.put("unif271","期");
		aaa97.put("unif124","武");
		aaa97.put("uniebcb","岭");
		aaa97.put("unie40a","松");
		aaa97.put("unie6ca","角");
		aaa97.put("unie969","纪");
		aaa97.put("unieaff","朝");
		aaa97.put("uniea5a","峰");
		aaa97.put("unif185","六");
		aaa97.put("uniefbf","振");
		aaa97.put("unief92","珠");
		aaa97.put("unif2cd","局");
		aaa97.put("unif1a0","岗");
		aaa97.put("unif720","洲");
		aaa97.put("unie26c","横");
		aaa97.put("unif7bf","边");
		aaa97.put("unif6bf","济");
		aaa97.put("unif42d","井");
		aaa97.put("unieda8","办");
		aaa97.put("unif2a7","汉");
		aaa97.put("unif265","代");
		aaa97.put("unif68b","临");
		aaa97.put("unie60b","弄");
		aaa97.put("unie501","团");
		aaa97.put("unif1e5","外");
		aaa97.put("unie8ea","塔");
		aaa97.put("unie0bd","杨");
		aaa97.put("unif7fc","铁");
		aaa97.put("unie0f4","浦");
		aaa97.put("unie633","字");
		aaa97.put("unieceb","年");
		aaa97.put("unie219","岛");
		aaa97.put("unie586","陵");
		aaa97.put("unieb80","原");
		aaa97.put("unie5e6","梅");
		aaa97.put("unie339","进");
		aaa97.put("unif01b","荣");
		aaa97.put("unie221","友");
		aaa97.put("unie9a3","虹");
		aaa97.put("unie039","央");
		aaa97.put("unif8b9","桂");
		aaa97.put("unie265","沿");
		aaa97.put("unie700","事");
		aaa97.put("unif4f5","津");
		aaa97.put("unied21","凯");
		aaa97.put("unie338","莲");
		aaa97.put("unif05f","丁");
		aaa97.put("uniea6e","秀");
		aaa97.put("unie802","柳");
		aaa97.put("uniee9c","集");
		aaa97.put("unif78d","紫");
		aaa97.put("unif361","旗");
		aaa97.put("unif61c","张");
		aaa97.put("unif171","谷");
		aaa97.put("unif4bd","的");
		aaa97.put("unif1c2","是");
		aaa97.put("unie235","不");
		aaa97.put("unif6c9","了");
		aaa97.put("unif812","很");
		aaa97.put("unie089","还");
		aaa97.put("unie496","个");
		aaa97.put("unif7fa","也");
		aaa97.put("uniec9b","这");
		aaa97.put("unie476","我");
		aaa97.put("unif76b","就");
		aaa97.put("unieff9","在");
		aaa97.put("unif28e","以");
		aaa97.put("unie70a","可");
		aaa97.put("unie88e","到");
		aaa97.put("unif53e","错");
		aaa97.put("unie31e","没");
		aaa97.put("unie565","去");
		aaa97.put("unie8f8","过");
		aaa97.put("unif86f","感");
		aaa97.put("uniea32","次");
		aaa97.put("unie41c","要");
		aaa97.put("uniec99","比");
		aaa97.put("unie711","觉");
		aaa97.put("uniefb3","看");
		aaa97.put("unied46","得");
		aaa97.put("unie631","说");
		aaa97.put("unif6f5","常");
		aaa97.put("unied2d","真");
		aaa97.put("uniec38","们");
		aaa97.put("unieb6f","但");
		aaa97.put("unie091","最");
		aaa97.put("uniec4e","喜");
		aaa97.put("unie028","哈");
		aaa97.put("unif20a","么");
		aaa97.put("unie40c","别");
		aaa97.put("unif260","位");
		aaa97.put("unif136","能");
		aaa97.put("unif210","较");
		aaa97.put("unieba8","境");
		aaa97.put("unie87c","非");
		aaa97.put("unif557","为");
		aaa97.put("unif365","欢");
		aaa97.put("unie8d6","然");
		aaa97.put("unie04a","他");
		aaa97.put("unif2a6","挺");
		aaa97.put("unif115","着");
		aaa97.put("unie519","价");
		aaa97.put("unie909","那");
		aaa97.put("unieafb","意");
		aaa97.put("unieb41","种");
		aaa97.put("unied93","想");
		aaa97.put("unie1b3","出");
		aaa97.put("unif14e","员");
		aaa97.put("uniec0e","两");
		aaa97.put("unif59d","推");
		aaa97.put("unif870","做");
		aaa97.put("unie276","排");
		aaa97.put("unif19d","实");
		aaa97.put("unie68d","分");
		aaa97.put("unie581","间");
		aaa97.put("unie560","甜");
		aaa97.put("unie693","度");
		aaa97.put("unie585","起");
		aaa97.put("unif653","满");
		aaa97.put("unie176","给");
		aaa97.put("unif615","热");
		aaa97.put("uniee0c","完");
		aaa97.put("unie09b","格");
		aaa97.put("unie340","荐");
		aaa97.put("unie900","喝");
		aaa97.put("unie1f4","等");
		aaa97.put("unif1f1","其");
		aaa97.put("unie621","再");
		aaa97.put("unif4db","几");
		aaa97.put("unie5e2","只");
		aaa97.put("unif81f","现");
		aaa97.put("unie080","朋");
		aaa97.put("unif08a","候");
		aaa97.put("unie2c9","样");
		aaa97.put("unif742","直");
		aaa97.put("unie765","而");
		aaa97.put("unie68a","买");
		aaa97.put("unie962","于");
		aaa97.put("unie7a3","般");
		aaa97.put("unied00","豆");
		aaa97.put("unie29c","量");
		aaa97.put("unif649","选");
		aaa97.put("unie742","奶");
		aaa97.put("unif0ca","打");
		aaa97.put("unieba2","每");
		aaa97.put("unif3c3","评");
		aaa97.put("unie379","少");
		aaa97.put("unie94d","算");
		aaa97.put("unie3ee","又");
		aaa97.put("unie68f","因");
		aaa97.put("unief72","情");
		aaa97.put("uniea53","找");
		aaa97.put("unie720","些");
		aaa97.put("uniec7f","份");
		aaa97.put("unif2f6","置");
		aaa97.put("unie30b","适");
		aaa97.put("unie2bb","什");
		aaa97.put("unie3a2","蛋");
		aaa97.put("unief3b","师");
		aaa97.put("uniec93","气");
		aaa97.put("unie9b1","你");
		aaa97.put("unif28b","姐");
		aaa97.put("unie760","棒");
		aaa97.put("unif521","试");
		aaa97.put("unif4bb","总");
		aaa97.put("uniebd6","定");
		aaa97.put("unif4c3","啊");
		aaa97.put("uniea09","足");
		aaa97.put("unif034","级");
		aaa97.put("uniee23","整");
		aaa97.put("unie0dd","带");
		aaa97.put("unie6fc","虾");
		aaa97.put("unif73a","如");
		aaa97.put("unie0b8","态");
		aaa97.put("unie0ff","且");
		aaa97.put("unie316","尝");
		aaa97.put("unieefd","主");
		aaa97.put("unif803","话");
		aaa97.put("unieae1","强");
		aaa97.put("uniedc3","当");
		aaa97.put("unieffc","更");
		aaa97.put("unif0fd","板");
		aaa97.put("unif658","知");
		aaa97.put("uniee68","己");
		aaa97.put("unie04e","无");
		aaa97.put("unif66c","酸");
		aaa97.put("unif25d","让");
		aaa97.put("unie504","入");
		aaa97.put("unied6a","啦");
		aaa97.put("unie904","式");
		aaa97.put("unie11f","笑");
		aaa97.put("unif606","赞");
		aaa97.put("unif7be","片");
		aaa97.put("unif684","酱");
		aaa97.put("uniea77","差");
		aaa97.put("unie9f8","像");
		aaa97.put("uniec57","提");
		aaa97.put("unie357","队");
		aaa97.put("unie19d","走");
		aaa97.put("unif0a6","嫩");
		aaa97.put("unif414","才");
		aaa97.put("unie0ae","刚");
		aaa97.put("unif524","午");
		aaa97.put("uniee92","接");
		aaa97.put("unie64c","重");
		aaa97.put("unief01","串");
		aaa97.put("unif6df","回");
		aaa97.put("unie218","晚");
		aaa97.put("unie9a4","微");
		aaa97.put("unie670","周");
		aaa97.put("unif0ce","值");
		aaa97.put("unie8ed","费");
		aaa97.put("unif8e1","性");
		aaa97.put("unieb86","桌");
		aaa97.put("unieb76","拍");
		aaa97.put("unie910","跟");
		aaa97.put("unieea1","块");
		aaa97.put("unie6f3","调");
		aaa97.put("unif1b2","糕");







	}












}
