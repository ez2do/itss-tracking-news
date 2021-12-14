package collector

import (
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"testing"
	"tracking-news/news-collector/repository"
)

func setupRepo() *repository.Repository {
	db, err := gorm.Open(sqlite.Open("../../news.db"), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	return repository.NewRepository(db)
}

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
