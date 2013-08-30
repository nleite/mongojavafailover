#!/bin/sh

#replset name
REPLSET=TEST
host=127.0.0.1
#create data folders at /tmp
dbp0=/tmp/db0
dbp1=/tmp/db1
dbp2=/tmp/db2
mkdir -p $dbp0 $dbp1 $dbp2

#start mongodb at localhost 27017
mongod --dbpath $dbp0 --port 27007 --replSet $REPLSET --logpath $dbp0/log --fork --bind_ip $host
mongod --dbpath $dbp1 --port 27017 --replSet $REPLSET --logpath $dbp1/log --fork --bind_ip $host
mongod --dbpath $dbp2 --port 27027 --replSet $REPLSET --logpath $dbp2/log --fork --bind_ip $host

#initiate replicaset 
conf="{_id:'$REPLSET', members:[ {_id:0, host:'$host:27007', priority:10}, {_id:1, host:'$host:27017'}, {_id:2, host:'$host:27027'}]}"
sleep 2
mongo --port 27007 --eval "out=rs.initiate($conf);printjson(out);printjson(rs.conf());"
