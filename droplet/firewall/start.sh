sudo apt install ufw
sudo ufw default deny incoming
sudo ufw default allow outgoing
sudo ufw allow ssh
sudo ufw allow 80
sudo ufw allow 443
# sudo ufw allow 5430:5432/tcp
# sudo ufw deny from 203.0.123.5
sudo ufw enable
# sudo ufw status verbose