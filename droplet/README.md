## ubuntu 18.04 =)

These bash scripts sets a droplet for a quick functional environment to twitter-api application

```
$ ssh root@178.62.219.124
$ cd /home 
$ git clone https://github.com/devbytom/twitter-api.git app && cd app
$ cd droplet && chmod +x ./setup.sh
$ ./setup.sh
```
while setup.sh run, a confirm will be prompted u_u

nvm about this, or not
```
$ scp -r ../droplet root@178.62.219.124:/home/
```