<html>
<head>
    <title>Student</title>
</head>
   <body>
      学生信息:<br>
      学号:${student.id}&nbsp;&nbsp;&nbsp;&nbsp;
      姓名:${student.name}&nbsp;&nbsp;&nbsp;&nbsp;
      年龄:${student.age}&nbsp;&nbsp;&nbsp;&nbsp;
      地址:${student.address}<br>
      学生列表:<br>
   <table border="1px">
       <tr>
           <th>序号</th>
           <th>学号</th>
           <th>姓名</th>
           <th>年龄</th>
           <th>地址</th>
       </tr>
       <#list studentList as student>
       <#if student_index%2==0>
            <tr bgcolor="red">
       <#else>
             <tr bgcolor="green">
       </#if>
               <td>${student_index}</td>
               <td>${student.id}</td>
               <td>${student.name}</td>
               <td>${student.age}</td>
               <td>${student.address}</td>
       </#list>
   </table>
   <br>
      <#--可以使用?date,?time,?datetime-->
      <#--?stirng("yyyy-MM-dd hh:mm:ss")-->
   当前日期:${date?date}
   <br>
   ${val!"默认值为2"}<br>
   判断val的值是否为null
   <#if val??>
       val值有内容
     <#else>
     val值为null
   </#if>
   引用模板测试<br>
   <#include "hello.ftl">
   </body>
</html>