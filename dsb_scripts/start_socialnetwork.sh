cd ~/DeathStarBench/socialNetwork/
sudo docker stack deploy --compose-file=docker-compose.yml socialnetwork
sleep 3
sudo docker node ps $(sudo docker node ls -q) --filter desired-state=Running | uniq
echo "sudo docker node ps \$(sudo docker node ls -q) --filter desired-state=Running | uniq"
