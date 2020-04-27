sudo apt update

sudo apt install nginx

echo "
  worker_processes  1;
  events {
      worker_connections  1024;
  }

  http {
      include       mime.types;
      default_type  application/octet-stream;

      sendfile        on;
      keepalive_timeout  65;

      upstream echotom.dev {
          server localhost:3000;
      }

      server {
          server_name echotom.dev;
          listen 443 ssl;
          ssl_certificate /etc/letsencrypt/live/echotom.dev/cert.pem;
          ssl_certificate_key /etc/letsencrypt/live/echotom.dev/privkey.pem;
          ssl_verify_client off;
          location / {
              proxy_pass  https://echotom.dev;
              proxy_set_header Host $http_host;
              proxy_set_header X_FORWARDED_PROTO https;
          }
      }
  }
" >> /etc/nginx/nginx.conf

sudo systemctl start nginx
stemctl status nginx

sudo apt install docker.io
sudo systemctl start docker
sudo systemctl enable docker

docker build -t twitter-api /home/app/
docker run -d -p 80:3000 twitter-api:latest
