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

func (r *Repository) GetAllCategories() ([]*Category, error) {
	categories := make([]*Category, 0)
	err := r.db.Model(&Category{}).Find(&categories).Error
	if err != nil {
		return nil, err
	}
	return categories, nil
}

func (r *Repository) UpsertArticles(articles []*Article) error {
	return r.db.Model(&Article{}).
		Clauses(clause.OnConflict{
			Columns: []clause.Column{
				{Name: "link"},
				{Name: "category"},
				{Name: "source"},
			},
			DoUpdates: clause.AssignmentColumns([]string{"updated_at"}),
		}).
		Create(&articles).Error
}

func (r *Repository) GetSettingByName(name string) (*Setting, error) {
	setting := &Setting{}
	err := r.db.Model(setting).
		Where("name = ?", name).Take(setting).Error

	if err != nil {
		if err == gorm.ErrRecordNotFound {
			return nil, ErrDataNotFound
		}
		return nil, err
	}

	return setting, nil
}

func (r *Repository) GetLatestArticle() (*Article, error) {
	article := &Article{}
	err := r.db.Model(article).Order("created_at desc").First(article).Error

	if err != nil {
		if err == gorm.ErrRecordNotFound {
			return nil, ErrDataNotFound
		}
		return nil, err
	}

	return article, nil
}
