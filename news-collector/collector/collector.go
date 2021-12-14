package collector

import (
	"github.com/mmcdole/gofeed"
	"time"
	"tracking-news/news-collector/model"
	"tracking-news/news-collector/pkg"
	"tracking-news/news-collector/repository"
)

type Collector struct {
	Parser     *gofeed.Parser
	Repository *repository.Repository
}

func NewCollector(repository *repository.Repository) *Collector {
	return &Collector{
		Parser:     gofeed.NewParser(),
		Repository: repository,
	}
}

func (c *Collector) CollectAll(categories []*model.Category) error {
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

func (c *Collector) collectByCategory(category *model.Category) ([]*model.Article, error) {
	articles := make([]*model.Article, 0)
	feed, err := c.Parser.ParseURL(category.Link)
	if err != nil {
		return nil, err
	}

	for _, item := range feed.Items {
		articles = append(articles, c.feedItemToArticle(item, category.Name, category.Source))
	}
	return articles, nil
}

func (c *Collector) feedItemToArticle(item *gofeed.Item, category, source string) *model.Article {
	link := item.Link
	if link == "" {
		for _, l := range item.Links {
			if l != "" {
				link = l
			}
		}
	}

	extraData := model.ExtraData{}
	for k, v := range item.Custom {
		extraData[k] = v
	}

	var image string
	if item.Image != nil && item.Image.URL != "" {
		image = item.Image.URL
	} else if extraData["image"] != "" {
		image = extraData["image"]
	} else {
		image = pkg.ParseImageURL(item.Description)
	}

	return &model.Article{
		Title:           item.Title,
		Description:     item.Description,
		Link:            link,
		Published:       item.Published,
		PublishedParsed: item.PublishedParsed,
		Image:           image,
		ExtraData:       &extraData,
		Category:        category,
		Source:          source,
		CreatedAt:       time.Now(),
	}
}
