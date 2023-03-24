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
