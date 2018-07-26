package com.example.demoweibo.test;

import com.example.demoweibo.dao.Username;
import com.example.demoweibo.dao.WeiboExample;
import com.example.demoweibo.dao.WeiboInfo;
import com.example.demoweibo.mapper.WeiboMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import weibo4j.Users;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wanghaijun
 * @date 2018/7/25
 * @desc
 */
@Service
public class TestDemo {

    @Autowired
    private WeiboMapper weiboMapper;

    public void showUserByScreenName() throws InterruptedException {
        List<Username> userList = weiboMapper.selectUserName();
        List<List<Username>> list = ListUtil.split(userList,10);
        for (List<Username> usernameList:list){
            for (Username username :usernameList){
                if (!ObjectUtils.isEmpty(username)){
                    String screen_name =username.getUsername();
                    WeiboExample weiboExample = new WeiboExample();
                    weiboExample.createCriteria().andUsernameEqualTo(screen_name);
                    User user = showUserByScreenName(screen_name);
                    WeiboInfo weiboInfo = userToWeiboInfo(user);
                    weiboMapper.updateByExampleSelective(weiboInfo,weiboExample);
                }
            }
        }
    }

    public List<Username> queryUsername(){
        List<Username> userList = weiboMapper.selectUserName();
//        List<List<Username>> list = ListUtil.split(userList,10);
        return userList;
    }

    public void updataUser(WeiboInfo weiboInfo,WeiboExample weiboExample){
        weiboMapper.updateByExampleSelective(weiboInfo,weiboExample);
    }
    public User showUserByScreenName(String screen_name) {
        String access_token = "2.00c1OOtFVmawkC3cfdc198bbMbMGcE";
        Users um = new Users(access_token);
        try {
            User user = um.showUserByScreenName(screen_name);
            Log.logInfo(user.toString());
            return user;
        } catch (WeiboException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WeiboInfo userToWeiboInfo(User user){
        WeiboInfo weiboInfo = new WeiboInfo();
        weiboInfo.setUserid(user.getId());
        weiboInfo.setRegistrationtime(getDate(user.getCreatedAt()));
        return weiboInfo;
    }

    private static String getDate(Date parse){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateStr = dateFormat.format(parse);
        return dateStr;
    }
}
