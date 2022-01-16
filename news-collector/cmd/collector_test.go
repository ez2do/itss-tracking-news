package cmd

import (
	"testing"
)

func setupCollector() *Collector {
	r := setupRepo()
	return NewCollector(r)
}

func TestCollector_CollectAll(t *testing.T) {
	c := setupCollector()
	categories, err := c.Repository.GetAllCategories()
	if err != nil {
		t.Error(err)
		return
	}
	err = c.CollectAll(categories)
	if err != nil {
		t.Error(err)
		return
	}
}

func TestCollector_CollectAll2(t *testing.T) {
	c := setupCollector()
	categories, err := c.Repository.GetAllCategories()
	if err != nil {
		t.Error(err)
		return
	}
	err = c.CollectAll(categories[:2])
	if err != nil {
		t.Error(err)
		return
	}
}

func TestCollector_extractContent(t *testing.T) {
	c := setupCollector()
	s := c.extractContent("<a href=\"https://vnexpress.net/ba-chang-trai-duoc-lay-vo-nho-mac-ket-4408069.html\"><img src=\"https://i1-giadinh.vnecdn.net/2021/12/25/a9-6030-1633434177-1640407652-3167-1640415288.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=3MiAkIb9t0LQ8X4Uu1mWTg\" ></a></br>Đại dịch làm đảo lộn cuộc sống hàng triệu người, song cũng vô tình tạo cơ hội cho Evan Farfan, Thanh Ngọc và Vĩnh Đăng lấy được vợ nhanh hơn.")
	t.Log(s)
}
