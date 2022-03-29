run() {
    for i in {1..4};
    do
        docker run -d --name jdpos-${i} --cpus=0.5 -p 808${i}:8080 jdpos;
    done
}

start () {
    for i in {1..4};
    do
        docker start jdpos-${i};
    done
}

stop() {
    for i in {1..4};
    do
        docker stop jdpos-${i};
    done
}

remove() {
    for i in {1..4};
    do
        docker rm jdpos-${i};
    done
}
