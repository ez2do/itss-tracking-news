package cmd

import (
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"strings"
	"testing"
	"time"
)

func setupRepo(path string) *Repository {
	db, err := gorm.Open(sqlite.Open(path), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	return NewRepository(db)
}

func TestRepository_GetAllCategories(t *testing.T) {
	r := setupRepo("../news.db")
	categories, err := r.GetAllCategories()
	if err != nil {
		t.Error(err)
		return
	}
	t.Log(categories)
}

func TestRepository_GetSettingByName(t *testing.T) {
	r := setupRepo("../news.db")
	setting, err := r.GetSettingByName("update_interval_hour")
	if err != nil {
		t.Error(err)
		return
	}
	t.Log(setting)
}

func TestRepository_GetLatestArticle(t *testing.T) {
	r := setupRepo("../news.db")
	article, err := r.GetLatestArticle()
	if err != nil {
		t.Error(err)
		return
	}
	t.Log(article.UpdatedAt)
}

func TestRepository_DeleteArticles(t *testing.T) {
	r := setupRepo("../news.db")
	err := r.DeleteArticles([]int64{1, 2})
	if err != nil {
		t.Error(err)
	}
}

func TestRepository_DeleteHistories(t *testing.T) {
	r := setupRepo("../news.db")
	err := r.DeleteHistories([]int64{1, 2})
	if err != nil {
		t.Error(err)
	}
}

func TestRepository_GetOldArticleIDs(t *testing.T) {
	r := setupRepo("../news.db")
	ids, err := r.GetOldArticleIDs(time.Now())
	if err != nil {
		t.Error(err)
	}
	t.Log(ids)
}

func TestRepository_GetWatchLaterIDs(t *testing.T) {
	r := setupRepo("../news.db")
	ids, err := r.GetWatchLaterIDs()
	if err != nil {
		t.Error(err)
	}
	t.Log(ids)
}

func TestRepository_ExtractDescription(t *testing.T) {
	r := setupRepo("../../news.db")
	articles, err := r.GetAllArticles()
	if err != nil {
		t.Log(err)
		return
	}
	for _, article := range articles {
		article.Description = ExtractContent(article.Description)
		article.Link = strings.TrimSpace(article.Link)
	}
	err = r.UpsertArticles(articles)
	if err != nil {
		t.Log(err)
		return
	}
}

func TestRepository_InsertSettings(t *testing.T) {
	r := setupRepo("../news.db")
	settings := []*Setting{
		{
			Name:        "update_interval_hour",
			NumberValue: 3,
		},
		{
			Name:        "delete_interval_day",
			NumberValue: 30,
		},
	}
	err := r.InsertSettings(settings)
	if err != nil {
		t.Log(err)
		return
	}
}
