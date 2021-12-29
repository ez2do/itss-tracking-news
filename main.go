package main

import (
	"github.com/jasonlvhit/gocron"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"time"
	newscollector "tracking-news/news-collector"
	"tracking-news/news-collector/logger"
)

func main() {
	db, err := gorm.Open(sqlite.Open("news.db"), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	repo := newscollector.NewRepository(db)
	collector := newscollector.NewCollector(repo)

	logger.Info("Start job")

	gocron.Every(3).Hours().From(gocron.NextTick()).Do(func() {
		updateIntervalHour := getUpdateIntervalHour(repo)
		lastRun := getLastRun(repo)

		if time.Now().Add(30*time.Minute).Sub(lastRun) > time.Hour*time.Duration(updateIntervalHour) {
			logger.Infow("Start collecting", "interval hour", updateIntervalHour,
				"last run", lastRun.Format(newscollector.DefaultDateTimeFormat))
			categories, err := repo.GetAllCategories()
			if err != nil {
				logger.Errorw("Error get categories", "error", err)
			}

			err = runWithRetry(func() error {
				return collector.CollectAll(categories)
			}, 5)
			if err != nil {
				logger.Errorw("Error when collect articles", "error", err)
			}
			logger.Infof("Finish collecting")
		}
	})

	<-gocron.Start()
}

func getUpdateIntervalHour(repo *newscollector.Repository) uint64 {
	updateIntervalHour := uint64(3)
	updateIntervalSetting, err := repo.GetSettingByName("update_interval_hour")
	if err != nil {
		logger.Errorw("Error when get update interval hour", "error", err)
		return updateIntervalHour
	}

	if updateIntervalSetting.NumberValue > 0 {
		updateIntervalHour = uint64(updateIntervalSetting.NumberValue)
	}

	return updateIntervalHour
}

func getLastRun(repo *newscollector.Repository) (lastRun time.Time) {
	latestArticle, err := repo.GetLatestArticle()
	if err != nil {
		if err != newscollector.ErrDataNotFound {
			logger.Errorw("Error when get latest article", "error", err)
		}
		return
	}
	return latestArticle.UpdatedAt
}

func runWithRetry(f func() error, retryTimes int) (err error) {
	for i := 0; i < retryTimes; i++ {
		err = f()
		if err == nil {
			return nil
		}
		time.Sleep(5 * time.Second)
	}
	return
}
