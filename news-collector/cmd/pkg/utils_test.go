package pkg

import "testing"

func TestParseImageURL(t *testing.T) {
	results := ParseImageURL("<a href=\"https://vnexpress.net/sang-tao-mo-thuc-day-cac-y-tuong-khoi-nghiep-4402975.html\"><img src=\"https://i1-vnexpress.vnecdn.net/2021/12/14/anh-tung-5325-1639463106-16394-7631-7240-1639492229.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=vTNIM0w9MjELGyviSgVpxw\" ></a></br>Các chuyên gia cho rằng để các ý tưởng khởi nghiệp trở thành hiện thực cần liên kết, tạo cộng hưởng giữa nhà nước, startup, doanh nghiệp... để hình thành chuỗi giá trị.")
	t.Log(results)
}
