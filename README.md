# Tracking news application

## Description
The application contains 2 components, the collector and the GUI.<br> 
The collector is a background job run to collect new articles from RSS sources.<br>
The GUI is the user interface to show articles and interact with user.<br>
After install, set up the collector to run in background as below, 
the collector will start running when system boot.

## Run background tracking-news collector:
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

## Run application GUI:
At the root folder of the project, execute gui.sh file by the command `./gui.sh`
   