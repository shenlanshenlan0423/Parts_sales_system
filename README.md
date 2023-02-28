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
- MainActivity.java：用户登陆后的主界面
- private_UseManagementActivity.java：使用方的私有功能-使用管理界面
- data&ui_login：Android studio自带的登录注册模板，**@汤燕茹**参考、修改并编写相关方法
  
### res文件夹解释：
- drawable：图片资源
- layout：布局文件
  - activity_main.xml：用户主界面侧滑窗口界面
  - activity_scrolling.xml：二级功能上浮界面
  - app_bar_main.xml：一级功能上浮界面
  - content_main.xml：用户主界面下层界面
  - content_scrolling：使用方私有功能-使用管理下层界面
  - nav_header_main.xml：用户主界面侧滑窗口中的个人信息界面
- menu：菜单文件
  - activity_main_drawer.xml：用户主界面侧滑窗口界面菜单
  - main.xml：用户主界面右上角弹出菜单
  - popmenu_patrolmanagement.xml：使用方私有功能-使用管理-巡检管理的弹出菜单
- values：
  - colors.xml：颜色相关
  - dimens.xml：组件间距相关
  - strings.xml：字符相关
  - themes：主题设置相关