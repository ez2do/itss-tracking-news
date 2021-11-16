Set up environment for project:
- python3 -m venv env
- source env/bin/activate
- pip install -r requirements.txt
- export PYTHONPATH=$(pwd)
- python3 src/db/seeder.py

Project structure:
- db: define models
- menu: menu for terminal
- news_collector: collect news from internet