#!/bin/bash
set -x

display_usage() {
        echo -e "This command must be run with [join-token] [docker leader ip]" 
}
if [ "$#" -lt 2 ]; then
        display_usage
        exit 1
fi

sudo docker swarm leave
sudo docker system prune -af
sudo docker swarm join --token ${1} ${2}

