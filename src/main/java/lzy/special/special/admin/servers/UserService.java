package lzy.special.special.admin.servers;

import lzy.special.special.admin.popj.UserPopj;

import java.util.Set;

/**
 * 用户业务类
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    UserPopj findByUsername(String username);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> findPermissions(String username);



}
