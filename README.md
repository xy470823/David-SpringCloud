>本项目用到的注册中心是consul;启动项目之前请自行安装consul;

## consul Linux安装教程
### 下载consul
下载：https://developer.hashicorp.com/consul/downloads

选择 Binary download for Linux </br>
╘═════[AMD64(Version: 1.15.2)](https://releases.hashicorp.com/consul/1.15.2/consul_1.15.2_linux_amd64.zip)

### 安装Consul

```shell
[root@localhost local]# cd app/
[root@localhost app]# ll
total 51580
-rw-r--r--.  1 root root 52811382 May 12 07:19 consul_1.15.2_linux_amd64.zip
#解压安装包
[root@localhost app]# unzip consul_1.15.2_linux_amd64.zip 
Archive:  consul_1.15.2_linux_amd64.zip
  inflating: consul                  
#启动
[root@localhost app]# sudo /usr/local/app/consul/consul agent -server -bootstrap-expect 1 --data-dir=data -node=master -ui -bind=192.168.139.128 -client=0.0.0.0

[root@localhost app]# mkdir -p /usr/local/app/consul/consul.d/
[root@localhost app]# vi /usr/local/app/consul/consul.d/server.json
#注意下面的192.168.139.128 IP地址换成自己的
{
  "datacenter": "bc-dc-1",
  "data_dir": "/usr/local/app/consul/data/",
  "log_level": "INFO",
  "node_name": "bc-dc-1",
  "server": true,
  "ui": true,
  "acl_enforce_version_8":false,
  "bootstrap": true,
  "bootstrap_expect":1,
  "client_addr": "0.0.0.0",
  "bind_addr": "192.168.139.128",
  "advertise_addr_wan": "192.168.139.128",
  "domain":"consul",
  "telemetry": {
      "statsd_address": "127.0.0.1:8125"
  }
}
#启动consul
[root@localhost app]# /usr/local/app/consul/consul agent -config-dir /usr/local/app/consul/consul.d
```

配置开机自启动

```shell
[root@localhost app]# vi /lib/systemd/system/consul.service
#注意相关的路径改成自己的
[Unit]
Description=Consul
After=network.target
[Service]
ExecStart=/usr/local/app/consul/consul agent -config-dir /usr/local/app/consul/consul.d -log-file=/usr/local/app/consul/logs/consul.log -log-rotate-bytes=102400000
KillSignal=SIGINT
[Install]
WantedBy=multi-user.target

#重新加载启动配置
[root@localhost app]# systemctl daemon-reload
#设置开机启动
[root@localhost app]# systemctl enable consul
#通过服务启动
[root@localhost app]# systemctl start consul
#重启服务
[root@localhost app]# systemctl restart consul
#查看服务是否已启动 
[root@localhost system]#systemctl is-active consul
#查看服务的状态 
[root@localhost system]# systemctl status consul
```





