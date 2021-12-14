package news_collector

import (
	"gorm.io/gorm"
	"gorm.io/gorm/clause"
)

type Repository struct {
	db *gorm.DB
}

func NewRepository(db *gorm.DB) *Repository {
	return &Repository{
		db: db,
	}
}

func (r *Repository) UpsertCategories(categories []*Category) error {
	return r.db.Model(&Category{}).
		Clauses(clause.OnConflict{
			Columns: []clause.Column{
				{Name: "link"},
			},
			DoUpdates: clause.AssignmentColumns([]string{"name", "source"}),
		}).
		Create(&categories).Error
}
