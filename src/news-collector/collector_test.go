package news_collector

import (
	"log"
	"testing"
)

func setupCollector() *Collector {
	categories, err := ReadSources("sources.json")
	if err != nil {
		log.Fatal(err)
	}
	return NewCollector(categories)
}

func TestCollector_CollectAll(t *testing.T) {
	c := setupCollector()
	feeds, err := c.CollectAll()
	if err != nil {
		t.Error(err)
		return
	}
	t.Log(feeds)
}
