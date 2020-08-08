#!/usr/bin/env bash
# 执行 simple ./findDns.sh api.baidu.cn 102.102.102.219 rel.txt
#path=$4
echo "$1 $2"
ips=$(/usr/bin/host $1 $2 | /usr/bin/awk  '/has address/{print $4}')
#mkdir -p $path
if [[ ! -f $3 ]];   #判断此目录是否不存在
then
echo "文件不存在"
else
echo "文件存在"
rm $3;  #存在则直接切换目录
fi
echo $ips
echo $ips > $3