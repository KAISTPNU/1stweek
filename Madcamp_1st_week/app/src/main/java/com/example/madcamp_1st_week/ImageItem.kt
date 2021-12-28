package com.example.madcamp_1st_week

class ImageItem {
    private var resourceID: Int = 0
    private var description: String = ""
    constructor(resourceID: Int, description: String) {
        this.resourceID = resourceID
        this.description = description;
    }

    public fun getResourceID(): Int { return resourceID; }
    public fun setResourceID(resourceID: Int) { this.resourceID = resourceID }

    public fun getDescription(): String { return description }
    public fun setDescription(description: String) { this.description = description }


}