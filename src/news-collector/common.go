package news_collector

import (
	"encoding/json"
	"os"
)

func ReadSources(filePath string) ([]*Category, error) {
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
