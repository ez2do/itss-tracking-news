package news_collector

import (
	"testing"
)

func setupCollector() *Collector {
	r := setupRepo()
	return NewCollector(r)
}

func TestCollector_CollectAll(t *testing.T) {
	c := setupCollector()
	categories, err := c.Repository.GetAllCategories()
	if err != nil {
		t.Error(err)
		return
	}
	err = c.CollectAll(categories)
	if err != nil {
		t.Error(err)
		return
	}
}

func TestCollector_CollectAll2(t *testing.T) {
	c := setupCollector()
	categories, err := c.Repository.GetAllCategories()
	if err != nil {
		t.Error(err)
		return
	}
	err = c.CollectAll(categories[:2])
	if err != nil {
		t.Error(err)
		return
	}
}
