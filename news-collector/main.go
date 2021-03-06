package main

import (
	"github.com/jasonlvhit/gocron"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	"time"
	"tracking-news/cmd"

	"tracking-news/cmd/logger"
)

func main() {
	db, err := gorm.Open(sqlite.Open("news.db"), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	err = cmd.MigrateSchemas(db)
	if err != nil {
		log.Fatal(err)
	}
	repo := cmd.NewRepository(db)
	collector := cmd.NewCollector(repo)

	categories, err := repo.GetAllCategories()
	if err != nil {
		log.Fatal(err)
	}
	if len(categories) == 0 {
		err = cmd.MigrateCategories(repo)
		if err != nil {
			log.Fatal(err)
		}
	}
	err = cmd.MigrateSettings(repo)
	if err != nil {
		log.Fatal(err)
	}

	logger.Info("Start job")

	err = gocron.Every(3).Hours().From(gocron.NextTick()).Do(func() {
		updateIntervalHour := getUpdateIntervalHour(repo)
		lastRun := getLastRun(repo)

		err = deleteOldArticles(repo)
		if err != nil {
			logger.Errorw("Error when delete articles", "error", err)
		}

		if time.Now().Add(30*time.Minute).Sub(lastRun) > time.Hour*time.Duration(updateIntervalHour) {
			logger.Infow("Start collecting", "interval hour", updateIntervalHour,
				"last run", lastRun.Format(cmd.DefaultDateTimeFormat))
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
	if err != nil {
		logger.Errorw("Error when schedule job", "error", err)
	}

	<-gocron.Start()
}

func deleteOldArticles(repo *cmd.Repository) error {
	deleteBefore := getDeleteBefore(repo)
	oldIDs, err := repo.GetOldArticleIDs(deleteBefore)
	if err != nil {
		return err
	}
	watchLaterIDs, err := repo.GetWatchLaterIDs()
	if err != nil {
		return err
	}
	watchLaterMap := make(map[int64]bool)
	for _, w := range watchLaterIDs {
		watchLaterMap[w] = true
	}
	deleteIDs := make([]int64, 0)
	for _, o := range oldIDs {
		if watchLaterMap[o] {
			deleteIDs = append(deleteIDs, o)
		}
	}
	err = repo.DeleteHistories(deleteIDs)
	if err != nil {
		return err
	}
	return repo.DeleteHistories(deleteIDs)
}

func getDeleteBefore(repo *cmd.Repository) (deleteBefore time.Time) {
	deleteIntervalSetting, err := repo.GetSettingByName("delete_interval_day")
	if err != nil {
		logger.Errorw("Error when get update interval hour", "error", err)
		return time.Now().AddDate(0, 0, -30)
	}

	if deleteIntervalSetting.NumberValue > 0 {
		return time.Now().AddDate(0, 0, int(-deleteIntervalSetting.NumberValue))
	}

	return
}

func getUpdateIntervalHour(repo *cmd.Repository) uint64 {
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

func getLastRun(repo *cmd.Repository) (lastRun time.Time) {
	latestArticle, err := repo.GetLatestArticle()
	if err != nil {
		if err != cmd.ErrDataNotFound {
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
