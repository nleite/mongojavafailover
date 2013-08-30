#!/bin/sh

out=`mongo --eval "db.isMaster().primary"`
primary=`echo $out | awk '{split( $0, a, " " ); print a[length(a)]; }'`
mongo --host $primary --eval "db.adminCommand({ shutdown: 1, force: true })"
