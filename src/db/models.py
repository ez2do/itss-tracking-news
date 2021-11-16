from peewee import SqliteDatabase, Model, CharField, DateTimeField
import pathlib

db_path = pathlib.Path(__file__).parent.parent.parent.resolve().joinpath('news.db')
db = SqliteDatabase(db_path)


class Article(Model):
    id = CharField(primary_key=True)
    title = CharField(null=False)
    summary = CharField(null=True)
    link = CharField(null=False)
    published = DateTimeField(null=True)

    class Meta:
        database = db
