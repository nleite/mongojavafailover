#!/bin/sh

#killall mongod's
killall mongod
#remove database folders
rm -rf /tmp/db*

echo "Done"
