package news_collector

import (
	"github.com/mmcdole/gofeed"
)

type Collector struct {
	Parser     *gofeed.Parser
	Repository *Repository
}

func NewCollector(repository *Repository) *Collector {
	return &Collector{
		Parser:     gofeed.NewParser(),
		Repository: repository,
	}
}

func (c *Collector) CollectAll(categories []*Category) error {
	for _, category := range categories {
		articles, err := c.collectByCategory(category)
		if err != nil {
			return err
		}
		err = c.Repository.UpsertArticles(articles)
		if err != nil {
			return err
		}
	}
	return nil
}

func (c *Collector) collectByCategory(category *Category) ([]*Article, error) {
	articles := make([]*Article, 0)
	feed, err := c.Parser.ParseURL(category.Link)
	if err != nil {
		return nil, err
	}

	for _, item := range feed.Items {
		articles = append(articles, c.feedItemToArticle(item, category.Name, category.Source))
	}
	return articles, nil
}

func (c *Collector) feedItemToArticle(item *gofeed.Item, category, source string) *Article {
	link := item.Link
	if link == "" {
		for _, l := range item.Links {
			if l != "" {
				link = l
			}
		}
	}

	var image string
	if item.Image != nil {
		image = item.Image.URL
	}

	return &Article{
		Title:           item.Title,
		Description:     item.Description,
		Content:         item.Content,
		Link:            link,
		Published:       item.Published,
		PublishedParsed: item.PublishedParsed,
		Image:           image,
		Category:        category,
		Source:          source,
	}
}
