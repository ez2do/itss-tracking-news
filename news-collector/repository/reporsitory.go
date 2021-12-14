package repository

import (
	"gorm.io/gorm"
	"gorm.io/gorm/clause"
	"tracking-news/news-collector/model"
)

type Repository struct {
	db *gorm.DB
}

func NewRepository(db *gorm.DB) *Repository {
	return &Repository{
		db: db,
	}
}

func (r *Repository) UpsertCategories(categories []*model.Category) error {
	return r.db.Model(&model.Category{}).
		Clauses(clause.OnConflict{
			Columns: []clause.Column{
				{Name: "link"},
			},
			DoUpdates: clause.AssignmentColumns([]string{"name", "source"}),
		}).
		Create(&categories).Error
}

func (r *Repository) GetAllCategories() ([]*model.Category, error) {
	categories := make([]*model.Category, 0)
	err := r.db.Model(&model.Category{}).Find(&categories).Error
	if err != nil {
		return nil, err
	}
	return categories, nil
}

func (r *Repository) UpsertArticles(articles []*model.Article) error {
	return r.db.Model(&model.Article{}).
		Clauses(clause.OnConflict{
			Columns: []clause.Column{
				{Name: "link"},
				{Name: "category"},
				{Name: "source"},
			},
			DoNothing: true,
		}).
		Create(&articles).Error
}
