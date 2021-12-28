Run tracking-news service on boot:
1. cd /etc/systemd/system
2. create file tracking-news.service with content:
```
[Unit]
Description=Tracking news service
After=network.target
StartLimitIntervalSec=10

[Service]
Type=simple
Restart=always
RestartSec=30
WorkingDirectory={absolute-path-to-current-directory}
ExecStart={absolute-path-to start.sh}

[Install]
WantedBy=multi-user.target
```
3. sudo systemctl daemon-reload
4. sudo systemctl restart tracking-news
5. sudo systemctl enable tracking-news

Check for status:
sudo systemctl status tracking-news


   