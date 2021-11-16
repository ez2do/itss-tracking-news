from src.db.models import db, Article

db.connect()
db.create_tables([Article])
