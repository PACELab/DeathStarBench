4 VMs
dockerclient, dockerworker1, dockerworker2, dockerworker3

Step 1 (stop all containers) (Be aware of executing this command)
 Run following commands on all 4 VMs
sudo docker stop $(sudo docker ps -a -q)
sudo docker rm $(sudo docker ps -a -q)


Step 2 (setup key-value store)
Run this command on dockerclient
sudo docker run -d -p 8500:8500 -h consul --name consul progrium/consul -server -bootstrap


Step 3 (Set docker daemon to use key-value store for clustering)
Run following two commands on dockerworker{1,2,3}

sudo service docker stop

sudo dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock --cluster-advertise ens3:2375 --cluster-store consul://192.168.0.224:8500

(Here, 192.168.0.224 is ip address of dockerclient)


Step 4 (Create an overlay network)
Run this command on dockerworker1

sudo docker network create -d overlay — subnet=192.168.67.0/24 my-overlay

Step 5(Add containers to overlay network)
On dockerworker1
sudo docker run -itd --name containerA --net my-overlay busybox

On dockerworker2
sudo docker run -itd --name containerB --net my-overlay busybox

On dockerworker3
sudo docker run -itd --name containerC --net my-overlay busybox


Step 6 (Check my-overlay network)
Run this command on any machine( dockerworker{1,2,3} )
sudo docker inspect my-overlay


Step 7(Ping containers across overlay network)
Run this command on dockerworker1

sudo docker exec containerA ping -w 5 containerC
