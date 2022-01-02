package main

import (
	"encoding/json"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"os"
	newscollector "tracking-news/news-collector"
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

	r := newscollector.NewRepository(db)
	err = migrateCategories(r)
	if err != nil {
		log.Panicln(err)
	}
}

func migrateSchemas(db *gorm.DB) error {
	migrator := db.Migrator()

	dropModels := []interface{}{
		//&newscollector.Article{},
		&newscollector.Category{},
	}

	for _, model := range dropModels {
		migrator.DropTable(model)
	}

	models := []interface{}{
		&newscollector.Article{},
		&newscollector.Category{},
		&newscollector.Setting{},
	}

	migrateModels := make([]interface{}, 0)
	for _, model := range models {
		if !migrator.HasTable(model) {
			migrateModels = append(migrateModels, model)
		}
	}

	return db.AutoMigrate(migrateModels...)
}

func migrateCategories(r *newscollector.Repository) error {
	categories, err := readSources("sources.json")
	if err != nil {
		return err
	}
	return r.UpsertCategories(categories)
}

func readSources(filePath string) ([]*newscollector.Category, error) {
	f, err := os.OpenFile(filePath, os.O_RDONLY, 0644)
	if err != nil {
		return nil, err
	}

	var data map[string][]*newscollector.Category
	err = json.NewDecoder(f).Decode(&data)
	if err != nil {
		return nil, err
	}

	categories := make([]*newscollector.Category, 0)
	for categoryName, categoryList := range data {
		for _, category := range categoryList {
			category.Name = categoryName
			categories = append(categories, category)
		}
	}

	return categories, nil
}
