package com.example.madcamp_1st_week

class FriendItem (var _name: String, var _phone: String, var _info: String)
{
    var name:String = _name
        get() = this._name
        set(nickname: String) {
            field = nickname
        }
    var phone: String = _phone
        get() = this._phone
        set(phone: String) {
            field = phone
        }
    var info: String = _info
        get() = this._info
        set(info: String) {
            field = info
        }

}