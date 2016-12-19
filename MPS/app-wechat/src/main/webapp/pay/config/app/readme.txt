  OPT目录更新了与公安网实名认证的license、与交行的PFX、cre 以及WEBSITE的自定义属性配置。如下描述，需要SCM到时候配合更新测试、生产环境，若有疑问请及时联系我。

文件夹
bocommjava
\opt\pay\config\app\bocommjava\cert\ 301310063009501.PFX  交互文件
\opt\pay\config\app\bocommjava\cert\root.cer              生产证书
\opt\pay\config\app\bocommjava\cert\test_root.cer          测试证书
opt\pay\config\app\bocommjava\ini\B2CMerchant.xml        交互配置
opt\pay\config\app\bocommjava\log                       调用日志(可考虑迁移致统一日志存储)
opt\pay\config\app\bocommjava\settlement                                     



license.txt      公安网授权文件
gov.properties   与公安网交互配置
bocom.properties 与交行交互配置



另：测试环境或者线上JVM需增加 -Dhttps.proxyHost="192.168.0.101" -Dhttps.proxyPort="3128"   因为本地通过代理上到外网所以需要配置，可以考虑联系运维配置对外NDS映射。


2011-04-29
公安网实名认证使用基于CXF本地接口实现，现需要“NciicServices.wsdl”WSDL文件和“license.txt”公安网授权文件。
(使用本实现gov.properties文件不需要配置。)