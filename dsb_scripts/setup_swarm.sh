#!/bin/bash
set -x
SETUP_WORKER_COMMAND='/home/ubuntu/dsb_scripts/setup_worker.sh'

display_usage() {
        echo -e "This command must be run with [number of workers]" 
}
if [ "$#" -lt 1 ]; then
        display_usage
        exit 1
fi

echo "Number of workers: ${1}"
#cd /home/ubuntu/DeathStarBench/socialNetwork

sudo docker network rm snoverlaynetwork
sudo docker swarm leave --force
sudo docker system prune -af
sudo docker swarm init --advertise-addr 192.168.0.142 > /home/ubuntu/DeathStarBench/dsb_scripts/init.out

JOIN_TOKEN=`sed -n '5p' < /home/ubuntu/DeathStarBench/dsb_scripts/init.out | awk '{print $5}'`
LEADER_IP=`sed -n '5p' < /home/ubuntu/DeathStarBench/dsb_scripts/init.out | awk '{print $6}'`

sudo rm /home/ubuntu/DeathStarBench/dsb_scripts/init.out


#Creating the overlay network
sudo docker network create -d overlay snoverlaynetwork

sleep 1 


for i in `cat /home/ubuntu/DeathStarBench/dsb_scripts/workerList | head -${1}`
do
        ssh -i ~/compass.key ubuntu@${i} "${SETUP_WORKER_COMMAND} ${JOIN_TOKEN} ${LEADER_IP}" 
done

sleep 2

sudo docker node ls
