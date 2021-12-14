package pkg

import "regexp"

func ParseImageURL(text string) string {
	r, _ := regexp.Compile("<img [^>]*src=\"([^\"]+)\"[^>]*>")
	results := r.FindAllStringSubmatch(text, 1)
	if len(results) < 1 {
		return ""
	}
	if len(results[0]) < 2 {
		return ""
	}
	return results[0][1]
}
