package news_collector

import (
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"testing"
	"time"
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

func TestRepository_DeleteArticles(t *testing.T) {
	r := setupRepo()
	err := r.DeleteArticles(time.Date(2021, 12, 26, 0, 0, 0, 0, time.Now().Location()))
	if err != nil {
		t.Error(err)
	}
}
