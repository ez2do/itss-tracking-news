package news_collector

import (
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"testing"
)

func setupRepo() *Repository {
	db, err := gorm.Open(sqlite.Open("../../news.db"), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	return NewRepository(db)
}

func TestRepository_GetAllCategories(t *testing.T) {
	r := setupRepo()
	categories, err := r.GetAllCategories()
	if err != nil {
		t.Error(err)
		return
	}
	t.Log(categories)
}
