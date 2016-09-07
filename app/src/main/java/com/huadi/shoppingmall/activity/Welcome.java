package com.huadi.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import com.huadi.shoppingmall.Adapter.ViewPagerAdapter;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class Welcome extends Activity implements OnClickListener, OnPageChangeListener{

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    //底部小店图片
    private ImageView[] dots;
    //最后一张图片
    ImageView last_image;
    //记录当前选中位置
    private int currentIndex;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        views = new ArrayList<View>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);



        initViews();
        //初始化底部小点
        initDots();
        Log.i("welcome", "onCreate");
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        if (preferences.getBoolean("firststart", true)) {
            initData();
            Editor editor = preferences.edit();
            editor.putBoolean("firststart", false);
            editor.commit();
        }






    }

    private void initViews(){
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.welcome_first_image, null));
        views.add(inflater.inflate(R.layout.welcome_second_image, null));
        View v1=getLayoutInflater().inflate(R.layout.welcome_last_image, null);
        v1.findViewById(R.id.welcome_last_image).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                goHome();

                setGuided();
            }

        });
        views.add(v1);
        //views.add(inflater.inflate(R.layout.welcome_last_image, null));

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views,this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);

        // 绑定回调
        vp.setOnPageChangeListener(this);
    }
    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        //循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);//都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色，即选中状态
    }


    private void setCurView(int position)
    {
        if (position < 0 || position >= views.size()) {
            return;
        }

        vp.setCurrentItem(position);
        /*
        if (position==views.size()-1){
            ImageView mStartMimi = (ImageView) findViewById(R.id.welcome_last_image);
            mStartMimi.setOnClickListener(new OnClickListener(){
                public void onClick(View v){
                    goHome();
                    //不再设置向导
                    setGuided();
                }
            });
        }
        */
    }

    private void goHome() {
        // 跳转
        Intent intent = new Intent(Welcome.this, MainActivity.class);
        startActivity(intent);
        Welcome.this.finish();
    }
    private void setGuided() {
        SharedPreferences preferences = getSharedPreferences(
                Splash.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        // 存入数据
        editor.putBoolean("isFirstIn", false);
        // 提交修改
        editor.commit();
    }

    /**
     *这只当前引导小点的选中
     */
    private void setCurDot(int positon)
    {
        if (positon < 0 || positon > views.size() - 1 || currentIndex == positon) {
            return;
        }

        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = positon;
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        setCurDot(arg0);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    private void initData() {

        // 用户信息表

        String[] sql = new String[121];
        sql[1] = "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT)" +
                "values('a','admin','18522135981','265896532@qq.com',1,850,200)";

        sql[2] = "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT) " +
                "values('刘呈杰','LiuChengJie12345','13679010971','1607289450@qq.com',6,2000,500)";
        sql[3] = "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT) " +
                "values('陈俊先','ChenJunXian666','18482188024','1136106281@qq.com',5,1500,450)";
        sql[4] = "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT) " +
                "values('刘晓璇','LiuXiaoXuan999','18224031628','143981809@qq.com',6,2800,650)";
        sql[5]= "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT)" +
                "values('刘子健','LiuZiJian678','18482184835','946922848@qq.com',4,1200,300)";
        sql[6]= "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT)" +
                "values('任涛','Stupid2333','18953122581','235698562@qq.com',2,1000,150)";
        sql[7] = "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT)" +
                "Values('上帝','GodIsMeHhhh','18958246948','569425688@qq.com',6,5000,900)";
        sql[8] = "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT)" +
                "values('张爽','ZhangShuang940921','15221570578','369213448@qq.com',2,1800,250)";
        sql[9] = "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT)" +
                "values('沈杨超','SYC19950521',18716677223,'601589951@qq.com',3,1500,500)";
        sql[10] = "INSERT INTO USER(NAME,PASSWORD,PHONE,EMAIL,RANK,REMIND,POINT)" +
                "values('石丽红','ShiLiHong888','18482187046','1095532964@qq.com',4,1300,350)";


        //地址表

        sql[11] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(1,'四川省成都市犀浦县西南交大','12302422234','110234','任涛')";
        sql[12] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(2,'四川省重庆市安抚县西南大学','12302343138','110211','刘呈杰')";
        sql[13] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(3,'江苏省苏州市甘露县卡夫镇社科联分局','12344665745','12022','陈俊先')";
        sql[14] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(4,'云南省贵州市达到县阿德镇派出所','12304235644','201212','刘晓璇')";
        sql[15] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(9,'江西省南昌市达瓦县南昌大学','13384756234','113054','沈杨超')";
        sql[16] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(10,'吉林省蛟河市开水县到家小镇时刻技术公司','13498345621','120254','石丽红')";
        sql[17] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(5,'湖南省市犀飒县快的小镇飞龙小区120号','11302423221','110296','刘子健')";
        sql[18] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(6,'湖北省武汉市复浦县方琳小镇地方小学','14643245678','320254','任涛')";
        sql[19] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(1,'青海省金浮市检分县安居小镇第一医院','12123678234','110224','张三')";
        sql[20] = "INSERT INTO ADDRESS(user_id,address_info,phone,PostCode,name) Values(1,'广东省思福市少赛县西实东小镇海世超市','13462457891','150274','李四')";

        //商品表
        sql[21]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('衣贝洁洗衣',89.9,'【衣贝洁】羽绒服4件随心洗上门洗衣服务','img20160721111','羽绒服4件随心洗在线洗衣上门收件专业服务干洗水洗在线预约',850,5240,'成都','衣贝洁','生活服务','家庭保洁','2016-07-21 13:43:50','蓝','large')";
        sql[22]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('全民家电',100,'全民家电空调清洗','img20160721112','全民家电深圳空调清洗保养 家电清洗保养上门服务',650,5400,'深圳','全民家电','生活服务','家庭保洁','2016-09-2418:09:29','粉','middle')";
        sql[23]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('福运佳清洗',200,'福运佳沙发清洗','img20160721113','北京沙发清洗服务/真皮沙发保养清洁/床垫上门清洗消毒/除尘除螨',150,2510,'北京','福运佳','生活服务','家庭保洁','2015-05-23 14:43:21','白','middle')";
        sql[24]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('环鹏保洁',100,'环鹏保洁地毯清洗','img20160721114','上海环鹏保洁公司/上海家庭保洁开荒保洁地毯清洗地板打蜡家政',390,5200,'上海','环鹏保洁','生活服务','家庭保洁','2016-03-14 09:56:59','红','Small')";
        sql[25]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('空调维修',99,'杭州空调维修','img20160721115','杭州空调维修 专业空调上门维修服务/清洗服务修理 加液加氟移机',2924,8952,'杭州','祥钰维修部','生活服务','家庭保洁','2016-08-07 12:54:27','蓝','Small')";
        sql[26]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('明贝月嫂',1000,'高级月嫂预订','img20160721121','四川成都明贝家庭服务有限公司 高级月嫂预订金月嫂 工资预收款',150,2401,'成都','明贝家庭服务','生活服务','保姆月嫂','2016-10-21 07:26:28','无','无')";
        sql[27]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('金宝宝家政',6800,'成都金宝宝家政','img20160721122','负责照顾每一个产妇和新生婴儿，如生活起居料理、产后乳房护理、营养膳食调配、产后身心恢复等',100,2630,'成都','成都金宝宝家政','生活服务','保姆月嫂','2015-12-03 12:21:43','无','无')";
        sql[28]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('芝辉家政',320,'芝辉家政钟点工','img20160721123','服务包含家庭日常保洁、家常菜制作、接送孩子、带购物等一切和家政有关的工作',210,2400,'上海','芝辉家政','生活服务','保姆月嫂','2015-11-23 11:43:21','无','无')";
        sql[29]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('鲁师傅保养',198,'鲁师傅汽车上门保养服务','img20160721131','本鲁师傅汽车保养服务包括车内饰清洗、座椅皮革护理、全车易损件养护、31项爱车检测项目',500,6806,'上海','伍尔特养护专业产品','生活服务','汽车服务','2015-06-24 15:43:56','黑','Small')";
        sql[30]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('筋斗云保养',398,'筋斗云汽车服务','img20160721132','筋斗云汽车服务洁癖级内饰清洁护理/内堂清洗除菌除异味强力去污',100,2541,'成都','筋斗云','生活服务','汽车服务','2015-09-23 10:31:32','棕','Large')";
        sql[31]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('西安美年体检',700,'西安美年大健康体检','img20160721141','西安美年大健康体检，感恩父母体检卡性价套餐/松头部核磁共振',280,8540,'西安','美年大健康','生活服务','健康服务','2015-09-25 16:17:45','白','Middle')";
        sql[32]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('瑞尔齿科洁牙卡',220,'瑞尔齿科电子券','img20160721142','本服务电子券包括初诊建档费、全景X光片一张、全面口腔检查、超声波洁牙一次+全口抛光一次、牙线漱口水使用和口腔宣教',166,5243,'北京','瑞尔齿科','生活服务','健康服务','2015-06-14','紫','Small')";
        sql[33]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('小件搬家',100,'成都最快的小型搬家公司','img20160721151','成都最快的小型搬家公司/微型货车/学生租房搬家/搬冰箱钢琴鱼缸',130,1200,'成都','田田小件搬家','生活服务','搬家搬运','2015-05-2112:32:25','灰','Large')";
        sql[34]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('小一长途搬家',10,'南京小一大中小型搬家','img20160721152','南京小一大中小型优为客居家搬家长途货运公司拒绝中途加价',8325,12031,'南京','优为客居家搬家','生活服务','搬家搬运','2016-01-17 08:56:31','橙','Large')";
        sql[35]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('E修房屋快修',10,'防水补漏/维修','img20160721161','本快修服务可满足用户对房屋的维修、安装、拆除、翻新、改造、清洁等各方面的需求',739,5469,'成都','E修','生活服务','维修服务','2015-11-24 13:32:54','绿','Middle')";
        sql[36]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('极有家闪电刷新',500,'闪电刷新 墙面翻新刷漆','img20160721162','闪电刷新/墙面刷新/刷漆/墙面粉刷/卫生间翻新/厨卫改造交定金',531,2400,'北京','极有家','生活服务','维修服务','2016-09-08 18:06:53','黄','Large')";
        sql[37]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('连衣裙精品女装',239,'夏季新款背心裙','img20160721211','连衣裙精品女装2016夏季新款韩版显瘦欧根纱雪纺中长款背心裙',190,5326,'杭州','Lujour','女装','裙装','2016-04-09 12:23:45','白','Middle')";
        sql[38]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('修身A字裙',108,'夏季新款A字裙','img20160721212','2016夏季新款韩版修身一字领印花无袖连衣裙夏显瘦高腰A字裙',2937,8659,'杭州','Bytonest','女装','裙装','2016-06-16 17:43:23','白','Small')";
        sql[39]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('海青蓝2016夏季新款V领一步裙无袖修身连衣裙',108,'海青蓝2016夏季新款V领一步裙无袖修身连衣裙','img20160721213','海青蓝2016夏季新款V领一步裙无袖修身连衣裙',2937,8659,'杭州','Bytonest','女装','裙装','2016-06-16 17:43:23','白','Small')";
        sql[40]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('2016秋装新款五分袖印花连衣裙子',106,'2016秋装新款五分袖印花连衣裙子','img20160721214','2016秋装新款五分袖印花连衣裙子',2937,8659,'杭州','Bytonest','女装','裙装','2016-06-16 17:43:23','白','Small')";
        sql[41]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('snidel 飞袖碎花连衣裙',105,'飞袖碎花连衣裙','img20160721215','飞袖碎花连衣裙',2937,8659,'杭州','Bytonest','女装','裙装','2016-06-16 17:43:23','白','Small')";
        sql[42]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('修身显瘦印花桑蚕丝大摆裙',104,'修身显瘦印花桑蚕丝大摆裙','img20160721216','修身显瘦印花桑蚕丝大摆裙',2937,8659,'杭州','Bytonest','女装','裙装','2016-06-16 17:43:23','白','Small')";
        sql[43]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('碎花高腰裙子荷叶边连衣裙a字裙短裙',103,'碎花高腰裙子荷叶边连衣裙a字裙短裙','img20160721217','碎花高腰裙子荷叶边连衣裙a字裙短裙',2937,8659,'杭州','Bytonest','女装','裙装','2016-06-16 17:43:23','白','Small')";

        sql[44]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('学院风衬衫',17,'夏季女款衬衫','img20160721221','2016夏装女装新款韩风学院风乖巧双V黑边木耳白衬衫女潮学生包邮',2034,5684,'广州','ToKay','女装','上装','2016-07-16 16:45:21','白','Small')";
        sql[45]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('七分袖衬衫',204.5,'印花七分袖衬衫','img20160721222','特LIVAS一眼相中！条纹星星印花，前短后长真丝七分袖衬衫',47,3612,'杭州','LIVAS','女装','上装','2016-03-21 12:56:54','灰','Large')";
        sql[46]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('白色牛仔短裤',49,'白色弹力牛仔短裤','img20160721231','包邮夏季学生白色弹力磨破洞牛仔短裤女/毛边韩版宽松显瘦阔腿热裤',159609,526431,'广州','Traler','女装','裤装','2015-09-30 18:25:36','白','Middle')";
        sql[47]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('弹力牛仔短裤',30,'高腰牛仔短裤','img20160721232','韩版高腰牛仔短裤女夏季薄款百搭超显瘦弹力大码学生热裤子胖mm潮',77776,96541,'武汉','Umbar','女装','裤装','2016-02-25 14:23:41','蓝','Small')";
        sql[48]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('妈妈夏装',40,'中老年女装短袖','img20160721241','妈妈装夏装套装40~50岁中老年女装短袖奶奶装夏季两件套T恤裤子薄',3400,6900,'苏州','慕榕','女装','妈妈装','2016-03-25 15:24:30','绿','Middle')";
        sql[49]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('中老年女夏装',38,'妈妈装夏装T恤','img20160721242','此妈妈套装使用优质面料，款式上使用了精致圆领和精美印花，并且密封接缝',26283,65233,'苏州','慕榕','女装','妈妈装','2016-08-21 19:52:47','粉','Middle')";
        sql[50]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('修身运动女生装',39,'短袖休闲女学生运动装','img20160721251','短袖休闲女士印花运动服七分裤两件套修身运动套装女夏跑步学生',28359,65982,'广州','Oranf','女装','运动套装','2016-01-13 21:58:26','灰','Small')";
        sql[51]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('纯棉休闲女装',79,'纯棉休闲运动女装','img20160721252','纯棉休闲运动套装女夏季运动服跑步短袖七分裤衣两件套25~35周岁',22241,56498,'广州','Amuro','女装','运动套装','2015-12-05 18:24:58','红','Large')";
        sql[52]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('新款职业女装',49,'女士正装面试工作服','img20160721261','新款职业装女装套裙夏短袖职业套装衬衫女式正装OL面试工作服西裙',16712,25684,'上海','Gady','女装','职业套装','2016-05-24 12:14:35','白','Middle')";
        sql[53]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('新款职业女装',98,'夏季新款女职业装','img20160721262','夏季新款职业装女装套裙短袖职业套装衬衫女士正装面试工作服西裙',13791,56321,'湖州','阳光娇丽','女装','职业套装','2015-12-13 22:02:25','白','Small')";
        sql[54]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('纯棉男士背心',29,'男士纯色背心','img20160721311','花花公子男士穿色棉质修身型背心紧身运动健身打底汗背心男夏季潮',56534,65945,'金华','花花公子','男装','内搭','2016-07-14 16:23:43','黑','Large')";
        sql[55]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('夏季男士短袖衬衫',29,'夏季男士纯色商务白衬衣','img20160721312','MJX夏季男士短袖衬衫修身纯色商务正装休闲职工装半袖白衬衣',74761,95241,'金华','MJX','男装','内搭','2016-06-09 15:42:56','白','Middle')";
        sql[56]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('夏季中国风印花汉字悟空七龙珠短袖T',69,'夏季中国风印花汉字悟空七龙珠短袖T','img20160721313','夏季中国风印花汉字悟空七龙珠短袖T',74761,95241,'广东广州','麦田里','男装','内搭','2016-06-09 15:42:56','白','Middle')";
        sql[57]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('Bytehare原创自制情侣恐龙印花TEE夏款男短袖竹节棉T',99,'Bytehare原创自制情侣恐龙印花TEE夏款男短袖竹节棉T','img20160721314','Bytehare原创自制情侣恐龙印花TEE夏款男短袖竹节棉T',74761,95241,'浙江杭州','Bytehare','男装','内搭','2016-06-09 15:42:56','白','Middle')";
        sql[58]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('RCC男装夏季新款心形领纯棉休闲男士修身短袖T',138,'RCC男装夏季新款心形领纯棉休闲男士修身短袖T','img20160721315','RCC男装夏季新款心形领纯棉休闲男士修身短袖T',74761,95241,'韩国','RCC','男装','内搭','2016-06-09 15:42:56','白','Middle')";
        sql[59]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('齐天大圣孙悟空 简约纯色弹力竹节棉卡通短袖中国风T',45,'简约纯色弹力竹节棉卡通短袖中国风T','img20160721316','简约纯色弹力竹节棉卡通短袖中国风T',74761,95241,'广东广州','MJX','男装','内搭','2016-06-09 15:42:56','白','Middle')";
        sql[60]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('阿迪达斯NEO彭于晏夏季男子圆领短袖T',109,'阿迪达斯NEO彭于晏夏季男子圆领短袖T','img20160721317','阿迪达斯NEO彭于晏夏季男子圆领短袖T',74761,95241,'上海','adidas','男装','内搭','2016-06-09 15:42:56','白','Middle')";
        sql[61]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('双头燕EKHLAS HEY BITCH2015反面恶搞印花T',127,'BITCH2015反面恶搞印花T','img20160721318','双头燕EKHLAS HEY BITCH2015反面恶搞印花T',74761,95241,'广东广州','双头燕','男装','内搭','2016-06-09 15:42:56','白','Middle')";
        sql[62]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('男士夹克',69,'夏季修身男士夹克','img20160721321','防晒衣男2016夏季新款修身潮夹克情侣款薄款透气皮肤衣防晒服外套',14944,52641,'杭州','Tacnick','男装','外套','2016-07-15 16:25:23','灰','Small')";
        sql[63]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('男士运动夹克',59,'男士防晒运动夹克','img20160721322','防晒衣男韩版超薄透气2016夏季潮青年运动夹克外衣夏天防晒服外套',4129,21321,'北京','Radie','男装','外套','2016-06-21 17:52:21','灰','Middle')";
        sql[64]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('夏季男士短裤',27,'夏季男士运动五分短裤','img20160721331','夏季运动五分裤男式短裤男装休闲沙滩裤子男大裤衩薄潮',122850,252410,'杭州','Tacnick','男装','下装','2015-10-19 12:34:54','白','Middle')";
        sql[65]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('夏季男士休闲裤',60,'夏季男士纯棉九分裤','img20160721332','夏季男士休闲裤小脚纯棉九分裤男薄款修身九分裤男韩版潮',73650,88562,'广州','Radie','男装','下装','2016-07-14 13:54:12','浅灰','Small')";
        sql[66]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('纯色大码小衫',30,'夏季运动打底衫','img20160721341','纯色大码青少年韩版修身V领短袖T恤男士夏季运动打底衫潮男装小衫',212353,52134,'广州','Toopic','男装','大码','2016-05-15 21:35:24','深蓝','Middle')";
        sql[67]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('大码半袖上衣',40,'夏季男士大码短袖','img20160721342','夏季男士短袖T恤纯棉韩版修身圆领T恤大码学生半袖上衣潮男装',72933,85642,'杭州','Diss','男装','大码','2016-04-29 14:35:25','白','Large')";
        sql[68]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('大码爸爸装',40,'夏装中老年人T恤','img20160721351','爸爸装夏装中老年人男装T恤polo大码短袖',39594,52310,'杭州','Frasign','男装','中老年','2016-07-25 23:25:24','黑','Middle')";
        sql[69]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('中年夏装短裤',40,'夏装男士半短裤','img20160721352','夏装中年爸爸男士休闲五分运动中老年沙滩短裤',720,2315,'广州','Toopic','男装','中老年','2016-07-17 15:25:35','灰','Large')";
        sql[70]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('夏季情侣短裤',30,'夏季男士情侣沙滩短裤','img20160721361','夏季男士休闲短裤纯色韩版百大青年情侣沙滩裤五分热裤潮',45115,53621,'杭州','Rafire','男装','情侣装','2016-09-21 16:24:51','白','Small')";
        sql[71]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('小怪兽情侣衣',318,'小怪兽情侣款短袖','img20160721362','香港代购潮牌小怪兽小恶魔刺绣眼睛拉链圆领男女装情侣款短袖T恤',130,1520,'香港','X','男装','情侣装','2016-04-16 14:28:46','黑','Small')";
        sql[72]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('玉米味鱼肠',13,'小力士鱼肠','img20160721411','韩国进口零食海牌小力士玉米味鱼肠80g鲜嫩可口',3382,7520,'韩国','海牌小力士','食品','当季推荐','2016-03-15 12:48:56','无','无')";
        sql[73]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('巧克力甜甜圈',8,'膨化甜甜圈','img20160721412','台湾进口膨化零食品张君雅小妹妹巧克力甜甜圈45g',16268,22520,'台湾','张君雅小妹妹','食品','当季推荐','2015-12-13 11:58:19','无','无')";
        sql[74]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('曲奇饼干',9,'蔓越莓曲奇饼干','img20160721421','卜珂蔓越莓曲奇饼干200g/袋饼干糕点制作休闲零食',142929,155260,'上海','卜珂','食品','休闲零食','2016-03-27 09:25:38','无','无')";
        sql[75]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('威化夹心饼',33,'丽芝士威化夹心饼','img20160721422','印尼进口 丽芝士纳宝帝nabati威化夹心饼干奶酪休闲零食145g*6盒',13174,15200,'印度尼西亚','丽芝士纳宝帝nabati','食品','休闲零食','2016-07-16 19:08:26','无','无')";
        sql[76]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('虾米',19,'淡干无盐虾米','img20160721431','虾皮特级无盐 淡干无盐虾皮 虾米 500克海米干货孕妇宝宝补钙包邮',8029,15420,'日照','金海龙','食品','水产生鲜','2016-04-30 11:15:36','无','无')";
        sql[77]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('澳洲牛排',128,'澳洲家庭牛排','img20160721432','澳洲家庭牛排套餐团购10片牛肉1760g含菲力黑椒包邮顺丰',41002,51520,'中国大陆','赤豪','食品','水产生鲜','2016-07-18 08:31:45','无','Middle')";
        sql[78]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('伊利牛奶',24.5,'伊利纯牛奶','img20160721441','伊利纯牛奶 240ml*12包/提整箱',76991,81520,'内蒙古','伊利','食品','茶酒冲饮','2016-02-21 18:28:12','无','无')";
        sql[79]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('雀巢咖啡',34,'雀巢速溶咖啡','img20160721442','Nestle/雀巢速溶咖啡无蔗糖添加二合一(30条装)',953,1520,'东莞','雀巢','食品','茶酒冲饮','2015-12-26 13:08:41','无','无')";
        sql[80]= "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('酸菜牛肉面',11,'老坛酸菜牛肉面','img20160721451','统一100老坛酸菜牛肉面121g*5包 老坛酸菜方便面',90611,111560,'上海','统一','食品','粮油干货','2015-12-26 15:18:36','无','无')";
        sql[81] = "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('雀巢淡奶油',8.5,'稀奶动物性奶油','img20160721452','烘焙原料雀巢淡奶油 稀奶动物性鲜奶油250ml 蛋挞蛋糕裱花奶油',135151,155209,'青岛','雀巢','食品','粮油干货','2016-01-26 16:21:49','无','无')";
        sql[82] = "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('多花蜜蜂蜜',40,'自产多花蜜蜂蜜','img20160721461','宜蜂尚蜂蜜 多花蜜 天然纯农家土自产野生洋槐百花蜜',31283,41320,'淮安','宜蜂尚','食品','保健滋补','2016-03-27 12:08:16','无','无')";
        sql[83] = "INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('特级枸杞',33,'宁夏特级枸杞','img20160721462','百瑞源正宗宁夏枸杞子中宁枸杞子特级500g精选枸杞袋装包邮',83843,16510,'银川','百瑞源','食品','保健滋补','2016-06-21 10:24:26','无','无')";

        //订单表

        sql[84] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,PRICE,ADDRESS_ID,PRODUCT_ID,NUMBER) Values(1,'2016-07-04 12:52:05',3,105.0,1,21,1)";
        sql[85] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-05-29 18:12:57',3,0,208.0,22,2)";
        sql[86] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-07-21 19:52:05',3,1,309.0,23,3)";
        sql[87] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-06-14 10:54:25',0,1,34,24,2)";
        sql[88] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-08-15 21:29:36',2,0,409,25,2)";
        sql[89] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-04-18 13:32:28',2,0,98.0,33,1)";
        sql[90] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-07-24 23:52:37',3,1,508.0,41,4)";
        sql[91] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-06-28 20:32:49',3,0,50.0,15,5)";
        sql[92] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-08-27 18:57:50',2,0,30.0,14,3)";
        sql[93] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-07-11 14:20:21',1,1,108.0,19,1)";
        sql[94] = "INSERT INTO ORDER_TB(USER_ID,ORDER_TIME,STATUS,ADDRESS_ID,PRICE,PRODUCT_ID,NUMBER) values(1,'2016-06-12 16:13:42',0,1,79.0,31,1)";


        //优惠券（Coupon）表

        sql[95] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2016-04-19 13:13:24',20,'立减10元',10)";
        sql[96] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2017-06-12 13:12:10',30,'立减20元',20)";
        sql[97] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2017-02-23 23:14:11',50,'立减30元',30)";
        sql[98] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2017-05-10 10:35:16',24,'立减40元',40)";
        sql[99] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2014-07-25 11:26:17',48,'立减30元',30)";
        sql[100] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2016-03-13 16:54:15',72,'立减50元',50)";
        sql[101] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2017-02-25 17:23:56',10,'立减100元',100)";
        sql[102] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2016-02-05 18:12:14',12,'立减50元',50)";

        sql[103] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2012-06-08 16:14:14',2,'立减10元',10)";
        sql[104] = "INSERT INTO COUPON(USER_ID,CREATE_TIME, LAST,COUPON_INFO,COUPON_SUM) Values(1,'2015-03-15 17:16:10',40,'立减10元',10)";

        //物流表

        sql[105] = "INSERT INTO EXPRESS(ORDER_ID,CREATE_TIME,LOCATION)   Values(1,'2016-07-15 13:21:10','成都')";
        sql[106] = "INSERT INTO EXPRESS(ORDER_ID,CREATE_TIME,LOCATION)   Values(1,'2016-11-15 13:21:10','重庆')";
        sql[107] = "INSERT INTO EXPRESS(ORDER_ID,CREATE_TIME,LOCATION)   Values(1,'2016-10-11 14:23:10','上海')";
        sql[108] = "INSERT INTO EXPRESS(ORDER_ID,CREATE_TIME,LOCATION)   Values(1,'2016-12-12 13:21:10','广州')";
        sql[109] = "INSERT INTO EXPRESS(ORDER_ID,CREATE_TIME,LOCATION)   Values(1,'2016-09-14 14:23:10','江苏')";

        //折扣表

        sql[110] = "INSERT INTO DISCOUNT(PRODUCT_ID, NAME,DISCOUNT_INFO,CREATE_TIME,LAST) Values(1,'全场八折','0.20','2016-02-12 22:10:53','24')";
        sql[111] = "INSERT INTO DISCOUNT(PRODUCT_ID,NAME,DISCOUNT_INFO,CREATE_TIME,LAST) Values(2,'全场五折','0.50','2015-07-13 21:30:43','48')";
        sql[112] = "INSERT INTO DISCOUNT(PRODUCT_ID, NAME,DISCOUNT_INFO,CREATE_TIME,LAST) Values(3,'全场六折','0.40','2013-02-15 17:40:03','72')";
        sql[113] = "INSERT INTO DISCOUNT(PRODUCT_ID, NAME,DISCOUNT_INFO,CREATE_TIME,LAST) Values(4,'全场三折','0.70','2016-01-17 23:50:33','20')";
        sql[114] = "INSERT INTO DISCOUNT(PRODUCT_ID, NAME,DISCOUNT_INFO,CREATE_TIME,LAST) Values(5,'全场九折','0.10','2014-03-23 15:80:43','10')";


        //购物车

        sql[115] = "INSERT INTO SHOP_CAR(USER_ID,PRODUCT_ID,PRODUCT_NUM) VALUES(1,20,1)";
        sql[116] = "INSERT INTO SHOP_CAR(USER_ID,PRODUCT_ID,PRODUCT_NUM) VALUES(1,21,2)";
        sql[117] = "INSERT INTO SHOP_CAR(USER_ID,PRODUCT_ID,PRODUCT_NUM) VALUES(1,22,2)";
        sql[118] = "INSERT INTO SHOP_CAR(USER_ID,PRODUCT_ID,PRODUCT_NUM) VALUES(1,23,4)";

        //评价表

        sql[119] = "INSERT INTO COMMENT(USER_ID,ORDER_ID,PRODUCT_ID,CREATE_TIME, START_LEVEL,CONTENT) values(1,1,21,'2016-06-21 10:43:16',4,'服务很周到，下次还会来~')";
        sql[120] = "INSERT INTO COMMENT(USER_ID,ORDER_ID,PRODUCT_ID,CREATE_TIME, START_LEVEL,CONTENT) values(1,4,21,'2016-06-23 09:12:48',4,'态度很好，以后有需要还会再买~')";

    }




}