package com.yuchengtech.mrtn.login.bean;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.Objects;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = -1373760761780840081L;
    public String userId;
    public String deptDutyman;// 后加的
    public String userName;
    public Long deptId;
    public String instId;
    public String postId;
    public Long currSysRoleId;
    public String currSysRoleName;
    public String pwdStatus;
    public String hText;
    public String currTheme;
    public String kickout_msg;
    public String roleIds;
    public String roleNames;
    public String childInstIds;
    public String sysRoleIds;
    public String sysRoleNames;

    public ShiroUser(String userId, String userName) {

        this.userId = userId;
        this.userName = userName;
    }

    public ShiroUser() {
        // TODO Auto-generated constructor stub
    }

    public String getName() {
        return userName;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return userId;
    }

    /**
     * 重载hashCode,只计算loginName;
     */
    @SuppressLint("NewApi")
    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    /**
     * 重载equals,只计算loginName;
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiroUser other = (ShiroUser) obj;
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        return true;
    }
}
