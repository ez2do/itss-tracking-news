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
	err := r.DeleteArticles([]int64{1, 2})
	if err != nil {
		t.Error(err)
	}
}

func TestRepository_DeleteHistories(t *testing.T) {
	r := setupRepo()
	err := r.DeleteHistories([]int64{1, 2})
	if err != nil {
		t.Error(err)
	}
}

func TestRepository_GetOldArticleIDs(t *testing.T) {
	r := setupRepo()
	ids, err := r.GetOldArticleIDs(time.Now())
	if err != nil {
		t.Error(err)
	}
	t.Log(ids)
}

func TestRepository_GetWatchLaterIDs(t *testing.T) {
	r := setupRepo()
	ids, err := r.GetWatchLaterIDs()
	if err != nil {
		t.Error(err)
	}
	t.Log(ids)
}
