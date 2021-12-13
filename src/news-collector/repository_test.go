package news_collector

import (
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"testing"
)

func setupRepo() *Repository {
	db, err := gorm.Open(sqlite.Open(DBPath), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	return NewRepository(db)
}

func TestRepository_ReadSources(t *testing.T) {
	r := setupRepo()
	categories, err := r.ReadSources("sources.json")
	if err != nil {
		t.Error(err)
		return
	}
	t.Log(categories)
}
