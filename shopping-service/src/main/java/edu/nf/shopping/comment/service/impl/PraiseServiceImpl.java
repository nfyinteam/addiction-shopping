package edu.nf.shopping.comment.service.impl;

import com.github.pagehelper.PageInfo;
import edu.nf.shopping.comment.dao.PraiseDao;
import edu.nf.shopping.comment.entity.Comment;
import edu.nf.shopping.comment.entity.Praise;
import edu.nf.shopping.comment.exception.CommentException;
import edu.nf.shopping.comment.service.PraiseService;
import edu.nf.shopping.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Bull fighters
 * @date 2020/3/19
 */
@Service("praiseService")
public class PraiseServiceImpl implements PraiseService {

    @Autowired
    private PraiseDao praiseDao;

    @Override
    public Praise findPraise(String userId, String comId) {
        try{
            Praise praise=praiseDao.findPraise(userId,comId);
            return praise;
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new CommentException("数据库出错");
        }
    }

    @Override
    public Boolean spotPraise(String userId, String comId) {
        try{
            if(praiseDao.findPraise(userId,comId)==null){
                Praise praise=new Praise();
                praise.setPraId(UUIDUtils.createUUID());
                praise.setUserId(userId);
                praise.setComId(comId);
                praise.setTime(new Date());
                praiseDao.addPraise(praise);
                return true;
            }else {
                praiseDao.deletePraise(userId,comId);
                return false;
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new CommentException("数据库出错");
        }
    }

    @Override
    public void deletePraise(String userId, String comId) {
        try{
            praiseDao.deletePraise(userId,comId);
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new CommentException("数据库出错");
        }
    }
}