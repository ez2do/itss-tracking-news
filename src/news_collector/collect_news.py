import feedparser
import json

from src.db.models import Article


def main():
    d = feedparser.parse('https://vnexpress.net/rss/tin-moi-nhat.rss')
    with open('feed.json', 'w') as f:
        json.dump(d, f)

    for entry in d['entries']:
        Article.create(id=entry['id'], title=entry['title'], link=entry['link'])


main()
