REM 声明采用UTF-8编码
chcp 65001

del .\*.jar

@echo off
echo copy h2数据库开发包
copy ..\..\..\lib\h2\bin\h2-1.3.173.jar .\  
echo copy excl开发包
copy ..\..\..\lib\jexcelapi\jxl.jar .\
echo copy jyloo皮肤开发包
copy ..\..\..\lib\jyloo\synthetica_2.17.1_eval\synthetica.jar .\
copy ..\..\..\lib\jyloo\syntheticaAluOxide.jar .\

echo copy jreechart封装图表开发包
copy ..\..\spchart\store\sp_chart.jar .\
echo copy jtable封装图表开发包
copy ..\..\sptable\dist\sptable.jar .\
echo copy spdev_dll 驱动
copy ..\..\sp_drv\dev_win_drv\dist\dev_win_drv.jar .\

echo copy 自定义平台开发包
copy ..\..\..\PlatForm\commonbean\dist\commonbean.jar .\

pause