# blog_service
wordpress通用博客爬虫
mongodb+webflux+jsoup+springboot+java17+jpa

爬虫会遍历整个博客网站链接，对链接内容循环分析和爬取。  

维护任务队列，队列长度默认为1000.  

可以修改为通过配置文件设置最大队列长度以及最小队列。  
 
当前队列长度小于50时，会再次从数据库同步任务。  

未实现前端页面。  
  
  StartService.class 实现 ApplicationRunner 的run方法在程序启动时开启任务。

接口调用方式

GET https://www.gardeningknowhow.com
Accept: application/json
###
GET https://www.housedigest.com/855526/couch-vs-chaise-longue-whats-the-difference/
Accept: application/json


### server test


### 家居
POST http://localhost:8888/task/save?address=https://www.epicurious.com&category=HomeFurnishing
Content-Type: application/x-www-form-urlencoded

address=https://www.candlejunkies.com&category=HomeFurnishing

### 园艺
POST http://localhost:8888/task/save?address=https://www.smartgardenguide.com&category=Gardening
Content-Type: application/x-www-form-urlencoded

### 宠物
POST http://localhost:8888/task/save?address=https://www.petcomments.com&category=Pet
Content-Type: application/x-www-form-urlencoded

### 健身
POST http://localhost:8888/task/save?address=https://www.dailycup.yoga&category=Fitness
Content-Type: application/x-www-form-urlencoded


