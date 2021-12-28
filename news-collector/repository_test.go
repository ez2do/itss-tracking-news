package news_collector

import (
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"testing"
)

func setupRepo() *Repository {
	db, err := gorm.Open(sqlite.Open("../news.db"), &gorm.Config{})
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

func TestRepository_GetSettingByName(t *testing.T) {
	r := setupRepo()
	setting, err := r.GetSettingByName("update_interval_hour")
	if err != nil {
		t.Error(err)
		return
	}
	t.Log(setting)
}

func TestRepository_GetLatestArticle(t *testing.T) {
	r := setupRepo()
	article, err := r.GetLatestArticle()
	if err != nil {
		t.Error(err)
		return
	}
	t.Log(article.UpdatedAt)
}
