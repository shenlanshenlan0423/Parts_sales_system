## 零部件销售管理系统（Parts sales system）
**信息管理与信息系统19**<br>
**1908020202何宏伟 1908000422汤燕茹 1908000417黄明宇 1809020116苏煜昕**<br>
**指导老师：陈晨**<br>
**2023/02~2023/03**<br>

<font color=red>文件解释并不完全完整，各位成员在开发时请勿随意删除已有文件，并尽量按照正规的开发标准进行开发</font>

### 小组提示：
- 公共功能-财务管理-付款管理中的增删改查、翻页功能已经实现，方法供各位参考，**请抓紧进度**
### 开发习惯建议：
- 新增页面要在AndroidManifest.xml中声明，并在README.md中写明
- 新增功能若为公共功能，则以public_开头，若为私有功能，则以private_开头
- 进行一部分工作后记得在git中commit，commit命名：日期+新增功能+By+姓名，例：0228-基本框架与使用方私有功能-使用管理-By-hhw

### 私有功能-使用管理-巡检管理模板复用指南：
以下文件均为巡检管理所用到的文件，**复用时请相应生成**：
- ui.use_management:
  - cbx_Adapter.java
  - HomeFragment.java
  - PatrolManagement.java
- private_UseManagement_PatrolManagement_PatrolList_AddData.java
- private_UseManagement_PatrolManagement_PatrolList_SetData.java
- private_UseManagementActivity.java
- **manifest中声明这三个java文件**
- activity_private_use_management.xml
- private_use_management_patrolrecordlist_setdata.xml
- private_use_management_patrolrecordlist_adddata.xml
- private_use_management_patrolrecordlist_item_cbx.xml
- private_use_management_patrolrecordlist_item.xml
- **在string.xml中增加相应字段（规范化开发）**

### java文件夹解释：
- public_MainActivity.java：用户登陆后的主界面
- private_UseManagementActivity.java：**使用方的私有功能**-使用管理界面
- data&ui_login：Android studio自带的登录注册模板:修改中，未完成，报错正常
- public_BasicSetting.java:**公共功能**-基础配置界面
- public_FinancialManagementActivity.java：**公共功能**-财务管理界面
- ui_login_Register.java:**公共功能**-注册界面
- public_UseManagementActivity.java：**公共功能**-使用管理界面


### res文件夹解释：
- drawable：图片资源
  - circle_border_design.xml：圆形布局设计
  - rectangle_border_design.xml：矩形布局设计
- layout：布局文件
  - activity_main.xml：用户主界面侧滑窗口界面
  - activity_private_use_management.xml：**使用方私有功能**-使用管理底层界面 
  - activity_public_basic_setting.xml：**公共功能**-基础配置界面
  - activity_public_financial_management.xml：**公共功能**-财务管理底层界面
  - activity_public_use_management.xml：**公共功能**-使用管理底层界面
  - activity_register.xml:**公共功能**-用户注册界面
  - activity_scrolling.xml：二级功能上层界面
  - app_bar_main.xml：用户主界面上层界面
  - content_main.xml：用户主界面底层界面
  - fragment_gallery.xml：点击gallery后的主界面显示
  - fragment_home.xml：点击home后的主界面显示
  - fragment_slideshow.xml：点击slideshow后的主界面显示
  - item_add_float_button.xml：二级功能页面悬浮的item新增按钮（此布局文件尚未与public_FinancialManagementActivity绑定）
  - nav_header_main.xml：用户主界面侧滑窗口中的个人信息块
  - order_id_list.xml：**订货付款列表**的item新增操作中的**订货单编码ID**的下拉选择框
  - orderpaymentlist_item_add.xml：用户点击新增按钮，实现**订货付款列表**的item新增操作
  - orderpaymentlist_item_detail.xml：用户点击item，弹出**订货付款列表**item的详细信息窗口，可进行修改、删除操作


- menu：菜单文件
  - activity_main_drawer.xml：用户主界面侧滑窗口界面菜单
  - main.xml：用户主界面右上角弹出菜单
  - popmenu_patrolmanagement.xml：使用方私有功能-使用管理-巡检管理的弹出菜单
- values：
  - array.xml：Spinner下拉框的固定写法，只作**测试用**
  - colors.xml：颜色相关
  - dimens.xml：组件间距相关
  - strings.xml：字符相关
  - themes：主题设置相关

### data.api_connection文件夹解释：
  - addData.java：实现addData方法，参数为(String dbname,String params)，dbname为表名，params为字符串类型的json格式数据，不需要返回输出。
  - delData.java：实现delData方法，参数同上，不需要返回输出。
  - getData.java：实现getData方法，参数同上，需要返回输出。
  - modifyData.java：实现modifyData方法，参数同上，不需要返回输出。
  - 使用方法：在想要操作数据库的地方写入以下代码：
```  new Thread(new Runnable(){
  @Override
  public void run() {
    try {
      addData.addData("UseDept","{'UseDeptName':'test'}");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  }).start();
```