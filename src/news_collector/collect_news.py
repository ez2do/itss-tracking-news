import feedparser
import json

from src.db.models import Article


def main():
    d = feedparser.parse('https://nld.com.vn/thoi-su.rss')
    print(d)
    with open('feed.json', 'w') as f:
        json.dump(d, f)

    # for entry in d['entries']:
    #     Article.create(id=entry['id'], title=entry['title'], link=entry['link'])


main()
