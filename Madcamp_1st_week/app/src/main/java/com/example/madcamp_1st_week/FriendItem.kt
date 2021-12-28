package com.example.madcamp_1st_week

class FriendItem (var _name: String, var _message: String)
{
    var name:String = _name
        get() = this.name
        set(nickname: String) {
            field = nickname
        }
    var message: String = _message
        get() = this.message
        set(msg: String) {
            field = msg
        }
}