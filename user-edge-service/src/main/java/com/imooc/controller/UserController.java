package com.imooc.controller;

import com.imooc.support.Response;
import com.imooc.thrift.ServiceProvider;
import com.imooc.thrift.user.UserInfo;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * description: UserController <br>
 * date: 2020/2/29 19:38 <br>
 *
 * @author K.O <br>
 * @version 1.0 <br>
 */
@RestController
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @PostMapping("login")
    public Response login(@RequestParam String username,
                      @RequestParam String password) {
        //1.验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;
        }

        if (userInfo == null) {
            return Response.USERNAME_PASSWORD_INVALID;
        }

        if (!userInfo.getPassword().equalsIgnoreCase(md5(password))) {
            return Response.USERNAME_PASSWORD_INVALID;
        }

        //2.生成token
        String token = getToken();

        //3.缓存用户

        return null;
    }

    private String getToken() {
        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder builder = new StringBuilder(size);

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int loc = random.nextInt(s.length());
            builder.append(s.charAt(loc));
        }

        return builder.toString();
    }

    private String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(password.getBytes("utf-8"));
            return HexUtils.toHexString(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
