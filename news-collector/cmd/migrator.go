package cmd

import (
	"encoding/json"
	"gorm.io/gorm"
	"os"
)

func MigrateSchemas(db *gorm.DB) error {
	migrator := db.Migrator()

	//dropModels := []interface{}{
	//	//&newscollector.Article{},
	//	//&newscollector.Category{},
	//	&ArticleHistory{},
	//	&ArticleWatchLater{},
	//}
	//
	//for _, model := range dropModels {
	//	migrator.DropTable(model)
	//}

	models := []interface{}{
		&Article{},
		&Category{},
		&Setting{},
		&ArticleHistory{},
		&ArticleWatchLater{},
	}

	migrateModels := make([]interface{}, 0)
	for _, model := range models {
		if !migrator.HasTable(model) {
			migrateModels = append(migrateModels, model)
		}
	}

	return db.AutoMigrate(migrateModels...)
}

func MigrateCategories(r *Repository) error {
	categories, err := readSources("sources.json")
	if err != nil {
		return err
	}
	return r.UpsertCategories(categories)
}

func MigrateSettings(r *Repository) error {
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
	return r.InsertSettings(settings)
}

func readSources(filePath string) ([]*Category, error) {
	f, err := os.OpenFile(filePath, os.O_RDONLY, 0644)
	if err != nil {
		return nil, err
	}

	var data map[string][]*Category
	err = json.NewDecoder(f).Decode(&data)
	if err != nil {
		return nil, err
	}

	categories := make([]*Category, 0)
	for categoryName, categoryList := range data {
		for _, category := range categoryList {
			category.Name = categoryName
			categories = append(categories, category)
		}
	}

	return categories, nil
}
