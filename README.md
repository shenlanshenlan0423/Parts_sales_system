## 零部件销售管理系统（Parts sales system）
**信息管理与信息系统19**
**1908020202何宏伟 1908000422汤燕茹 1908000417黄明宇 1809020116苏煜昕**
**指导老师：陈晨**
**2023/02~2023/03**

<font color=red>文件解释并不完全完整，各位成员在开发时请勿随意删除已有文件，并尽量按照正规的开发标准进行开发</font>

### 开发习惯建议：
- 新增页面要在AndroidManifest.xml中声明，并在README.md中写明
- 新增功能若为公共功能，则以public_开头，若为私有功能，则以private_开头
- 进行一部分工作后记得在git中commit，commit命名：日期+新增功能+By+姓名，例：0228-基本框架与使用方私有功能-使用管理-By-hhw


### java文件夹解释：
- public_MainActivity.java：用户登陆后的主界面
- private_UseManagementActivity.java：**使用方的私有功能**-使用管理界面
- data&ui_login：Android studio自带的登录注册模板，**@汤燕茹**参考、修改并编写相关方法
- public_FinancialManagementActivity.java：**公共功能**-财务管理界面
  
### res文件夹解释：
- drawable：图片资源
- layout：布局文件
  - activity_main.xml：用户主界面侧滑窗口界面
  - activity_private_use_management.xml：**使用方私有功能**-使用管理底层界面 
  - activity_public_financial_management.xml：**公共功能**-财务管理底层界面 
  - activity_scrolling.xml：二级功能上层界面
  - app_bar_main.xml：用户主界面上层界面
  - content_main.xml：用户主界面底层界面
  - fragment_gallery.xml：点击gallery后的主界面显示
  - fragment_home.xml：点击home后的主界面显示
  - fragment_slideshow.xml：点击slideshow后的主界面显示
  - item_add_float_button.xml：二级功能页面悬浮的数据新增按钮（add方法尚未绑定）
  - nav_header_main.xml：用户主界面侧滑窗口中的个人信息块

- menu：菜单文件
  - activity_main_drawer.xml：用户主界面侧滑窗口界面菜单
  - main.xml：用户主界面右上角弹出菜单
  - popmenu_patrolmanagement.xml：使用方私有功能-使用管理-巡检管理的弹出菜单
- values：
  - colors.xml：颜色相关
  - dimens.xml：组件间距相关
  - strings.xml：字符相关
  - themes：主题设置相关

### data.api_connection文件夹解释：
  - addData.java类中实现addData方法，参数为(String dbname,String params)，dbname为表名，params应为字符串类型的json格式数据。
  - delData.java类中实现delData方法，参数同addData。
  - getData.java类中实现getData方法，参数同addData。
  - 使用方法：在想要操作数据库的地方写入以下代码：
  new Thread(new Runnable(){
  @Override
  public void run() {
  try {
  addData.addData("UseDept","{'UseDeptName':'test'}");
  } catch (Exception e) {
  e.printStackTrace();
  }
  }
  }).start();