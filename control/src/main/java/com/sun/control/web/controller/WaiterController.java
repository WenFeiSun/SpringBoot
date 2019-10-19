package com.sun.control.web.controller;

import com.sun.common.core.Result;
import com.sun.common.entity.Waiter;
import com.sun.common.utils.LuceneUtils;
import com.sun.common.utils.RedisUitls;
import com.sun.common.utils.ResultUtil;
import com.sun.control.web.common.annotation.SysLog;
import com.sun.control.web.service.WaiterServie;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/waiter")
public class WaiterController {

    @Autowired private WaiterServie waiterServie;
    //@Autowired private KafkaProducer kafkaProducer;

    @Resource
    private RedisUitls redisUitls;

    /**
     *@author SunWenFei
     *waiter表格数据展示
     */
    @SysLog(methodValue = "获取waiter数据",methodType = "查询")
    @RequestMapping(value = "/waiterList",method = RequestMethod.POST)
    public Map waiterList(String userName,int page,int limit){
        Map map = new HashMap();
        Map waiterMap = new HashMap();

        try {
            //kafkaProducer.send("Hello World");
            waiterMap = redisUitls.hmget("waiterMap");
            if(waiterMap.size()<=0){
                waiterMap = waiterServie.queryWaiterList(userName,page,limit);
                redisUitls.hmset("waiterMap",waiterMap);
            }
            map = waiterMap;
            map.put("code",0);
            map.put("msg","");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","请稍后重试！");
            return map;
        }
        /*LuceneUtils.getSearcher();*/
    }

    /**
     * 查要修改的对象
     * @param code
     * @return
     */
    @RequestMapping(value = "/updateWaiter",method = RequestMethod.POST)
    public Result updateWaiter(String code){
        String dataSourceId = "sunwenfei123";
        try{
            Waiter waiter = waiterServie.queryUpdateWaiterByCode(dataSourceId,code);
            return ResultUtil.success(waiter);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("程序异常！");
        }
    }
    /**
     *@author SunWenFei
     *
     */
    @RequestMapping(value = "/saveWaiter",method = RequestMethod.POST)
    public Result saveWaiter(String userName,String tel,String age,String sex,String idCardType,String idCard,String des,String status){
        Waiter waiter;
        try{
            waiter = waiterServie.selectWaiterByIdCard(idCard);
            if (waiter != null){
                //return ResultUtil.error("当前身份证号已被添加！！！");
                waiter.setUserName(userName);
                waiter.setTel(tel);
                waiter.setAge(age);
                waiter.setSex(sex);
                waiter.setIdCardType(idCardType);
                waiter.setIdCard(idCard);
                waiter.setStatus(status);
                waiter.setDes(des);
                waiterServie.updateWaiter(waiter);
                Map waiterMap = redisUitls.hmget("waiterMap");
                redisUitls.hdel("waiterMap","data","count");
                return ResultUtil.success("修改成功");
            }
            waiter = new Waiter();
            waiter.setCode(UUID.randomUUID().toString().replace("-",""));
            waiter.setUserName(userName);
            waiter.setTel(tel);
            waiter.setAge(age);
            waiter.setSex(sex);
            waiter.setIdCardType(idCardType);
            waiter.setIdCard(idCard);
            waiter.setStatus(status);
            waiter.setDes(des);
            waiterServie.saveWaiter(waiter);
            return ResultUtil.success("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("添加或者修改失败！！！");
        }

    }

    /**
     *@author SunWenFei
     *删除
     */
    @RequestMapping(value = "/deleteWaiter",method = RequestMethod.POST)
    public Result deleteWaiter(String code){
        try{
            waiterServie.deleteWaiter(code);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("删除失败");
        }
        return ResultUtil.success("删除成功");
    }


}
