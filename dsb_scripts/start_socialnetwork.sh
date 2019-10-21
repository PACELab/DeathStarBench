cd ~/DeathStarBench/socialNetwork/
sudo docker stack deploy --compose-file=docker-compose.yml socialnetwork
sleep 3
echo "sudo docker node ps $(sudo docker node ls -q) --filter desired-state=Running | uniq"
sudo docker node ps $(sudo docker node ls -q) --filter desired-state=Running | uniq
