package model

import (
	"database/sql/driver"
	"encoding/json"
	"errors"
	"fmt"
	"time"
)

type ExtraData map[string]string

type Category struct {
	Name    string    `json:"name"`
	Source  string    `json:"source"`
	Link    string    `json:"link" gorm:"index:category_link_IDX,unique"`
	LastRun time.Time `json:"last_run"`
}

type Article struct {
	ID              int64      `json:"id"`
	Title           string     `json:"title,omitempty" gorm:"index"`
	Description     string     `json:"description,omitempty" gorm:"index"`
	Link            string     `json:"link,omitempty" gorm:"index:source_link_category_unique_IDX,unique"`
	Published       string     `json:"published,omitempty"`
	PublishedParsed *time.Time `json:"publishedParsed,omitempty" gorm:"index"`
	Image           string     `json:"image,omitempty"`
	ExtraData       *ExtraData `json:"extra_data"`
	Category        string     `json:"category" gorm:"index:source_link_category_unique_IDX,unique"`
	Source          string     `json:"source" gorm:"index:source_link_category_unique_IDX,unique"`
	CreatedAt       time.Time  `json:"created_at"`
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
