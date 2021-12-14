package news_collector

import (
	"time"
)

type Category struct {
	Name    string    `json:"name"`
	Source  string    `json:"source"`
	Link    string    `json:"link" gorm:"index:category_link_IDX,unique"`
	LastRun time.Time `json:"lastRun"`
}

type Article struct {
	ID              int64      `json:"id"`
	Title           string     `json:"title,omitempty" gorm:"index"`
	Description     string     `json:"description,omitempty" gorm:"index"`
	Content         string     `json:"content,omitempty"`
	Link            string     `json:"link,omitempty" gorm:"index:source_link_category_unique_IDX,unique"`
	Published       string     `json:"published,omitempty"`
	PublishedParsed *time.Time `json:"publishedParsed,omitempty" gorm:"index"`
	Image           string     `json:"image,omitempty"`
	Category        string     `json:"category" gorm:"index:source_link_category_unique_IDX,unique"`
	Source          string     `json:"source" gorm:"index:source_link_category_unique_IDX,unique"`
}
