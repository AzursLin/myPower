package com.hctek.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.hctek.dao.UserDormMapper;
import com.hctek.dao.UserElectricityMapper;
import com.hctek.dao.UserMapper;
import com.hctek.model.User;
import com.hctek.model.UserDorm;
import com.hctek.model.UserElectricity;
import com.hctek.myImpl.CommonResult;
import com.hctek.myImpl.EncodingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Azurs on 2017-01-25.
 */
@CrossOrigin()
@Controller
@RequestMapping(value = "/")
public class MainController {
    ApplicationContext ctx=new ClassPathXmlApplicationContext("ApplicationContext.xml");
    private Gson gson = new Gson();
    private Map<String, Object> jsonMap = new HashMap<String, Object>();
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDormMapper userDormMapper;
    @Autowired
    private UserElectricityMapper userElectricityMapper;
    //获得一个BEAN USER表操作对象
    UserMapper UserDao=ctx.getBean(UserMapper.class);



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String index(@RequestParam("userName") String userName,@RequestParam("pass") String pass) {
        User user =  userMapper.getOneByName(userName);
        if (user != null) {
            if (user.getUserName().equals(userName)&&user.getPassword().equals(pass)) {
                jsonMap.put("msg","登录成功");
                jsonMap.put("Data",user);
                jsonMap.put("ret","1");
            } else {
                jsonMap.put("msg","用户或密码错误");
                jsonMap.put("ret","0");
            }

        } else {
            jsonMap.put("msg","用户不存在");
            jsonMap.put("ret","0");
        }
        return gson.toJson(jsonMap);
    }

