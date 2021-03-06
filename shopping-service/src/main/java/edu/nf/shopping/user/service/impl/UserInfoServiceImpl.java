package edu.nf.shopping.user.service.impl;

import edu.nf.shopping.user.dao.UserInfoDao;
import edu.nf.shopping.user.entity.UserInfo;
import edu.nf.shopping.user.exception.UserException;
import edu.nf.shopping.user.service.UserInfoService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * @author re
 * @date 2020/3/22
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    @Cacheable(value = "userInfoCache" ,key="#userId")
    public UserInfo getUserInfo(String userId) {
        try{
           UserInfo userInfo = userInfoDao.getUserInfo(userId);
            return userInfo;
        } catch (RuntimeException e) {
            throw new UserException("错误");
        }
    }

    @Override
    public List<UserInfo> listUser()  {
        try{  List<UserInfo> list = userInfoDao.listUser();
            return list;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new UserException("错误");
        }

    }

    @Override
    @Cacheable(value = "userInfoCache" ,key="#userId")
    public UserInfo userLogin(String userId,String passWord) {
        try{
            if(userId == null || passWord == null || userId.equals("") || passWord.equals("")){
                throw new UserException("账号和密码不能为空");
            }
            UserInfo u = userInfoDao.userLogin(userId,passWord);
            if(u == null || !passWord.equals(u.getPassword())){
                throw new UserException("用户名或密码错误");
            }
            return u;
        }catch (UserException e) {
            throw e;
        } catch (RuntimeException e) {
            e.printStackTrace();
           throw new UserException("账号或秘密错误");
        }
    }
}
