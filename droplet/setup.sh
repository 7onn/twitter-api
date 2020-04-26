sudo apt update

sh ./firewall/start.sh
sh ./docker/start.sh

docker build -t twitter-api /home/app/
docker run -d -p 80:3000 twitter-api:latest

