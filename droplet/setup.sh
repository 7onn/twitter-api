sudo apt update

sudo apt install docker.io
sudo systemctl start docker
sudo systemctl enable docker

docker build -t twitter-api /home/app/
docker run -d -p 80:3000 twitter-api:latest
