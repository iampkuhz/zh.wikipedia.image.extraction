# zh.wikipedia.image.extraction


## 项目概述

本程序可以自动抓取中文维基百科信息框中的图片链接(以及图片大小), 比如演员[张家辉](http://zh.wikipedia.org/wiki/%E5%BC%B5%E5%AE%B6%E8%BC%9D)的维基百科主页

![张家辉](https://dn-iampkuhz.qbox.me/github/zh.wikipedia.image.extraction/zhangjiahui.png)

在搜索结果WikiIMG文件中对应一条记录:
<br />
> 472824  张家辉  upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Cheung_Ka_Fai_2010.jpg/240px-Cheung_Ka_Fai_2010.jpg   240     322

表示张家辉的编号是472824, 维基百科中使用的链接如上, 图片的宽240像素, 高322像素
![zjh](upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Cheung_Ka_Fai_2010.jpg/240px-Cheung_Ka_Fai_2010.jpg)


## 使用说明

1. 可以直接使用我已经抽取好的结果[WikiIMG](https://github.com/iampkuhz/zh.wikipedia.image.extraction/blob/master/WikiIMG)
2. 或者运行项目根目录下面的[zh.wikipedia.img.extraction.jar](https://github.com/iampkuhz/zh.wikipedia.image.extraction/blob/master/zh.wikipedia.img.extraction.jar)

```
para format:
java ZhwikiIMGExtractor.jar htmlFolderPath status dataFolderPath outputFilePath appendFile
	status = 0, 1 or 2
		0: use htmlPages inside Folder, 2 layer, e. g. "htmlFolderPath/103/472824_張家輝.html"
		1: extract htmlPages online, without saving the htmlPages
		2: extract htmlPages online, as well as saving the htmlPages under htmlFolderPath

	appendFile = 0 or 1
		0: delete origin output file if exist
		1: append tail to existing output file
```

输出:
> Hello!<br />
> this program aims at automatically extracting entities' images inside Infoboxes in chinese wikipedia. the Infobox list is already filtered by myself using various rules. I hope you can enjoying using it!<br />
> hanzhe(iampkuhz.cn)<br />
> infoboxIdList size:378269<br />
> NoInfoboxIdList size:1<br />
> Id2TitsMap.size:977570<br />
> InfoboxNameList size:7228<br />
> TitsIdMap.size:1348673<br />
> Extract:5 folders passed, 3605 pages passed, 845 img url extracted!	 cost:57sec<br />
> Extract:10 folders passed, 7293 pages passed, 1310 img url extracted!	 cost:1min29sec<br />
> Extract:15 folders passed, 10898 pages passed, 1695 img url extracted!	 cost:2min2sec<br />
> Extract:20 folders passed, 14503 pages passed, 2908 img url extracted!	 cost:2min37sec<br />
> Extract:25 folders passed, 18108 pages passed, 3827 img url extracted!	 cost:3min13sec<br />
> Extract:30 folders passed, 21713 pages passed, 4830 img url extracted!	 cost:3min48sec<br />
> Extract:35 folders passed, 25318 pages passed, 5803 img url extracted!	 cost:4min26sec<br />
> Extract:40 folders passed, 29002 pages passed, 6306 img url extracted!	 cost:4min59sec<br />
> ...






## 备份

原始代码备份在:[全部文件 > 实验室数据 > 代码存档 > 维基百科infobox](http://pan.baidu.com/disk/home#path=%252F%25E5%25AE%259E%25E9%25AA%258C%25E5%25AE%25A4%25E6%2595%25B0%25E6%258D%25AE%252F%25E4%25BB%25A3%25E7%25A0%2581%25E5%25AD%2598%25E6%25A1%25A3%252F%25E7%25BB%25B4%25E5%259F%25BA%25E7%2599%25BE%25E7%25A7%2591infobox%25E5%259B%25BE%25E7%2589%2587%25E9%2593%25BE%25E6%258E%25A5%25E6%258A%2593%25E5%258F%2596%25E7%25A8%258B%25E5%25BA%258F)


