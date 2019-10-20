#!/bin/bash
set -x

sudo docker swarm leave
sudo docker system prune -af

