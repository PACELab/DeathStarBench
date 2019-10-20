#!/bin/bash
set -x
SETUP_WORKER_COMMAND='/home/ubuntu/dsb_scripts/leave_swarm.sh'

display_usage() {
        echo -e "This command must be run with [number of workers]" 
}
if [ "$#" -lt 1 ]; then
        display_usage
        exit 1
fi

echo "Number of workers: ${1}"
#cd /home/ubuntu/DeathStarBench/socialNetwork

for i in `cat /home/ubuntu/DeathStarBench/dsb_scripts/workerList | head -${1}`
do
        ssh -i ~/compass.key ubuntu@${i} "${SETUP_WORKER_COMMAND}" 
done

sudo docker network rm snoverlaynetwork
sudo docker swarm leave --force
sudo docker system prune -af

sudo docker node ls
