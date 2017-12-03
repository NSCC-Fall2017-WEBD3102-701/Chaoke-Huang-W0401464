package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
@ManagedBean(name = "userService")
@SessionScoped
public class UserService {
    User user;
    String longinUsername;
    boolean isAuthenticated;
    String longinPassword;
    UserDBUtil userDBUtil;
    boolean adminStatus;

    public static final String SALT = "ck-salt";
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }


    public boolean isAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLonginUsername() {
        return longinUsername;
    }

    public void setLonginUsername(String longinUsername) {
        this.longinUsername = longinUsername;
    }

    public String getLonginPassword() {
        return longinPassword;
    }

    public void setLonginPassword(String longinPassword) {
        this.longinPassword = longinPassword;
    }

    public UserService() throws Exception {
        super();
        user = new User();
        user.setUser_name("Guest");
        userDBUtil = UserDBUtil.getInstance();


    }
    public void signup(User user) {
        String saltedPassword = SALT + user.getPassword();
        String hashedPassword = generateHash(saltedPassword);
        System.out.println("hashed:" + hashedPassword);
        user.setPassword(hashedPassword);

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String isAdmin = ec.getRequestParameterMap().get("registerForm:iSAdmin");
        userDBUtil.addUser(user,isAdmin);

        return;
    }

    public String login(String username, String password) {
        User tempUser = userDBUtil.getUser(username);

        // remember to use the same SALT value use used while storing password
        // for the first time.
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);

        if(tempUser.getPassword().equals(hashedPassword)){
            this.setAuthenticated(true);
            this.setUser(tempUser);
            this.setAdminStatus(userDBUtil.checkAdmin(user));
        }else{
            this.setAuthenticated(false);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback","User name and password don't match, please try again.");
            return "login.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback","Welcome to sleeping well, "+user.getUser_name()+"!");
        return "sleep.xhtml?faces-redirect=true";
    }

    public String logout(){

        if(user.getUser_name().equals("Guest")){

            return "login.xhtml?faces-redirect=true";
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession Session = (HttpSession) fc.getExternalContext()
                .getSession(false);
        Session.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback","You have logged out.");
        return "login.xhtml?faces-redirect=true";
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }
public String goLogin(){

        return"login.xhtml?faces-redirect=true";
}


}
