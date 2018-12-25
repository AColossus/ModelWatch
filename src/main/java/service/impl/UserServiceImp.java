package service.impl;

import bean.Comment;
import bean.User;
import dao.CommentsDao;
import dao.UserDao;
import dto.ListDto;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.UserService;
import util.MyFileUtil;
import util.SendMailUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentsDao commentsDao;


    public User updateUserProfile(String user, MultipartFile file) {
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd"}) );
        User us=(User) JSONObject.toBean(JSONObject.fromObject(user),User.class);

        if(file!=null){

            try {
                File outDir =new File(MyFileUtil.getResourcesUrl()+"/upload/useravater");
                String[] fileName=file.getOriginalFilename().split("\\.");
                String suffix=fileName[fileName.length-1];
                if(!outDir.exists())
                    outDir.mkdir();
                FileUtils.copyInputStreamToFile(file.getInputStream(),new File(outDir,us.getuId()+"."+suffix));
                us.setuAvater("/resources/upload/useravater/"+us.getuId()+"."+suffix);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userDao.updateData(us);
        return us;
    }

    public void updateUserPass(User user) {
        userDao.updatePassword(user.getuId(),user.getuPassword());
    }

    public User login(String username, String password) {
        return userDao.selectUserByUNAndPass(username,password);
    }

    public boolean register(String username, String password) {
        int res=userDao.addUser(username,password);
        return res==0?false:true;
    }

    public boolean add(int addid) {
        return false;
    }

    public  User selectUser(int uId){return userDao.selectUserByUid(uId);}

    public boolean addComment(Comment comment) {
        return commentsDao.AddComment(comment) == 1 ?true:false;
    }

    public ListDto selectByName(String uName){
        List<User> users=userDao.selectUname(uName);
        return new ListDto(users);
    }

    public ListDto getUmailByName(String uName){
        List<User> users=userDao.getMailByName(uName);
        return new ListDto(users);
    }

}
