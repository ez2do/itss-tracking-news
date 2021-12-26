package news_collector

import (
	"github.com/mmcdole/gofeed"
	"time"
	"tracking-news/news-collector/pkg"
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

	extraData := ExtraData{}
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

	now := time.Now()
	y, M, d := now.Date()
	h, m, s := now.Hour(), now.Minute(), now.Second()

	updatedAt := time.Date(y, M, d, h, m, s, 0, now.Location())
	parsedTime := updatedAt
	if item.PublishedParsed != nil {
		parsedTime = item.PublishedParsed.In(now.Location())
	}
	return &Article{
		Title:           item.Title,
		Description:     item.Description,
		Link:            link,
		Published:       item.Published,
		PublishedParsed: parsedTime,
		Image:           image,
		ExtraData:       &extraData,
		Category:        category,
		Source:          source,
		UpdatedAt:       updatedAt,
	}
}

//func (c *Collector) extractContent(raw string) string {
//	r, _ := regexp.Compile("<(.*)>")
//}
