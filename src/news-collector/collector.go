package news_collector

import (
	"github.com/mmcdole/gofeed"
)

type Collector struct {
	Parser     *gofeed.Parser
	Categories []*Category
}

func NewCollector(categories []*Category) *Collector {
	return &Collector{
		Parser:     gofeed.NewParser(),
		Categories: categories,
	}
}

func (c *Collector) CollectAll() ([]*gofeed.Feed, error) {
	feeds := make([]*gofeed.Feed, 0)
	for _, category := range c.Categories {
		feed, err := c.Parser.ParseURL(category.Link)
		if err != nil {
			return nil, err
		}
		feeds = append(feeds, feed)
	}
	return feeds, nil
}