    /**
     * 获取账户列表
     * */
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    @ResponseBody
    public String userManage(@RequestParam("userId") String userId,@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,
                             @RequestParam(value="name",required = false)String name,@RequestParam(value="nickname",required = false)String nickname ) {
        User user =  userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            List<User> ls = null;
            PageHelper.startPage(pageNum, pageSize);
            if (user.getAdmin() == 2) {
                User u = new User();
                if (name != null){
                    u.setUserName(EncodingTool.encodeStr(name));
                }else if (nickname != null) {
                    u.setUserNickname(EncodingTool.encodeStr(nickname));
                }
                ls = userMapper.getAll(u);
            } else if (user.getAdmin() == 1){

            }
            PageInfo page = new PageInfo(ls);
            jsonMap.put("msg","操作成功");
            jsonMap.put("Data",ls);
            jsonMap.put("total",page.getTotal());
            jsonMap.put("ret","1");
        } else {
            jsonMap.put("msg","用户不存在");
            jsonMap.put("ret","0");
        }
        return gson.toJson(jsonMap);
    }

    /**
     * 添加账户
     * */
    @RequestMapping(value = "/user/list/add", method = RequestMethod.GET)
    @ResponseBody
    public String userAdd(@RequestParam("userName") String userName,@RequestParam("userNickname") String userNickname,
                          @RequestParam("password") String password ) {
        UUID uuid = UUID.randomUUID();
        User user1 = new User();
        user1.setUserId(String.valueOf(uuid));
        user1.setUserName(EncodingTool.encodeStr(userName));
        user1.setUserNickname(EncodingTool.encodeStr(userNickname));
        user1.setPassword(password);
        user1.setCreateDate(new Date());
        user1.setLoginDate(user1.getCreateDate());
        user1.setAdmin(1);
        userMapper.insert(user1);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }

    /**
     * 修改用电信息
     * */
    @RequestMapping(value = "/user/list/update", method = RequestMethod.GET)
    @ResponseBody
    public String userUpdate(@RequestParam("userName") String userName,@RequestParam("userNickname") String userNickname,
                            @RequestParam("password") String password,@RequestParam("admin") int admin,
                            @RequestParam("userId") String userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserNickname(userNickname);
        user.setPassword(password);
        user.setAdmin(admin);
        userMapper.updateByPrimaryKeySelective(user);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }

    /**
     * 删除用户信息
     * */
    @RequestMapping(value = "/user/list/delete", method = RequestMethod.GET)
    @ResponseBody
    public String userDelete(@RequestParam("userId") String userId) {
        userMapper.deleteByuserId(userId);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }

    /**
     * 获取账户列表
     * */
    @RequestMapping(value = "/dorm/list", method = RequestMethod.GET)
    @ResponseBody
    public String dormManage(@RequestParam("userId") String userId,@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,
                             @RequestParam(value="dorm",required = false)String dorm,@RequestParam(value="userName",required = false)String userName,
                             @RequestParam(value="college",required = false)String college,@RequestParam(value="classInfo",required = false)String classInfo,
                             @RequestParam(value="userNickname",required = false)String userNickname) {
        User user =  userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            List<UserDorm> ls = null;
            PageHelper.startPage(pageNum, pageSize);
            if (user.getAdmin() == 2) {
                UserDorm userDorm = new UserDorm();
                userDorm.setUserNickname(EncodingTool.encodeStr(userNickname));
                userDorm.setDorm(EncodingTool.encodeStr(dorm));
                userDorm.setUserName(EncodingTool.encodeStr(userName));
                userDorm.setCollege(EncodingTool.encodeStr(college));
                userDorm.setClassInfo(EncodingTool.encodeStr(classInfo));
                ls = userDormMapper.getAll(userDorm);
            } else if (user.getAdmin() == 1){
                UserDorm userDorm = new UserDorm();
                userDorm.setUserId(userId);
                ls = userDormMapper.getAll(userDorm);
            }
            PageInfo page = new PageInfo(ls);
            jsonMap.put("msg","操作成功");
            jsonMap.put("Data",ls);
            jsonMap.put("total",page.getTotal());
            jsonMap.put("ret","1");
        } else {
            jsonMap.put("msg","用户不存在");
            jsonMap.put("ret","0");
        }
        return gson.toJson(jsonMap);
    }


    /**
     * 添加宿舍信息
     * */
    @RequestMapping(value = "/dorm/list/add", method = RequestMethod.GET)
    @ResponseBody
    public String dormAdd(@RequestParam("dorm") String dorm,@RequestParam("userName") String userName,
                          @RequestParam("college") String college,@RequestParam("classInfo") String classInfo,
                          @RequestParam("userId") String userId) {
        UserDorm userDorm = new UserDorm();
        userDorm.setUserId(userId);
        userDorm.setUserName(EncodingTool.encodeStr(userName));
        userDorm.setDorm(dorm);
        userDorm.setCollege(EncodingTool.encodeStr(college));
        userDorm.setClassInfo(EncodingTool.encodeStr(classInfo));
        userDorm.setUpdateDate(new Date());
        userDormMapper.insert(userDorm);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }



    /**
     * 修改宿舍信息
     * */
    @RequestMapping(value = "/dorm/list/update", method = RequestMethod.GET)
    @ResponseBody
    public String dormUpdate(@RequestParam("dorm") String dorm,@RequestParam("userName") String userName,
                          @RequestParam("college") String college,@RequestParam("classInfo") String classInfo,
                          @RequestParam("userId") String userId,@RequestParam("id") int id) {
        UserDorm userDorm = new UserDorm();
        userDorm.setId(id);
        userDorm.setUserId(userId);
        userDorm.setUserName(EncodingTool.encodeStr(userName));
        userDorm.setDorm(dorm);
        userDorm.setCollege(EncodingTool.encodeStr(college));
        userDorm.setClassInfo(EncodingTool.encodeStr(classInfo));
        userDorm.setUpdateDate(new Date());
        userDormMapper.updateById(userDorm);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }

    /**
     * 删除宿舍信息
     * */
    @RequestMapping(value = "/dorm/list/delete", method = RequestMethod.GET)
    @ResponseBody
    public String dormDelete(@RequestParam("id") int id) {
        userDormMapper.deleteById(id);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }

    /**
     * 获取用电情况列表
     * */
    @RequestMapping(value = "/ele/list", method = RequestMethod.GET)
    @ResponseBody
    public String eleManage( @RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,
                             @RequestParam("userId") String userId,@RequestParam(value = "userName",required = false) String userName,
                            @RequestParam(value = "startDate",required = false) String startDate,@RequestParam(value = "endDate",required = false) String endDate) {
        User user =  userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            UserElectricity userElectricity = new UserElectricity();
            if (user.getAdmin() == 1) {
                userElectricity.setUserId(userId);
            }
            userElectricity.setUserNickname(EncodingTool.encodeStr(userName));
            userElectricity.setStartDate(EncodingTool.vueStrToDate(startDate));
            userElectricity.setEndDate(EncodingTool.vueStrToDate(endDate));
            PageHelper.startPage(pageNum, pageSize);
            List<UserElectricity> ls = userElectricityMapper.getAll(userElectricity);
            PageInfo page = new PageInfo(ls);

            jsonMap.put("msg","操作成功");
            jsonMap.put("Data",ls);
            jsonMap.put("total",page.getTotal());
            jsonMap.put("ret","1");
        }
        return gson.toJson(jsonMap);
    }

    /**
     * 添加用电信息
     * */
    @RequestMapping(value = "/ele/list/add", method = RequestMethod.GET)
    @ResponseBody
    public String eleAdd(@RequestParam("userRead") int read,@RequestParam("money") int money,
                          @RequestParam("pay") int pay,@RequestParam("createDate") String createDate,
                          @RequestParam("userId") String userId) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        UserElectricity userElectricity = new UserElectricity();
        userElectricity.setUserId(userId);
        userElectricity.setUserRead(read);
        userElectricity.setMoney(money);
        userElectricity.setPay(pay);
        userElectricity.setCreateDate(format.parse(createDate.split("T")[0]));
        userElectricityMapper.insert(userElectricity);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }

    /**
     * 修改用电信息
     * */
    @RequestMapping(value = "/ele/list/update", method = RequestMethod.GET)
    @ResponseBody
    public String eleUpdate(@RequestParam("userRead") int read,@RequestParam("money") int money,
                          @RequestParam("pay") int pay,@RequestParam("createDate") String createDate,
                          @RequestParam("userId") String userId,@RequestParam("id") int id) throws ParseException {
        UserElectricity userElectricity = new UserElectricity();
        userElectricity.setId(id);
        userElectricity.setUserId(userId);
        userElectricity.setUserRead(read);
        userElectricity.setMoney(money);
        userElectricity.setPay(pay);
        String[] str = createDate.split("T");
        if (str.length > 1) {
            createDate = createDate.replace("Z"," UTC");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            userElectricity.setCreateDate(format.parse(createDate));
        }
        userElectricityMapper.updateByPrimaryKeySelective(userElectricity);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }

    /**
     * 删除宿舍信息
     * */
    @RequestMapping(value = "/ele/list/delete", method = RequestMethod.GET)
    @ResponseBody
    public String eleDelete(@RequestParam("id") int id) {
        userElectricityMapper.deleteById(id);
        jsonMap.put("msg","操作成功");
        jsonMap.put("ret","1");
        return gson.toJson(jsonMap);
    }
}
