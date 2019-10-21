#!/bin/bash
set -x

display_usage() {
        echo -e "This command must be run with [state - active or drain]" 
}
if [ "$#" -lt 1 ]; then
        display_usage
        exit 1
fi

echo "State: ${1}"
#cd /home/ubuntu/DeathStarBench/socialNetwork


for i in `sudo docker node ls -q`
do
        sudo docker node update --availability ${1} ${i}
done

sleep 2

sudo docker node ls
