package main

import (
	"encoding/json"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"os"
	news_collector2 "tracking-news/news-collector/model"
	"tracking-news/news-collector/repository"
)

func main() {
	db, err := gorm.Open(sqlite.Open("news.db"), &gorm.Config{})
	if err != nil {
		log.Panicln(err)
	}

	err = migrateSchemas(db)
	if err != nil {
		log.Panicln(err)
	}

	r := repository.NewRepository(db)
	err = migrateCategories(r)
	if err != nil {
		log.Panicln(err)
	}
}

func migrateSchemas(db *gorm.DB) error {
	migrator := db.Migrator()

	dropModels := []interface{}{
		&news_collector2.Article{},
		&news_collector2.Category{},
	}

	for _, model := range dropModels {
		migrator.DropTable(model)
	}

	models := []interface{}{
		&news_collector2.Article{},
		&news_collector2.Category{},
	}

	migrateModels := make([]interface{}, 0)
	for _, model := range models {
		if !migrator.HasTable(model) {
			migrateModels = append(migrateModels, model)
		}
	}

	return db.AutoMigrate(migrateModels...)
}

func migrateCategories(r *repository.Repository) error {
	categories, err := readSources("sources.json")
	if err != nil {
		return err
	}
	return r.UpsertCategories(categories)
}

func readSources(filePath string) ([]*news_collector2.Category, error) {
	f, err := os.OpenFile(filePath, os.O_RDONLY, 0644)
	if err != nil {
		return nil, err
	}

	var data map[string][]*news_collector2.Category
	err = json.NewDecoder(f).Decode(&data)
	if err != nil {
		return nil, err
	}

	categories := make([]*news_collector2.Category, 0)
	for categoryName, categoryList := range data {
		for _, category := range categoryList {
			category.Name = categoryName
			categories = append(categories, category)
		}
	}

	return categories, nil
}
