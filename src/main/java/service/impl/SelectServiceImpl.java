package service.impl;

import bean.*;
import dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SelectService;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {
    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogDao logDao;

    @Autowired
    private CommentsDao commentsDao;

    public List<Project> getUserProjects(String uId) {
        return projectDao.getProjectsByCreateUid(Integer.parseInt(uId));
    }

    public List<Project> getSubProjects(String uId) {
        return projectDao.getSubProjects(Integer.parseInt(uId));
    }

    public List<Model> getMarkModels(String uId) {
        return modelDao.getModelsByMarkUId(Integer.parseInt(uId));
    }

    public List<Comment> getCommentsInModel(String mId){return commentsDao.SelectCommentsByMId(Integer.parseInt(mId));}

    public Model selectModelByMId(String mId) {
        return modelDao.getModelById(Long.valueOf(mId));
    }

    public User getUserData(String uId) {
        return userDao.selectUserByUid(Integer.parseInt(uId));
    }

    public List<User> selectUser(String content) {
        return userDao.selectUsersByContent(content);
    }

    public Project getProjectData(String pId) {
        return projectDao.getProjectById(Long.parseLong(pId));
    }

    public Project selectProject(long pId){
        return projectDao.getProjectById(pId);
    }
    public List<Log> selectLog(long pId){
        return logDao.getLogByPid(pId);
    }
    public List<Log> filterLog(Timestamp beginTime,Timestamp endTime,String uUsername,String mName){
        return logDao.filterLog(beginTime,endTime,uUsername,mName);
    }
    public List<Model> selectModel(long pId){
        return modelDao.getProjectModelByPid(pId);
    }
}
