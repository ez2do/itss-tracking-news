package main

import (
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"tracking-news/cmd"
)

func main() {
	db, err := gorm.Open(sqlite.Open("news.db"), &gorm.Config{})
	if err != nil {
		log.Panicln(err)
	}

	err = cmd.MigrateSchemas(db)
	if err != nil {
		log.Panicln(err)
	}

	r := cmd.NewRepository(db)
	err = cmd.MigrateCategories(r)
	if err != nil {
		log.Panicln(err)
	}
}
