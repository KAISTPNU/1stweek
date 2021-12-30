package com.example.madcamp_1st_week

class FeedItem {
    var resourceID: Int = 0
    var description: String = ""

    constructor(resourceID: Int, description: String) {
        this.resourceID = resourceID
        this.description = description;
    }
}