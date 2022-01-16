package logger

import (
	"go.uber.org/zap"
	"go.uber.org/zap/zapcore"
	"os"
)

var (
	sugar *zap.SugaredLogger
)

func init() {
	conf := zap.NewDevelopmentEncoderConfig()
	encoder := zapcore.NewConsoleEncoder(conf)

	f, err := os.OpenFile("logs", os.O_CREATE|os.O_APPEND|os.O_WRONLY, 0644)
	if err != nil {
		panic(err)
	}
	core := zapcore.NewTee(
		zapcore.NewCore(encoder, zapcore.AddSync(f), zap.InfoLevel),
		zapcore.NewCore(encoder, zapcore.AddSync(os.Stdout), zap.InfoLevel),
	)
	logger := zap.New(core)
	sugar = logger.Sugar()
}

func Info(args ...interface{}) {
	sugar.Info(args...)
}

func Debug(args ...interface{}) {
	sugar.Debug(args...)
}

func Error(args ...interface{}) {
	sugar.Error(args...)
}

func Infof(template string, args ...interface{}) {
	sugar.Infof(template, args...)
}

func Debugf(template string, args ...interface{}) {
	sugar.Debugf(template, args...)
}

func Errorf(template string, args ...interface{}) {
	sugar.Errorf(template, args...)
}

func Infow(msg string, keyAndValues ...interface{}) {
	sugar.Infow(msg, keyAndValues...)
}

func Debugw(msg string, keyAndValues ...interface{}) {
	sugar.Debugw(msg, keyAndValues...)
}

func Errorw(msg string, keyAndValues ...interface{}) {
	sugar.Errorw(msg, keyAndValues...)
}
