package com.johnson.jyvertification.Consts;

/**
 * Created by johnsmac on 4/10/16.
 */
public class PublicUtil {

    public static final String NETWORKING = "networking";
    public static final String FILEOBJECT = "person_info";
    public static final int DEFAUTL_MSG_WHAT = 8;
    public static final String LOAD_FINISHED_ACTION = "loadFinished";
    public static final String ERROR_ACTION = "error";
    //    public static final String login_id = "loginid";
    public static final String role_id = "roleid";
    public static final String status = "status";
    public static final String hasFile = "hasfile";
    public static final String login_company_id = "logincompanyid";
    public static final String login_name_key = "LoginName";
    public static final String login_password_key = "LoginPassword";
    public static final String public_key = "your key ";
    public static final String base_url = "http://xxx.com/";
    public static final String private_key = "your key";
/*
GET api/Menus/Get?login={login}&pwd={pwd}&signature={signature}
获取根据用户角色获取相应的平台菜单(All)


*POST api/Verify/BankCard?login={login}&pwd={pwd}&signature={signature}
银行卡认证
IDCardType
证件类型（01身份证，02军官证，03护照，04回乡证，05台胞证，06警官证，07士兵证，99其它证）
{
  "CardNo": "sample string 1",
  "IDCardType": "sample string 2",
  "IDCardNo": "sample string 3",
  "UserName": "sample string 4",
  "TelephoneNo": "sample string 5"
}

POST api/Logs/GetVerify?login={login}&pwd={pwd}&signature={signature}
按条件获取用户的核查记录
ModuleType
认证模块/核查项目 类型(查全部:0)
{
  "Login": "sample string 1",
  "UserName": "sample string 2",
  "CardNo": "sample string 3",
  "ModuleType": 4,
  "IP": "sample string 5",
  "StartDate": "2016-04-11T10:48:06.1799911+08:00",
  "EndDate": "2016-04-11T10:48:06.1799911+08:00",
  "Skip": 8,
  "Take": 9
}


* */
}
