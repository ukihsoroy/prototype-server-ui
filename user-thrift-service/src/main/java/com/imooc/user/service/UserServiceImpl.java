package com.imooc.user.service;

import com.imooc.thrift.user.UserInfo;
import com.imooc.thrift.user.UserService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description: UserServiceImpl <br>
 * date: 2020/2/29 19:10 <br>
 *
 * @author K.O <br>
 * @version 1.0 <br>
 */
@Service
public class UserServiceImpl implements UserService.Iface {

    private Map<Integer, UserInfo> table = new ConcurrentHashMap<>();

    @Override
    public UserInfo getUserById(int id) throws TException {
        return table.get(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        for (Map.Entry<Integer, UserInfo> integerUserInfoEntry : table.entrySet()) {
            UserInfo userInfo = integerUserInfoEntry.getValue();
            String name = userInfo.getUsername();
            if (name.equals(username)) return userInfo;
        }
        return null;
    }

    @Override
    public void registerUser(UserInfo userInfo) throws TException {
        table.put(userInfo.getId(), userInfo);
    }
}
