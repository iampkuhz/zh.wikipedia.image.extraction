package com.hz;

public enum tagType {

	// plain text
	PlainText,
	// <a>...</a>
	A, 
	// toPlainText like this: ".../..."
	Split,
	// contain annotation like this: "...(...)"
	Note,
	// default tags
	Tag;
}
