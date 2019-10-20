#!/bin/bash
#Script that pushes worker scripts to workers
set -x
for i in `cat workerList`
do
        sudo scp -r -i ~/compass.key ~/DeathStarBench/dsb_scripts/worker_scripts/* ubuntu@${i}:~/dsb_scripts/
done

