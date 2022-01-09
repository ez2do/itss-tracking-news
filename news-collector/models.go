package news_collector

import (
	"database/sql/driver"
	"encoding/json"
	"errors"
	"fmt"
	"time"
)

type ExtraData map[string]string

const DefaultDateTimeFormat = "2006-01-02 15:04:05"

var (
	ErrDataNotFound = errors.New("data not found")
)

type Category struct {
	ID     int64  `json:"id"`
	Name   string `json:"name"`
	Source string `json:"source"`
	Link   string `json:"link" gorm:"index:category_link_IDX,unique"`
}

type Article struct {
	ID              int64      `json:"id"`
	Title           string     `json:"title" gorm:"index"`
	Description     string     `json:"description" gorm:"index"`
	Link            string     `json:"link" gorm:"index:source_link_category_unique_IDX,unique"`
	Published       string     `json:"published"`
	PublishedParsed time.Time  `json:"published_parsed" gorm:"index"`
	Image           string     `json:"image"`
	ExtraData       *ExtraData `json:"extra_data"`
	Category        string     `json:"category" gorm:"index:source_link_category_unique_IDX,unique"`
	Source          string     `json:"source" gorm:"index:source_link_category_unique_IDX,unique"`
	UpdatedAt       time.Time  `json:"updated_at"`
}

type Setting struct {
	Name        string `json:"name" gorm:"primaryKey"`
	NumberValue int64  `json:"number_value"`
	TextValue   string `json:"text_value"`
}

type ArticleHistory struct {
	ArticleID int64     `json:"article_id" gorm:"primaryKey"`
	CreatedAt time.Time `json:"created_at" gorm:"default:current_timestamp"`
}

type ArticleWatchLater struct {
	ArticleID int64     `json:"article_id" gorm:"primaryKey"`
	CreatedAt time.Time `json:"created_at" gorm:"default:current_timestamp"`
}

func (e *ExtraData) Scan(value interface{}) error {
	bytes, ok := value.([]byte)
	if !ok {
		return errors.New("extra data error")
	}

	result := &ExtraData{}
	err := json.Unmarshal(bytes, result)
	if err != nil {
		return fmt.Errorf("extra data got error %v", err)
	}

	*e = *result
	return err
}

func (e ExtraData) Value() (driver.Value, error) {
	return json.Marshal(e)
}
