package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
@ManagedBean(name = "userService")
@SessionScoped
public class UserService {

    UserDBUtil userDBUtil;
    public static final String SALT = "ck-salt";

    public UserService() throws Exception {
        super();

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

    public Boolean login(String username, String password) {
        Boolean isAuthenticated = false;
        User user = userDBUtil.getUser(username);

        // remember to use the same SALT value use used while storing password
        // for the first time.
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);

        if(user.getPassword().equals(hashedPassword)){
            isAuthenticated = true;
        }else{
            isAuthenticated = false;
        }
        return isAuthenticated;
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



}
