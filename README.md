## 零部件销售管理系统（Parts sales system）
**信息管理与信息系统19**<br>
**1908020202何宏伟 1908000422汤燕茹 1908000417黄明宇 1809020116苏煜昕**<br>
**指导老师：陈晨**<br>
**2023/02/19~2023/03/18**<br>

### 项目说明：
- 本次开发基于谷歌推出的Android集成开发工具：Android Studio 2021.1
- github仓库：https://github.com/shenlanshenlan0423/Parts_sales_system
- 条件查询示例：基础配置-用户列表

### 注意：
- 源码查看方法：
  1. 将仓库压缩文件解压，用Android Studio打开即可
  2. fork上述github仓库，克隆到本地用Android Studio打开即可
- App使用方法：
  1. 在安卓手机上安装apk文件，打开即可
  2. 用Android Studio打开源码后创建模拟器或连接手机运行即可
  3. 需要连接网络使用

### 小组提示：
- 新增页面要在AndroidManifest.xml中声明
- 新增功能若为公共功能，则以public_开头，若为私有功能，则以private_开头
- 进行一部分工作后记得在git中commit，commit命名：日期+新增功能+By+姓名，例：0228-基本框架与使用方私有功能-使用管理-By-hhw

### 私有功能-使用管理-巡检管理模板复用指南：
以下文件均为巡检管理所用到的文件，**复用时请相应创建**：
- ui.use_management:
  - cbx_Adapter.java
  - HomeFragment.java
  - PatrolManagement.java
- private_UseManagement_PatrolManagement_PatrolList_AddData.java
- private_UseManagement_PatrolManagement_PatrolList_SetData.java
- private_UseManagementActivity.java
- **manifest中声明这三个java类**
- activity_private_use_management.xml
- private_use_management_patrolrecordlist_setdata.xml
- private_use_management_patrolrecordlist_adddata.xml
- private_use_management_patrolrecordlist_item_cbx.xml
- private_use_management_patrolrecordlist_item.xml
- **在string.xml中增加相应字段（规范化开发）**
