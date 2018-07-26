package com.example.demoweibo.test;

import com.example.demoweibo.dao.Username;
import com.example.demoweibo.dao.WeiboExample;
import com.example.demoweibo.dao.WeiboInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import weibo4j.Users;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import java.util.List;

/**
 * @author wanghaijun
 * @date 2018/7/25
 * @desc
 */
@Controller
public class MainTest {

    @Autowired
    private TestDemo testDemo;

    @RequestMapping(value = "/query/userinfo",method = RequestMethod.GET)
    public void queryUserInfo() throws InterruptedException {

        testDemo.showUserByScreenName();
    }

    @RequestMapping(value = "/query/userinfo1",method = RequestMethod.GET)
    public void queryUserInfo1() throws InterruptedException{
        List<Username> strings = testDemo.queryUsername();
        System.out.print(strings);
        String access_token = "2.00JPqsYF01fYTVdf8e3fd73e0Kuwry";
        for (Username username:strings){
            Users um = new Users(access_token);
            try {
                User user = um.showUserByScreenName(username.getUsername());
                WeiboExample weiboExample = new WeiboExample();
                weiboExample.createCriteria().andUsernameEqualTo(username.getUsername());
                WeiboInfo weiboInfo = testDemo.userToWeiboInfo(user);
                testDemo.updataUser(weiboInfo,weiboExample);
                Log.logInfo(user.toString());
                System.out.println("暂停");
                Thread.sleep(10000);
            } catch (WeiboException e) {
                e.printStackTrace();
            }
        }
    }
}
