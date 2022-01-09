package news_collector

import (
	"gorm.io/gorm"
	"gorm.io/gorm/clause"
	"time"
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
	err := r.db.Model(article).Order("updated_at desc").First(article).Error

	if err != nil {
		if err == gorm.ErrRecordNotFound {
			return nil, ErrDataNotFound
		}
		return nil, err
	}

	return article, nil
}

type articleIdentity struct {
	ID int64 `json:"id"`
}

func (r *Repository) GetOldArticleIDs(before time.Time) ([]int64, error) {
	articleIdentities := make([]*articleIdentity, 0)
	err := r.db.Model(&Article{}).
		Select("id").
		Where("updated_at < ?", before).
		Find(&articleIdentities).Error
	if err != nil {
		return nil, err
	}
	ids := make([]int64, 0)
	for _, a := range articleIdentities {
		ids = append(ids, a.ID)
	}

	return ids, nil
}

func (r *Repository) GetWatchLaterIDs() ([]int64, error) {
	articleIdentities := make([]*articleIdentity, 0)
	err := r.db.Model(&ArticleWatchLater{}).
		Select("article_id as id").
		Find(&articleIdentities).Error
	if err != nil {
		return nil, err
	}
	ids := make([]int64, 0)
	for _, a := range articleIdentities {
		ids = append(ids, a.ID)
	}

	return ids, nil
}

func (r *Repository) DeleteArticles(ids []int64) error {
	return r.db.Model(&Article{}).
		Where("id in ?", ids).Delete(&Article{}).Error
}

func (r *Repository) DeleteHistories(ids []int64) error {
	return r.db.Model(&ArticleHistory{}).
		Where("article_id in ?", ids).Delete(&ArticleHistory{}).Error
}
