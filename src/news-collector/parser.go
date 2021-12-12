package news_collector

import (
	"github.com/mmcdole/gofeed"
)

type Collector struct {
	Parser     *gofeed.Parser
	Categories []*Category
}

type Category struct {
	Source string `json:"source"`
	Link   string `json:"link"`
}

func NewCollector(categories []*Category) *Collector {
	return &Collector{
		Parser:     gofeed.NewParser(),
		Categories: categories,
	}
}

func (*Collector) CollectAll() {

}
