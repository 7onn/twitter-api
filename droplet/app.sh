cd /home
git clone https://github.com/devbytom/twitter-api app
cd app
docker build -t twitter-api /home/app/
docker run -d -p 80:3000 twitter-api:latest